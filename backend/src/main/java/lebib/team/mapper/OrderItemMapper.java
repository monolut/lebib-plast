package lebib.team.mapper;

import lebib.team.dto.OrderItemDto;
import lebib.team.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "order.id", target = "orderId")
    })
    OrderItemDto toDto(OrderItemEntity orderItemEntity);

    @Mappings({
            @Mapping(source = "productId", target = "product.id"),
            @Mapping(source = "orderId", target = "order.id")
    })
    OrderItemEntity toEntity(OrderItemDto orderItemDto);
}
