package dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.naming.InitialContext;
import javax.sql.*;

import model.ModifiedStock;
import model.Product;
import model.ProductModified;
import model.SearchOption;
public class ProductDao {
	// singleton
	private static ProductDao instance = new ProductDao();
	private ProductDao() {}
	public static ProductDao getInstance() {
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
	
	//상품 리스트
	public List<Product> productList(int startRow, int endRow) {
		List<Product> productList = new ArrayList<Product>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from (select rowNum rn,a.* from "
				+ "(select * from product order by product_no desc) a)"				
				+ "where rn between ? and ?"; 						
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Product product = new Product();
				product.setProduct_no(rs.getInt("product_no"));
				product.setProduct_name(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setCost(rs.getInt("cost"));
				product.setStock(rs.getInt("stock"));
				product.setProduct_memo(rs.getString("product_memo"));
				
				productList.add(product);
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
		return productList;
	}
	
	//상품 입력
	public int insertProduct(Product product) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		//상품코드, 상품명, 판매가, 매입가, 현재 재고량, 상품메모
		String sql="insert into product values(?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, product.getProduct_no());
			pstmt.setString	(2, product.getProduct_name());
			pstmt.setInt	(3, product.getPrice());
			pstmt.setInt	(4, product.getCost());
			pstmt.setInt	(5, product.getStock());
			pstmt.setString	(6, product.getProduct_memo());
			
			result = pstmt.executeUpdate();			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return result;
	}
	
	//개별 상품 선택
	public Product selectProduct(int product_no) {
		Product product = new Product();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from product where product_no=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				product.setProduct_no(rs.getInt("product_no"));
				product.setProduct_name(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setCost(rs.getInt("cost"));
				product.setStock(rs.getInt("stock"));
				product.setProduct_memo(rs.getString("product_memo"));
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
		return product;
	}
	
	//상품 수정
	public int updateProduct(Product product) { // product 화면에서 입력한 게시글
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		
		String sql="update product "
				+ "set "
				+ "product_name=?, "
				+ "price=?, "
				+ "cost=?, "
				+ "stock=?, "
				+ "product_memo=? "
				+ "where product_no=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString	(1, product.getProduct_name());
			pstmt.setInt	(2, product.getPrice());
			pstmt.setInt	(3, product.getCost());
			pstmt.setInt	(4, product.getStock());
			pstmt.setString	(5, product.getProduct_memo());	
			pstmt.setInt	(6, product.getProduct_no());
			
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
	
	//전체 상품 개수
	public int getTotalProduct() {
		int total = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select count(*) from product";
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
	
	//전체 상품 리스트
	public List<Product> productList() {
		List<Product> productList = new ArrayList<Product>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from product order by product_no desc"; 						
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product product = new Product();
				product.setProduct_no(rs.getInt("product_no"));
				product.setProduct_name(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setCost(rs.getInt("cost"));
				product.setStock(rs.getInt("stock"));
				product.setProduct_memo(rs.getString("product_memo"));
				
				productList.add(product);
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
		return productList;
	}
	
	//searchWindow에서 검색
	public List<Product> searchProduct(SearchOption options) {
		List<Product> searchList = new ArrayList<Product>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from product "
				+ "where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' "
				+ "order by product_no desc";					
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product product = new Product();
				product.setProduct_no(rs.getInt("product_no"));
				product.setProduct_name(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setCost(rs.getInt("cost"));
				product.setStock(rs.getInt("stock"));
				product.setProduct_memo(rs.getString("product_memo"));
				
				searchList.add(product);
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
	
	//재고 변동사항 입력
	public int modifyProductStock(Product product, ProductModified productModified) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		
		try {
			conn.setAutoCommit(false);
			
			//상품 재고 수정
			String sql = "update product set stock=? where product_no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getStock());
			pstmt.setInt(2, product.getProduct_no());
			
			result += pstmt.executeUpdate();
			pstmt.close();
			
			//상품 변동 내역 입력
			sql = "insert into product_modified values (sysdate, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt	(1, productModified.getProduct_no());
			pstmt.setString	(2, productModified.getEmp_no());
			pstmt.setInt	(3, productModified.getModified_stock());
			pstmt.setString	(4, productModified.getModified_memo());
			
			result += pstmt.executeUpdate();
			pstmt.close();
			
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			} catch (Exception e) {		}
		}
		return result;
	}
	
	//재고 변동 내역 리스트 불러오기
	public List<ModifiedStock> modifiedStockList(int startRow, int endRow) {
		List<ModifiedStock> modifiedStockList = new ArrayList<ModifiedStock>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from (select rownum rn, ms.* from modified_stock ms) where rn between ? and ?"; 						
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ModifiedStock modifiedStock = new ModifiedStock();
				modifiedStock.setProduct_modified_date	(rs.getDate("product_modified_date"));
				modifiedStock.setProduct_no				(rs.getInt("product_no"));
				modifiedStock.setProduct_name			(rs.getString("product_name"));
				modifiedStock.setModified_stock			(rs.getInt("modified_stock"));
				modifiedStock.setModified_memo			(rs.getString("modified_memo"));
				modifiedStock.setEmp_no					(rs.getString("emp_no"));
				modifiedStock.setEmp_name				(rs.getString("emp_name"));
				
				modifiedStockList.add(modifiedStock);
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
		
		return modifiedStockList;
	}
	
	//재고 변동 내역 개수
	public int getTotalModifiedStock() {
		int total = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select count(*) from modified_stock";
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
	
	//검색 조건을 만족하는 내역 개수
	public int getTotalSearchProduct(SearchOption options) {
		int total = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select count(*) "
				+ "from product "
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
	
	//검색 조건을 만족하는 상품 리스트
	public List<Product> searchProductList(int firstRow, int lastRow, SearchOption options) {
		List<Product> searchList = new ArrayList<Product>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = 
				"select * "
				+ "from ("
				+ "		select rowNum rn, a.* "
				+ "		from ("
				+ "			select * "
				+ "			from product "
				+ "			where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' "
				+ " 		order by product_no desc"
				+ "		) a)"				
				+ "where rn between ? and ?"; 						
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, firstRow);
			pstmt.setInt(2, lastRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Product product = new Product();
				product.setProduct_no(rs.getInt("product_no"));
				product.setProduct_name(rs.getString("product_name"));
				product.setPrice(rs.getInt("price"));
				product.setCost(rs.getInt("cost"));
				product.setStock(rs.getInt("stock"));
				product.setProduct_memo(rs.getString("product_memo"));
				
				searchList.add(product);
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

	//검색 조건을 만족하는 재고 변동 내역 리스트
	public List<ModifiedStock> searchModifiedList(int firstRow, int lastRow, SearchOption options) {
		List<ModifiedStock> searchList = new ArrayList<ModifiedStock>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = 
				"select * from ("
				+ "		select rownum rn, ms.* "
				+ "		from modified_stock ms "
				+ "		where "+options.getSearchField()+" like '%'||?||'%' "
				+ "		and product_modified_date > to_date(?, 'yyyy-mm-dd') "
				+ "		and product_modified_date < to_date(?, 'yyyy-mm-dd')+1 "
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
				ModifiedStock modifiedStock = new ModifiedStock();
				modifiedStock.setProduct_no(rs.getInt("product_no"));
				modifiedStock.setProduct_name(rs.getString("product_name"));
				modifiedStock.setProduct_modified_date(rs.getDate("product_modified_date"));
				modifiedStock.setModified_stock(rs.getInt("modified_stock"));
				modifiedStock.setModified_memo(rs.getString("modified_memo"));
				modifiedStock.setEmp_no(rs.getString("emp_no"));
				
				searchList.add(modifiedStock);
			}
		}catch (Exception e) {
			System.out.println("검색 : " + e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return searchList;
	}
	
	//검색 조건을 만족하는 재고 변동 내역 개수
	public int getTotalSearchModified(SearchOption options) {
		int total = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = 
				"select count(*) "
				+ "from modified_stock ms "
				+ "where "+options.getSearchField()+" like '%'||?||'%' "
				+ "and product_modified_date > to_date(?, 'yyyy-mm-dd') "
				+ "and product_modified_date < to_date(?, 'yyyy-mm-dd')+1 ";
		
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
			System.out.println("검색 : " + e.getMessage());
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