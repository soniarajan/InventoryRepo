package testpack;

import java.io.PrintWriter;

public class HelperMethods {
	public static String getHtmlHeader(String title) {
		return "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><title>"+title+"</title></head><body><center>";
	}
	public static String getHtmlFooter() {
		return "</center></body></html>";
	}
	public static boolean isParamValid(String param) {
		boolean valid = true;
		
		if(param == null || param.trim().equals("") || param.length() < 4 || param.length()> 20)
			valid = false;
		
		return valid;
	}
	
	public static void getCreateModifyAccountContent( String action , User user, PrintWriter out, String msg){
		out.println("<div id=\"header\"><span style=\"display:block;float:left;\">Welcome: "+user.getName()+" </span><span style=\"display:block;float:right;\"> <a href=LogOut>Log Out</a></span></div>");
		String login = (null == user.getLoginName() ) ?  "" : user.getLoginName();
		String name = (null == user.getName() ) ?  "" : user.getName();
		String pass = (null == user.getLoginPass() ) ?  "" : user.getLoginPass();
		
		out.println("<h2>"+ action + " User Details</h2>");
		out.println("<h3 style=\"color:red;\">"+msg+"</h3>");
		out.println("<form action="+ action +"Account method=post>");
		out.println("Name		: <input type=text name=username value =\""+ name + "\"><br>");
		out.println("Login Name : <input type=text name=lname value =\"" +login+"\"><br>");
		out.println("Password	: <input type=text name=lpass value =\""  + pass + "\"><br>");
		out.println("<input type=submit value=Update> <span></span>");
		out.println("<input type=submit value=Cancel name= cancel>");
		out.println("</form>");
		
		
	}
}
