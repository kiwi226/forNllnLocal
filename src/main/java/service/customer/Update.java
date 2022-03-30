package service.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Customer;
import dao.CustomerDao;

public class Update implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//유효한 접근인지 확인 
		if(request.getHeader("referer") == null || !request.getHeader("referer").contains("customer/updateForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/customer/updateResult.jsp";
		}
		
		//업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 담당자 사번, 참고, 삭제 
		Customer customer = new Customer();
		customer.setCustomer_no				(request.getParameter("customer_no"));
		customer.setCustomer_name			(request.getParameter("customer_name"));
		customer.setCustomer_reg_num		(request.getParameter("customer_reg_num"));
		customer.setCustomer_tel			(request.getParameter("customer_tel"));
		customer.setCustomer_email			(request.getParameter("customer_email"));
		customer.setCustomer_addr_no		(request.getParameter("customer_addr_no"));
		customer.setCustomer_addr			(request.getParameter("customer_addr"));
		customer.setCustomer_addr_detail	(request.getParameter("customer_addr_detail"));
		customer.setEmp_no					(request.getParameter("emp_no"));
		customer.setCustomer_memo			(request.getParameter("customer_memo"));
		
		int result = CustomerDao.getInstance().updateCustomer(customer);
		
		request.setAttribute("result", result);
		
		return "/view/customer/updateResult.jsp";
	}

}
