package faceweb.demo.Service;

import faceweb.demo.DTO.SignupDTO;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SignupService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    @Autowired
    public  SignupService(UserRepository userRepository1, BCryptPasswordEncoder bCryptPasswordEncoder1){
        userRepository = userRepository1;
        bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }

    public int getUserIdByUsername(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username);
        return userEntity != null ? userEntity.getIdNum() : null;
    }

    public boolean signupProcess(SignupDTO signupDTO){
        System.out.println("SignupService.signupProcess");

        if(!signupDTO.getPassword().matches("[a-zA-Z0-9!@#$%,.^&*()]+")){
            System.out.println("SignupService.signupProcess : bad password");
            return false;
        }
        if(!signupDTO.getUsername().matches("[a-zA-Z0-9!@#$%,.^&*()]+")){
            System.out.println("SignupService.signupProcess : bad username");
            return false;
        }

        UserEntity newUser = new UserEntity();

        newUser.setUsername(signupDTO.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(signupDTO.getPassword()));
        newUser.setName(signupDTO.getName());
        newUser.setEmail(signupDTO.getEmail());
        newUser.setGender(signupDTO.getGender());
        newUser.setRegisterDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        newUser.setRole("ROLE_" + signupDTO.getRole());

        try{
            userRepository.save(newUser);
            return true;
        }
        catch (RuntimeException ex){
            System.out.println("SignupService.signupProcess : runtime exception");
            return false;
        }
    }
}
