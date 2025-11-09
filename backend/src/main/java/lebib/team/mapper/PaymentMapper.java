package lebib.team.mapper;

import lebib.team.dto.PaymentDto;
import lebib.team.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mappings({
        @Mapping(source = "order.id", target = "orderId")
    })
    PaymentDto toDto(PaymentEntity paymentEntity);
    PaymentEntity toEntity(PaymentDto paymentDto);
}
