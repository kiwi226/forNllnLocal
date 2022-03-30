package model;

public class Customer {
	//업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 등록자 사번, 메모사항, 삭제여부
	private String customer_no;
	private String customer_name; 
	private String customer_reg_num;
	private String customer_tel;
	private String customer_email;
	private String customer_addr_no;
	private String customer_addr;
	private String customer_addr_detail;
	private String emp_no;
	private String customer_memo;
	private String customer_del;
	
	public String getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_reg_num() {
		return customer_reg_num;
	}
	public void setCustomer_reg_num(String customer_reg_num) {
		this.customer_reg_num = customer_reg_num;
	}
	public String getCustomer_tel() {
		return customer_tel;
	}
	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_addr() {
		return customer_addr;
	}
	public void setCustomer_addr(String customer_addr) {
		this.customer_addr = customer_addr;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getCustomer_memo() {
		return customer_memo;
	}
	public void setCustomer_memo(String customer_memo) {
		this.customer_memo = customer_memo;
	}
	public String getCustomer_del() {
		return customer_del;
	}
	public void setCustomer_del(String customer_del) {
		this.customer_del = customer_del;
	}
	public String getCustomer_addr_no() {
		return customer_addr_no;
	}
	public void setCustomer_addr_no(String customer_addr_no) {
		this.customer_addr_no = customer_addr_no;
	}
	public String getCustomer_addr_detail() {
		return customer_addr_detail;
	}
	public void setCustomer_addr_detail(String customer_addr_detail) {
		this.customer_addr_detail = customer_addr_detail;
	}
	
	public String toString() {
		return customer_no + " " + customer_name + " " + customer_reg_num;
	}
}
