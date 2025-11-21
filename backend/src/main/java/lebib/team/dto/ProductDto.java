package lebib.team.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 30, message = "Name should be between 0 and 30 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 2000, message = "Description length should not exceed 2000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price should be 0 or greater")
    private Double price;
    private List<ProductImageDto> images;
    private Long categoryId;
    private List<CartItemDto> cartItems;
    private List<ReviewDto> reviews;
    private InventoryDto inventory;
}
