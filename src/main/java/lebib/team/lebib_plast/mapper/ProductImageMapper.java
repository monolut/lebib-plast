package lebib.team.lebib_plast.mapper;

import lebib.team.lebib_plast.dto.ProductImageDto;
import lebib.team.lebib_plast.entity.ProductImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageDto toDto(ProductImageEntity productImageEntity);
    ProductImageEntity toEntity(ProductImageDto productImageDto);
}
