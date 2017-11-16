package testpack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String msg = "";
		if(request.getParameter("msg") != null) msg = request.getParameter("msg");
		
		out.println(HelperMethods.getHtmlHeader("LoginPage"));
		out.println("<form method=post>");
		out.println("<h2>Login</h2>");
		out.println("<h3 style=\"color:red;\">"+msg+"</h3>");
		out.println("Login Name: <input type=text name=lname><br>");
		out.println("Password: <input type=password name=pass><br>");
		out.println("<input type=submit value=Login>");
		out.println("</form>");
		out.println("<br><br><h2><a href=CreateAccount>Create Account</a></h2>");
		out.println(HelperMethods.getHtmlFooter());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lname = request.getParameter("lname");
		String pass = request.getParameter("pass");
		
		if(HelperMethods.isParamValid(lname) && 
				HelperMethods.isParamValid(pass)) {
			// both values are ok and need to be sent to the database
			DB_Access db = new DB_Access();
			int uid = db.validateLogin(lname, pass);
			if(uid==-1) {
				// -1 means login credentials were wrong, send user back to login page
				response.sendRedirect("Login?msg=wrong login credentials, try again");
			}
			else {
				// we have valid login credentials, send user to the home page
				HttpSession sess = request.getSession();
				sess.setAttribute("uid", uid);
				response.sendRedirect("Home");
			}
		}
		else {
			// either both or one of the params did not meet the business requirements
			// send back to the login page with error message
			response.sendRedirect("Login?msg=wrong login credentials, try again");
		}
	}
}










