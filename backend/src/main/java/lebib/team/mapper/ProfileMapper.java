package lebib.team.mapper;

import lebib.team.dto.PaymentDto;
import lebib.team.dto.ProfileDto;
import lebib.team.entity.PaymentEntity;
import lebib.team.entity.ProfileEntity;
import org.mapstruct.Mapper;
import software.amazon.awssdk.profiles.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileEntity toEntity(ProfileDto profileDto);

    ProfileDto toDto(ProfileEntity profileEntity);
}
