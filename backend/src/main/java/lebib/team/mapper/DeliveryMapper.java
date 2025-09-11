package lebib.team.mapper;

import lebib.team.dto.DeliveryDto;
import lebib.team.entity.DeliveryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryDto toDto(DeliveryEntity deliveryEntity);
    DeliveryEntity toEntity(DeliveryDto deliveryDto);
}
