/* ProductDetailTag.java - a tag to display the content for productdetail.jsp */

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

public class ProductDetailTag extends TagSupport {
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

		String message=(String)request.getAttribute("message");
		if (message==null) message="Select the catalogue you wish to view.";

		// Get the store items on sale, from the session attributes (which are retrieved by the DisplayCatalogue servlet).	
		ArrayList columnNames=(ArrayList)session.getAttribute("columnNames");
		ArrayList catalogueItems=(ArrayList)session.getAttribute("catalogueItems");

		try{
			out.print("<h1>Online Store</h1>");
			out.print(message);
			out.print("<br>");
			out.print("<br>");
		
			out.print("<br>");
			out.print("<br>");
			out.print("<br>");
			
			if (catalogueItems!=null) {
				// Use the product parameter from the querystring to display details on a product selected by the member...
				out.print("<table>");
				out.print("<tr>");
				int productNum=Integer.parseInt(request.getParameter("product"));
				ArrayList itemDetails=(ArrayList)catalogueItems.get(productNum);
				out.print("<td>");
				out.print("<h1>");
				out.print(itemDetails.get(0));
				out.print("</h1>");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print(itemDetails.get(2));
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print(itemDetails.get(1));
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("<h1>");
				out.print("£"+itemDetails.get(3));
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("ProductID: "+itemDetails.get(5));
				out.print("</td>");
				out.print("</tr>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				String buyitem=response.encodeURL("viewbasket.jsp?item="+productNum);
				out.print("<a href=\""+buyitem+"\">Buy this item</a>");
				out.print("</td>");
				out.print("</tr>");
				out.print("</table>");
				out.print("<br>");
				String back=response.encodeURL("store.jsp");
				out.print("<a href=\""+back+"\">Back to catalogue list</a>");
			}
			out.print("<br>");
			out.print("<br>");
			out.print("<br>");	
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("ProductDetailTag: "+msg);
	}
}
