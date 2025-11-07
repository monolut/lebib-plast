package lebib.team.repository;

import lebib.team.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(Long userId);

    List<OrderEntity> findByDateBetween(LocalDateTime start, LocalDateTime end);

}
