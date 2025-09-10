package lebib.team.mapper;

import lebib.team.dto.CategoryDto;
import lebib.team.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toEntity(CategoryDto catrgoryDto);
    CategoryDto toDto(CategoryEntity categoryEntity);
}
