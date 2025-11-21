package lebib.team.mapper;

import lebib.team.dto.InventoryDto;
import lebib.team.entity.InventoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "productId", target = "product.id")
    InventoryEntity toEntity(InventoryDto inventoryDto);

    @Mapping(source = "product.id", target = "productId")
    InventoryDto toDto(InventoryEntity inventoryEntity);
}
