package lebib.team.mapper;

import lebib.team.dto.CartDto;
import lebib.team.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mappings({
            @Mapping(source = "id", target = "id")
    })

    @Mapping(target = "userId", source = "user.id")
    CartDto toDto(CartEntity entity);

    @Mapping(target = "user", ignore = true)
    CartEntity toEntity(CartDto dto);
}
