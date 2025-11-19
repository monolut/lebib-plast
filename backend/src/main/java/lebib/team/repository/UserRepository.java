package lebib.team.repository;

import lebib.team.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = "role")
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    @EntityGraph(attributePaths = {"addresses", "profile", "cart", "role"})
    Optional<UserEntity> findById(Long id);
}
