package faceweb.demo.Service;


import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository1){
        userRepository = userRepository1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService.loadUserByUsername");

        UserEntity data =  userRepository.findByUsername(username);

        if(data != null){
            return new CustomUserDetails(data);
        }

        System.out.println("CustomUserDetailsService.loadUserByUsername : userdata is null");
        return null;
    }
}