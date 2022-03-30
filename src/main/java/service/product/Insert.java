package service.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Product;
import dao.ProductDao;

public class Insert implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 유효한 접근인지 확인
		if (request.getHeader("referer") == null || !request.getHeader("referer").contains("product/insertForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/product/insertResult.jsp";
		}
		
		Product product = new Product();
		
		int 	product_no 		= Integer.parseInt(request.getParameter("product_no"));
		String 	product_name 	= request.getParameter("product_name");
		int 	price 			= Integer.parseInt(request.getParameter("price"));
		int 	cost 			= Integer.parseInt(request.getParameter("cost"));
		String 	product_memo 	= request.getParameter("product_memo");
		
		product.setProduct_no	(product_no);
		product.setProduct_name	(product_name);
		product.setPrice		(price);					
		product.setCost			(cost);
		product.setStock		(0);	 				
		product.setProduct_memo	(product_memo);
		
		int result = ProductDao.getInstance().insertProduct(product);
		request.setAttribute("result", result);
		
		return "/view/product/insertResult.jsp";
	}

}
