package service.hr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.HrDao;
import model.Hr;

public class SearchList implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HrDao hd = HrDao.getInstance();
		//페이지당 열 개수
		final int ROW_PER_PAGE = 10;
		
		//페이지 버튼 블럭당 페이지 개수
		final int PAGE_PER_BLOCK = 5;
		
		//현재 페이지(기본값은 1페이지)
		int p = 1;
		
		if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
			p = Integer.parseInt(request.getParameter("p"));
		}
		
		//mybatis에 넣어줄 Map만들기
		Map<String, String> param = new HashMap<>();
		if (request.getParameter("searchField").equals("0")) {
			param.put("searchField", "emp_no");
			param.put("keyword", "");
		} else {
			param.put("searchField", request.getParameter("searchField"));
			param.put("keyword", request.getParameter("keyword"));
		}
		
		//마지막 페이지 구하기
		int endPage = (hd.countHrSearchList(param)-1)/ROW_PER_PAGE + 1;
		
		//페이지 값이 1보다 작으면 페이지 값은 1
		//페이지 값이 마지막 페이지보다 크면 페이지 값은 마지막 페이지
		p = p < 1 ? 1 : p;
		p = p > endPage ? endPage : p;
		
		//꺼내올 첫번째 열 = (현재 페이지 - 1) * 페이지 당 열 개수 + 1;
		//꺼내올 마지막 열 = 현재 페이지 * 페이지당 열 개수
		int firstRow	= (p-1)*ROW_PER_PAGE + 1;
		int lastRow		= p*ROW_PER_PAGE;
		
		param.put("firstRow", ""+firstRow);
		param.put("lastRow", ""+lastRow);
		
		//pageButton에 넣을 변수 만들기
		int firstPage = PAGE_PER_BLOCK*( (p-1)/PAGE_PER_BLOCK ) + 1;  
		int lastPage = PAGE_PER_BLOCK*( (p-1)/PAGE_PER_BLOCK + 1);
		
		firstPage = firstPage < 1 ? 1 : firstPage;
		lastPage = lastPage > endPage ? endPage : lastPage;
		
		List<Hr> searchList = hd.selectHrSearchList(param);
		
		request.setAttribute("hrList", searchList);
		request.setAttribute("p", p);
		request.setAttribute("firstPage", firstPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("searchField", request.getParameter("searchField"));
		request.setAttribute("keyword", request.getParameter("keyword"));
		
		return "/view/hr/searchList.jsp";
	}

}
