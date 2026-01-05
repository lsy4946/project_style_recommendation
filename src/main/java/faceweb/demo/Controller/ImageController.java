package faceweb.demo.Controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ImageController {
    @GetMapping("/created/Actual_Model_ver0/data/rec_pics_female/{path3}/{path4}/{path5}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable("path3") String path3,@PathVariable("path4") String path4, @PathVariable("path5") String path5) {
        String path = "Actual_Model_ver0/data/rec_pics_female/" + path3 + '/' + path4 + '/' + path5;
        File file = new File(path);

        // 이미지 파일을 클라이언트로 전송
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(file));
    }
}
