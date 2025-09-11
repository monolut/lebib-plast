package lebib.team.mapper;

import lebib.team.dto.AddressDto;
import lebib.team.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressEntity toEntity(AddressDto addressDto);
    AddressDto toDto(AddressEntity addressEntity);
}
