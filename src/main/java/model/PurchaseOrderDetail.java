package model;

public class PurchaseOrderDetail {
	//구매주문번호, 상품번호, 상품주문수량
	private int 	purchase_order_no;
	private int		product_no;
	private int		purchase_detail_pcount;
	
	public int getPurchase_order_no() {
		return purchase_order_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public int getPurchase_detail_pcount() {
		return purchase_detail_pcount;
	}
	public void setPurchase_order_no(int purchase_order_no) {
		this.purchase_order_no = purchase_order_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public void setPurchase_detail_pcount(int purchase_detail_pcount) {
		this.purchase_detail_pcount = purchase_detail_pcount;
	}
	

	
}
