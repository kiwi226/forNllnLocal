package service.hr;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;

import model.Emp;
import dao.HrDao;

public class Init implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//유효한 접근인지 확인
		if (request.getHeader("referer") == null || !(request.getHeader("referer").contains("checkForm.do") || request.getHeader("referer").contains("initForm.do"))) {
			request.setAttribute("result", -1);
			return "/view/hr/initPasswordResult.jsp";
		}
		
		//비밀번호 초기화
		String emp_no = request.getParameter("emp_no");
		//임의의 문자 15자의 문자열 만들기
		String newPassword = getNewPassword();
		
		//사원의 패스워드 변경
		Emp emp = HrDao.getInstance().selectEmp(emp_no);
		emp.setPassword(newPassword);
		
		//비밀번호 초기화 성공 시 사용자를 보낼 주소
		String nextURL = emp.getEmp_email().substring(emp.getEmp_email().indexOf('@') + 1);
		
		//변경된 패스워드를 사원의 메일주소로 보내기
		Properties p = new Properties(); // 정보를 담을 객체 
		p.put("mail.smtp.host","smtp.naver.com"); 
		p.put("mail.smtp.port", "587"); 
		p.put("mail.smtp.starttls.enable", "true"); 
		p.put("mail.smtp.auth", "true");
//		p.put("mail.smtp.debug", "true"); 
		p.put("mail.smtp.ssl.protocols", "TLSv1.2");

//		p.put("mail.smtp.socketFactory.port", "465"); 
//		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
//		p.put("mail.smtp.socketFactory.fallback", "false");
		
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(p, auth);
//		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setSubject("[만만]비밀번호 변경 알림");
			
			Address fromAddr = new InternetAddress("ejm4000@naver.com");
			message.setFrom(fromAddr);
			
			Address toAddr = new InternetAddress(emp.getEmp_email());
			message.setRecipient(Message.RecipientType.TO, toAddr);
			
			String msg = 
					  "<!DOCTYPE html>\r\n"
					+ "<html style=\"margin: 0; padding: 0; font-size: 10px;\">\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"UTF-8\">\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"font-family: 'Nanum Gothic', sans-serif; background: #f8f7f2; border-radius: 0.8rem;\" width=\"600\">\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"padding-left: 20px; padding-top: 20px; padding-bottom: 20px;\">\r\n"
					+ "				<img alt=\"\" src=\"https://raw.githubusercontent.com/fjswhd/project-erp/fjswhd/src/main/webapp/images/logo4.jpg\" width=\"200\">\r\n"
					+ "			</td>\r\n"
					+ "		</tr>\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"padding-left: 20px; font-size: 24px;\">\r\n"
					+ "				귀하의 계정 비밀번호가 초기화되었습니다.<br>\r\n"
					+ "				새로운 비밀번호는\r\n"
					+ "			</td>\r\n"
					+ "		</tr>\r\n"
					+ "		<tr>\r\n"
					+ "			<td align=\"center\">\r\n"
					+ "				<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background: #ffffff; border-radius: 0.8rem;\" width=\"400\">\r\n"
					+ "					<tr>\r\n"
					+ "						<td align=\"center\" style=\"font-size: 32px; color: #f4a261;\">\r\n"
					+ "							"+newPassword+"\r\n"
					+ "						</td>\r\n"
					+ "					</tr>\r\n"
					+ "				</table>\r\n"
					+ "			</td>\r\n"
					+ "		</tr>\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"padding-left: 20px; font-size: 24px\">\r\n"
					+ "				입니다.\r\n"
					+ "			</td>\r\n"
					+ "		</tr>\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"padding-left: 20px; padding-bottom: 10px; font-size: 16px\">\r\n"
					+ "				<a href=\"http://localhost:"+request.getLocalPort()+"/project/loginForm.do\">로그인 페이지 바로가기</a>\r\n"
					+ "			</td>\r\n"
					+ "		</tr>\r\n"
					+ "	</table>\r\n"
					+ "</body>\r\n"
					+ "</html>";
			
			message.setContent(msg, "text/html;charset=utf-8");
			
			Transport.send(message);
			
			int result = HrDao.getInstance().updateEmp(emp);
			
			request.setAttribute("result", result);
			request.setAttribute("nextURL", nextURL);
			
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}		
		
		return "/view/hr/initPasswordResult.jsp";
	}

	private String getNewPassword() {
		String newPassword = "";
		
		//초기화된 패스워드가 숫자를 포함하고, 영 소문자를 포함하고, 영 대문자를 포함할 때까지
		while (!(newPassword.matches(".*[0-9]+.*") && newPassword.matches(".*[a-z]+.*") && newPassword.matches(".*[A-Z]+.*"))) {
			for(int i = 0; i < 15; i++) {
				
				int j = (int)(Math.random()*3);
				int k = 0;
				
				if (j == 0) 
					k = (int)(Math.random() * 10) + 48;
				else if (j == 1) 
					k = (int)(Math.random() * 26) + 65;
				else if (j == 2) 
					k = (int)(Math.random() * 26) + 97;
				
				char c = (char)k;
				
				newPassword += c; 
			}
		}
		return newPassword;
	}
}

class SMTPAuthenticator extends Authenticator {
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("ejm4000@naver.com", "M12LJH9VHBSS");
	}
}
