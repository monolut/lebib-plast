package lebib.team.repository;

import lebib.team.entity.RoleEntity;
import lebib.team.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(Role role);
}
