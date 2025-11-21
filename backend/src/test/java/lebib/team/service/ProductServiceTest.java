package lebib.team.service;

import lebib.team.dto.ProductDto;
import lebib.team.dto.ProductImageDto;
import lebib.team.entity.CategoryEntity;
import lebib.team.entity.InventoryEntity;
import lebib.team.entity.ProductEntity;
import lebib.team.entity.ProductImageEntity;
import lebib.team.exception.CategoryNotFoundException;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.mapper.ProductImageMapper;
import lebib.team.mapper.ProductMapper;
import lebib.team.repository.CategoryRepository;
import lebib.team.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductImageMapper productImageMapper;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById_returnsDto() {
        // given
        Long id = 1L;
        ProductEntity entity = new ProductEntity();
        ProductDto dto = new ProductDto();

        when(productRepository.findById(id)).thenReturn(Optional.of(entity));
        when(productMapper.toDto(entity)).thenReturn(dto);

        // when
        ProductDto result = productService.getProductById(id);

        // then
        assertNotNull(result);
        verify(productRepository).findById(id);
        verify(productMapper).toDto(entity);
    }

    @Test
    void testGetProductById_notFound_throwsException() {
        // given
        Long id = 999L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void testCreateProduct_success() {
        // given
        ProductDto dto = new ProductDto();
        dto.setCategoryId(10L);

        CategoryEntity category = new CategoryEntity();
        ProductEntity entity = new ProductEntity();
        ProductEntity savedEntity = new ProductEntity();
        InventoryEntity inventory = new InventoryEntity();
        ProductDto resultDto = new ProductDto();

        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(dto)).thenReturn(entity);
        when(inventoryService.createInventory(entity, 5)).thenReturn(inventory);
        when(productRepository.save(entity)).thenReturn(savedEntity);
        when(productMapper.toDto(savedEntity)).thenReturn(resultDto);

        // when
        ProductDto result = productService.createProduct(dto, 5);

        // then
        assertNotNull(result);
        verify(categoryRepository).findById(10L);
        verify(productRepository).save(entity);
        verify(inventoryService).createInventory(entity, 5);
    }

    @Test
    void testCreateProduct_categoryNotFound() {
        ProductDto dto = new ProductDto();
        dto.setCategoryId(100L);

        when(categoryRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.createProduct(dto, 10));
    }

    @Test
    void testUpdateProductById_success() {
        Long id = 1L;

        ProductDto dto = new ProductDto();
        dto.setName("new name");
        dto.setDescription("new desc");
        dto.setPrice(99.99);
        dto.setCategoryId(5L);
        dto.setImages(List.of(new ProductImageDto()));

        ProductEntity existing = new ProductEntity();
        existing.setImages(new ArrayList<>());

        CategoryEntity category = new CategoryEntity();
        ProductImageEntity imageEntity = new ProductImageEntity();
        ProductEntity saved = new ProductEntity();
        ProductDto resultDto = new ProductDto();

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(5L)).thenReturn(Optional.of(category));
        when(productImageMapper.toEntity(any())).thenReturn(imageEntity);
        when(productRepository.save(existing)).thenReturn(saved);
        when(productMapper.toDto(saved)).thenReturn(resultDto);

        // when
        ProductDto result = productService.updateProductById(id, dto);

        // then
        assertNotNull(result);
        assertEquals("new name", existing.getName());
        assertEquals("new desc", existing.getDescription());
        assertEquals(99.99, existing.getPrice());
        verify(productRepository).save(existing);
    }

    @Test
    void testUpdateProductById_productNotFound() {
        when(productRepository.findById(55L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductById(55L, new ProductDto()));
    }

    @Test
    void testUpdateProductById_categoryNotFound() {
        ProductEntity entity = new ProductEntity();
        ProductDto dto = new ProductDto();
        dto.setCategoryId(777L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(categoryRepository.findById(777L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.updateProductById(1L, dto));
    }

    @Test
    void testUpdateProductCategory_success() {
        Long prodId = 1L;
        Long catId = 2L;

        ProductEntity product = new ProductEntity();
        CategoryEntity category = new CategoryEntity();
        ProductEntity saved = new ProductEntity();
        ProductDto dto = new ProductDto();

        when(productRepository.findById(prodId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(catId)).thenReturn(Optional.of(category));
        when(productRepository.save(product)).thenReturn(saved);
        when(productMapper.toDto(saved)).thenReturn(dto);

        ProductDto result = productService.updateProductCategory(catId, prodId);

        assertNotNull(result);
        verify(productRepository).save(product);
    }

    @Test
    void testUpdateProductCategory_productNotFound() {
        when(productRepository.findById(222L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductCategory(1L, 222L));
    }

    @Test
    void testUpdateProductCategory_categoryNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(new ProductEntity()));
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> productService.updateProductCategory(999L, 1L));
    }
}
