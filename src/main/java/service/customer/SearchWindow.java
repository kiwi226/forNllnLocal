package service.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.CustomerDao;
import model.Customer;
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
			List<Customer> customerList = CustomerDao.getInstance().customerList();
			
			request.setAttribute("searchList", customerList);
			
			return "/view/customer/searchWindow.jsp";
		}
		
		SearchOption options = new SearchOption();
		options.setSearchField	(request.getParameter("searchField"));
		options.setKeyword		(request.getParameter("keyword"));
		
		List<Customer> searchList = CustomerDao.getInstance().searchCustomer(options);
		
		request.setAttribute("searchList", searchList);
		
		return "/view/customer/searchWindow.jsp";
	}

}
