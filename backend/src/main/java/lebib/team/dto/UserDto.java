package lebib.team.dto;

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
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private ProfileDto profile;
    private CartDto cart;
    private List<AddressDto> address;
    private RoleDto role;
    private List<OrderDto> orders;
    private List<ReviewDto> reviews;
}
