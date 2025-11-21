package lebib.team.service;

import lebib.team.dto.CartDto;
import lebib.team.entity.*;
import lebib.team.exception.CartNotFoundException;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.exception.UserNotFoundException;
import lebib.team.mapper.CartMapper;
import lebib.team.repository.CartRepository;
import lebib.team.repository.ProductRepository;
import lebib.team.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCartByUserId_success() {
        Long userId = 1L;

        UserEntity user = new UserEntity();
        CartEntity cart = new CartEntity();
        CartDto cartDto = new CartDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartMapper.toDto(cart)).thenReturn(cartDto);

        CartDto result = cartService.getCartByUserId(userId);

        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(cartRepository).findByUserId(userId);
        verify(cartMapper).toDto(cart);
    }

    @Test
    void getCartByUserId_userNotFound() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cartService.getCartByUserId(10L));
    }

    @Test
    void getCartByUserId_cartNotFound() {
        Long userId = 1L;
        UserEntity user = new UserEntity();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.getCartByUserId(userId));
    }

    @Test
    void getCartById_success() {
        CartEntity cart = new CartEntity();
        CartDto dto = new CartDto();

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartMapper.toDto(cart)).thenReturn(dto);

        CartDto result = cartService.getCartById(1L);

        assertNotNull(result);
        verify(cartRepository).findById(1L);
    }

    @Test
    void getCartById_notFound() {
        when(cartRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> cartService.getCartById(99L));
    }

    @Test
    void createCart_success() {
        CartDto dto = new CartDto();
        CartEntity entity = new CartEntity();
        CartEntity saved = new CartEntity();
        CartDto savedDto = new CartDto();

        when(cartMapper.toEntity(dto)).thenReturn(entity);
        when(cartRepository.save(entity)).thenReturn(saved);
        when(cartMapper.toDto(saved)).thenReturn(savedDto);

        CartDto result = cartService.createCart(dto);

        assertNotNull(result);
        verify(cartMapper).toEntity(dto);
        verify(cartRepository).save(entity);
    }

    @Test
    void createCartForUser_existingCart() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CartEntity existingCart = new CartEntity();

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(existingCart));

        CartEntity result = cartService.createCartForUser(user);

        assertEquals(existingCart, result);
        verify(cartRepository).findByUserId(1L);
    }

    @Test
    void createCartForUser_newCart() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        CartEntity result = cartService.createCartForUser(user);

        assertEquals(user, result.getUser());
        assertTrue(result.getCartItems() == null || result.getCartItems().isEmpty());
    }

    @Test
    void deleteCartByUserId_success() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CartEntity cart = new CartEntity();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        cartService.deleteCartByUserId(1L);

        verify(cartRepository).delete(cart);
    }

    @Test
    void deleteCartByUserId_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> cartService.deleteCartByUserId(1L));
    }

    @Test
    void deleteCartByUserId_cartNotFound() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.deleteCartByUserId(1L));
    }

    @Test
    void addProductToCart_addNewItem() {
        Long userId = 1L;
        Long productId = 5L;

        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>());

        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setPrice(100.0);

        CartDto outputDto = new CartDto();

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(outputDto);

        CartDto result = cartService.addProductToCart(userId, productId);

        assertNotNull(result);
        assertEquals(1, cart.getCartItems().size());
        assertEquals(100.0, cart.getTotalAmount());
    }

    @Test
    void addProductToCart_incrementExistingItem() {
        Long userId = 1L;
        Long productId = 5L;

        ProductEntity product = new ProductEntity();
        product.setId(productId);
        product.setPrice(50.0);

        CartItemEntity item = new CartItemEntity();
        item.setProduct(product);
        item.setQuantity(1);

        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>(List.of(item)));

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(new CartDto());

        cartService.addProductToCart(userId, productId);

        assertEquals(2, item.getQuantity());
        assertEquals(100.0, cart.getTotalAmount());
    }

    @Test
    void addProductToCart_cartNotFound() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundException.class, () -> cartService.addProductToCart(1L, 5L));
    }

    @Test
    void addProductToCart_productNotFound() {
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>());

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartService.addProductToCart(1L, 99L));
    }

    @Test
    void removeProductFromCart_success() {
        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setPrice(100.0);

        CartItemEntity item = new CartItemEntity();
        item.setProduct(product);
        item.setQuantity(1);

        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>(List.of(item)));

        CartDto dto = new CartDto();

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(dto);

        cartService.removeProductFromCart(1L, 10L);

        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    void clearCartByUserId_success() {
        CartEntity cart = new CartEntity();
        cart.setCartItems(new ArrayList<>(List.of(new CartItemEntity())));
        cart.setTotalAmount(500.0);

        CartDto dto = new CartDto();

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(dto);

        CartDto result = cartService.clearCartByUserId(1L);

        assertEquals(0.0, cart.getTotalAmount());
        assertTrue(cart.getCartItems().isEmpty());
        assertNotNull(result);
    }

    @Test
    void updateQuantityCartItem_success() {
        ProductEntity product = new ProductEntity();
        product.setId(3L);
        product.setPrice(50.0);

        CartItemEntity item = new CartItemEntity();
        item.setProduct(product);
        item.setQuantity(1);

        CartEntity cart = new CartEntity();
        cart.setCartItems(List.of(item));

        CartDto dto = new CartDto();

        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(dto);

        cartService.updateQuantityCartItem(1L, 3L, 5);

        assertEquals(5, item.getQuantity());
        assertEquals(250.0, cart.getTotalAmount());
    }
}
