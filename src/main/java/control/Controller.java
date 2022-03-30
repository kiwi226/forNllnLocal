package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Command;


/**
 * Servlet implementation class UserController
 */
@WebServlet(urlPatterns = "*.do", initParams = {@WebInitParam(name = "configFile", value = "/WEB-INF/prop/command.properties")})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Command> commandMap = new HashMap<>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String path = config.getInitParameter("configFile");
		String realPath = config.getServletContext().getRealPath(path);
		
		Properties prop = new Properties();
		
		try (
			FileInputStream fis = new FileInputStream(realPath);
		) {
			prop.load(fis);
			
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		Iterator<Object> iter = prop.keySet().iterator();
		while (iter.hasNext()) {
			String command = (String) iter.next();
			String className = prop.getProperty(command);
			
			try {
				Class<?> commandClass = Class.forName(className);
				Command	commandInstance = (Command) commandClass.getDeclaredConstructor().newInstance();
				commandMap.put(command, commandInstance);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pgm = request.getRequestURI().substring(request.getContextPath().length() + 1);
		Command command = commandMap.get(pgm);
		String viewPage = null;
		
		if (command != null) 
			viewPage = command.execute(request, response);
		else if (command == null) 
			viewPage = "/view/login/loginForm.jsp";
		
		if (viewPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}
	
}
