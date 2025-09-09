package lebib.team.lebib_plast.mapper;

import lebib.team.lebib_plast.dto.ProductDto;
import lebib.team.lebib_plast.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toEntity(ProductEntity productEntity);
    ProductEntity toDto(ProductDto productDto);
}
