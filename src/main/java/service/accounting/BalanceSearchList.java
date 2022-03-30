package service.accounting;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import dao.PurchaseDao;
import dao.SalesDao;
import model.Balance;
import model.Purchase;
import model.Sales;

public class BalanceSearchList implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String month = request.getParameter("month").substring(2).replace("-", "/");
		
		List<Purchase> 	purchaseList = PurchaseDao.getInstance().purchaseList(month);
		List<Sales>		salesList	 = SalesDao.getInstance().salesList(month);
		
		List<Balance> 	balanceList  = new ArrayList<Balance>();
		
		int totalPurchase = 0;
		int totalSales = 0;
		
		for (int i = 0; i < purchaseList.size(); i++) {
			Balance balance = new Balance();
			balance.setPurchase(purchaseList.get(i));
			
			for (int j = 0; j < salesList.size(); j++) {
				if (salesList.get(j).getProduct_no() == purchaseList.get(i).getProduct_no()) {
					balance.setSales(salesList.get(j));
				}
			}
			
			if (balance.getSales() == null) {
				Sales sales = new Sales();
				sales.setProduct_no			(purchaseList.get(i).getProduct_no());
				sales.setProduct_name		(purchaseList.get(i).getProduct_name());
				sales.setPrice				(purchaseList.get(i).getPrice());
				sales.setSales_detail_pcount(0);
				
				balance.setSales(sales);
			}
			
			totalPurchase += purchaseList.get(i).getCost() * purchaseList.get(i).getPurchase_detail_pcount();
			
			balanceList.add(balance);
		}
		
		for (int j = 0; j < salesList.size(); j++) {
			int product_no = salesList.get(j).getProduct_no();
			
			int result = 0;
			for (int i = 0; i < balanceList.size(); i++) {
				if(balanceList.get(i).getPurchase().getProduct_no() == product_no) {
					result = 1;
				}
			}
			
			if (result == 0) {
				Balance balance = new Balance();
				balance.setSales(salesList.get(j));
				
				Purchase purchase = new Purchase();
				
				purchase.setProduct_no			  (salesList.get(j).getProduct_no());
				purchase.setProduct_name		  (salesList.get(j).getProduct_name());
				purchase.setCost				  (salesList.get(j).getCost());
				purchase.setPurchase_detail_pcount(0);
				
				balance.setPurchase(purchase);
				
				balanceList.add(balance);
			}
			totalSales += salesList.get(j).getPrice() * salesList.get(j).getSales_detail_pcount();
		}
		
		request.setAttribute("month", request.getParameter("month"));
		request.setAttribute("totalPurchase", totalPurchase);
		request.setAttribute("totalSales", totalSales);
		request.setAttribute("balanceList", balanceList);
		
		return "/view/accounting/balanceSearchList.jsp";
	}

}
