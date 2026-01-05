package faceweb.demo.Entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentID;

    //내용
    @Nonnull
    private String content;

    //작성 시간
    private String timeStamp;

    //댓글이 작성된 게시글 외래키
    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    //작성자 외래키
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

}
