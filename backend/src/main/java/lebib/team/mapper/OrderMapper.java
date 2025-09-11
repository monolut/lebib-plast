package lebib.team.mapper;

import lebib.team.dto.OrderDto;
import lebib.team.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderEntity toEntity(OrderDto orderDto);
    OrderDto toDto(OrderEntity orderEntity);
}
