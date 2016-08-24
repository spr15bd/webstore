/* StoreMenuTag - displays the menu used by the store pages of the site.*/

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

public class StoreMenuTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		
		String displaybookscatalogue = response.encodeURL("getServlet?action=displayCatalogue&cat=book");
		String displayvideoscatalogue = response.encodeURL("getServlet?action=displayCatalogue&cat=dvd");
		String viewbasket = response.encodeURL("viewbasket.jsp");
		String checkout = response.encodeURL("checkout.jsp");

		try{
			out.print("<a href=\""+displaybookscatalogue+"\">Books</a> | <a href=\""+displayvideoscatalogue+"\">DVDs</a> | <a href=\""+viewbasket+"\">View Shopping Basket</a> | <a href=\""+checkout+"\">Checkout</a>");
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("StoreMenuTag: "+msg);
	}
}
