/* OuterMenuTag - displays the initial menu (the one for pages outside the members' area).*/

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

public class OuterMenuTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		
		String home=response.encodeURL("home.jsp");
		String register=response.encodeURL("register.jsp");
		String login=response.encodeURL("login.jsp");

		try{
			out.print("<a href=\""+home+"\">Home</a> | <a href=\""+register+"\">Register</a> | <a href=\""+login+"\">Login</a>");
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("OuterMenuTag: "+msg);
	}
}
