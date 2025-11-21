package lebib.team.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private Long cartId;
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity should be greater than zero")
    private Integer quantity;
}
