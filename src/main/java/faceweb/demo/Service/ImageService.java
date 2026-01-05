package faceweb.demo.Service;

import faceweb.demo.Entity.ImageEntity;
import faceweb.demo.Repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService UserService;

    public void uploadImage(MultipartFile file, String username) {
        ImageEntity image = new ImageEntity();
        image.setFilename(file.getOriginalFilename());
        image.setFilepath("/images/" + file.getOriginalFilename());
        int userId = this.UserService.getUserIdByUsername(username);
        image.setUserId(userId);
        this.imageRepository.save(image);
    }

    public ImageService(final ImageRepository imageRepository, final UserService service) {
        this.imageRepository = imageRepository;
        this.UserService = service;
    }
}