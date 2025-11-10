package lebib.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
    private String name;
    private List<ProductDto> products;
}
