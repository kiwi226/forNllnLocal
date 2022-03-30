package service.hr;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Dept;
import dao.HrDao;

public class InsertForm implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HrDao hd = HrDao.getInstance();
		
		//올해 연도 구하고 앞 2자리 자르기
		String year = Calendar.getInstance().get(Calendar.YEAR) + "";
		year = year.substring(2);
		
		//연도 값으로 올해 입사한 사원수 구한 뒤 그 다음 사번값 만들기
		String no = "0000" + (hd.selectEmpCountWithYear(year) + 1);	//올해 입사한 사원수 + 1 한뒤 앞에 0000붙이고
		no = no.substring(no.length() - 5);							//그 문자열 중에서 뒤에 5자리만 남기고 자름
		
		String emp_no = year + "-" + no;
		request.setAttribute("emp_no", emp_no);
		
		//부서리스트 받아서 insertForm에 출력
		List<Dept> deptList = hd.selectDeptList();
		request.setAttribute("deptList", deptList);
		
		return "/view/hr/insertForm.jsp";
	}

}
