package lebib.team.mapper;

import lebib.team.dto.ProductImageDto;
import lebib.team.entity.ProductImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(source = "product.id", target = "productId")
    ProductImageDto toDto(ProductImageEntity productImageEntity);

    @Mapping(source = "productId", target = "product.id")
    ProductImageEntity toEntity(ProductImageDto productImageDto);
}
