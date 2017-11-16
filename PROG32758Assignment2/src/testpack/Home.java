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

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		if(sess.getAttribute("uid") == null) {
			// user never used the login page, send him back to login page
			response.sendRedirect("Login?msg=please login first");
		}
		else {
			int uid = (Integer) sess.getAttribute("uid");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			DB_Access db = new DB_Access();
			String uname = db.getUserName(uid);
			ArrayList<Item> items = db.getUserItems(uid);
			
			String msg = "";
			if(request.getParameter("msg") != null) msg = request.getParameter("msg");
			
			out.println(HelperMethods.getHtmlHeader("Home Page"));
			out.println("<div id=\"header\"><span style=\"display:block;float:left;\">Welcome: "+uname+" </span><span style=\"display:block;float:right;\"><a href=ModifyAccount>Modify Account</a> <a href=LogOut>Log Out</a></span></div>");
			out.println("<br><h3 style=\"color:red;clear:both;\">"+msg+"</h3>");
			out.println("<h2>List of Items</h2>");
			out.println("<table>");
			out.println("<tr><th>Actions</th><th>Item Name</th><th>Quantity</th></tr>");
			for(Item i : items) {
				out.println("<tr>");
				out.println("<td>");
				out.println("<a href=ViewItem?id="+i.getIid()+">View</a> ");
				out.println("<a href=ModifyItem?id="+i.getIid()+">Modify</a> ");
				out.println("<a href=DeleteItem?id="+i.getIid()+">Delete</a>");
				out.println("</td>");
				out.println("<td>"+i.getItemName()+"</td>");
				out.println("<td>"+i.getQuantity()+"</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("<h2>Insert New Item</h2>");
			out.println("<form action=InsertItem method=post>");
			out.println("Item Name: <input type=text name=iname> ");
			out.println("Quantity: <input type=text name=iqty><br>");
			out.println("<input type=submit value=Create>");
			out.println("</form>");
			out.println(HelperMethods.getHtmlFooter());
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}











