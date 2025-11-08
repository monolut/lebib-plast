package lebib.team.service;

import lebib.team.mapper.DeliveryMapper;
import lebib.team.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            DeliveryMapper deliveryMapper
    ) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    public DeliveryDto
}
