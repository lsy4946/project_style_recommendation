package faceweb.demo.Repository;

import faceweb.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
    UserEntity findByIdNum(int idNum);

    List<UserEntity> findTop15ByOrderByIdNumDesc();
}
