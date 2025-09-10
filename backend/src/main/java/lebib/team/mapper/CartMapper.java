package lebib.team.mapper;

import lebib.team.dto.CartDto;
import lebib.team.entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartEntity toEntity(CartDto cartDto);
    CartDto toDto(CartEntity entity);
}
