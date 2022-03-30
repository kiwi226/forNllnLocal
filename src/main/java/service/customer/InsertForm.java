package service.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.CustomerDao;

public class InsertForm implements Command {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String customerCount = "000" + (CustomerDao.getInstance().getTotalCustomer() + 1);
		customerCount = customerCount.substring(customerCount.length() - 4);
		
		String customer_no = "C" + customerCount;
		
		request.setAttribute("customer_no", customer_no);
		
		return "/view/customer/insertForm.jsp";
	}

}
