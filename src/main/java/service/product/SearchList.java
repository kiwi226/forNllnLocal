package service.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.ProductDao;
import model.Product;
import model.SearchOption;

public class SearchList implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}

		ProductDao pd = ProductDao.getInstance();
		// 페이지당 열 개수
		final int ROW_PER_PAGE = 10;

		// 페이지 버튼 블럭당 페이지 개수
		final int PAGE_PER_BLOCK = 5;

		// 검색옵션 만들기
		SearchOption options = new SearchOption();
		
		// 검색 필드 설정하지 않았으면 상품번호로 검색
		if (request.getParameter("searchField").equals("0")) {
			options.setSearchField("product_no");
			options.setKeyword("");
		} else {
			options.setSearchField(request.getParameter("searchField"));
			options.setKeyword(request.getParameter("keyword"));
		}

		// 마지막 페이지 구하기
		int endPage = (pd.getTotalSearchProduct(options) - 1) / ROW_PER_PAGE + 1;

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

		List<Product> productList = ProductDao.getInstance().searchProductList(firstRow, lastRow, options);

		request.setAttribute("p", p);
		request.setAttribute("firstPage", firstPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("productList", productList);
		
		return "/view/product/searchList.jsp";
	}

}
