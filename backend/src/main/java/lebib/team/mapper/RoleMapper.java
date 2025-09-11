package lebib.team.mapper;

import lebib.team.dto.RoleDto;
import lebib.team.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleEntity toEntity(RoleDto roleDto);
    RoleDto toDto(RoleEntity roleEntity);
}
