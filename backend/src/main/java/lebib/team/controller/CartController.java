package lebib.team.controller;

import lebib.team.dto.CartDto;
import lebib.team.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/carts")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @PutMapping("/users/{userId}/product/{productId}")
    public ResponseEntity<CartDto> addProductToCart(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addProductToCart(userId, productId));
    }

    @PutMapping("/users/{userId}/cart/items/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productId));
    }

    @DeleteMapping("/users/{userId}/cart/items")
    public ResponseEntity<CartDto> clearCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.clearCartByUserId(userId));
    }

    @PutMapping("/users/{userId}/cart/items/{productId}/quantity")
    public ResponseEntity<CartDto> updateQuantityCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {
        return ResponseEntity.ok(cartService.updateQuantityCartItem(userId, productId, quantity));
    }
}