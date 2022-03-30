package model;

public class Seller {
	//업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 등록자 사번, 메모사항, 삭제여부
	private String seller_no;
	private String seller_name; 
	private String seller_reg_num;
	private String seller_tel;
	private String seller_email;
	private String seller_addr_no;
	private String seller_addr;
	private String seller_addr_detail;
	private String emp_no;
	private String seller_memo;
	private String seller_del;
	
	public String getSeller_no() {
		return seller_no;
	}
	public String getSeller_name() {
		return seller_name;
	}
	public String getSeller_reg_num() {
		return seller_reg_num;
	}
	public String getSeller_tel() {
		return seller_tel;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public String getSeller_addr_no() {
		return seller_addr_no;
	}
	public String getSeller_addr() {
		return seller_addr;
	}
	public String getSeller_addr_detail() {
		return seller_addr_detail;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public String getSeller_memo() {
		return seller_memo;
	}
	public String getSeller_del() {
		return seller_del;
	}
	public void setSeller_no(String seller_no) {
		this.seller_no = seller_no;
	}
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	public void setSeller_reg_num(String seller_reg_num) {
		this.seller_reg_num = seller_reg_num;
	}
	public void setSeller_tel(String seller_tel) {
		this.seller_tel = seller_tel;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public void setSeller_addr_no(String seller_addr_no) {
		this.seller_addr_no = seller_addr_no;
	}
	public void setSeller_addr(String seller_addr) {
		this.seller_addr = seller_addr;
	}
	public void setSeller_addr_detail(String seller_addr_detail) {
		this.seller_addr_detail = seller_addr_detail;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public void setSeller_memo(String seller_memo) {
		this.seller_memo = seller_memo;
	}
	public void setSeller_del(String seller_del) {
		this.seller_del = seller_del;
	}
}
