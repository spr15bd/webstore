/* InnerMenuTag - displays the inner area menu (the menu for pages within the members' area).*/

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

public class InnerMenuTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		
		String membershome = response.encodeURL("membershome.jsp");
		String viewdetails = response.encodeURL("viewdetails.jsp");
		String editdetails = response.encodeURL("editdetails.jsp");
		String pastorders = response.encodeURL("pastorders.jsp");
		String membersdetails = response.encodeURL("othermembers.jsp");
		String store = response.encodeURL("store.jsp");
		String logout = response.encodeURL("home.jsp");

		try{
			out.print("<a href=\""+membershome+"\">Member HomePage</a> | <a href=\""+viewdetails+"\">View Details</a> | <a href=\""+editdetails+"\">Edit Details</a> | <a href=\""+pastorders+"\">Past Orders</a> | <a href=\""+membersdetails+"\">Other Members</a> | <a href=\""+store+"\">Online Store</a> | <a href=\""+logout+"\">Logout</a>");
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("InnerMenuTag: "+msg);
	}
}
