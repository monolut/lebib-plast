package lebib.team.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private Long userId;

    @NotNull(message = "Total amount cannot be null")
    @DecimalMin(value = "0.0", message = "Total amount should be positive")
    private Double totalAmount;
    private List<CartItemDto> cartItems;
}
