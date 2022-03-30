package service.seller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.SellerDao;
import model.Seller;

public class Insert implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null || !request.getHeader("referer").contains("seller/insertForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/seller/insertResult.jsp";
		}

		// 업체코드, 업체명, 사업자번호, 전화번호 이메일 주소(우편번호 주소 상세주소) 담당자 참고사항
		Seller seller = new Seller();
		seller.setSeller_no			(request.getParameter("seller_no"));
		seller.setSeller_name		(request.getParameter("seller_name"));
		seller.setSeller_reg_num	(request.getParameter("seller_reg_num"));
		seller.setSeller_tel		(request.getParameter("seller_tel"));
		seller.setSeller_email		(request.getParameter("seller_email"));
		seller.setSeller_addr_no	(request.getParameter("seller_addr_no"));
		seller.setSeller_addr		(request.getParameter("seller_addr"));
		seller.setSeller_addr_detail(request.getParameter("seller_addr_detail"));
		seller.setEmp_no			(request.getParameter("emp_no"));
		seller.setSeller_memo		(request.getParameter("seller_memo"));

		int result = SellerDao.getInstance().insertSeller(seller);

		request.setAttribute("result", result);
		
		return "/view/seller/insertResult.jsp";
	}

}
