package service.seller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.SellerDao;
import model.Seller;
import model.SearchOption;

public class SearchWindow implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}

		if (request.getParameter("keyword") == null || request.getParameter("keyword").trim().equals("")) {
			List<Seller> sellerList = SellerDao.getInstance().sellerList();

			request.setAttribute("searchList", sellerList);

			return "/view/seller/searchWindow.jsp";
		}

		SearchOption options = new SearchOption();
		options.setSearchField(request.getParameter("searchField"));
		options.setKeyword(request.getParameter("keyword"));

		List<Seller> searchList = SellerDao.getInstance().searchSeller(options);

		request.setAttribute("searchList", searchList);

		return "/view/seller/searchWindow.jsp";
	}

}
