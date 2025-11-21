package lebib.team.mapper;

import lebib.team.dto.ProfileDto;
import lebib.team.entity.ProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
        RoleMapper.class,
        AddressMapper.class,
        CartMapper.class,
        OrderMapper.class,
        ReviewMapper.class,
        PaymentMapper.class
})
public interface ProfileMapper {
    ProfileEntity toEntity(ProfileDto profileDto);

    ProfileDto toDto(ProfileEntity profileEntity);
}
