package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDTO {
    private String title;
    private String content;
    private String board;

    //private String verificationCode;
}
