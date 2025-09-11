package lebib.team.mapper;

import lebib.team.dto.PaymentDto;
import lebib.team.entity.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    PaymentEntity toEntity(PaymentDto paymentDto);
    PaymentDto toDto(PaymentEntity paymentEntity);
}
