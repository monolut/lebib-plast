package lebib.team.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lebib.team.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    @NotNull(message = "Role name cannot be null")
    private Role role;

    private List<UserDto> users;
}
