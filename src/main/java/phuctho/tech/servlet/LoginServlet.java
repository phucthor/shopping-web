package phuctho.tech.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import phuctho.tech.connection.DbCon;
import phuctho.tech.dao.UserDao;
import phuctho.tech.model.User;


@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
//			out.print("this is login servlet");
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
//			out.print(email+password);
			
			try {
				UserDao udao = new UserDao(DbCon.getConnection());
				User user = udao.userLogin(email, password);
				
				if(user != null) {
					out.print("user login");
					request.getSession().setAttribute("auth", user);
					response.sendRedirect("index.jsp");
				}else {
					out.print("user login failed");
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
