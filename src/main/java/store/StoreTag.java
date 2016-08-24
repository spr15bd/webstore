/* StoreTag.java - a tag to display the content for store.jsp */

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

public class StoreTag extends TagSupport {
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

			
			if (columnNames!=null && catalogueItems!=null) {
				out.print("<table>");
				out.print("<tr><b>");
	
				for (int a=0; a<columnNames.size(); a++) {
					out.print("<td>");
					String name=(String)columnNames.get(a);
			
					// Not all the columns are displayed on the store page (some are on the individual product pages)...
					if (!name.equals("Description") && !name.equals("Type") && !name.equals("Productid")) {
						out.print("<b>"+name+"</b>");
					}
					out.print("</td>");		
				}
				out.print("</b></tr>");
	
				for (int b=0; b<catalogueItems.size(); b++) {
					out.print("<tr>");
					ArrayList itemDetails=(ArrayList)catalogueItems.get(b);
					for (int c=0; c<=itemDetails.size(); c++) {
						out.print("<td>");
						if (c==itemDetails.size()) {
							String addtobasket=response.encodeURL("viewbasket.jsp?item="+b);
							out.print("<a href=\""+addtobasket+"\">Buy</a>");
						} else if (c==0){
							String productdetail=response.encodeURL("productdetail.jsp?product="+b);
							out.print("<a href=\""+productdetail+"\">"+itemDetails.get(c)+"</a>");
						} else if (c>1 && c<4) {
							out.print(itemDetails.get(c));
						}
						out.print("</td>");
					}
					out.print("</tr>");
				}
				out.print("</table>");
				out.print("<br>");
				out.print("<br>");
			} else {
				out.print("<table height=\"300\">");
				out.print("<tr><td></td></tr>");
				out.print("</table>");
				out.print("<br>");
				out.print("<br>");
			}	
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("StoreTag: "+msg);
	}
}
