package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class boardServiceImpl implements boardService {
	@Autowired
	private boardDAO dao;

	public void setDao(boardDAO dao) {
		this.dao = dao;
	}

	public List<Object> getArticles(int startRow,int endRow,String search,int searchn) {
		List<Object> list = null;
		
		if(search == null || search.equals(""))
			list = dao.getArticles(startRow, endRow);
		else
			list = dao.getArticles(startRow, endRow, searchn, search);
	

		return list;
	}
	
	public boardDataBean getArticle(int num) {
		boardDataBean list = null;
		System.out.println(num);
		try {                        //city는 mepper파일의 id값에 해당한다.
			list = dao.getArticle(num);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}
	
	public int getArticleCount(String search, int searchn){
		int count = 0;
		if(search == null || search.equals(""))
			count = dao.getArticleCount();
		else
			count = dao.getArticleCount(searchn,search);
		
		return count;
	}
 
	public int insert(boardDataBean article) {
		int check =0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
	
		
		int num=article.getNum();
		int ref=article.getRef();
		int re_step=article.getRe_step();
		int re_level=article.getRe_level();
		String number=null;
		int number1=0;
		
		number=dao.getMax();
		
		if(number!=null)
			number1=dao.getMax1();
		
		if(number!=null){
		number1 += 1;	
		}
		else{
			number1 = 1;
		}
		
		if(num!=0){
			dao.updateRef(article);
			re_step += 1;
			re_level = re_level+1;
		}else{
			ref=number1;
			re_step=0;
			re_level=0;
		}
		
		try {
			map.put("writer", article.getWriter());
			map.put("email", article.getEmail());
			map.put("subject", article.getSubject());
			map.put("passwd", article.getPasswd());
			map.put("reg_date", article.getReg_date());
			map.put("ref", ref);
			map.put("re_step", re_step);
			map.put("re_level", re_level);
			map.put("content", article.getContent());
			map.put("ip", article.getIp());		
			check = dao.Insert("myMem.insert", map);  //city는 내가 선택한 도시명
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return check;
	}

	public int delete(int num, String passwd) {
		int list;
		
		String check = dao.deletecheck(num);
		
		if(check.equals(passwd)){
		
		try {
			list = dao.delete(num);  //city는 내가 선택한 도시명
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return 1;
		}
		else{
			return 0;
		}
		
	}
	
	
	public int updateArticle(boardDataBean article,String passwd){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String pwcheck = dao.deletecheck(article.getNum());
		
		if(pwcheck.equals(article.getPasswd()))
		{
		
		map.put("num",article.getNum());
		map.put("writer",article.getWriter());
		map.put("email",article.getEmail());
		map.put("subject", article.getSubject());
		map.put("passwd", article.getPasswd());
		map.put("content", article.getContent());
		
		dao.update(map);
		
		System.out.println("updateArticle입니다");
		return 1;
		}
		
		else
			return 0;
		
	}
	
	public void readcount(int num){
		System.out.println("readcount()실행");
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("num", num);
		dao.readcount(num);
	}

}
