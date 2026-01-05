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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNum;//계정번호

    @Column(unique = true, nullable = false)
    private String username;//ID
    @Nonnull
    private String password;//비밀번호
    @Nonnull
    private String name;//이름
    @Column(unique = true, nullable = false)
    private String email;
    @Nonnull
    private String gender;//성별
    @Nonnull
    private String registerDate;//회원가입 일시


    private String role;//="USER"

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ArticleEntity> articles = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "rater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RateUpArticleEntity> rateUps = new ArrayList<>();

    @OneToMany(mappedBy = "rater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RateDownArticleEntity> rateDowns = new ArrayList<>();

    @OneToMany(mappedBy = "blockedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlockEntity> blockEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StyleEntity> styleList = new ArrayList<>();

    public boolean isBlocked(){
        return !blockEntities.isEmpty();
    }
}
