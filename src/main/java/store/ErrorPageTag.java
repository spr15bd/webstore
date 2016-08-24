/* ErrorPageTag.java - displays error info on exceptions thrown. */

package store;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;

public class ErrorPageTag extends TagSupport {
	
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		
		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
		JspWriter out=pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Exception exception = (Exception) request.getAttribute("javax.servlet.jsp.jspException");
		try {
			out.print(exception);
			out.println( "<!--" );
    			
    			exception.printStackTrace( pw );
    			out.print(sw);
    			sw.close();
    			pw.close();
    			out.println( "-->" );
    		     
			
		} catch (IOException ioe) {
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	static void dbm(String msg) {
		System.out.println("ErrorPageTag: "+msg);
	}
}


