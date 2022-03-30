package model;

public class SalesOrderDetail {
	//판매주문번호, 상품번호, 상품주문수량
	private int 	sales_order_no;
	private int		product_no;
	private int		sales_detail_pcount;
	
	public int getSales_order_no() {
		return sales_order_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public int getSales_detail_pcount() {
		return sales_detail_pcount;
	}
	public void setSales_order_no(int sales_order_no) {
		this.sales_order_no = sales_order_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public void setSales_detail_pcount(int sales_detail_pcount) {
		this.sales_detail_pcount = sales_detail_pcount;
	}
	
	
	
	
}
