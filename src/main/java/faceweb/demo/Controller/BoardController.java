package faceweb.demo.Controller;

import faceweb.demo.DTO.ArticleDTO;
import faceweb.demo.DTO.CommentDTO;
import faceweb.demo.Details.CustomUserDetails;
import faceweb.demo.Entity.ArticleEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Service.BoardService;
import faceweb.demo.TableRow.ArticleTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;
    @Autowired
    public BoardController(BoardService boardService1){
        boardService = boardService1;
    }

    @GetMapping("/board/user")
    public String userBoardPage(Model model, @RequestParam(name = "error", required = false) String error){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        if(error!=null)
            model.addAttribute("error", error);

        List<ArticleEntity> articleList = boardService.getRecent15Rows("user");
        List<ArticleTableRow> articleTableRowList = ArticleTableRow.getList(articleList);
//        for(ArticleTableRow articleTableRow : articleTableRowList){
//            System.out.println("idNum : " + articleTableRow.getIdNum() + "title : " + articleTableRow.getTitle());
//        }
        model.addAttribute("articleRowList", articleTableRowList);

        return "/user/board/board";
    }

    @GetMapping("/board/user/write")
    public String userWritePage(Model model){
        System.out.println("BoardController.userWritePage");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getClass());

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());

            if(currentUser.isBlocked()){
                return "redirect:/board/user/blocked";
            }
        }

        return "/user/board/write";
    }

    @PostMapping("/board/user/write")
    public String userWrite(RedirectAttributes redirectAttributes, ArticleDTO articleDTO){
        System.out.println("BoardController.userWrite");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getClass());

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            if(currentUser.isBlocked()){
                return "redirect:/board/user/blocked";
            }

            if(!boardService.writeArticleProcess(articleDTO, currentUser)){
                redirectAttributes.addAttribute("error", "저장 실패");
            }
        }

        return "redirect:/board/user";
    }

    @GetMapping("/board/user/article/{article-id}")
    public String articlePage(@PathVariable("article-id") Long article_id, Model model,
                              @RequestParam(name = "redirected", required = false)boolean redirected){
        System.out.println("BoardController.articlePage");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        ArticleEntity article = boardService.getArticleByArticleID(article_id);

        if(article == null){
            return "redirect:/board/user/notFound";
        }

        //게시물 내용, 제목, 댓글
        model.addAttribute("article", article);

        //조회수 1 증가 후 저장
        if(!redirected){
            article.setView(article.getView()+1);
            boardService.saveArticleEntity(article);
        }

        //게시글 목록 넣기
        List<ArticleEntity> articleList = boardService.getRecent15Rows();
        List<ArticleTableRow> articleTableRowList = ArticleTableRow.getList(articleList);
        model.addAttribute("articleRowList", articleTableRowList);

        return "/user/board/article";
    }

    @GetMapping("/board/user/notFound")
    public String notFoundPage(Model model){
        System.out.println("BoardController.notFoundPage");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "/user/error/notFound";
    }

    @PostMapping("/board/reply")
    public String addComment(RedirectAttributes redirectAttributes, Model model, CommentDTO commentDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getClass());

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            if(currentUser.isBlocked()){
                return "redirect:/board/user/blocked";
            }

            if(!boardService.writeCommentProcess(commentDTO, currentUser))
                return "redirect:/board/user/notFound";
        }

        redirectAttributes.addAttribute("redirected", true);

        return "redirect:/board/user/article/"+commentDTO.getArticleID();
    }

    @GetMapping("/board/user/comment/{article-id}/{comment-id}/delete")
    public String deleteCommentPage(Model model, @PathVariable("article-id") long article_id, @PathVariable("comment-id") long comment_id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            long commentWriterID = boardService.getCommentByCommentID(comment_id).getWriter().getIdNum();

            if(currentUser.getIdNum()!=commentWriterID)
                return "redirect:/bad-access";

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        else
            return "redirect:/bad-access";

        model.addAttribute("article_id", article_id);
        model.addAttribute("comment_id", comment_id);

        return "/user/board/delete-comment";
    }

    @PostMapping("/board/user/comment/{article-id}/{comment-id}/delete")
    public String deleteCommentProcess(@PathVariable("article-id") long article_id,
                                      @PathVariable("comment-id") long comment_id, long articleID){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            long commentWriterID = boardService.getCommentByCommentID(comment_id).getWriter().getIdNum();

            if(currentUser.getIdNum()!=commentWriterID)
                return "redirect:/bad-access";
        }
        else
            return "redirect:/bad-access";

        boardService.deleteCommentByID(comment_id);

        return "redirect:/board/user/article/"+articleID;
    }

    @GetMapping("/board/user/article/{article-id}/delete")
    public String deleteArticlePage(Model model, @PathVariable("article-id") long article_id){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus =SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            long writerID = boardService.getArticleByArticleID(article_id).getWriter().getIdNum();

            if(writerID!=currentUser.getIdNum())
                return "redirect:/bad-access";

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        else
            return "redirect:/bad-access";

        model.addAttribute("article_id", article_id);

        return "/user/board/delete-article";
    }

    @PostMapping("/board/user/article/{article-id}/delete")
    public String deleteArticleProcess(@PathVariable("article-id") long article_id, long articleID){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus =SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            long writerID = boardService.getArticleByArticleID(article_id).getWriter().getIdNum();

            if(writerID!=currentUser.getIdNum())
                return "redirect:/bad-access";
        }
        else
            return "redirect:/bad-access";

        boardService.deleteArticleByID(articleID);

        return "redirect:/board/user";
    }

    @GetMapping("/board/professional")
    public String professionalBoardPage(Model model, @RequestParam(name = "error", required = false) String error){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        if(error!=null)
            model.addAttribute("error", error);

        List<ArticleEntity> articleList = boardService.getRecent15Rows("professional");
        List<ArticleTableRow> articleTableRowList = ArticleTableRow.getList(articleList, "professional");
        model.addAttribute("articleRowList", articleTableRowList);

        return "/professional/board";
    }

    @GetMapping("/board/professional/write")
    public String professionalWritePage(Model model){
        System.out.println("BoardController.professionalWritePage");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getClass());

        if (principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            if(currentUser.isBlocked()){
                return "redirect:/board/professional/blocked";
            }

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        return "/professional/write";
    }

    @PostMapping("/board/professional/write")
    public String professionalWrite(RedirectAttributes redirectAttributes, ArticleDTO articleDTO){
        System.out.println("BoardController.userWrite");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.getClass());

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            if(currentUser.isBlocked()){
                return "redirect:/board/professional/blocked";
            }

            if(!boardService.writeArticleProcess(articleDTO, currentUser, "professional")){
                redirectAttributes.addAttribute("error", "저장 실패");
            }
        }

        return "redirect:/board/user";
    }

    @GetMapping("/board/professional/article/{article-id}")
    public String articleProfessionalPage(@PathVariable("article-id") Long article_id, Model model){
        System.out.println("BoardController.articlePage");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }

        ArticleEntity article = boardService.getArticleByArticleID(article_id);

        if(article == null){
            return "redirect:/board/user/notFound";
        }

        //게시물 내용, 제목, 댓글
        model.addAttribute("article", article);

        //조회수 1 증가 후 저장
        article.setView(article.getView()+1);
        boardService.saveArticleEntity(article);

        //게시글 목록 넣기
        List<ArticleEntity> articleList = boardService.getRecent15Rows("professional");
        List<ArticleTableRow> articleTableRowList = ArticleTableRow.getList(articleList, "professional");
        model.addAttribute("articleRowList", articleTableRowList);

        return "/professional/article";
    }

    @GetMapping("/board/professional/comment/{article-id}/{comment-id}/delete")
    public String deleteProfessionalCommentPage(Model model, @PathVariable("article-id") long article_id, @PathVariable("comment-id") long comment_id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            long commentWriterID = boardService.getCommentByCommentID(comment_id).getWriter().getIdNum();

            if(currentUser.getIdNum()!=commentWriterID)
                return "redirect:/bad-access";

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        else
            return "redirect:/bad-access";

        model.addAttribute("article_id", article_id);
        model.addAttribute("comment_id", comment_id);

        return "professional/delete-comment";
    }

    @PostMapping("/board/professional/comment/{article-id}/{comment-id}/delete")
    public String deleteProfessionalCommentProcess(@PathVariable("article-id") long article_id,
                                       @PathVariable("comment-id") long comment_id, long articleID){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();
            long commentWriterID = boardService.getCommentByCommentID(comment_id).getWriter().getIdNum();

            if(currentUser.getIdNum()!=commentWriterID)
                return "redirect:/bad-access";
        }
        else
            return "redirect:/bad-access";

        boardService.deleteCommentByID(comment_id);

        return "redirect:/board/professional/article/"+articleID;
    }

    @GetMapping("/board/professional/article/{article-id}/delete")
    public String deleteProfessionalArticlePage(Model model, @PathVariable("article-id") long article_id){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus =SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            long writerID = boardService.getArticleByArticleID(article_id).getWriter().getIdNum();

            if(writerID!=currentUser.getIdNum())
                return "redirect:/bad-access";

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
        }
        else
            return "redirect:/bad-access";

        model.addAttribute("article_id", article_id);

        return "/professional/delete-article";
    }

    @PostMapping("/board/professional/article/{article-id}/delete")
    public String deleteProfessionalArticleProcess(@PathVariable("article-id") long article_id, long articleID){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean loginStatus =SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(loginStatus && principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            long writerID = boardService.getArticleByArticleID(article_id).getWriter().getIdNum();

            if(writerID!=currentUser.getIdNum())
                return "redirect:/bad-access";
        }
        else
            return "redirect:/bad-access";

        boardService.deleteArticleByID(articleID);

        return "redirect:/board/professional";
    }

    @PostMapping("/board/{board}/{article-id}/articleRateUp")
    public String articleRateUp(@PathVariable("article-id") long article_id, @PathVariable("board") String board){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            if(currentUser.isBlocked()){
                return "redirect:/board/" + board + "/blocked";
            }

            if(!boardService.rateUpArticleProcess(currentUser, article_id, board))
                return "redirect:/board/" + board +"/article/" + article_id;
        }

        return "redirect:/board/" + board + "/article/" + article_id;
    }

    @PostMapping("/board/{board}/{article-id}/articleRateDown")
    public String articleRateDown(RedirectAttributes redirectAttributes,
                                  @PathVariable("article-id") long article_id, @PathVariable("board") String board){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            if(currentUser.isBlocked()){
                return "redirect:/board/" + board + "/blocked";
            }

            if(!boardService.rateDownArticleProcess(currentUser, article_id, board))
                return "redirect:/board/" + board + "/article/" + article_id;
        }

        redirectAttributes.addAttribute("redirected", true);

        return "redirect:/board/" + board + "/article/" + article_id;
    }

    @GetMapping("/board/{board}/blocked")
    public String blockedPage(Model model, @PathVariable("board") String board){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails currentUserDetails){
            UserEntity currentUser = currentUserDetails.getUserEntity();

            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("userRole", currentUser.getRole());
            model.addAttribute("whyBlocked", currentUser.getBlockEntities().get(0).getWhyBlocked());
            model.addAttribute("board", board);
        }

        return "/user/error/isBlocked";
    }
}
