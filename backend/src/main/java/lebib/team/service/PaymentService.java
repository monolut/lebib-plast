package lebib.team.service;

import lebib.team.dto.PaymentDto;
import lebib.team.mapper.PaymentMapper;
import lebib.team.repository.OrderRepository;
import lebib.team.repository.PaymentRepository;
import lebib.team.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentMapper paymentMapper,
            UserRepository userRepository,
            OrderRepository orderRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public PaymentDto createPayment(Long userId, Long orderId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User " + userId + " do not exists");
        }
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order " + orderId + " do not exists");
        }


    }
}
