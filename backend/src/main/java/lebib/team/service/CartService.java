package lebib.team.service;

import lebib.team.dto.CartDto;
import lebib.team.entity.CartEntity;
import lebib.team.entity.CartItemEntity;
import lebib.team.entity.ProductEntity;
import lebib.team.entity.UserEntity;
import lebib.team.exception.CartNotFoundException;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.exception.UserNotFoundException;
import lebib.team.mapper.CartMapper;
import lebib.team.repository.CartRepository;
import lebib.team.repository.ProductRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(
            CartRepository cartRepository,
            CartMapper cartMapper,
            UserRepository userRepository,
            ProductRepository productRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartDto getCartByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.byId(userId));

        return cartMapper.toDto(cartRepository.findByUserId(userId)
                .orElseThrow(() -> CartNotFoundException.byUserId(userId)));
    }

    public CartDto getCartById(Long id) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> CartNotFoundException.byId(id));
        return cartMapper.toDto(cart);
    }

    @Transactional
    public CartDto createCart(CartDto cartDto) {
        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDto)));
    }

    public CartEntity createCartForUser(UserEntity user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUser(user);
                    return cart;
                });
    }

    @Transactional
    public void deleteCartByUserId(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.byId(id));

        CartEntity cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> CartNotFoundException.byUserId(id));

        cartRepository.delete(cart);
    }

    @Transactional
    public CartDto addProductToCart(Long userId, Long productId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> CartNotFoundException.byUserId(userId));

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        CartItemEntity existingItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productEntity.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setCart(cart);
            newItem.setProduct(productEntity);
            newItem.setQuantity(1);
            cart.getCartItems().add(newItem);
        }
        cart.setTotalAmount(cart.getCartItems()
                .stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum());

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Transactional
    public CartDto removeProductFromCart(Long userId, Long productId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> CartNotFoundException.byUserId(userId));

        cart.getCartItems()
                .removeIf(item -> item.getProduct().getId().equals(productId));

        recalculateTotal(cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Transactional
    public CartDto clearCartByUserId(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> CartNotFoundException.byUserId(userId));

        cart.getCartItems().clear();
        cart.setTotalAmount(0.0);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Transactional
    public CartDto updateQuantityCartItem(Long userId, Long productId, Integer quantity) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> CartNotFoundException.byUserId(userId));

        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        recalculateTotal(cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    private void recalculateTotal(CartEntity cart) {
        Double total = cart.getCartItems() == null ? 0.0 :
                cart.getCartItems()
                        .stream()
                        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                        .sum();
        cart.setTotalAmount(total);
    }
}
