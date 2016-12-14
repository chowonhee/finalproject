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
	private boardService service; // CityServiceImpl��ü�� �޾ƿ´�. CityService
									// interface�̹Ƿ�

	public void setService(boardService service) {
		this.service = service;
	}

	@RequestMapping(value = "/list.do")
	public String view(String pageNum, String search,@RequestParam(defaultValue="0") int searchn, Model model) throws Throwable {
		System.out.println("/list.do��û");

		if (pageNum == null) {
			pageNum = "1";
		}
		int pageSize = 5;// �� �������� ���� ����
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;// �� �������� ���۱� ��ȣ
		int endRow = currentPage * pageSize;// �� �������� ������ �۹�ȣ
		int count = 0;
		int number = 0;

		List articleList = null;

		count = service.getArticleCount(search, searchn );

		if (count > 0) {
			articleList = service.getArticles(startRow, endRow,search, searchn);// ���� �������� �ش��ϴ�													// �� ���
		} else {
			articleList = Collections.EMPTY_LIST;
		}

		number = count - (currentPage - 1) * pageSize;// �۸�Ͽ� ǥ���� �۹�ȣ
		// �ش� �信�� ����� �Ӽ�
		model.addAttribute("currentPage", new Integer(currentPage));
		model.addAttribute("startRow", new Integer(startRow));
		model.addAttribute("endRow", new Integer(endRow));
		model.addAttribute("count", new Integer(count));
		model.addAttribute("pageSize", new Integer(pageSize));
		model.addAttribute("number", new Integer(number));
		model.addAttribute("articleList", articleList);

		return "list";// �ش� ��
	}

	@RequestMapping(value = "/content.do", method = RequestMethod.GET)
	public String requestPro(@RequestParam("num") int num, String pageNum, Model model) {

		boardDataBean articleList = null;
		service.readcount(num);
		articleList = service.getArticle(num);// �ش� �۹�ȣ�� ���� �ش� ���ڵ�
		System.out.println("articleList::" + articleList.getReadcount());

		// �ش� �信�� ����� �Ӽ�
		model.addAttribute("num", new Integer(num));
		model.addAttribute("pageNum", new Integer(pageNum));
		model.addAttribute("article", articleList);

		return "content";// �ش� ��
	}

	@RequestMapping(value = "/writeForm.do")
	public String write(@ModelAttribute("board") boardDataBean board) throws Throwable {
		System.out.println(board.getNum());
		return "writeForm";// �ش� ��
	}

	@RequestMapping(value = "/writePro.do")
	public String writePro(boardDataBean article, HttpServletRequest request) throws Throwable {
		article.setIp(request.getRemoteAddr());
		service.insert(article);
		return "writePro";
	}

	@RequestMapping(value = "/deleteForm.do")
	public String delete(int num, String pageNum, Model model) throws Throwable {

		// �ش� �信�� ����� �Ӽ�
		model.addAttribute("num", new Integer(num));
		model.addAttribute("pageNum", new Integer(pageNum));

		return "deleteForm";// �ش��
	}

	@RequestMapping(value = "/deletePro.do")
	public String deletePro(int num, String pageNum, String passwd, Model model) throws Throwable {

		int check = service.delete(num, passwd);

		// �ش� �信�� ����� �Ӽ�
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("check", check);

		return "deletePro";// �ش��
	}

	@RequestMapping(value = "/updateForm.do")
	public String update(int num, String pageNum,Model model) throws Throwable {

		boardDataBean article = service.getArticle(num);

		// �ش� �信�� ����� �Ӽ�
		model.addAttribute("pageNum", new Integer(pageNum));
		model.addAttribute("article", article);

		return "updateForm";// �ش��
	}

	@RequestMapping(value = "/updatePro.do")
	public String updatePro(boardDataBean article, String pageNum, Model model) throws Throwable {
		
		int check = service.updateArticle(article, article.getPasswd());

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("check", check);

		return "updatePro";
	}
}
