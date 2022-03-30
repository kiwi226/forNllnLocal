package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Customer;
import model.SearchOption;

public class CustomerDao {
	//singleton
	private static CustomerDao instance = new CustomerDao();
	private CustomerDao() { }
	public static CustomerDao getInstance() { 
		return instance;
	}
	// DataBase Connection Pool
	private Connection getConnection() {	
		Connection conn=null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		}catch (Exception e) {
			System.out.println("연결 에러 : "+e.getMessage());
		}
		return conn;
	}
	public int getTotalCustomer() {
		int total = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select count(*) from Customer";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) { }
		}
		return total;
	}
	public List<Customer> customerList(int firstRow, int lastRow) {
		List<Customer> customerList = new ArrayList<Customer>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from (select rowNum rn,a.* from "
				+ "(select * from customer order by customer_no desc) a)"				
				+ "where rn between ? and ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, firstRow);
			pstmt.setInt(2, lastRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomer_no(rs.getString("customer_no"));
				customer.setCustomer_name(rs.getString("customer_name"));   
				customer.setCustomer_reg_num(rs.getString("customer_reg_num"));
				customer.setCustomer_tel(rs.getString("customer_tel"));
				customer.setCustomer_email(rs.getString("customer_email"));
				customer.setCustomer_addr(rs.getString("customer_addr"));
				customer.setEmp_no(rs.getString("emp_no"));
				customer.setCustomer_memo(rs.getString("customer_memo"));
				customer.setCustomer_del(rs.getString("customer_del"));

				customerList.add(customer); 
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}

		}

		return customerList;
	}
	public List<Customer> customerList() {
		List<Customer> customerList = new ArrayList<Customer>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from Customer order by customer_no desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomer_no(rs.getString("customer_no"));
				customer.setCustomer_name(rs.getString("customer_name"));   
				customer.setCustomer_reg_num(rs.getString("customer_reg_num"));
				customer.setCustomer_tel(rs.getString("customer_tel"));
				customer.setCustomer_email(rs.getString("customer_email"));
				customer.setCustomer_addr(rs.getString("customer_addr"));
				customer.setEmp_no(rs.getString("emp_no"));
				customer.setCustomer_memo(rs.getString("customer_memo"));
				customer.setCustomer_del(rs.getString("customer_del"));

				customerList.add(customer); 
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}

		}

		return customerList;
	}

	public int insertCustomer(Customer customer) {
		int result = 0;
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		//업체코드 업체명 사업자번호 전화번호 이메일 우편번호 주소 상세주소, 담당자 사번, 참고, 삭제  
		String sql="insert into customer values(?,?,?,?,?,?,?,?,?,?,'n')";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer.getCustomer_no());
			pstmt.setString(2, customer.getCustomer_name());
			pstmt.setString(3, customer.getCustomer_reg_num());
			pstmt.setString(4, customer.getCustomer_tel());
			pstmt.setString(5, customer.getCustomer_email());
			pstmt.setString(6, customer.getCustomer_addr_no());
			pstmt.setString(7, customer.getCustomer_addr());
			pstmt.setString(8, customer.getCustomer_addr_detail());
			pstmt.setString(9, customer.getEmp_no());
			pstmt.setString(10, customer.getCustomer_memo());

			result = pstmt.executeUpdate();			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return result;
	}
	public Customer selectCustomer(String customer_no) {
		Customer customer = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from customer where customer_no = ? and customer_del = 'n'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				//업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 담당자 사번, 참고, 삭제  
				customer = new Customer();
				customer.setCustomer_no			(rs.getString("customer_no"));
				customer.setCustomer_name		(rs.getString("customer_name"));
				customer.setCustomer_reg_num	(rs.getString("customer_reg_num"));
				customer.setCustomer_tel		(rs.getString("customer_tel"));
				customer.setCustomer_email		(rs.getString("customer_email"));
				customer.setCustomer_addr_no	(rs.getString("customer_addr_no"));
				customer.setCustomer_addr		(rs.getString("customer_addr"));
				customer.setCustomer_addr_detail(rs.getString("customer_addr_detail"));
				customer.setEmp_no				(rs.getString("emp_no"));
				customer.setCustomer_memo		(rs.getString("customer_memo"));
				customer.setCustomer_del		(rs.getString("customer_del"));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return customer; 
	}
	public Customer selectCustomerWithRegNum(String reg_num) {
		Customer customer = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from customer where customer_reg_num = ? and customer_del = 'n'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reg_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				//업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 담당자 사번, 참고, 삭제  
				customer = new Customer();
				customer.setCustomer_no			(rs.getString("customer_no"));
				customer.setCustomer_name		(rs.getString("customer_name"));
				customer.setCustomer_reg_num	(rs.getString("customer_reg_num"));
				customer.setCustomer_tel		(rs.getString("customer_tel"));
				customer.setCustomer_email		(rs.getString("customer_email"));
				customer.setCustomer_addr_no	(rs.getString("customer_addr_no"));
				customer.setCustomer_addr		(rs.getString("customer_addr"));
				customer.setCustomer_addr_detail(rs.getString("customer_addr_detail"));
				customer.setEmp_no				(rs.getString("emp_no"));
				customer.setCustomer_memo		(rs.getString("customer_memo"));
				customer.setCustomer_del		(rs.getString("customer_del"));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return customer; 
	}

	public int updateCustomer(Customer customer) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		
		//업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 담당자 사번, 참고, 삭제  
		String sql="update customer "
				+ "set "
				+ "customer_name=?, "
				+ "customer_reg_num=?, "
				+ "customer_tel=?, "
				+ "customer_email=?, "
				+ "customer_addr_no=?, "
				+ "customer_addr=?, "
				+ "customer_addr_detail=?, "
				+ "emp_no=?, "
				+ "customer_memo=? "
				+ "where customer_no=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer.getCustomer_name());
			pstmt.setString(2, customer.getCustomer_reg_num());
			pstmt.setString(3, customer.getCustomer_tel());
			pstmt.setString(4, customer.getCustomer_email());
			pstmt.setString(5, customer.getCustomer_addr_no());
			pstmt.setString(6, customer.getCustomer_addr());
			pstmt.setString(7, customer.getCustomer_addr_detail());
			pstmt.setString(8, customer.getEmp_no());
			pstmt.setString(9, customer.getCustomer_memo());
			pstmt.setString(10, customer.getCustomer_no());		

			result = pstmt.executeUpdate();	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}

		return result;
	}

	public int delete(String customer_no) {
		int result = 0;
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		String sql="update customer set customer_del='y' where customer_no=?";
		Customer bd = selectCustomer(customer_no);
		if (customer_no.equals(bd.getCustomer_no())) { // 화면에서 읽은 암호와 읽은 게시글의 암호와 비교
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, customer_no);				
				result = pstmt.executeUpdate();			
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {		}
			}
		} else result = -1;
		return result;
	}
	public List<Customer> searchCustomer(SearchOption options) {
		List<Customer> searchList = new ArrayList<Customer>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from Customer "
				+ "where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' "
				+ "order by customer_no desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomer_no(rs.getString("customer_no"));
				customer.setCustomer_name(rs.getString("customer_name"));   
				customer.setCustomer_reg_num(rs.getString("customer_reg_num"));
				customer.setCustomer_tel(rs.getString("customer_tel"));
				customer.setCustomer_email(rs.getString("customer_email"));
				customer.setCustomer_addr(rs.getString("customer_addr"));
				customer.setEmp_no(rs.getString("emp_no"));
				customer.setCustomer_memo(rs.getString("customer_memo"));
				customer.setCustomer_del(rs.getString("customer_del"));

				searchList.add(customer); 
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}

		}

		return searchList;
	}

	//판매처 검색 리스트
	public List<Customer> searchCustomerList(int firstRow, int lastRow, SearchOption options) {
		List<Customer> searchList = new ArrayList<Customer>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = 
				"select * "
				+ "from ("
				+ "		select rowNum rn, a.* "
				+ "		from ("
				+ "			select * "
				+ "			from customer "
				+ "			where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' "
				+ " 		order by customer_no desc"
				+ "		) a)"				
				+ "where rn between ? and ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, firstRow);
			pstmt.setInt(2, lastRow);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomer_no		(rs.getString("customer_no"));
				customer.setCustomer_name	(rs.getString("customer_name"));   
				customer.setCustomer_reg_num(rs.getString("customer_reg_num"));
				customer.setCustomer_tel	(rs.getString("customer_tel"));
				customer.setCustomer_email	(rs.getString("customer_email"));
				customer.setCustomer_addr	(rs.getString("customer_addr"));
				customer.setEmp_no			(rs.getString("emp_no"));
				customer.setCustomer_memo	(rs.getString("customer_memo"));
				customer.setCustomer_del	(rs.getString("customer_del"));

				searchList.add(customer); 
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		
		return searchList;
	}
	
	//판매처 검색 내역 개수
	public int getTotalSearchCustomer(SearchOption options) {
		int total = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select count(*) "
				+ "from customer "
				+ "where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' ";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return total;
	}
		
}
