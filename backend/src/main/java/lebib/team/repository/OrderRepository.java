package lebib.team.repository;

import lebib.team.entity.OrderEntity;
import lebib.team.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(Long userId);

    List<OrderEntity> findByDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT CASE WHEN COUNT(order) > 0 THEN TRUE ELSE FALSE END
                FROM OrderEntity order
                JOIN order.items item
                WHERE order.user.id = :userId
                  AND item.product.id = :productId
                  AND order.orderStatus = :status
            """)
    boolean existsDeliveredOrderForProduct(
            @Param("userId") Long userId,
            @Param("productId") Long productId,
            @Param("status") OrderStatus status
    );
}
