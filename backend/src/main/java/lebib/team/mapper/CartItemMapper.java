package lebib.team.mapper;

import lebib.team.dto.CartItemDto;
import lebib.team.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemEntity toEntity(CartItemDto cartItemDto);
    CartItemDto toDto(CartItemEntity cartItemEntity);
}
