package service.seller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.SellerDao;
import model.Seller;

public class Update implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null || !request.getHeader("referer").contains("seller/updateForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/seller/updateResult.jsp";
		}
		
		Seller seller = new Seller();
		String seller_no  			= request.getParameter("seller_no");
		String seller_name 			= request.getParameter("seller_name");
		String seller_reg_num 		= request.getParameter("seller_reg_num");
		String seller_tel 			= request.getParameter("seller_tel");
		String seller_email 		= request.getParameter("seller_email");
		String seller_addr_no 		= request.getParameter("seller_addr_no");
		String seller_addr 			= request.getParameter("seller_addr");
		String seller_addr_detail 	= request.getParameter("seller_addr_detail"); 
		String emp_no 				= request.getParameter("emp_no");
		String seller_memo 			= request.getParameter("seller_memo");

		seller.setSeller_no			(seller_no); 
		seller.setSeller_name		(seller_name);
		seller.setSeller_reg_num	(seller_reg_num);		
		seller.setSeller_tel		(seller_tel);				
		seller.setSeller_email		(seller_email);			
		seller.setSeller_addr_no	(seller_addr_no);
		seller.setSeller_addr		(seller_addr);				
		seller.setSeller_addr_detail(seller_addr_detail);
		seller.setEmp_no			(emp_no);						
		seller.setSeller_memo		(seller_memo);
		
		int result = SellerDao.getInstance().updateSeller(seller);
		
		request.setAttribute("result", result);
		
		return "/view/seller/updateResult.jsp";
	}

}
