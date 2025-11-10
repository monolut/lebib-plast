package lebib.team.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity should be greater than zero")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price should be 0 or greater")
    private Double price;
    private Long productId;
    private Long orderId;
}
