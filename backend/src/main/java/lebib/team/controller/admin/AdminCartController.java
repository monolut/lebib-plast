package lebib.team.controller.admin;

import jakarta.validation.Valid;
import lebib.team.dto.CartDto;
import lebib.team.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/carts")
@CrossOrigin("*")
public class AdminCartController {

    private final CartService cartService;

    @Autowired
    public AdminCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/users/{userId}/cart")
    public ResponseEntity<CartDto> createCart(@Valid @RequestBody CartDto cartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(cartDto));
    }

    @DeleteMapping("/users/{userId}/cart")
    public ResponseEntity<Void> deleteCart(@PathVariable Long userId) {
        cartService.deleteCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
