package lebib.team.lebib_plast.mapper;

import lebib.team.lebib_plast.dto.CatrgoryDto;
import lebib.team.lebib_plast.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toEntity(CatrgoryDto catrgoryDto);
    CatrgoryDto toDto(CategoryEntity categoryEntity);
}
