package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PredictDTO {
    MultipartFile image;

    private int person_see_up_dos;

    private int person_hair_length;
}
