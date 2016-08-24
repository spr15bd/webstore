/* HomeTag - tag to display the content used for home.jsp*/

package store;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import javax.servlet.*;

public class HomeTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}

	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out = pageContext.getOut();
		try {
			out.print("<table width=\"400\" height=\"350\">");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<h1>Homepage</h1>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td valign=\"top\">");
			out.print("<img src=\"images/justbooksanddvds1.png\"></img>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td>");
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