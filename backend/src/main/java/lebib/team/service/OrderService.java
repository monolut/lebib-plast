package lebib.team.service;

import lebib.team.dto.CartDto;
import lebib.team.dto.OrderDto;
import lebib.team.entity.OrderEntity;
import lebib.team.entity.OrderItemEntity;
import lebib.team.entity.ProductEntity;
import lebib.team.entity.UserEntity;
import lebib.team.enums.OrderStatus;
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
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository) {
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
    public OrderDto createOrder(CartDto cartDto) {
        UserEntity user = userRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> UserNotFoundException.byId(cartDto.getUserId()));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        orderEntity.setOrderStatus(OrderStatus.NEW);
        orderEntity.setDate(LocalDateTime.now());

        List<OrderItemEntity> orderItems = cartDto.getCartItems().stream()
                .map(cartItemDto -> {
                    OrderItemEntity orderItem = new OrderItemEntity();
                    ProductEntity product = productRepository.findById(cartItemDto.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(cartItemDto.getProductId()));

                    orderItem.setOrder(orderEntity);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(cartItemDto.getQuantity());
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
