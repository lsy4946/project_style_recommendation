package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rateUpArticle")
public class RateUpArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "rater")
    private UserEntity rater;
}
