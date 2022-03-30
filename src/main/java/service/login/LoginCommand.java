package service.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Emp;
import dao.HrDao;

public class LoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String emp_no 	= request.getParameter("emp_no");
		String password = request.getParameter("password");
		
		//로그인 결과표
		//  0 : 아이디 없음
		// -1 : 아이디 있음 / 비밀번호 틀림
		//  1 : 아이디 있음 / 비밀번호 맞음
		
		int result = 0;
		
		HrDao hd = HrDao.getInstance();
		Emp emp = hd.selectEmp(emp_no);
		
		if (emp == null) {
			result = 0;
		} else if (password.equals(emp.getPassword())) {
			result = 1;
			request.getSession().setAttribute("Hr", hd.selectHr(emp_no));
			if (password.equals("0000")) {
				request.getSession().setAttribute("newbee", "newbee");
			}
		} else {
			result = -1;
		}
		
		request.setAttribute("result", result);
		
		return "/view/login/loginResult.jsp";
	}

}
