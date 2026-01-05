package faceweb.demo.Controller;

import faceweb.demo.DTO.ReportDTO;
import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.ArticleEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Service.BoardService;
import faceweb.demo.Service.ReportService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReportController {
    private final BoardService boardService;
    private final ReportService reportService;

    public ReportController(BoardService boardService, ReportService reportService) {
        this.boardService = boardService;
        this.reportService = reportService;
    }

    @PostMapping("/user/{board}/report/{objectType}/{object_id}")
    public String reportArticleProcess(ReportDTO reportDTO, @PathVariable("objectType") String objectType,
                                       @PathVariable("object_id")long object_id, @PathVariable("board") String board){
        if(!boardService.existByID(objectType, object_id))
            return "redirect:/board/user/notFound";

        UserEntity currentUser = new UserEntity();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof CustomUserDetails currentUserDetails){
            currentUser = currentUserDetails.getUserEntity();
        }

        reportService.saveReportProcess(reportDTO, currentUser, "article", object_id);

        ArticleEntity returnArticle;

        if(objectType.equals("article"))
            returnArticle = boardService.getArticleByArticleID(object_id);
        else returnArticle = boardService.getCommentByCommentID(object_id).getArticle();

        return "redirect:/board/" + returnArticle.getBoard() + "/article/" + returnArticle.getArticleID();
    }

    @GetMapping("/user/{board}/report/{object_type}/{object_id}")
    public String reportPage(@PathVariable("board")String board, @PathVariable("object_type")String object_type,
                             @PathVariable("object_id")long object_id, Model model){
        ArticleEntity article = boardService.getArticleByArticleID(object_id);
        if(article==null)
            return "redirect:/board/user/notFound";

        UserEntity currentUser = new UserEntity();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof CustomUserDetails currentUserDetails){
            currentUser = currentUserDetails.getUserEntity();
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        model.addAttribute("object_type", object_type);
        model.addAttribute("object_id", object_id);

        return "/user/board/report";
    }
}
