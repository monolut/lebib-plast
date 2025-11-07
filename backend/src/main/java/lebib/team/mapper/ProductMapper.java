package lebib.team.mapper;

import lebib.team.dto.ProductDto;
import lebib.team.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "category.id", target = "categoryId")
    })
    ProductDto toDto(ProductEntity productEntity);
    ProductEntity toEntity(ProductDto productDto);
}
