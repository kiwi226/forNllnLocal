package service.purchase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.PurchaseDao;
import model.Purchase;
import model.SearchOption;

public class OrderSearchList implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}

		PurchaseDao pd = PurchaseDao.getInstance();
		// 페이지당 열 개수
		final int ROW_PER_PAGE = 10;

		// 페이지 버튼 블럭당 페이지 개수
		final int PAGE_PER_BLOCK = 5;

		// 검색옵션 만들기
		SearchOption options = new SearchOption();
		// from 선택 안했으면 현재 날짜로부터 1년 전
		if (request.getParameter("from") == null || request.getParameter("from").equals("")) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -1);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			options.setFrom(sdf.format(cal.getTime()));
		} else {
			options.setFrom(request.getParameter("from"));
		}
		// to 선택 안했으면 현재 날짜
		if (request.getParameter("to") == null || request.getParameter("to").equals("")) {
			Calendar cal = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			options.setTo(sdf.format(cal.getTime()));
		} else {
			options.setTo(request.getParameter("to"));
		}
		// 검색 필드 설정하지 않았으면 주문번호로 검색
		if (request.getParameter("searchField").equals("0")) {
			options.setSearchField("purchase_order_no");
			options.setKeyword("");
		} else {
			options.setSearchField(request.getParameter("searchField"));
			options.setKeyword(request.getParameter("keyword"));
		}

		// 마지막 페이지 구하기
		int endPage = (pd.getTotalPurchaseSearch(options) - 1) / ROW_PER_PAGE + 1;

		// 현재 페이지(기본값은 1페이지)
		int p = 1;

		if (request.getParameter("p") != null && !request.getParameter("p").equals("")) {
			p = Integer.parseInt(request.getParameter("p"));
		}

		// 페이지 값이 1보다 작으면 페이지 값은 1
		// 페이지 값이 마지막 페이지보다 크면 페이지 값은 마지막 페이지
		p = p < 1 ? 1 : p;
		p = p > endPage ? endPage : p;

		// 꺼내올 첫번째 열 = (현재 페이지 - 1) * 페이지 당 열 개수 + 1;
		// 꺼내올 마지막 열 = 현재 페이지 * 페이지당 열 개수
		int firstRow = (p - 1) * ROW_PER_PAGE + 1;
		int lastRow = p * ROW_PER_PAGE;

		// pageButton에 넣을 변수 만들기
		int firstPage = PAGE_PER_BLOCK * ((p - 1) / PAGE_PER_BLOCK) + 1;
		int lastPage = PAGE_PER_BLOCK * ((p - 1) / PAGE_PER_BLOCK + 1);

		firstPage = firstPage < 1 ? 1 : firstPage;
		lastPage = lastPage > endPage ? endPage : lastPage;

		List<Purchase> purchaseList = pd.purchaseSearchList(firstRow, lastRow, options);

		request.setAttribute("p", p);
		request.setAttribute("firstPage", firstPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("purchaseList", purchaseList);

		return "/view/purchase/orderSearchList.jsp";
	}

}
