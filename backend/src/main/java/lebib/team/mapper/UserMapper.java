package lebib.team.mapper;

import lebib.team.dto.UserDto;
import lebib.team.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {
        RoleMapper.class,
        ReviewMapper.class,
        OrderMapper.class,
        CartMapper.class,
        AddressMapper.class,
        ProfileMapper.class,
        PaymentMapper.class,
        OrderItemMapper.class
})
public interface UserMapper {
    @Mappings({
            @Mapping(source = "birthDate", target = "birthDate"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "profile", target = "profile"),
            @Mapping(source = "cart", target = "cart")
    })
    UserEntity toEntity(UserDto userDto);
    UserDto toDto(UserEntity userEntity);
}
