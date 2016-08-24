/* LoginTag.java - tag to display the content used by login.java*/

package store;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");

		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		JspWriter out = pageContext.getOut();
		String ecommerceservlet = response.encodeURL("getServlet?action=login");
		String message=(String)request.getAttribute("message");
		String user=(String)request.getAttribute("user");
		String password=(String)request.getAttribute("password");
	
		if (message==null) message="If you are a member, please type your username and password.<br><br>If not, you must first register to enter this website.<br><br><font color=red>[For demo purposes you may use the login details below]</font>";
		//if (user==null) user="";
		//if (password==null) password="";
		// Replace the two lines above with the two lines below for demo purposes
		user="johnsmith";
		password="passw";
		try {
			out.print("<table width=\"400\" height=\"500\">");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<h1>Login</h1>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td>");
			out.print(message);
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td colspan=\"2\">");
			out.print("<form action=\""+ecommerceservlet+"\" method=\"POST\">");
			out.print("<table border=\"2\">");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Username</td>");
			out.print("<td><input type=\"text\" name=\"user\" value=\""+user+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Password</td><td><input type=\"password\" name=\"password\" value=\""+password+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td><input type=\"reset\" value=\"Clear Display\"></td><td><input type=\"submit\" name=\"send\" value=\"Logon\"></td>");
			out.print("</tr>");
			out.print("</table>");
			out.print("</form>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td valign=\"bottom\">");
			out.print("</td>");
			out.print("</tr>");
			out.print("</table>");
		} catch (IOException ioe) {
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
	}

	private static void dbm(String msg) {
		System.out.println("HomeTag: "+msg);
	}

} // class