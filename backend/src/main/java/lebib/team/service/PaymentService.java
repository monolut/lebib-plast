package lebib.team.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lebib.team.dto.PaymentDto;
import lebib.team.entity.OrderEntity;
import lebib.team.entity.PaymentEntity;
import lebib.team.enums.OrderStatus;
import lebib.team.enums.PaymentStatus;
import lebib.team.mapper.PaymentMapper;
import lebib.team.repository.OrderRepository;
import lebib.team.repository.PaymentRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentMapper paymentMapper,
            OrderRepository orderRepository,
            UserRepository userRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PaymentDto createPayment(Long userId, Long orderId) {
        System.out.println(">>> START createPayment for userId=" + userId + ", orderId=" + orderId);

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User " + userId + " does not exist");
        }

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order " + orderId + " does not exist"));

        Double totalAmount = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        System.out.println(">>> totalAmount = " + totalAmount);
        if (totalAmount <= 0) {
            throw new IllegalStateException("Order " + orderId + " has no items or zero amount.");
        }

        Long amountInCents = Math.round(totalAmount * 100);
        System.out.println(">>> amountIncents = " + amountInCents);

        Stripe.apiKey = stripeSecretKey;

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("usd")
                    .setDescription("Payment for order #" + orderId)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println(">>> Stripe PaymentIntent created: " + intent.getId());

            PaymentEntity payment = new PaymentEntity();
            payment.setOrder(order);
            payment.setAmount(totalAmount);
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStripePaymentIntentId(intent.getId());
            payment.setStripeClientSecret(intent.getClientSecret());

            PaymentEntity saved = paymentRepository.save(payment);
            order.getPayments().add(saved);

            PaymentDto dto = paymentMapper.toDto(saved);
            dto.setStripeClientSecret(saved.getStripeClientSecret());
            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Stripe error: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void markPaymentAsSucceeded(String paymentIntentId) {
        PaymentEntity payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for intent " + paymentIntentId));

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        OrderEntity order = payment.getOrder();
        order.setOrderStatus(OrderStatus.PAID);

        paymentRepository.save(payment);
        orderRepository.save(order);

        System.out.println("Order #" + order.getId() + " marked as PAID.");
    }
}