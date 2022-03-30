package service.inventory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.ProductDao;
import model.Product;

public class ModifyStockForm implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int p = Integer.parseInt(request.getParameter("p"));
		int product_no = Integer.parseInt(request.getParameter("product_no"));
		
		ProductDao pd = ProductDao.getInstance();
		Product product = pd.selectProduct(product_no);
		
		request.setAttribute("p", p);
		request.setAttribute("product",product);
		
		return "/view/inventory/modifyStockForm.jsp";
	}

}
