package faceweb.demo.Controller;

import faceweb.demo.DTO.SignupDTO;
import faceweb.demo.DTO.UserUpdateDTO;
import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService1){
        userService = userService1;
    }

    @GetMapping("/user/login")
    public String loginPage(){return "/user/login";}

    @GetMapping("/user/signup")
    public String signupPage(){
        return "/user/signup";
    }

    @GetMapping("/user/signup-tac")
    public String signup_tac(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        System.out.println("join-tac");
        return "/user/signup-tac";
    }

    @PostMapping("/user/signup")
    public String signupProcess(SignupDTO signupDTO){
        System.out.println("UserController.signupProcess");
        signupDTO.printAll();
        userService.signupProcess(signupDTO);

        return "redirect:/login";
    }

    @PostMapping("/user/update")
    public String updateUser(Model model, UserUpdateDTO updateDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            userService.updateUser(updateDTO, currentUser);
        }
        return "redirect:/mypage";
    }
}
