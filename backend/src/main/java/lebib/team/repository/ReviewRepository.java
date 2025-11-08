package lebib.team.repository;

import lebib.team.entity.ProductEntity;
import lebib.team.entity.ReviewEntity;
import lebib.team.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    String getTextById(Long id);

    LocalDateTime getDateById(Long Id);

    UserEntity getUserById(Long id);

    ProductEntity getProductById(Long id);

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    List<ReviewEntity> findByProductId(Long productId);
}
