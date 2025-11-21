package lebib.team.repository;

import lebib.team.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByCity(String city);

    Optional<AddressEntity> findByCountry(String country);

    Optional<AddressEntity> findByPostalCode(String postalCode);
}
