package service.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.SalesDao;

public class OrderInsertForm implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("sales_order_no", SalesDao.getInstance().getTotalSales() + 1);
		
		
		return "/view/sales/orderInsertForm.jsp";
	}

}
