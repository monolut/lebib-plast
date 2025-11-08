package lebib.team.service;

import lebib.team.config.S3Config;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductImageMapper productImageMapper;
    private final InventoryService inventoryService;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            ProductMapper productMapper,
            InventoryService inventoryService,
            CategoryRepository categoryRepository,
            ProductImageMapper productImageMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.productImageMapper = productImageMapper;
        this.inventoryService = inventoryService;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDto(product);
    }

    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository
                .findByCategoryName(category)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<ProductDto> getProductsByName(String name) {
        return productRepository
                .findByName(name)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto, Integer quantity) {
        Long categoryId = productDto.getCategoryId();

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        ProductEntity productEntity = productMapper.toEntity(productDto);
        InventoryEntity inventoryEntity = inventoryService.createInventory(productEntity, quantity);

        productEntity.setCategory(categoryEntity);
        productEntity.setInventory(inventoryEntity);

        return productMapper.toDto(productRepository.save(productEntity));
    }

    @Transactional
    public void deleteProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        ProductEntity productEntityToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        productEntityToUpdate.setName(productDto.getName());
        productEntityToUpdate.setDescription(productDto.getDescription());
        productEntityToUpdate.setPrice(productDto.getPrice());
        productEntityToUpdate.setCategory(
                categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryId()))
        );

        List<ProductImageDto> productImagesDtos = productDto.getImages();

        if (productImagesDtos != null && !productImagesDtos.isEmpty()) {
            List<ProductImageEntity> productImagesEntities = productImagesDtos
                    .stream()
                    .map(productImageMapper::toEntity)
                    .toList();

            productEntityToUpdate.getImages().clear();
            productEntityToUpdate.getImages().addAll(productImagesEntities);
        }

        return productMapper.toDto(productRepository.save(productEntityToUpdate));
    }

    @Transactional
    public ProductDto updateProductCategory(Long categoryId, Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        productEntity.setCategory(categoryEntity);
        return productMapper.toDto(productRepository.save(productEntity));
    }
}
