package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="rateDownArticle")
public class RateDownArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "article")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "rater")
    private UserEntity rater;
}
