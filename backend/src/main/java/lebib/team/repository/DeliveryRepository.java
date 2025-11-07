package lebib.team.repository;

import lebib.team.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    Optional<DeliveryEntity> findByOrderId(Long orderId);

    List<DeliveryEntity> findByDeliveryDate(LocalDateTime deliveryDate);
}
