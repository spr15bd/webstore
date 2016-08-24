/* OrderSuccessfulTag.java - a tag to display the content for ordersuccessful.jsp */

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

public class OrderSuccessfulTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException{
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);

		try{
			out.print("<h1>Order Successful!</h1>");
			out.print("<br>");
			out.print("<br>");
			session.setAttribute("basket", null);
			out.print((String)request.getAttribute("message"));
			out.print("<br>");
			out.print("<br>");
			out.print("<br>");
			out.print("<br>");	
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("OrderSuccessfulTag: "+msg);
	}
}
