package service.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.CashDao;
import dao.ProductDao;
import dao.PurchaseDao;
import model.Cash;
import model.Product;
import model.PurchaseOrder;
import model.PurchaseOrderDetail;

public class OrderInsert implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//유효한 접근인지 확인
		if (request.getHeader("referer") == null || !request.getHeader("referer").contains("purchase/orderInsertForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/purchase/orderInsertResult.jsp";
		}
		
		//구매주문일, 주문번호, 구매처코드, 구매처명, 상품코드, 상품명, 구매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
		//구매주문     : 구매주문번호, 구매처 번호, 구매 주문일, 주문등록 사원
		//구매주문 상세 : 구매주문번호, 상품번호, 상품주문수량
		//현금 		 : 현금 코드, 현금, 구매주문 번호, 구매주문 번호
		//상품		 : 상품번호, 상품명, 판매가, 매입가, 현재 재고량, 상품메모
		
		//상품이 구매가 되면 현금이 매입단가 * 주문수량 만큼 현금 감소- / 주문수량만큼 상품 재고 증가
		int 	purchase_order_no 		= Integer.parseInt(request.getParameter("purchase_order_no"));
		String 	seller_no 				= request.getParameter("seller_no");
		String	emp_no					= request.getParameter("emp_no");
		
		int 	product_no 				= Integer.parseInt(request.getParameter("product_no"));
		int		purchase_detail_pcount	= Integer.parseInt(request.getParameter("purchase_detail_pcount"));
		
		int 	cost					= Integer.parseInt(request.getParameter("cost"));
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPurchase_order_no	(purchase_order_no);
		purchaseOrder.setSeller_no			(seller_no);
		purchaseOrder.setEmp_no				(emp_no);
		
		PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
		purchaseOrderDetail.setPurchase_order_no		(purchase_order_no);
		purchaseOrderDetail.setProduct_no				(product_no);
		purchaseOrderDetail.setPurchase_detail_pcount	(purchase_detail_pcount);
		
		//상품이 구매가 되면 현금이 매입단가 * 주문수량 만큼 현금 감소-
		Cash cash = new Cash();
		cash.setCash_code		(CashDao.getInstance().getTotalCashCode() + 1);
		cash.setCash			(CashDao.getInstance().selectCash() - cost*purchase_detail_pcount);
		cash.setPurchase_order_no	(purchase_order_no);
		
		Product product = ProductDao.getInstance().selectProduct(product_no);
		product.setStock(product.getStock() + purchase_detail_pcount);
		
		int result = PurchaseDao.getInstance().insertPurchase(purchaseOrder, purchaseOrderDetail, cash, product);
		
		request.setAttribute("result", result);

		return "/view/purchase/orderInsertResult.jsp";
	}

}
