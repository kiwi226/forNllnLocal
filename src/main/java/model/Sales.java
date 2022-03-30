package model;

import java.sql.Date;

public class Sales {
	//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
	private Date		sales_order_date;
	private int			sales_order_no;
	private String		customer_no;
	private String		customer_name;
	private int			product_no;
	private String		product_name;
	private int			price;
	private int			cost;
	private int			stock;
	private int			sales_detail_pcount;
	private String		emp_no;
	private String		emp_name;
	
	public Date getSales_order_date() {
		return sales_order_date;
	}
	public int getSales_order_no() {
		return sales_order_no;
	}
	public String getCustomer_no() {
		return customer_no;
	}
	public String getCustomer_name() {
		return customer_name;
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
	public int getSales_detail_pcount() {
		return sales_detail_pcount;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setSales_order_date(Date sales_order_date) {
		this.sales_order_date = sales_order_date;
	}
	public void setSales_order_no(int sales_order_no) {
		this.sales_order_no = sales_order_no;
	}
	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
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
	public void setSales_detail_pcount(int sales_detail_pcount) {
		this.sales_detail_pcount = sales_detail_pcount;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
}
