/* EditDetailsTag.java - a tag to display the content for editdetails.jsp */

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class EditDetailsTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException{
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		HttpSession session=request.getSession(false);

		String ecommerceservlet=response.encodeURL("getServlet?action=editMemberDetails");
		String message=(String)request.getAttribute("message");
		String user=(String)session.getAttribute("user");
		String fname=(String)session.getAttribute("fname");
		String lname=(String)session.getAttribute("lname");
		String address1=(String)session.getAttribute("address1");
		String address2=(String)session.getAttribute("address2");
		String postcode=(String)session.getAttribute("postcode");
		String country=(String)session.getAttribute("country");
		String password=(String)session.getAttribute("password");

		if (message==null) message="";
		if (user==null) user="";
		if (fname==null) fname="";
		if (lname==null) lname="";
		if (address1==null) address1="";
		if (address2==null) address2="";
		if (postcode==null) postcode="";
		if (country==null) country="";
		if (password==null) password="";

		
		try{
			out.print("<table height=\"500\">");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<h1>Edit Personal Details</h1>");
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
			out.print("<td><input type=\"reset\" value=\"Clear Display\"></td><td><input type=\"submit\" name=\"send\" value=\"Update Details\"></td>");
			out.print("</tr>");
			out.print("</table>");
			out.print("</form>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td>");
			out.print("<font color=\"red\">*</font> - Denotes Mandatory field");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td valign=\"bottom\">");
	
	
			out.print("</td>");
			out.print("</tr>");
			out.print("</table>");	
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("EditDetailsTag: "+msg);
	}
}
