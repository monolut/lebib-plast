package lebib.team.dto;

import jakarta.validation.constraints.NotNull;
import lebib.team.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;

    @NotNull(message = "Amount cannot be null")
    private Double amount;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
    private String stripeClientSecret;
    private String stripePaymentIntentId;
    private Long orderId;
}
