package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    String content;
    long articleID;
}
