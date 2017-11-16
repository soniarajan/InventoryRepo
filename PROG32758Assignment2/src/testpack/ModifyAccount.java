package testpack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/ModifyAccount"})
public class ModifyAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		if(sess.getAttribute("uid") == null) {
			// user is not loged in, send user to login page
			response.sendRedirect("Login?msg=must login first");
		}else{
			int uid = (Integer) sess.getAttribute("uid");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			DB_Access db = new DB_Access();
			User user = db.getUserInfo(uid);
			

			String msg = "";
			if(request.getParameter("msg") != null) msg = request.getParameter("msg");
			out.println(HelperMethods.getHtmlHeader("Home Page"));
			HelperMethods.getCreateModifyAccountContent("Modify",user,out,msg);
			out.println(HelperMethods.getHtmlFooter());
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		if(sess.getAttribute("uid") == null) {
			// user is not loged in, send user to login page
			response.sendRedirect("Login?msg=must login first");
		}
	else {
		if(request.getParameter("cancel") != null) {
			response.sendRedirect("Home");
			return;
		}
			// modify account
			String lName = request.getParameter("lname");
			String name = request.getParameter("username");
			String lPass = request.getParameter("lpass");
			if(!HelperMethods.isParamValid(lName) || !HelperMethods.isParamValid(name) || !HelperMethods.isParamValid(lPass)) {
				response.sendRedirect("ModifyAccount?msg=Try again with valid inputs");
			}else {
				DB_Access db = new DB_Access();
				boolean res = db.modifyAccount((Integer)sess.getAttribute("uid"), lName, name, lPass);
				if(res) {
					// success inserting
					response.sendRedirect("Home?msg=success updating account");
				}
				else {
					// failure inserting
					response.sendRedirect("Home?msg=failure to update account");
				}
			}
			
		}
	}
}
