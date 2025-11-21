package lebib.team.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {
    private Long id;

    @NotBlank(message = "Image url cannot be blank")
    private String imageUrl;
    private Long productId;
}
