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

@WebServlet(urlPatterns = {"/ModifyItem"})
public class ModifyItem extends HttpServlet {
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
			String id = request.getParameter("id");
			Integer intId =Integer.valueOf(id);
			Item item = db.getItemById(intId,uid);
			String msg = "";
			if(request.getParameter("msg") != null) msg = request.getParameter("msg");
			out.println(HelperMethods.getHtmlHeader("Home Page"));
			out.println("<div id=\"header\"><span style=\"display:block;float:left;\">Welcome: "+user.getName()+" </span><span style=\"display:block;float:right;\"> <a href=LogOut>Log Out</a></span></div>");
			out.println("<h2>Update Item Details</h2>");
			out.println("<h3 style=\"color:red;\">"+msg+"</h3>");
			out.println("<form action=ModifyItem method=post>");
			out.println("Item Name: <input type=text name=iname value= \""+ item.getItemName() + "\"> ");
			out.println("Quantity: <input type=text name=iqty value = \""+ item.getQuantity()+ "\"><br>");
			out.println("Quantity: <input type=text name=id   style=\"display:none\"  value = \""+ item.getIid() + "\"><br>");
			out.println("<input type=submit value=Update> <span></span>");
			out.println("<input type=submit name=cancel value=Cancel>");;
			out.println("</form>");
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
			String iName = request.getParameter("iname");
			String iQty = request.getParameter("iqty");
			String id = request.getParameter("id");
			int qty = 0;
			try {
				qty = Integer.parseInt(iQty);
			}catch(Exception e) {}
			if(iName == null || iName.isEmpty()) {
				response.sendRedirect("ModifyItem?id="+id+"&msg=Try again with valid inputs");
			}else {
				DB_Access db = new DB_Access();
				boolean res = db.modifyItem((Integer)sess.getAttribute("uid"), iName, iQty, Integer.valueOf(id));
				if(res) {
					// success inserting
					response.sendRedirect("Home?msg=success updating item");
				}
				else {
					// failure inserting
					response.sendRedirect("Home?msg=failure to update item");
				}
			}
			
		}
	}
}
