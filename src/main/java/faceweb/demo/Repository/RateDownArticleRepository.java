package faceweb.demo.Repository;

import faceweb.demo.Entity.ArticleEntity;
import faceweb.demo.Entity.RateDownArticleEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateDownArticleRepository extends JpaRepository<RateDownArticleEntity, Long> {
    boolean existsByArticleAndRater(ArticleEntity article, UserEntity rater);
}
