package service.seller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.HrDao;
import dao.SellerDao;
import model.Hr;
import model.Seller;

public class UpdateForm implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null || !request.getHeader("referer").contains("seller/list.do")) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}
		
		String seller_no = request.getParameter("seller_no");
		
		Seller seller = SellerDao.getInstance().selectSeller(seller_no);
		Hr hr = HrDao.getInstance().selectHr(seller.getEmp_no());
		
		request.setAttribute("seller", seller);
		request.setAttribute("Hr", hr);

		return "/view/seller/updateForm.jsp";
	}

}
