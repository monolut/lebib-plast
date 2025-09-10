package lebib.team.mapper;

import lebib.team.dto.ProductDto;
import lebib.team.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toEntity(ProductEntity productEntity);
    ProductEntity toDto(ProductDto productDto);
}
