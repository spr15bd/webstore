/* MembersHomeTag - tag to display the content used for the membershome jsp*/

package store;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MembersHomeTag extends TagSupport {
	private String firstname = "you are now a member!";
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		String firstname=(String)session.getAttribute("fname");
		JspWriter out = pageContext.getOut();
		try {
			out.print("<table height=\"500\">");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<h1>Member's Homepage</h1>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<h2>Welcome, "+firstname+"</h2>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td valign=\"bottom\" height=\"350\">");
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

	public void setFirstname(String firstname) {
		this.firstname=firstname;
	}

	public String getFirstname() {
		return firstname;
	}
} // class