package faceweb.demo.Repository;

import faceweb.demo.Entity.StyleEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<StyleEntity, Long> {
    List<StyleEntity> findTop8ByUser(UserEntity user);

    List<StyleEntity> findTop8ByOrderByStyleid();

    StyleEntity findByStyleid(long styleid);
}
