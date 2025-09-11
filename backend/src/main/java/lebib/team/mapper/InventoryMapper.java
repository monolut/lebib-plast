package lebib.team.mapper;

import lebib.team.dto.InventoryDto;
import lebib.team.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryEntity toEntity(InventoryDto inventoryDto);
    InventoryDto toDto(InventoryEntity inventoryEntity);
}
