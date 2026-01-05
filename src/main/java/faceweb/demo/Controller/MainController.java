package faceweb.demo.Controller;

import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String homePage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        //System.out.println("MainController.homePage : " + username);

        return "user/home";
    }

    @GetMapping("/user/face")
    public String facePage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "user/simulate";
    }

    @GetMapping("/bad-access")
    public String badAccessPage(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "/user/error/bad-access";
    }

    @GetMapping("/user/error")
    public String errorPage(Model model, @RequestParam("error") String error){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        model.addAttribute("error-message", error);

        return "/user/error/error";
    }
}
