package lebib.team.service;

import lebib.team.dto.OrderItemDto;
import lebib.team.entity.OrderEntity;
import lebib.team.exception.OrderNotFoundException;
import lebib.team.mapper.OrderItemMapper;
import lebib.team.repository.OrderItemRepository;
import lebib.team.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemService(OrderItemMapper orderItemMapper, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
    }

    public List<OrderItemDto> getOrderItems(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return order.getItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }
}
