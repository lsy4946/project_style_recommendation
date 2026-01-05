package faceweb.demo.Repository;

import faceweb.demo.Entity.ProfessionalProfileEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfileEntity, Long> {
    ProfessionalProfileEntity findByOwner(UserEntity owner);

    ProfessionalProfileEntity findById(long id);
}
