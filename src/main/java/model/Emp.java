package model;

import java.sql.Date;

public class Emp {
	//사번, 부서번호, 비밀번호, 이름, 이메일, 우편번호, 주소, 상세주소, 전화번호, 입사일, 퇴사여부
	private String 	emp_no;
	private String	dept_no;
	private String	password;
	private String 	emp_name;
	private String 	emp_email;
	private String 	emp_addr_no;
	private String 	emp_addr;
	private String 	emp_addr_detail;
	private String 	emp_tel;
	private Date 	hiredate;
	private String 	resign;
	
	public String getEmp_no() {
		return emp_no;
	}
	public String getDept_no() {
		return dept_no;
	}
	public String getPassword() {
		return password;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public String getEmp_email() {
		return emp_email;
	}
	public String getEmp_addr_no() {
		return emp_addr_no;
	}
	public String getEmp_addr() {
		return emp_addr;
	}
	public String getEmp_addr_detail() {
		return emp_addr_detail;
	}
	public String getEmp_tel() {
		return emp_tel;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public String getResign() {
		return resign;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	public void setEmp_addr_no(String emp_addr_no) {
		this.emp_addr_no = emp_addr_no;
	}
	public void setEmp_addr(String emp_addr) {
		this.emp_addr = emp_addr;
	}
	public void setEmp_addr_detail(String emp_addr_detail) {
		this.emp_addr_detail = emp_addr_detail;
	}
	public void setEmp_tel(String emp_tel) {
		this.emp_tel = emp_tel;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public void setResign(String resign) {
		this.resign = resign;
	}
	
	
	
	
}