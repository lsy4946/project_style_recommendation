package faceweb.demo.Repository;

import faceweb.demo.Entity.ArticleEntity;
import faceweb.demo.Entity.UserEntity;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findAllByWriter(UserEntity writer);

    ArticleEntity findByArticleID(Long article_id);

    long count();

    // 최근 15행 가져오기
    List<ArticleEntity> findTop15ByOrderByArticleIDDesc();

    // 그 다음 15행 가져오기
    List<ArticleEntity> findTop15ByOrderByArticleIDDesc(Pageable pageable);

    @Query("SELECT a FROM ArticleEntity a WHERE a.board = :board ORDER BY a.articleID DESC")
    List<ArticleEntity> findTop15ByBoardOrderByArticleIDDesc(@Param("board") String board, Pageable pageable);

}
