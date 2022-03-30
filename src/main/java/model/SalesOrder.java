package model;

import java.sql.Date;

public class SalesOrder {
	//판매주문번호, 판매처 번호, 판매 주문일, 주문등록 사원
	private int 	sales_order_no;
	private String 	customer_no;
	private Date	sales_order_date;
    private String 	emp_no;
	public int getSales_order_no() {
		return sales_order_no;
	}
	public String getCustomer_no() {
		return customer_no;
	}
	public Date getSales_order_date() {
		return sales_order_date;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setSales_order_no(int sales_order_no) {
		this.sales_order_no = sales_order_no;
	}
	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public void setSales_order_date(Date sales_order_date) {
		this.sales_order_date = sales_order_date;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
}
