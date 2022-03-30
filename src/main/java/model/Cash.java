package model;

public class Cash {
	//현금 코드, 현금, 구매주문 번호, 판매주문 번호
	private int cash_code;
	private int cash;
	private int purchase_order_no;
	private int sales_order_no;
	
	public int getCash_code() {
		return cash_code;
	}
	public int getCash() {
		return cash;
	}
	public int getPurchase_order_no() {
		return purchase_order_no;
	}
	public int getSales_order_no() {
		return sales_order_no;
	}
	public void setCash_code(int cash_code) {
		this.cash_code = cash_code;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public void setPurchase_order_no(int purchase_order_no) {
		this.purchase_order_no = purchase_order_no;
	}
	public void setSales_order_no(int sales_order_no) {
		this.sales_order_no = sales_order_no;
	}
}
