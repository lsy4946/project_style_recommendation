package faceweb.demo.TableRow;

import faceweb.demo.Entity.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArticleTableRow {
    //게시판 행 클래스
    private String title;
    private String writer;
    private String link;
    private String timeStamp;
    private long view;

    //ArticleEntity 리스트를 ArticleTableRow 리스트로 변환하는 메소드
    public static List<ArticleTableRow> getList(List<ArticleEntity> articleEntityList){
        List<ArticleTableRow> articleTableRows = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntityList) {
            ArticleTableRow articleTableRow = new ArticleTableRow();
            articleTableRow.setTitle(articleEntity.getTitle());
            articleTableRow.setWriter(articleEntity.getWriter().getName());
            articleTableRow.setLink("/board/user/article/"+ articleEntity.getArticleID());
            articleTableRow.setTimeStamp(articleEntity.getTimeStamp());
            articleTableRow.setView(articleEntity.getView());
            articleTableRows.add(articleTableRow);
        }
        return articleTableRows;
    }
    public static List<ArticleTableRow> getList(List<ArticleEntity> articleEntityList, String board){
        List<ArticleTableRow> articleTableRows = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntityList) {
            ArticleTableRow articleTableRow = new ArticleTableRow();
            articleTableRow.setTitle(articleEntity.getTitle());
            articleTableRow.setWriter(articleEntity.getWriter().getName());
            articleTableRow.setLink("/board/"+ board +"/article/"+ articleEntity.getArticleID());
            articleTableRow.setTimeStamp(articleEntity.getTimeStamp());
            articleTableRow.setView(articleEntity.getView());
            articleTableRows.add(articleTableRow);
        }
        return articleTableRows;
    }
}
