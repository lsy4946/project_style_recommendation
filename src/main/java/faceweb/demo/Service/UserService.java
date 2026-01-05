package faceweb.demo.Service;

import faceweb.demo.DTO.BlockDTO;
import faceweb.demo.DTO.SignupDTO;
import faceweb.demo.DTO.UserUpdateDTO;
import faceweb.demo.Entity.BlockEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.BlockRepository;
import faceweb.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final DateTimeFormatter formatter;
    @Autowired
    public UserService(UserRepository userRepository1, BCryptPasswordEncoder bCryptPasswordEncoder1, BlockRepository blockRepository){
        userRepository = userRepository1;
        bCryptPasswordEncoder = bCryptPasswordEncoder1;
        this.blockRepository = blockRepository;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public int getUserIdByUsername(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username);
        return userEntity != null ? userEntity.getIdNum() : null;
    }

    public UserEntity getUserbyUserID(int idNum){
        UserEntity user = userRepository.findByIdNum(idNum);
        return user;
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
        newUser.setRegisterDate(LocalDateTime.now().format(formatter));
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

    public boolean updateUser(UserUpdateDTO updateDTO, UserEntity currentUser){
        currentUser.setName(updateDTO.getName());
        if(currentUser.getPassword().isEmpty())
            currentUser.setPassword(bCryptPasswordEncoder.encode(currentUser.getPassword()));
        currentUser.setEmail(updateDTO.getEmail());

        userRepository.save(currentUser);

        return true;
    }

    //이용자 차단
    public boolean blockUserProcess(BlockDTO blockDTO, int user_id){
        UserEntity blockUser = userRepository.findByIdNum(user_id);

        BlockEntity block = new BlockEntity();
        block.setBlockedUser(blockUser);

        switch (blockDTO.getDataType()){
            case "permanent":
                block.setDuration("permanent");
            case "month":
                block.setDuration(LocalDateTime.now().plusMonths(blockDTO.getBlockLength()).format(formatter));
            case "day":
                block.setDuration(LocalDateTime.now().plusDays(blockDTO.getBlockLength()).format(formatter));
            case "hour":
                block.setDuration(LocalDateTime.now().plusHours(blockDTO.getBlockLength()).format(formatter));
        }

        block.setWhyBlocked(blockDTO.getWhyBlocked());

        blockRepository.save(block);

        return true;
    }

    //이용자 차단 해제
    public boolean unblockUserProcess(int user_id){
        UserEntity blockUser = userRepository.findByIdNum(user_id);

        blockRepository.deleteAllByBlockedUser(blockUser);

        return true;
    }

    public List<UserEntity> getRecent15Rows(){
        return userRepository.findTop15ByOrderByIdNumDesc();
    }
}
