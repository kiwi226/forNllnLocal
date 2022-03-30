package service.seller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.SellerDao;

public class InsertForm implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String count = "000" + (SellerDao.getInstance().getTotalSeller() + 1);
		count = count.substring(count.length() - 4);
		
		String seller_no = "S" + count;
		
		request.setAttribute("seller_no", seller_no);
		
		
		return "/view/seller/insertForm.jsp";
	}

}
