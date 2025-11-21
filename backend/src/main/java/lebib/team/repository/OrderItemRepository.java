package lebib.team.repository;

import lebib.team.entity.OrderEntity;
import lebib.team.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
    Optional<OrderEntity> findByProductId(Long productId);

    List<OrderItemEntity> findByOrderId(Long orderId);
}
