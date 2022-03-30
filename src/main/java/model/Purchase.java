package model;

import java.sql.Date;

public class Purchase {
	// 판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
	private Date purchase_order_date;
	private int purchase_order_no;
	private String seller_no;
	private String seller_name;
	private int product_no;
	private String product_name;
	private int price;
	private int cost;
	private int stock;
	private int purchase_detail_pcount;
	private String emp_no;
	private String emp_name;
	
	public Date getPurchase_order_date() {
		return purchase_order_date;
	}
	public int getPurchase_order_no() {
		return purchase_order_no;
	}
	public String getSeller_no() {
		return seller_no;
	}
	public String getSeller_name() {
		return seller_name;
	}
	public int getProduct_no() {
		return product_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public int getPrice() {
		return price;
	}
	public int getCost() {
		return cost;
	}
	public int getStock() {
		return stock;
	}
	public int getPurchase_detail_pcount() {
		return purchase_detail_pcount;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setPurchase_order_date(Date purchase_order_date) {
		this.purchase_order_date = purchase_order_date;
	}
	public void setPurchase_order_no(int purchase_order_no) {
		this.purchase_order_no = purchase_order_no;
	}
	public void setSeller_no(String seller_no) {
		this.seller_no = seller_no;
	}
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setPurchase_detail_pcount(int purchase_detail_pcount) {
		this.purchase_detail_pcount = purchase_detail_pcount;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	
}
