package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestDTO {
    private String title;
    private String content;
    private MultipartFile applicationForm;
}
