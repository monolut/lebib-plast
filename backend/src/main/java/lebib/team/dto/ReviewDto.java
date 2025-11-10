package lebib.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;

    @NotBlank(message = "Text cannot be blank")
    @Size(max = 1000, message = "Text length should not exceed 1000 characters")
    private String text;
    private LocalDateTime reviewDate;
    private Long userId;
    private Long productId;
}
