package lebib.team.mapper;

import lebib.team.dto.CartItemDto;
import lebib.team.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mappings({
            @Mapping(source = "cart.id", target = "cartId"),
            @Mapping(source = "product.id", target = "productId")
    })
    CartItemDto toDto(CartItemEntity cartItemEntity);
    CartItemEntity toEntity(CartItemDto cartItemDto);
}
