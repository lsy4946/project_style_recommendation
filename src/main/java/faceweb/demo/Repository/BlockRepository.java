package faceweb.demo.Repository;

import faceweb.demo.Entity.BlockEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {
    void deleteAllByBlockedUser(UserEntity user);
}
