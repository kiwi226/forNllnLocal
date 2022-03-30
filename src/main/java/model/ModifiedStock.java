package model;

import java.sql.Date;

public class ModifiedStock {
	private Date	product_modified_date;
	private int		product_no;
	private String	product_name;
	private int		modified_stock;
	private String	modified_memo;
	private String	emp_no;
	private String 	emp_name;
	
	public Date getProduct_modified_date() {
		return product_modified_date;
	}
	public int getProduct_no() {
		return product_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public int getModified_stock() {
		return modified_stock;
	}
	public String getModified_memo() {
		return modified_memo;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setProduct_modified_date(Date product_modified_date) {
		this.product_modified_date = product_modified_date;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public void setModified_stock(int modified_stock) {
		this.modified_stock = modified_stock;
	}
	public void setModified_memo(String modified_memo) {
		this.modified_memo = modified_memo;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	
	
}
