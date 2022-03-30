package service.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.PurchaseDao;

public class OrderInsertForm implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("purchase_order_no", PurchaseDao.getInstance().getTotalPurchase() + 1);
		
		return "/view/purchase/orderInsertForm.jsp";
	}

}
