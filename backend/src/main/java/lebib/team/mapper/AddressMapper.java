package lebib.team.mapper;

import lebib.team.dto.AddressDto;
import lebib.team.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mappings({
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "country", target = "country"),
            @Mapping(source = "street", target = "street"),
            @Mapping(source = "postalCode", target = "postalCode")
    })
    AddressEntity toEntity(AddressDto addressDto);
    AddressDto toDto(AddressEntity addressEntity);
}
