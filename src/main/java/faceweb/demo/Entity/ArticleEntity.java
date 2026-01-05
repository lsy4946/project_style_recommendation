package faceweb.demo.Entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "article")
public class ArticleEntity {

    //게시글 식별번호(기본키)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleID;

    //제목
    @Nonnull
    private String title;

    //내용
    @Nonnull
    @Column(columnDefinition = "TEXT")
    private String content;

    //작성자 식별번호 외래키
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

    //게시판
    @Nonnull
    private String board;

    //작성시간
    private String timeStamp;

    //게시글 조회수
    private long view;

    //댓글 종속관계
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    //추천 종속관계
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RateUpArticleEntity> rateUps = new ArrayList<>();

    //비추천 종속관계
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RateDownArticleEntity> rateDowns = new ArrayList<>();
}
