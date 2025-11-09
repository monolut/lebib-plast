package lebib.team.repository;

import com.stripe.model.PaymentIntent;
import lebib.team.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    List<PaymentEntity> findByAmountBetween(Double amountFrom, Double amountTo);

    List<PaymentEntity> findByOrderId(Long orderId);

    Optional<PaymentEntity> findByStripePaymentIntentId(String paymentIntentId);
}
