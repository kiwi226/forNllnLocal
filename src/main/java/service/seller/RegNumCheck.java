package service.seller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.SellerDao;
import model.Seller;

public class RegNumCheck implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null || !(request.getHeader("referer").contains("seller/insertForm")
				|| request.getHeader("referer").contains("seller/updateForm"))) {
			request.getSession().invalidate();
			return "/login/loginForm.do";
		}
		String reg_num = request.getParameter("seller_reg_num");
		Seller seller = SellerDao.getInstance().selectSellerWithRegNum(reg_num);

		int result = 0;

		// 해당 사업자 번호를 갖는 업체 등록되지 않음
		if (seller == null) {
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
