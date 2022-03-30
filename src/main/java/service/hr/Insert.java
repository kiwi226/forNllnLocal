package service.hr;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Emp;
import dao.HrDao;

public class Insert implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Emp emp = new Emp();
		emp.setEmp_no(			request.getParameter("emp_no"));
		emp.setEmp_name(		request.getParameter("emp_name"));
		emp.setDept_no(			request.getParameter("dept_no"));
		emp.setEmp_addr_no(		request.getParameter("emp_addr_no"));
		emp.setEmp_addr(		request.getParameter("emp_addr"));
		emp.setEmp_addr_detail(	request.getParameter("emp_addr_detail"));
		emp.setEmp_tel(			request.getParameter("emp_tel"));
		emp.setEmp_email(		request.getParameter("emp_email"));
		emp.setHiredate(		Date.valueOf(request.getParameter("hiredate")));   
		
		int result = HrDao.getInstance().insertEmp(emp);
		
		request.setAttribute("result", result);
		
		return "/view/hr/insertResult.jsp";
	}

}


