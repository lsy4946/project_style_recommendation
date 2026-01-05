package faceweb.demo.Repository;

import faceweb.demo.Entity.ArticleEntity;
import faceweb.demo.Entity.RateUpArticleEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateUpArticleRepository extends JpaRepository<RateUpArticleEntity, Long> {
    public boolean existsByArticleAndRater(ArticleEntity article, UserEntity rater);
}
