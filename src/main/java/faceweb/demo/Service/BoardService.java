package faceweb.demo.Service;

import faceweb.demo.DTO.ArticleDTO;
import faceweb.demo.DTO.CommentDTO;
import faceweb.demo.Entity.*;
import faceweb.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BoardService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final RateUpArticleRepository rateUpRepository;
    private final RateDownArticleRepository rateDownArticleRepository;
    @Autowired
    public BoardService(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository, RateUpArticleRepository rateUpRepository, RateDownArticleRepository rateDownArticleRepository){
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.rateUpRepository = rateUpRepository;
        this.rateDownArticleRepository = rateDownArticleRepository;
    }

    public boolean writeArticleProcess(ArticleDTO articleDTO, UserEntity currentUser){
        return this.writeArticleProcess(articleDTO, currentUser, "user");
    }
    public boolean writeArticleProcess(ArticleDTO articleDTO, UserEntity currentUser, String board){

        ArticleEntity newArticle = new ArticleEntity();

        newArticle.setTitle(articleDTO.getTitle());
        newArticle.setContent(articleDTO.getContent());
        newArticle.setWriter(currentUser);
        newArticle.setBoard(board);
        newArticle.setView(0);

        newArticle.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        try{
            articleRepository.save(newArticle);
            System.out.println("post saved");
        }
        catch (RuntimeException exception){
            System.out.println("exception occurred");
            System.out.println(exception.getCause().toString());
            System.out.println(exception.getMessage());
            return false;
        }

        return true;
    }

    // 최근 15행 가져오기
    public List<ArticleEntity> getRecent15Rows() {
        return articleRepository.findTop15ByOrderByArticleIDDesc();
    }
    public List<ArticleEntity> getRecent15Rows(String board){
        Pageable pageable = PageRequest.of(0, 15, Sort.by("articleID").descending());
        return articleRepository.findTop15ByBoardOrderByArticleIDDesc(board, pageable);
    }

    // 다음 15행 가져오기
    public List<ArticleEntity> getNext15Rows(int page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("articleID").descending());
        return articleRepository.findTop15ByOrderByArticleIDDesc(pageable);
    }
    public List<ArticleEntity> getNext15Rows(int page, String board) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("articleID").descending());
        return articleRepository.findTop15ByBoardOrderByArticleIDDesc(board, pageable);
    }

    public ArticleEntity getArticleByArticleID(long articleID){
        return articleRepository.findByArticleID(articleID);
    }
    public CommentEntity getCommentByCommentID(long commentID) { return commentRepository.findByCommentID(commentID); }

    //게시글 저장
    public void saveArticleEntity(ArticleEntity article){
        articleRepository.save(article);
    }

    //댓글 저장
    public boolean writeCommentProcess(CommentDTO commentDTO, UserEntity currentUser){
        CommentEntity newComment = new CommentEntity();

        newComment.setWriter(currentUser);
        newComment.setContent(commentDTO.getContent());
        newComment.setArticle(articleRepository.findByArticleID(commentDTO.getArticleID()));
        newComment.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        commentRepository.save(newComment);

        return true;
    }

    //게시글 식별번호로 게시글 삭제
    public boolean deleteArticleByID(long articleID){
        articleRepository.deleteById(articleID);
        return true;
    }

    //댓글 식별번호로 댓글 삭제
    public boolean deleteCommentByID(long commentID){
        commentRepository.deleteById(commentID);
        return true;
    }

    //추천 저장
    public boolean rateUpArticleProcess(UserEntity currentUser, long articleID, String board){

        ArticleEntity currentArticle = articleRepository.findByArticleID(articleID);

        if(rateUpRepository.existsByArticleAndRater(currentArticle, currentUser) || !currentArticle.getBoard().equals(board))
            return false;

        RateUpArticleEntity rateUpEntity = new RateUpArticleEntity();

        rateUpEntity.setArticle(articleRepository.findByArticleID(articleID));
        rateUpEntity.setRater(currentUser);

        rateUpRepository.save(rateUpEntity);

        return true;
    }

    //비추천 저장
    public boolean rateDownArticleProcess(UserEntity currentUser, long articleID, String board){
        ArticleEntity currentArticle = articleRepository.findByArticleID(articleID);

        if(rateDownArticleRepository.existsByArticleAndRater(currentArticle, currentUser) || !currentArticle.getBoard().equals(board))
            return false;

        RateDownArticleEntity rateDownArticleEntity = new RateDownArticleEntity();

        rateDownArticleEntity.setArticle(currentArticle);
        rateDownArticleEntity.setRater(currentUser);

        rateDownArticleRepository.save(rateDownArticleEntity);

        return true;
    }
}
