package action;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class boardController {
	@Autowired
	private boardService service; // CityServiceImpl객체를 받아온다. CityService
									// interface이므로

	public void setService(boardService service) {
		this.service = service;
	}

	@RequestMapping(value = "/list.do")
	public String view(String pageNum, String search,@RequestParam(defaultValue="0") int searchn, Model model) throws Throwable {
		System.out.println("/list.do요청");

		if (pageNum == null) {
			pageNum = "1";
		}
		int pageSize = 5;// 한 페이지의 글의 개수
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;// 한 페이지의 시작글 번호
		int endRow = currentPage * pageSize;// 한 페이지의 마지막 글번호
		int count = 0;
		int number = 0;

		List articleList = null;

		count = service.getArticleCount(search, searchn );

		if (count > 0) {
			articleList = service.getArticles(startRow, endRow,search, searchn);// 현재 페이지에 해당하는													// 글 목록
		} else {
			articleList = Collections.EMPTY_LIST;
		}

		number = count - (currentPage - 1) * pageSize;// 글목록에 표시할 글번호
		// 해당 뷰에서 사용할 속성
		model.addAttribute("currentPage", new Integer(currentPage));
		model.addAttribute("startRow", new Integer(startRow));
		model.addAttribute("endRow", new Integer(endRow));
		model.addAttribute("count", new Integer(count));
		model.addAttribute("pageSize", new Integer(pageSize));
		model.addAttribute("number", new Integer(number));
		model.addAttribute("articleList", articleList);

		return "list";// 해당 뷰
	}

	@RequestMapping(value = "/content.do", method = RequestMethod.GET)
	public String requestPro(@RequestParam("num") int num, String pageNum, Model model) {

		boardDataBean articleList = null;
		service.readcount(num);
		articleList = service.getArticle(num);// 해당 글번호에 대한 해당 레코드
		System.out.println("articleList::" + articleList.getReadcount());

		// 해당 뷰에서 사용할 속성
		model.addAttribute("num", new Integer(num));
		model.addAttribute("pageNum", new Integer(pageNum));
		model.addAttribute("article", articleList);

		return "content";// 해당 뷰
	}

	@RequestMapping(value = "/writeForm.do")
	public String write(@ModelAttribute("board") boardDataBean board) throws Throwable {
		System.out.println(board.getNum());
		return "writeForm";// 해당 뷰
	}

	@RequestMapping(value = "/writePro.do")
	public String writePro(boardDataBean article, HttpServletRequest request) throws Throwable {
		article.setIp(request.getRemoteAddr());
		service.insert(article);
		return "writePro";
	}

	@RequestMapping(value = "/deleteForm.do")
	public String delete(int num, String pageNum, Model model) throws Throwable {

		// 해당 뷰에서 사용할 속성
		model.addAttribute("num", new Integer(num));
		model.addAttribute("pageNum", new Integer(pageNum));

		return "deleteForm";// 해당뷰
	}

	@RequestMapping(value = "/deletePro.do")
	public String deletePro(int num, String pageNum, String passwd, Model model) throws Throwable {

		int check = service.delete(num, passwd);

		// 해당 뷰에서 사용할 속성
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("check", check);

		return "deletePro";// 해당뷰
	}

	@RequestMapping(value = "/updateForm.do")
	public String update(int num, String pageNum,Model model) throws Throwable {

		boardDataBean article = service.getArticle(num);

		// 해당 뷰에서 사용할 속성
		model.addAttribute("pageNum", new Integer(pageNum));
		model.addAttribute("article", article);

		return "updateForm";// 해당뷰
	}

	@RequestMapping(value = "/updatePro.do")
	public String updatePro(boardDataBean article, String pageNum, Model model) throws Throwable {
		
		int check = service.updateArticle(article, article.getPasswd());

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("check", check);

		return "updatePro";
	}
}
