package faceweb.demo.Controller;

import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String myPage(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            model.addAttribute("userEntity", currentUser);

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }


        return "/user/mypage/mypage";
    }

    @GetMapping("/mypage/articles")
    public String myArticlePage(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            model.addAttribute("userEntity", currentUser);

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "/user/mypage/mypage-article";
    }

    @GetMapping("/mypage/comments")
    public String myCommentPage(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            model.addAttribute("userEntity", currentUser);

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "/user/mypage/mypage-comment";
    }

    @GetMapping("/mypage/styles")
    public String myStylePage(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            model.addAttribute("userEntity", currentUser);

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "/user/mypage/mypage-style";
    }
}
