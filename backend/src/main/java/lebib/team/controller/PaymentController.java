package lebib.team.controller;

import lebib.team.dto.PaymentDto;
import lebib.team.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/payments")
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create/{userId}/order/{orderId}")
    public ResponseEntity<PaymentDto> createPayment(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        System.out.println(">>> PaymentController called with userId=" + userId + ", orderId=" + orderId);
        PaymentDto payment = paymentService.createPayment(userId, orderId);
        System.out.println(">>> Returning PaymentDto: " + payment);
        return ResponseEntity.ok(payment);
    }

}
