package service.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.ProductDao;

public class InsertForm implements Command {
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("product_no", ProductDao.getInstance().getTotalProduct() + 1);
		
		return "/view/product/insertForm.jsp";
	}
}