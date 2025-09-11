package lebib.team.mapper;

import lebib.team.dto.OrderItemDto;
import lebib.team.entity.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemEntity toEntity(OrderItemDto orderItemDto);
    OrderItemDto toDto(OrderItemEntity orderItemEntity);
}
