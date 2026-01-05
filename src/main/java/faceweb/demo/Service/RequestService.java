package faceweb.demo.Service;

import faceweb.demo.DTO.RequestDTO;
import faceweb.demo.Entity.RequestEntity;
import faceweb.demo.Entity.RequestEntity.*;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final DateTimeFormatter formatter;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public boolean saveRequestProcess(RequestDTO requestDTO, UserEntity currentUser, String filePath){
        RequestEntity requestEntity = new RequestEntity();

        requestEntity.setRequester(currentUser);
        requestEntity.setStatus(STATUS.NOT_RECEIVED);
        requestEntity.setTitle(requestDTO.getTitle());
        requestEntity.setContent(requestDTO.getContent());
        requestEntity.setTimeStamp(LocalDateTime.now().format(formatter));
        requestEntity.setFilePath(filePath);

        requestRepository.save(requestEntity);

        return true;
    }

    public RequestEntity findByID(long id){
        return requestRepository.findById(id);
    }

    public List<RequestEntity> findAll(){
        return requestRepository.findAll();
    }
}
