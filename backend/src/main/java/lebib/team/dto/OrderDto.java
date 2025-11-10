package lebib.team.dto;

import jakarta.validation.constraints.NotNull;
import lebib.team.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime date;

    @NotNull(message = "Order Status cannot be null")
    private OrderStatus orderStatus;

    private List<PaymentDto> payments;
    private List<OrderItemDto> items;
}
