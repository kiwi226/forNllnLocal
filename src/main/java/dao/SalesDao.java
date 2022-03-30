package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Cash;
import model.Product;
import model.Sales;
import model.SalesOrder;
import model.SalesOrderDetail;
import model.SearchOption;

public class SalesDao {
	//singleton
	private static SalesDao instance = new SalesDao();
	private SalesDao() { }
	public static SalesDao getInstance() { 
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

	public int getTotalSales() {
		int total = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select count(*) from sales";
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

	public List<Sales> salesList(int startRow, int endRow) {
		List<Sales> salesList = new ArrayList<Sales>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select * from (select rownum rn, s.* from sales s) where rn between ? and ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Sales sales = new Sales();
				//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호, 사원명
				sales.setSales_order_date	(rs.getDate("sales_order_date"));
				sales.setSales_order_no		(rs.getInt("sales_order_no"));
				sales.setCustomer_no		(rs.getString("customer_no"));
				sales.setCustomer_name		(rs.getString("customer_name"));
				sales.setProduct_no			(rs.getInt("product_no"));
				sales.setProduct_name		(rs.getString("product_name"));
				sales.setPrice				(rs.getInt("price"));
				sales.setCost				(rs.getInt("cost"));
				sales.setStock				(rs.getInt("stock"));
				sales.setSales_detail_pcount(rs.getInt("sales_detail_pcount"));
				sales.setEmp_no				(rs.getString("emp_no"));
				sales.setEmp_name			(rs.getString("emp_name"));

				salesList.add(sales); 
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

		return salesList;
	}
	
	public List<Sales> salesList() {
		List<Sales> salesList = new ArrayList<Sales>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select product_no, product_name, cost, price, sum(sales_detail_pcount) "
				+ "from sales "
				+ "where sales_order_date < sysdate "
				+ "and sales_order_date > trunc(sysdate, 'mm') "
				+ "group by product_no, product_name, price, cost "
				+ "order by product_no desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				Sales sales = new Sales();
				//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호, 사원명
				sales.setProduct_no			(rs.getInt("product_no"));
				sales.setProduct_name		(rs.getString("product_name"));
				sales.setPrice				(rs.getInt("price"));
				sales.setCost				(rs.getInt("cost"));
				sales.setSales_detail_pcount(rs.getInt(5));

				salesList.add(sales); 
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

		return salesList;
	}
	
	public List<Sales> salesList(String month) {
		List<Sales> salesList = new ArrayList<Sales>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select product_no, product_name, cost, price, sum(sales_detail_pcount) "
				+ "from sales "
				+ "where sales_order_date between to_date('"+month+"', 'yy/mm') and add_months(to_date('"+month+"', 'yy/mm'), 1) "
				+ "group by product_no, product_name, cost, price "
				+ "order by product_no desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			
			while(rs.next()) {
				Sales sales = new Sales();
				//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호, 사원명
				sales.setProduct_no			(rs.getInt("product_no"));
				sales.setProduct_name		(rs.getString("product_name"));
				sales.setPrice				(rs.getInt("price"));
				sales.setCost				(rs.getInt("cost"));

				sales.setSales_detail_pcount(rs.getInt(5));

				salesList.add(sales); 
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

		return salesList;
	}
	
	public int insertSales(SalesOrder salesOrder, SalesOrderDetail salesOrderDetail, Cash cash, Product product) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		
		
		try {
			conn.setAutoCommit(false);
			
			//판매주문번호, 판매처 번호, 판매 주문일, 주문등록 사원
			String sql = "insert into sales_order values (?, ?, sysdate, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, salesOrder.getSales_order_no());
			pstmt.setString	(2, salesOrder.getCustomer_no());
			pstmt.setString	(3, salesOrder.getEmp_no());
			
			result += pstmt.executeUpdate(); 
			pstmt.close();
			
			//판매 주문 상세 입력
			//판매주문번호, 상품번호, 상품주문수량
			sql = "insert into sales_order_detail values (?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, salesOrderDetail.getSales_order_no());
			pstmt.setInt(2, salesOrderDetail.getProduct_no());
			pstmt.setInt(3, salesOrderDetail.getSales_detail_pcount());
			
			result += pstmt.executeUpdate(); 
			pstmt.close();
			
			//현금 변동 내역 입력
			//현금 코드, 현금, 구매주문 번호, 판매주문 번호
			sql = "insert into cash values (?, ?, null, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cash.getCash_code());
			pstmt.setInt(2, cash.getCash());
			pstmt.setInt(3, cash.getSales_order_no());
			
			result += pstmt.executeUpdate(); 
			pstmt.close();
			
			//상품재고 업데이트
			sql = "update product set stock=? where product_no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, product.getStock());
			pstmt.setInt	(2, product.getProduct_no());
			
			result += pstmt.executeUpdate();			
			pstmt.close();
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.out.println("판매주문 입력 오류 : " + e.getMessage());
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		
		return result;
	}
	
	public List<Sales> salesSearchList(int firstRow, int lastRow, SearchOption options) {
		List<Sales> salesList = new ArrayList<Sales>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select * "
				+ "from ("
				+ "		select rownum rn, s.* "
				+ "		from sales s "
				+ "		where "+options.getSearchField()+" like '%'||?||'%' "
				+ "		and sales_order_date > to_date(?, 'yyyy-mm-dd') "
				+ "		and sales_order_date < to_date(?, 'yyyy-mm-dd')+1 "
				+ ") "
				+ "where rn between ? and ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, options.getKeyword());
			pstmt.setString(2, options.getFrom());
			pstmt.setString(3, options.getTo());
			pstmt.setInt(4, firstRow);
			pstmt.setInt(5, lastRow);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Sales sales = new Sales();
				//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호, 사원명
				sales.setSales_order_date	(rs.getDate("sales_order_date"));
				sales.setSales_order_no		(rs.getInt("sales_order_no"));
				sales.setCustomer_no		(rs.getString("customer_no"));
				sales.setCustomer_name		(rs.getString("customer_name"));
				sales.setProduct_no			(rs.getInt("product_no"));
				sales.setProduct_name		(rs.getString("product_name"));
				sales.setPrice				(rs.getInt("price"));
				sales.setCost				(rs.getInt("cost"));
				sales.setStock				(rs.getInt("stock"));
				sales.setSales_detail_pcount(rs.getInt("sales_detail_pcount"));
				sales.setEmp_no				(rs.getString("emp_no"));
				sales.setEmp_name			(rs.getString("emp_name"));

				salesList.add(sales); 
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

		return salesList;
	}
	
	public int getTotalSalesSearch( SearchOption options) {
		int total = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select count(*) "
				+ "from sales "
				+ "where "+options.getSearchField()+" like '%'||?||'%' "
				+ "and sales_order_date > to_date(?, 'yyyy-mm-dd') "
				+ "and sales_order_date < to_date(?, 'yyyy-mm-dd')+1 ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, options.getKeyword());
			pstmt.setString(2, options.getFrom());
			pstmt.setString(3, options.getTo());
			rs = pstmt.executeQuery(); 
			
			if(rs.next()) {
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
