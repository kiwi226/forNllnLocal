package service.hr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Emp;
import dao.HrDao;

public class Update implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//updateForm.do가 아닌 곳에서 접근하는 경우에 세션을 초기화 하고 로그인 화면으로 보냄
		if(request.getHeader("referer") == null || !request.getHeader("referer").contains("hr/updateForm.do")) {
			request.getSession().invalidate();
			request.setAttribute("result", -1);
			return "/view/hr/updateResult.jsp";
		}
		
		HrDao hd = HrDao.getInstance();
		
		Emp empBefore = hd.selectEmp(request.getParameter("emp_no"));

		Emp empAfter = new Emp();
		//사번, x부서번호, 비밀번호, 이름, 이메일, 우편번호, 주소, 상세주소, 전화번호, x입사일, 퇴사여부
		empAfter.setEmp_no			(request.getParameter("emp_no"));
		empAfter.setDept_no			(empBefore.getDept_no());
		
		//비밀번호가 파라미터로 넘어오지 않았을 때
		//이전 패스워드를 그대로 사용
		if (request.getParameter("password") == null || request.getParameter("password").trim().equals("")) {
			empAfter.setPassword(empBefore.getPassword());
		} else {
			empAfter.setPassword(request.getParameter("password"));
		}
		empAfter.setEmp_name		(request.getParameter("emp_name"));
		empAfter.setEmp_email		(request.getParameter("emp_email"));
		empAfter.setEmp_addr_no		(request.getParameter("emp_addr_no"));
		empAfter.setEmp_addr		(request.getParameter("emp_addr"));
		empAfter.setEmp_addr_detail	(request.getParameter("emp_addr_detail"));
		empAfter.setEmp_tel			(request.getParameter("emp_tel"));
		empAfter.setHiredate		(empBefore.getHiredate());
		
		int result = hd.updateEmp(empAfter);
		
		request.setAttribute("result", result);
		
		if (request.getSession().getAttribute("newbee") != null && !empAfter.getPassword().equals("0000")) {
			request.getSession().removeAttribute("newbee");
		}
		
		return "/view/hr/updateResult.jsp";
	}

}
