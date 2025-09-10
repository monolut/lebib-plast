package lebib.team.mapper;

import lebib.team.dto.ProductImageDto;
import lebib.team.entity.ProductImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageDto toDto(ProductImageEntity productImageEntity);
    ProductImageEntity toEntity(ProductImageDto productImageDto);
}
