/* ViewDetailsTag.java - a tag to display the content for viewdetails.jsp */

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class ViewDetailsTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException{
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		String fname=(String)session.getAttribute("fname");

		try{
			out.print("<table height=\"500\">");
			out.print("<tr><td valign=\"top\"><h1>View Personal Details</h1></td></tr>");
			out.print("<tr><td>");
			out.print("<table width=\"275\" height=\"300\">");
			out.print("<tr><td>"+"Username:	"+"</td><td><b>"+session.getAttribute("user")+"</b></td></tr>");
			out.print("<tr><td>"+"Password:	"+"</td><td><b>"+session.getAttribute("password")+"</b></td></tr>");
			out.print("<tr><td>"+"Firstname:"+"</td><td><b>"+session.getAttribute("fname")+"</b></td></tr>");
			out.print("<tr><td>"+"Surname:	"+"</td><td><b>"+session.getAttribute("lname")+"</b></td></tr>");
			out.print("<tr><td>"+"Address1:	"+"</td><td><b>"+session.getAttribute("address1")+"</b></td></tr>");
			out.print("<tr><td>"+"Address2:	"+"</td><td><b>"+session.getAttribute("address2")+"</b></td></tr>");
			out.print("<tr><td>"+"Postcode:	"+"</td><td><b>"+session.getAttribute("postcode")+"</b></td></tr>");
			out.print("<tr><td>"+"Country:	"+"</td><td><b>"+session.getAttribute("country")+"</b></td></tr>");
			out.print("</table>");
			out.print("</td></tr>");
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
		System.out.println("ViewDetailsTag: "+msg);
	}
}
