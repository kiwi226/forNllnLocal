package service.inventory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.ProductDao;
import model.Product;
import model.ProductModified;

public class ModifyStock implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if(request.getHeader("referer") == null || !request.getHeader("referer").contains("inventory/modifyStockForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/inventory/modifyStockResult.jsp";
		}
		int p = Integer.parseInt(request.getParameter("p"));
		
		//product_no를 갖는 product 뽑기
		int product_no = Integer.parseInt(request.getParameter("product_no"));
		Product product = ProductDao.getInstance().selectProduct(product_no);
		
		//해당 상품의 재고 수정
		int stock = Integer.parseInt(request.getParameter("stock"));
		product.setStock(stock);
		
		//상품 변동 내역 만들기
		int modified_stock = stock - Integer.parseInt(request.getParameter("modified_stock"));
		String modified_memo = request.getParameter("modified_memo");
		String emp_no = request.getParameter("emp_no");
		
		//상품 변동 내역 만들기
		ProductModified productModified = new ProductModified();
		productModified.setProduct_no(product_no);
		productModified.setEmp_no(emp_no);
		productModified.setModified_stock(modified_stock);
		productModified.setModified_memo(modified_memo);

		//상품 재고 수정, 상품 변동 내역 입력
		int result = ProductDao.getInstance().modifyProductStock(product, productModified);
		
		request.setAttribute("result", result);
		request.setAttribute("p", p);
		
		return "/view/inventory/modifyStockResult.jsp";
	}

}
