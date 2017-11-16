package testpack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/InsertItem")
public class InsertItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		if(sess.getAttribute("uid") == null) {
			// user is not loged in, send user to login page
			response.sendRedirect("Login?msg=must login first");
		}
		else {
			// insert item
			String itemName = request.getParameter("iname");
			String itemQty = request.getParameter("iqty");
			int uid = (Integer) sess.getAttribute("uid");
			int qty = 0;
			try {
				qty = Integer.parseInt(itemQty);
			}catch(Exception e) {}
			DB_Access db = new DB_Access();
			boolean res = db.insertItem(uid, itemName, qty);
			if(res) {
				// success inserting
				response.sendRedirect("Home?msg=success inserting item");
			}
			else {
				// failure inserting
				response.sendRedirect("Home?msg=failure to insert item");
			}
		}
	}

}








