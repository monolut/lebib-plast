package lebib.team.service;

import lebib.team.dto.OrderDto;
import lebib.team.entity.*;
import lebib.team.enums.OrderStatus;
import lebib.team.exception.CartNotFoundException;
import lebib.team.exception.OrderNotFoundException;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.exception.UserNotFoundException;
import lebib.team.mapper.OrderMapper;
import lebib.team.repository.CartRepository;
import lebib.team.repository.OrderRepository;
import lebib.team.repository.ProductRepository;
import lebib.team.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            OrderMapper orderMapper, UserRepository userRepository,
            CartRepository cartRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderDto getOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Transactional
    public OrderDto createOrder(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> CartNotFoundException.byId(cartId));

        UserEntity user = userRepository.findById(cart.getUser().getId())
                .orElseThrow(() -> UserNotFoundException.byId(cart.getUser().getId()));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        orderEntity.setOrderStatus(OrderStatus.NEW);
        orderEntity.setDate(LocalDateTime.now());

        List<OrderItemEntity> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItemEntity orderItem = new OrderItemEntity();
                    ProductEntity product = productRepository.findById(cartItem.getProduct().getId())
                            .orElseThrow(() -> new ProductNotFoundException(cartItem.getProduct().getId()));

                    orderItem.setOrder(orderEntity);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(product.getPrice());

                    return orderItem;
                })
                .toList();

        orderEntity.setItems(orderItems);

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        cartRepository.deleteByUserId(user.getId());

        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.setOrderStatus(orderStatus);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public List<OrderDto> getAllOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderRepository.delete(order);
    }
}
