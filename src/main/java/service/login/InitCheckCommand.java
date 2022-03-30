package service.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Emp;
import dao.HrDao;

public class InitCheckCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//url로 직접 쳐서 실행시키는 경우 방지
		if (request.getHeader("referer") == null || !(request.getHeader("referer").contains("initForm.do"))) {
			return "/loginForm.do";
		}
		
		String emp_no = request.getParameter("emp_no");
		String email = request.getParameter("email");
		int result = 0;	//결과반환을 위한 변수
				
		Emp emp = HrDao.getInstance().selectEmp(emp_no);

		
		if (emp == null) {
			result = 0;	//존재하지 않는 아이디
		} else {
			if(email == null || email.trim().equals("")) {
				result = 1;										//존재하는 사번, email입력 form 활성화			
			} else if (email.equals(emp.getEmp_email())) {
				result = 3;										//존재하는 사번, 일치하는 이메일 : 초기화 진행
			} else {
				result = 2; 									//존재하는 사번, 일치하지 않는 이메일
			}
		}

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().println(result);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		return null;
	}

}
