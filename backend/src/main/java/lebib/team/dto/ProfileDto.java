package lebib.team.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;

    @Size(max = 300, message = "Bio length should not exceed 300 characters")
    private String bio;
    private String avatarUrl;
    private UserDto user;
}
