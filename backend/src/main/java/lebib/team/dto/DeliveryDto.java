package lebib.team.dto;

import lebib.team.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long id;
    private LocalDateTime estimatedArrival;
    private DeliveryStatus status;
    private Long orderId;
}
