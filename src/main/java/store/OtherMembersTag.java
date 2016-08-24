/* OtherMembersTag.java - a tag to display the content for othermembers.jsp */

package store;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public class OtherMembersTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		
		String message=request.getParameter("message");
		if (message==null) message="";
		String othermembersdata=response.encodeURL("getServlet?action=getOtherMembersDetails");
		ArrayList colNames=(ArrayList) request.getAttribute("columnNames");
		ArrayList users=(ArrayList) request.getAttribute("users");
		if (colNames==null || users==null){
			RequestDispatcher rd=request.getRequestDispatcher(othermembersdata);
			try {
				rd.forward(request, response);
			} catch (ServletException se){
				dbm("ServletException whilst trying to send request to EcommerceServlet");
			} catch (IOException ioe){
				dbm("IOException whilst trying to send request to EcommerceServlet");
			}
		} else {

			try{
				out.print("<table height=\"500\">");
				out.print("<tr>");
				out.print("<td valign=\"top\">");
				out.print("<h1>Other Members' Details</h1>");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print(message);
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");

				/* 	Print out the details of all members; if a member has a field which contains a null value, 			 *	don't print the field.
				 */
				out.print("<table height=\"250\">");
				out.print("<tr>");
				for (int x=0; x<colNames.size(); x++) {
					out.print("<td>");
					out.print("<b>");
					out.print((String)colNames.get(x));
					out.write("</b>");
					out.print("</td>");
				}
				out.print("</tr>");
				for (int y=0; y<users.size(); y++) {
					out.print("<tr>");
					ArrayList details=(ArrayList)users.get(y);
					for (int z=0; z<details.size(); z++) {
						out.print("<td>");
						String cell = (String)details.get(z);
						if (cell!=null) out.print(cell);
						out.print("</td>");
					}
					out.print("</tr>");
				}
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
		System.out.println("OtherMembersTag: "+msg);
	}
}
