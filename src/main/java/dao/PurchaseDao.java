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
import model.Purchase;
import model.PurchaseOrder;
import model.PurchaseOrderDetail;
import model.SearchOption;

public class PurchaseDao {
	// singleton
	private static PurchaseDao instance = new PurchaseDao();
	private PurchaseDao() {}
	public static PurchaseDao getInstance() {
		return instance;
	}
	// DataBase Connection Pool
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		}catch (Exception e) {
			System.out.println("연결 에러 : "+e.getMessage());
		}
		return conn;
	} 
	
	public List<Purchase> purchaseList(int startRow, int endRow) {
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from (select rownum rn, p.* from purchase p) where rn between ? and ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Purchase purchase = new Purchase();
				// 판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
				purchase.setPurchase_order_date		(rs.getDate("PURCHASE_ORDER_DATE"));
				purchase.setPurchase_order_no		(rs.getInt("purchase_order_no"));
				purchase.setSeller_no				(rs.getString("seller_no"));
				purchase.setSeller_name				(rs.getString("seller_name"));
				purchase.setProduct_no				(rs.getInt("product_no"));
				purchase.setProduct_name			(rs.getString("product_name"));
				purchase.setPrice					(rs.getInt("price"));
				purchase.setCost					(rs.getInt("cost"));
				purchase.setStock					(rs.getInt("stock"));
				purchase.setPurchase_detail_pcount	(rs.getInt("purchase_detail_pcount"));
				purchase.setEmp_no					(rs.getString("emp_no"));
				purchase.setEmp_name				(rs.getString("emp_name"));
				
				purchaseList.add(purchase);
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
		return purchaseList;
	}
	
	public int selectPurchase_order_no() {
		int selectPurchase_order_no = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql="select count(*) from Purchase_order";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				selectPurchase_order_no = rs.getInt(1);
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
		return selectPurchase_order_no;
	}
			
	public int getTotalPurchase() {
		int total = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select count(*) from ("
				+ "select a.purchase_order_date, b.seller_no, b.seller_name, d.product_no, d.product_name, d.cost, c.purchase_detail_pcount, a.emp_no "
				+ "from purchase_order a, seller b, purchase_order_detail c, product d " 
				+ "where a.seller_no = b.seller_no and a.purchase_order_no = c.purchase_order_no "
				+ "and c.product_no = d.product_no)";
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

	public int insertPurchase(PurchaseOrder purchaseOrder, PurchaseOrderDetail purchaseOrderDetail, Cash cash, Product product) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		
		try {
			conn.setAutoCommit(false);
			
			//판매주문번호, 판매처 번호, 판매 주문일, 주문등록 사원
			String sql = "insert into purchase_order values (?, ?, sysdate, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, purchaseOrder.getPurchase_order_no());
			pstmt.setString	(2, purchaseOrder.getSeller_no());
			pstmt.setString	(3, purchaseOrder.getEmp_no());
			
			result += pstmt.executeUpdate(); 
			pstmt.close();
			
			//판매 주문 상세 입력
			//판매주문번호, 상품번호, 상품주문수량
			sql = "insert into purchase_order_detail values (?, ?, ?)";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, purchaseOrderDetail.getPurchase_order_no());
			pstmt.setInt(2, purchaseOrderDetail.getProduct_no());
			pstmt.setInt(3, purchaseOrderDetail.getPurchase_detail_pcount());
			
			result += pstmt.executeUpdate(); 
			pstmt.close();
			
			//현금 변동 내역 입력
			//현금 코드, 현금, 구매주문 번호, 판매주문 번호
			sql = "insert into cash values (?, ?, ?, null)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cash.getCash_code());
			pstmt.setInt(2, cash.getCash());
			pstmt.setInt(3, cash.getPurchase_order_no());
			
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
			} catch (Exception e) {}
		}
		
		return result;
	}
	
	public List<Purchase> purchaseList() {
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		//이번 달 구매내역 구하기
		String sql = 
				"select product_no, product_name, cost, price, sum(purchase_detail_pcount) "
				+ "from purchase "
				+ "where purchase_order_date < sysdate "
				+ "and purchase_order_date > trunc(sysdate, 'mm') "
				+ "group by product_no, product_name, cost, price "
				+ "order by product_no desc";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Purchase purchase = new Purchase();
				// 판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
				purchase.setProduct_no				(rs.getInt("product_no"));
				purchase.setProduct_name			(rs.getString("product_name"));
				purchase.setCost					(rs.getInt("cost"));
				purchase.setPrice					(rs.getInt("price"));
				purchase.setPurchase_detail_pcount	(rs.getInt(5));
				
				purchaseList.add(purchase);
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
		return purchaseList;
	}
	
	public List<Purchase> purchaseList(String month) {
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		//이번 달 구매내역 구하기
		String sql = 
				"select product_no, product_name, cost, price, sum(purchase_detail_pcount) "
				+ "from purchase "
				+ "where purchase_order_date between to_date('"+month+"', 'yy/mm') and add_months(to_date('"+month+"', 'yy/mm'), 1) "
				+ "group by product_no, product_name, cost, price "
				+ "order by product_no desc";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Purchase purchase = new Purchase();
				// 판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호
				purchase.setProduct_no				(rs.getInt("product_no"));
				purchase.setProduct_name			(rs.getString("product_name"));
				purchase.setCost					(rs.getInt("cost"));
				purchase.setPrice					(rs.getInt("price"));
				purchase.setPurchase_detail_pcount	(rs.getInt(5));
				
				purchaseList.add(purchase);
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
		return purchaseList;
	}
	
	public List<Purchase> purchaseSearchList(int firstRow, int lastRow, SearchOption options) {
		List<Purchase> purchaseList = new ArrayList<Purchase>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select * "
				+ "from ("
				+ "		select rownum rn, s.* "
				+ "		from purchase s "
				+ "		where "+options.getSearchField()+" like '%'||?||'%' "
				+ "		and purchase_order_date > to_date(?, 'yyyy-mm-dd') "
				+ "		and purchase_order_date < to_date(?, 'yyyy-mm-dd')+1 "
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
				Purchase purchase = new Purchase();
				//판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호, 사원명
				purchase.setPurchase_order_date		(rs.getDate("purchase_order_date"));
				purchase.setPurchase_order_no		(rs.getInt("purchase_order_no"));
				purchase.setSeller_no				(rs.getString("seller_no"));
				purchase.setSeller_name				(rs.getString("seller_name"));
				purchase.setProduct_no				(rs.getInt("product_no"));
				purchase.setProduct_name			(rs.getString("product_name"));
				purchase.setPrice					(rs.getInt("price"));
				purchase.setCost					(rs.getInt("cost"));
				purchase.setStock					(rs.getInt("stock"));
				purchase.setPurchase_detail_pcount	(rs.getInt("purchase_detail_pcount"));
				purchase.setEmp_no					(rs.getString("emp_no"));
				purchase.setEmp_name				(rs.getString("emp_name"));

				purchaseList.add(purchase); 
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

		return purchaseList;
	}
	
	public int getTotalPurchaseSearch(SearchOption options) {
		int total = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select count(*) "
				+ "from purchase "
				+ "where "+options.getSearchField()+" like '%'||?||'%' "
				+ "and purchase_order_date > to_date(?, 'yyyy-mm-dd') "
				+ "and purchase_order_date < to_date(?, 'yyyy-mm-dd')+1 ";
		
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