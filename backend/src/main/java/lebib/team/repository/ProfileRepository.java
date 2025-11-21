package lebib.team.repository;

import lebib.team.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    ProfileEntity getProfileById(Long id);
}
