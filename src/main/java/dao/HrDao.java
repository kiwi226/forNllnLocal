package dao;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.Dept;
import model.Emp;
import model.Hr;

public class HrDao {
	private static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			System.out.println("연결에러 : " + e.getMessage());
		}
	}
	private static HrDao instance;
	private HrDao() {}
	public static HrDao getInstance() {
		if (instance == null) {
			instance = new HrDao();
			
		}
		return instance;
	}
	public Hr selectHr(String emp_no) {
		SqlSession session = sqlSessionFactory.openSession(true);
		Hr hr = (Hr)session.selectOne("hrNS.selectHr", emp_no);
		
		session.close();
		
		return hr;
	}
	
	//인사정보 리스트 받아오기
	public List<Hr> selectHrList(Map<String, Integer> paraMap) {
		SqlSession session = sqlSessionFactory.openSession(true);
		List<Hr> hrList = session.selectList("hrNS.selectHrList", paraMap);
		
		session.close();
		
		return hrList;
	}
	public Emp selectEmp(String emp_no) {
		SqlSession session = sqlSessionFactory.openSession(true);
		Emp emp = (Emp)session.selectOne("hrNS.selectEmp", emp_no);
		
		session.close();
		
		return emp;
	}
	public List<Emp> selectEmpList() {
		SqlSession session = sqlSessionFactory.openSession(true);
		List<Emp> empList = session.selectList("hrNS.selectEmpList");
		
		session.close();
		
		return empList;
	}
	
	//총 사원 수 구하기
	public int selectEmpCount() {
		SqlSession session = sqlSessionFactory.openSession(true);
		int empCount = (int) session.selectOne("hrNS.selectEmpCount");

		session.close();

		return empCount;
	}
	
	//특정 연도에 입사한 사원 수 구하기
	public int selectEmpCountWithYear(String year) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int empCount = (int) session.selectOne("hrNS.selectEmpCountWithYear", year);
		
		session.close();
		
		return empCount;
	}
	public List<Dept> selectDeptList() {
		SqlSession session = sqlSessionFactory.openSession(true);
		List<Dept> deptList = session.selectList("hrNS.selectDeptList");
		
		session.close();
		
		return deptList;
	}
	public int insertEmp(Emp emp) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int result = session.insert("hrNS.insertEmp", emp);
		
		session.close();
		return result;
	}
	public int updateEmp(Emp emp) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int result = session.update("hrNS.updateEmp", emp);
		
		session.close();
		return result;
	}
	
	public List<Hr> selectHrSearchList(Map<String, String> param) {
		SqlSession session = sqlSessionFactory.openSession(true);
		List<Hr> hrList = session.selectList("hrNS.selectHrSearchList", param);
		
		session.close();
		
		return hrList;
	}
	public int countHrSearchList(Map<String, String> param) {
		SqlSession session = sqlSessionFactory.openSession(true);
		int empCount = (int) session.selectOne("hrNS.countHrSearchList", param);
		
		session.close();
		
		return empCount;
	}
}