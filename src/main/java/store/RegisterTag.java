/* RegisterTag.java - displays the content used for register.jsp*/

package store;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");

		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		JspWriter out = pageContext.getOut();
		String ecommerceservlet=response.encodeURL("getServlet?action=register");
		String message=(String)request.getAttribute("message");
		String user=(String)request.getAttribute("user");
		String fname=(String)request.getAttribute("fname");
		String lname=(String)request.getAttribute("lname");
		String address1=(String)request.getAttribute("address1");
		String address2=(String)request.getAttribute("address2");
		String postcode=(String)request.getAttribute("postcode");
		String country=(String)request.getAttribute("country");
		String password=(String)request.getAttribute("password");

		if (message==null) message="Please enter the following details and we will set up your account.";
		if (user==null) user="";
		if (fname==null) fname="";
		if (lname==null) lname="";
		if (address1==null) address1="";
		if (address2==null) address2="";
		if (postcode==null) postcode="";
		if (country==null) country="";
		if (password==null) password="";

		try{
			out.print("<table width=\"400\" height=\"500\">");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<h1>Register</h1>");
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
			out.print("<td align=\"left\" bgcolor=\"green\">First Name<font color=\"red\">*</font></td><td><input type=\"text\" name=\"fname\" value=\""+fname+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Last Name<font color=\"red\">*</font></td><td><input type=\"text\" name=\"lname\" value=\""+lname+"\"></td>"); 
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Address1<font color=\"red\">*</font></td><td><input type=\"text\" name=\"address1\" value=\""+address1+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Address2</td><td><input type=\"text\" name=\"address2\" value=\""+address2+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Postcode</td><td><input type=\"text\" name=\"postcode\" value=\""+postcode+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Country<font color=\"red\">*</font></td><td><input type=\"text\" name=\"country\" value=\""+country+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Username<font color=\"red\">*</font></td><td><input type=\"text\" name=\"user\" value=\""+user+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td align=\"left\" bgcolor=\"green\">Password<font color=\"red\">*</font></td><td><input type=\"password\" name=\"password\" value=\""+password+"\"></td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td><input type=\"reset\" value=\"Clear Display\"></td><td><input type=\"submit\" name=\"send\" value=\"Register\"></td>");
			out.print("</tr>");
			out.print("</table>");
			out.print("</form>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td colspan=\"2\">");
			out.print("<font color=\"red\">*</font> - Denotes Mandatory field");
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
		System.out.println("RegisterTag: "+msg);
	}

} // class