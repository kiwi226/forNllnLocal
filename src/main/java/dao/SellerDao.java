package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.SearchOption;
import model.Seller;

public class SellerDao {
	// singleton
	private static SellerDao instance = new SellerDao();

	private SellerDao() {	}

	public static SellerDao getInstance() {
		return instance;
	}

	// DataBase Connection Pool
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("연결 에러 : " + e.getMessage());
		}
		return conn;
	}

	public int getTotalSeller() {
		int total = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select count(*) from seller where seller_del='n'";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return total;
	}

	public List<Seller> sellerList(int startRow, int endRow) {
		List<Seller> sellerList = new ArrayList<Seller>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from (select rowNum rn,a.* from " + "(select * from seller order by seller_no desc) a)"
				+ "where rn between ? and ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Seller seller = new Seller();
				seller.setSeller_no			(rs.getString("seller_no"));
				seller.setSeller_reg_num	(rs.getString("seller_reg_num"));
				seller.setSeller_name		(rs.getString("seller_name"));
				seller.setSeller_tel		(rs.getString("seller_tel"));
				seller.setSeller_addr_no	(rs.getString("seller_addr_no"));
				seller.setSeller_addr		(rs.getString("seller_addr"));
				seller.setSeller_addr_detail(rs.getString("seller_addr_detail"));
				seller.setEmp_no			(rs.getString("emp_no"));
				seller.setSeller_email		(rs.getString("seller_email"));
				seller.setSeller_memo		(rs.getString("seller_memo"));
				seller.setSeller_del		(rs.getString("seller_del"));

				sellerList.add(seller);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return sellerList;
	}
	public List<Seller> sellerList() {
		List<Seller> sellerList = new ArrayList<Seller>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from seller order by seller_no desc";
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Seller seller = new Seller();
				
				seller.setSeller_no			(rs.getString("seller_no"));
				seller.setSeller_reg_num	(rs.getString("seller_reg_num"));
				seller.setSeller_name		(rs.getString("seller_name"));
				seller.setSeller_tel		(rs.getString("seller_tel"));
				seller.setSeller_addr_no	(rs.getString("seller_addr_no"));
				seller.setSeller_addr		(rs.getString("seller_addr"));
				seller.setSeller_addr_detail(rs.getString("seller_addr_detail"));
				seller.setEmp_no			(rs.getString("emp_no"));
				seller.setSeller_email		(rs.getString("seller_email"));
				seller.setSeller_memo		(rs.getString("seller_memo"));
				seller.setSeller_del		(rs.getString("seller_del"));

				sellerList.add(seller);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)	rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {	}
		}
		return sellerList;
	}
	public Seller selectSellerWithRegNum(String reg_num) {
		Seller seller = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select * from seller where seller_reg_num = ? and seller_del = 'n'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reg_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 업체코드, 업체명, 사업자번호, 전화번호, 이메일, 우편번호, 주소, 상세주소, 담당자 사번, 참고, 삭제
				seller = new Seller();
				seller.setSeller_no			(rs.getString("seller_no"));
				seller.setSeller_name		(rs.getString("seller_name"));
				seller.setSeller_reg_num	(rs.getString("seller_reg_num"));
				seller.setSeller_tel		(rs.getString("seller_tel"));
				seller.setSeller_email		(rs.getString("seller_email"));
				seller.setSeller_addr_no	(rs.getString("seller_addr_no"));
				seller.setSeller_addr		(rs.getString("seller_addr"));
				seller.setSeller_addr_detail(rs.getString("seller_addr_detail"));
				seller.setEmp_no			(rs.getString("emp_no"));
				seller.setSeller_memo		(rs.getString("seller_memo"));
				seller.setSeller_del		(rs.getString("seller_del"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return seller;
	}

	public int insertSeller(Seller seller) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();

		// 업체코드, 업체명, 사업자번호, 전화번호 이메일 주소(우편번호 주소 상세주소) 담당자 참고사항
		String sql = "insert into seller values(?,?,?,?,?,?,?,?,?,?,'n')";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seller.getSeller_no());
			pstmt.setString(2, seller.getSeller_name());
			pstmt.setString(3, seller.getSeller_reg_num());
			pstmt.setString(4, seller.getSeller_tel());
			pstmt.setString(5, seller.getSeller_email());
			pstmt.setString(6, seller.getSeller_addr_no());
			pstmt.setString(7, seller.getSeller_addr());
			pstmt.setString(8, seller.getSeller_addr_detail());
			pstmt.setString(9, seller.getEmp_no());
			pstmt.setString(10, seller.getSeller_memo());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	public Seller selectSeller(String seller_no) {
		Seller seller = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select * from seller where seller_no=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seller_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				seller = new Seller();
				seller.setSeller_no			(rs.getString("seller_no"));
				seller.setSeller_name		(rs.getString("seller_name"));
				seller.setSeller_reg_num	(rs.getString("seller_reg_num"));
				seller.setSeller_tel		(rs.getString("seller_tel"));
				seller.setSeller_email		(rs.getString("seller_email"));
				seller.setSeller_addr_no	(rs.getString("seller_addr_no"));
				seller.setSeller_addr		(rs.getString("seller_addr"));
				seller.setSeller_addr_detail(rs.getString("seller_addr_detail"));
				seller.setEmp_no			(rs.getString("emp_no"));
				seller.setSeller_memo		(rs.getString("seller_memo"));
				seller.setSeller_del		(rs.getString("seller_del"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return seller;
	}

	public int updateSeller(Seller seller) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		
		String sql = "update seller "
				+ "set "
				+ "seller_name=?, "
				+ "seller_reg_num=?, "
				+ "seller_tel=?, "
				+ "seller_email=?, "
				+ "seller_addr_no=?, "
				+ "seller_addr=?, "
				+ "seller_addr_detail=?, "
				+ "emp_no=?, "
				+ "seller_memo=? "
				+ "where seller_no=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seller.getSeller_name());
			pstmt.setString(2, seller.getSeller_reg_num());
			pstmt.setString(3, seller.getSeller_tel());
			pstmt.setString(4, seller.getSeller_email());
			pstmt.setString(5, seller.getSeller_addr_no());
			pstmt.setString(6, seller.getSeller_addr());
			pstmt.setString(7, seller.getSeller_addr_detail());
			pstmt.setString(8, seller.getEmp_no());
			pstmt.setString(9, seller.getSeller_memo());
			pstmt.setString(10, seller.getSeller_no());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

	public List<Seller> searchSeller(SearchOption options) {
		List<Seller> searchList = new ArrayList<Seller>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();

		String sql = "select * from Seller "
				+ "where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' "
				+ "order by seller_no desc";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Seller seller = new Seller();
				seller.setSeller_no		(rs.getString("seller_no"));
				seller.setSeller_name	(rs.getString("seller_name"));   
				seller.setSeller_reg_num(rs.getString("seller_reg_num"));
				seller.setSeller_tel	(rs.getString("seller_tel"));
				seller.setSeller_email	(rs.getString("seller_email"));
				seller.setSeller_addr	(rs.getString("seller_addr"));
				seller.setEmp_no		(rs.getString("emp_no"));
				seller.setSeller_memo	(rs.getString("seller_memo"));
				seller.setSeller_del	(rs.getString("seller_del"));

				searchList.add(seller); 
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
	
	//구매처 검색 리스트
	public List<Seller> searchSellerList(int firstRow, int lastRow, SearchOption options) {
		List<Seller> searchList = new ArrayList<Seller>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		
		String sql = 
				"select * "
				+ "from ("
				+ "		select rowNum rn, a.* "
				+ "		from ("
				+ "			select * "
				+ "			from seller "
				+ "			where "+options.getSearchField()+" like '%'||'"+options.getKeyword()+"'||'%' "
				+ " 		order by seller_no desc"
				+ "		) a)"				
				+ "where rn between ? and ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, firstRow);
			pstmt.setInt(2, lastRow);
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				Seller seller = new Seller();
				seller.setSeller_no		(rs.getString("seller_no"));
				seller.setSeller_name	(rs.getString("seller_name"));   
				seller.setSeller_reg_num(rs.getString("seller_reg_num"));
				seller.setSeller_tel	(rs.getString("seller_tel"));
				seller.setSeller_email	(rs.getString("seller_email"));
				seller.setSeller_addr	(rs.getString("seller_addr"));
				seller.setEmp_no		(rs.getString("emp_no"));
				seller.setSeller_memo	(rs.getString("seller_memo"));
				seller.setSeller_del	(rs.getString("seller_del"));

				searchList.add(seller); 
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
	
	//구매처 검색 내역 개수
	public int getTotalSearchSeller(SearchOption options) {
		int total = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		
		String sql = "select count(*) "
				+ "from seller "
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
