package lebib.team.mapper;

import lebib.team.dto.OrderDto;
import lebib.team.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, PaymentMapper.class})
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId")
    })
    OrderDto toDto(OrderEntity orderEntity);
    OrderEntity toEntity(OrderDto orderDto);
}
