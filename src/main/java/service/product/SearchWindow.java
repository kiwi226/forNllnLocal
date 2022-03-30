package service.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.CashDao;
import dao.ProductDao;
import model.Product;
import model.SearchOption;

public class SearchWindow implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}
		
		if(request.getParameter("keyword") == null || request.getParameter("keyword").trim().equals("")) {
			List<Product> productList = ProductDao.getInstance().productList();
			
			request.setAttribute("searchList", productList);
			request.setAttribute("cash", CashDao.getInstance().selectCash());
			
			return "/view/product/searchWindow.jsp";
		}
		
		SearchOption options = new SearchOption();
		options.setSearchField	(request.getParameter("searchField"));
		options.setKeyword		(request.getParameter("keyword"));
		
		List<Product> searchList = ProductDao.getInstance().searchProduct(options);
		
		request.setAttribute("searchList", searchList);
		request.setAttribute("cash", CashDao.getInstance().selectCash());
		
		return "/view/product/searchWindow.jsp";
	}

}
