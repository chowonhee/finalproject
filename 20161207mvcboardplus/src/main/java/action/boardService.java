package action;

import java.util.List;

import org.springframework.stereotype.Controller;
@Controller
public interface boardService {
	public List<Object> getArticles(int startRow,int endRow,String search, int searchn);
	public boardDataBean getArticle(int num);
	public int getArticleCount(String search, int searchn);
	public int insert(boardDataBean article);
	public int delete(int num, String passwd);
	public int updateArticle(boardDataBean article,String passwd);
	public void readcount(int num);
}
