package faceweb.demo.Service;

import faceweb.demo.Entity.ProfessionalProfileEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.ProfessionalProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfessionalProfileRepository profileRepository;

    public ProfileService(ProfessionalProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfessionalProfileEntity findByID(long id){return profileRepository.findById(id);}
    public ProfessionalProfileEntity findByOwner(UserEntity owner){
        return profileRepository.findByOwner(owner);
    }
    public void save(ProfessionalProfileEntity profile){
        profileRepository.save(profile);
    }
}
