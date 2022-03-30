package service.hr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Emp;
import model.Hr;
import dao.HrDao;

public class Check implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int result = 1;
		
		if(request.getSession().getAttribute("checkResult") != null)
			result = (int)request.getSession().getAttribute("checkResult");
		
		//현재 세션에 등록된 사원의 비밀번호와 일치하는지 확인
		Emp emp = HrDao.getInstance().selectEmp((((Hr)request.getSession().getAttribute("Hr")).getEmp_no()));
		
		if(request.getParameter("password").equals(emp.getPassword())) {
			result = 0;
			request.getSession().removeAttribute("checkResult");
		} else {
			result++;
			request.getSession().setAttribute("checkResult", result);
		}
		
		if (result > 5) {
			request.getSession().invalidate();
		}
		
		try {
			response.getWriter().println(result);
		} catch (IOException e) {
			
		}
		
		return null;
	}

}
