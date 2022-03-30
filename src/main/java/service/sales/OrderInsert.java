package service.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.CashDao;
import dao.ProductDao;
import dao.SalesDao;
import model.Cash;
import model.Product;
import model.SalesOrder;
import model.SalesOrderDetail;

public class OrderInsert implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//유효한 접근인지 확인
		if (request.getHeader("referer") == null || !request.getHeader("referer").contains("sales/orderInsertForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/sales/orderInsertResult.jsp";
		}
		
		//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
		//판매주문     : 판매주문번호, 판매처 번호, 판매 주문일, 주문등록 사원
		//판매주문 상세 : 판매주문번호, 상품번호, 상품주문수량
		//현금 		 : 현금 코드, 현금, 구매주문 번호, 판매주문 번호
		//상품		 : 상품번호, 상품명, 판매가, 매입가, 현재 재고량, 상품메모
		
		//상품이 판매가 되면 현금이 판매단가 * 주문수량 만큼 현금 증가+ / 주문수량만큼 상품 재고 감소-
		int 	sales_order_no 	= Integer.parseInt(request.getParameter("sales_order_no"));
		String 	customer_no 	= request.getParameter("customer_no");
		String	emp_no			= request.getParameter("emp_no");
		
		int 	product_no 				= Integer.parseInt(request.getParameter("product_no"));
		int		sales_detail_pcount		= Integer.parseInt(request.getParameter("sales_detail_pcount"));
		
		int 	price					= Integer.parseInt(request.getParameter("price"));
		
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setSales_order_no(sales_order_no);
		salesOrder.setCustomer_no	(customer_no);
		salesOrder.setEmp_no		(emp_no);
		
		SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
		salesOrderDetail.setSales_order_no		(sales_order_no);
		salesOrderDetail.setProduct_no			(product_no);
		salesOrderDetail.setSales_detail_pcount	(sales_detail_pcount);
		
		Cash cash = new Cash();
		cash.setCash_code		(CashDao.getInstance().getTotalCashCode() + 1);
		cash.setCash			(CashDao.getInstance().selectCash() + price*sales_detail_pcount);
		cash.setSales_order_no	(sales_order_no);
		
		Product product = ProductDao.getInstance().selectProduct(product_no);
		product.setStock(product.getStock() - sales_detail_pcount);
		
		int result = SalesDao.getInstance().insertSales(salesOrder, salesOrderDetail, cash, product);
		
		request.setAttribute("result", result);

		return "/view/sales/orderInsertResult.jsp";
	}

}
