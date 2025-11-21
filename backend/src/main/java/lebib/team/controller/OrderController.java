package lebib.team.controller;

import lebib.team.dto.OrderDto;
import lebib.team.enums.OrderStatus;
import lebib.team.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long cartId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(cartId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus orderStatus
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllOrdersByUserId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}
