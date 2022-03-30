package service.customer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.CustomerDao;
import model.Customer;

public class RegNumCheck implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//유효한 접근인지 확인 
		if(request.getHeader("referer") == null || !(request.getHeader("referer").contains("customer/insertForm") || request.getHeader("referer").contains("customer/updateForm"))) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}
		String reg_num = request.getParameter("customer_reg_num");
		Customer customer = CustomerDao.getInstance().selectCustomerWithRegNum(reg_num);
		
		int result = 0;
		
		//해당 사업자 번호를 갖는 업체 등록되지 않음
		if(customer == null) {
			result = 1;
		} else {
			result = 0;
		}
		
		try {
			response.getWriter().println(result);
		} catch (IOException e) {
			
		}
		return null;
	}

}
