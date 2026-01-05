package faceweb.demo.Controller;

import faceweb.demo.DTO.BlockDTO;
import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.ArticleEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Service.BoardService;
import faceweb.demo.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController{

    private final BoardService boardService;
    private final UserService userService;

    public AdminController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping("/admin/front")
    public String adminLoginPage(){

        return "/admin/admin_login";
    }

    @GetMapping("/admin/board")
    public String boardPage(Model model){

        List<ArticleEntity> articles = boardService.getRecent15Rows();

        model.addAttribute("articles", articles);

        return "/admin/admin-board";
    }

    @GetMapping("/admin/article/{article_id}")
    public String articlePage(Model model, @PathVariable(name = "article_id") long article_id){
        ArticleEntity article = boardService.getArticleByArticleID(article_id);
        model.addAttribute("article", article);

        return "/admin/admin-article";
    }

    @GetMapping("/admin/userinfo/{user_id}")
    public String userInformationPage(Model model, @PathVariable(name = "user_id") int user_id){
        UserEntity user=userService.getUserbyUserID(user_id);

        model.addAttribute("userEntity", user);

        return "/admin/admin-userinfo";
    }

    @GetMapping("/admin/user")
    public String userPage(Model model, @RequestParam(name = "target",required = false) String target, @RequestParam(name = "keyWord",required = false) String keyWord){
        List<UserEntity> userList;

        if(keyWord==null){
            userList = userService.getRecent15Rows();
        }
        else
            userList = userService.getRecent15Rows();

        model.addAttribute("userList", userList);

        return "/admin/admin-user";
    }

    @PostMapping("/admin/block/{user_id}")
    public String blockUserProcess(@PathVariable("user_id")int user_id, BlockDTO blockDTO){
        userService.blockUserProcess(blockDTO, user_id);

        return "redirect:/admin/userinfo" + user_id;
    }

    @PostMapping("/admin/unblock/{user_id}")
    public String unblockUserProcess(@PathVariable("user_id")int user_id){
        userService.unblockUserProcess(user_id);

        return "redirect:/admin/userinfo" + user_id;
    }
}
