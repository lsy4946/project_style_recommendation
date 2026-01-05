package faceweb.demo.Repository;

import faceweb.demo.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    public CommentEntity findByCommentID(long commentID);
}
