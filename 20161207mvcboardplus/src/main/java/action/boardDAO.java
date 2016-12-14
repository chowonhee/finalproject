package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Controller;

public class boardDAO extends SqlSessionDaoSupport { // sql세션팩토링 주입받으면
														// SqlSessionDaoSupport
														// 사용가능

	public List<Object> getArticles(int startRow, int endRow) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		List<Object> getArticles = getSqlSession().selectList("myMem.getArticles", map);
		System.out.println(getArticles.size());
		return getArticles;
	}

	public int getArticleCount() {
		int count = getSqlSession().selectOne("myMem.getArticleCount", Integer.class); // cityMapper																		// 실행
		return count;
	}

	public boardDataBean getArticle(int num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("num", num);
		boardDataBean getArticle = getSqlSession().selectOne("myMem.getArticle", map);
		return getArticle;
	}

	public int Insert(String string, Map<String, Object> list) {
		int check = getSqlSession().insert(string, list);
		return check;
	}

	public String deletecheck(int num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("num", num);

		String passwd = getSqlSession().selectOne("myMem.deletepw", map);

		return passwd;
	}

	public int delete(int num) {
		int check = getSqlSession().delete("myMem.delete", num);
		return check;
	}

	public int update(Map<String, Object> map) {
		getSqlSession().update("myMem.update", map);
		return 1;
	}

	public String getMax() {
		String number = getSqlSession().selectOne("myMem.getMax");
		return number;
	}

	public int getMax1() {
		
		int number = getSqlSession().selectOne("myMem.getMax1");
		
		return number;
	}

	public void updateRef(boardDataBean article) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("ref", article.getRef());
		map.put("re_step", article.getRe_step());
		getSqlSession().update("myMem.updateRef", map);

	}

	public void readcount(int num) {
		int i = getSqlSession().update("myMem.readcount", num);
	}

	public int getArticleCount(int n, String searchKeyword){
		int x = 0;
		Map map = new HashMap<String,Object>();	
		map.put("searchN", n);
		map.put("searchKeyword", searchKeyword);
		x = getSqlSession().selectOne("myMem.getArticleCount", Integer.class); // cityMapper		
		return x;
	}

	public List getArticles(int start, int end, int n, String searchKeyword){
		Map map = new HashMap<String,Object>();
		map.put("startRow", start);
		map.put("endRow", end);
		map.put("searchN", n);
		map.put("searchKeyword", searchKeyword);
		
		List list = getSqlSession().selectList("myMem.searchgetArticles",map);
		return list;
	}

}