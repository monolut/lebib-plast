package lebib.team.dto;

import jakarta.validation.constraints.*;
import lebib.team.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 2, max = 30, message = "Surname length should be between 2 and 30 characters")
    private String surname;

    @NotNull(message = "Birth date cannot be null")
    @PastOrPresent(message = "Birth date cannot be in future")
    private LocalDate birthDate;
    private Gender gender;
    private ProfileDto profile;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should have email type")
    @Size(max = 50, message = "Email length should not exceed 50 characters")
    private String email;

    @Size(min = 8, max = 16, message = "Password length should be between 8 and 16 characters")
    private String password;
    private CartDto cart;
    private List<AddressDto> addresses;
    private RoleDto role;
    private List<OrderDto> orders;
    private List<ReviewDto> reviews;
}
