package lebib.team.mapper;

import lebib.team.dto.RoleDto;
import lebib.team.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "roleName", target = "role")
    RoleDto toDto(RoleEntity entity);

    @Mapping(source = "role", target = "roleName")
    RoleEntity toEntity(RoleDto dto);
}
