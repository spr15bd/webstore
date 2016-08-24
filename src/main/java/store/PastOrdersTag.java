/* PastOrdersTag.java - a tag to display the content for pastorders.jsp */

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class PastOrdersTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException{
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		
		String pastorderdata=response.encodeURL("getServlet?action=pastOrders");

		ArrayList columnNames=(ArrayList) session.getAttribute("columns");
		ArrayList productsOrdered=(ArrayList) session.getAttribute("products");
		String firstname=(String)session.getAttribute("fname");
		String surname=(String)session.getAttribute("lname");
		if (session==null) dbm("No session");
		if (columnNames==null || productsOrdered==null){
			dbm("No order data");
			RequestDispatcher rd=request.getRequestDispatcher(pastorderdata);
			try{
				rd.forward(request, response);
			} catch (ServletException se){
				dbm("Servlet Exception whilst attempting to forward user to the main servlet");
			} catch (IOException ioe){
				dbm("IO Exception whilst attempting to forward user to the main servlet");
			}
		} else {
			dbm("there is order data!");
			String message=request.getParameter("message");
			if (message==null) message="";

			try{
				out.print("<table height=\"500\">");
				out.print("<tr>");
				out.print("<td valign=\"top\">");
				out.print("<h1>Past Orders</h1>");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Past Orders for "+firstname+" "+surname+":");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print(message);
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");

				// Print out Past Orders for the member logged on.
			
				if (columnNames.size()>0 && productsOrdered.size()>0) {
					out.print("<table height=\"250\">");
					out.print("<tr>");
					for (int x=0; x<columnNames.size(); x++) {
						out.print("<td>");
						out.print("<b>");
						out.print((String)columnNames.get(x));
						out.print("</b>");
						out.print("</td>");
					}
					out.print("</tr>");
					for (int y=0; y<productsOrdered.size(); y++) {
						out.print("<tr>");
						ArrayList details=(ArrayList)productsOrdered.get(y);
						if (details.size()>0) {
							for (int z=0; z<details.size(); z++) {
								out.print("<td>");
								out.print((String)details.get(z));
								out.print("</td>");
							}
						} else {
							out.print("<td>");
							out.print("You have made no orders so far.");
							out.print("</td>");
						}
					}
				} else {
					out.print("You have made no orders so far.");
				}
				out.print("</tr>");
				out.print("</table>");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td align=\"bottom\">");
				out.print("</td>");
				out.print("</tr>");
				out.print("</table>");
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		}
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("PastOrdersTag: "+msg);
	}
}
