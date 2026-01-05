package faceweb.demo.Repository;

import faceweb.demo.Entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    RequestEntity findById(long id);
}
