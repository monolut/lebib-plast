package lebib.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;

    @NotBlank(message = "Country should not be blank")
    @Size(max = 20, message = "Country should not exceed 20 characters")
    private String country;

    @NotBlank(message = "City should not be blank")
    @Size(max = 30, message = "City should not exceed 30 characters")
    private String city;

    @NotBlank(message = "Street should not be blank")
    @Size(max = 45, message = "Street should not exceed 45 characters")
    private String street;

    @NotBlank(message = "Postal code should not be blank")
    @Size(max = 10, message = "Postal code should not exceed 10 characters")
    private String postalCode;

    private Long userId;
}
