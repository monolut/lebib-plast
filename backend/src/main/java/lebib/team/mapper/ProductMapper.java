package lebib.team.mapper;

import lebib.team.dto.ProductDto;
import lebib.team.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {
        ReviewMapper.class,
        InventoryMapper.class,
        CartItemMapper.class,
        ProductImageMapper.class
})
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "category.id", target = "categoryId")
    })
    ProductDto toDto(ProductEntity productEntity);

    @Mappings({
            @Mapping(source = "categoryId", target = "category.id")
    })
    ProductEntity toEntity(ProductDto productDto);
}
