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

@WebServlet(urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String msg = "";
		if(request.getParameter("msg") != null) msg = request.getParameter("msg");
		out.println(HelperMethods.getHtmlHeader("Home Page"));
		HelperMethods.getCreateModifyAccountContent("Create",new User(),out,msg);
		out.println(HelperMethods.getHtmlFooter());	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		
		String lName = request.getParameter("lname");
		String name = request.getParameter("username");
		String lPass = request.getParameter("lpass");
		DB_Access db = new DB_Access();
		// return if incorrect inputs
		if(!HelperMethods.isParamValid(lName) || !HelperMethods.isParamValid(name) || !HelperMethods.isParamValid(lPass)) {
			response.sendRedirect("CreateAccount?msg=Try again with valid inputs");
		} else {
			if(db.validateLogin(lName)) {
				// ask user to try different login name
				response.sendRedirect("CreateAccount?msg=Login Name is in use, try a different login name");
			} else {
				// do the db insert
				int uid = db.insertAccount(lName, name, lPass);
				if(uid== -1) {
					// failure inserting
					response.sendRedirect("CreateAccount?msg=Something went wrong, please try again ");
				}
				else {
					//success - set session and show home page
					sess.setAttribute("uid", uid);
					response.sendRedirect("Home");
				}
			}
		}	
	}
}
