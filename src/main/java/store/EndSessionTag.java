/* EndSessionTag.java - makes sure the user's session is invalid, when the user logs out voluntarily. */

package store;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EndSessionTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		session.setAttribute("user", null);
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("EndSessionTag: "+msg);
	}
	
	
}
