package lebib.team.mapper;

import lebib.team.dto.AddressDto;
import lebib.team.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "userId", target = "user.id")
    AddressEntity toEntity(AddressDto addressDto);

    @Mapping(source = "user.id", target = "userId")
    AddressDto toDto(AddressEntity addressEntity);
}
