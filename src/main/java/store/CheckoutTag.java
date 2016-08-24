/* CheckoutTag.java - a tag to display the content for checkout.jsp */

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
import java.text.DecimalFormat;

public class CheckoutTag extends TagSupport {
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
		// This class declaration ensures numbers are displayed with two decimal places (in the beans they are stored as double).
		DecimalFormat formatter = new DecimalFormat("0.00");
		
		String error = response.encodeURL("error");
		try{
			out.print("<h1>Checkout</h1>");
			String message=(String)request.getAttribute("message");
			if (message==null) message="<font color=red>[For demo purposes use the bank account details below.<br>Please do not use real accounts, no account details are stored in any case].<br><br></font>Please check your order before proceeding. If all is well, enter your bank card details below.<br>You may also change the delivery address from the one specified if you wish by amending the Delivery Address.";
			String sort=(String)request.getAttribute("sort");
			if (sort==null) sort="123456";
			String account=(String)request.getAttribute("account");
			if (account==null) account="12345678";
			String accountname=(String)request.getAttribute("accountname");
	
			if (accountname==null) accountname="";
			String bankname=(String)request.getAttribute("bankname");
			if (bankname==null) bankname="";
			String shipaddrOne=(String)request.getAttribute("shipaddr1");
			if (shipaddrOne==null) shipaddrOne=(String)session.getAttribute("address1");
			String shipaddrTwo=(String)request.getAttribute("shipaddr2");
			if (shipaddrTwo==null) shipaddrTwo=(String)session.getAttribute("address2")+" "+(String)session.getAttribute("postcode");
			store.BasketBean basket=(store.BasketBean)session.getAttribute("basket");
			if (basket==null || basket.getTotalPrice()==0) {
				out.print("There are no items in your basket yet.");
			} else {
				out.print(message);
				out.print("<br><br>");
				out.print("<table>");
				out.print("<tr>");
				out.print("<td><b>");
				out.print("Item");
				out.print("</b></td>");
				out.print("<td><b>");
				out.print("Cost");
				out.print("</b></td>");
				out.print("<td><b>");
				out.print("Quantity");
				out.print("</b></td>");
				out.print("</tr>");
				for (int x=0; x<basket.getSize(); x++) {
					store.ItemBean bean=(store.ItemBean)basket.getItemBean(x);
					out.print("<tr>");
					out.print("<td>");
					out.print(bean.getTitle());
					out.print("</td>");
					out.print("<td align=right>");
					out.print(bean.getPrice());
					out.print("</td>");
					out.print("<td>&nbsp&nbsp&nbsp");
					out.print(bean.getQuantity());
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("<tr>");
				out.print("<td>");
				out.print("</td>");
				out.print("<td align=right>");
				out.print("<b>");
				out.print(formatter.format(basket.getTotalPrice()));
				out.print("</b>");
				out.print("</td>");
				out.print("<td>");
				out.print("</td>");
				out.print("</tr>");
				out.print("</table>");
				out.print("<br><br>");

				String completeOrder = response.encodeURL("getServlet?action=validatePayment");
				out.print("<table>");
				out.print("<form action=\""+completeOrder+"\" method=post>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Sort Code<font color=\"red\">*</font>&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"sortcode\" maxlength=\"6\" value="+sort+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Account Number<font color=\"red\">*</font>&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"accountnum\" maxlength=\"8\" value="+account+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Account Name<font color=\"red\">*</font>&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"accountna\" value="+accountname+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Bank Name&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"bank\" value="+bankname+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Card Issue Number&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<select name=issuenum>");
				out.print("<option value=\"Not selected\">Select Issue Number:");
				out.print("<option value=01>01<option value=02>02<option value=03>03<option value=04>04");
				out.print("<option value=05>05<option value=06>06<option value=07>07<option value=08>08");
				out.print("<option value=09>09<option value=10>10");
				out.print("</select>");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Delivery Address 1&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"shipaddr1\" value=\""+shipaddrOne+"\">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("Delivery Address 2&nbsp&nbsp");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"shipaddr2\" value=\""+shipaddrTwo+"\">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td>");
				out.print("<input type=\"reset\" value=\"Clear Form\">");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"submit\" value=\"Complete Order\">");
				out.print("</td>");
				out.print("</tr>");
				out.print("</form>");
				out.print("</table>");
				out.print("<br>");
				out.print("<font color=\"red\">*</font> - Denotes mandatory field");
			}
			out.print("<br>");
			out.print("<br>");
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}

	private void displayMessage(HttpServletRequest request, HttpServletResponse response, String message, Exception exception, String url){
		dbm("Inside displayMessage.");
		if (exception!=null){
			request.setAttribute("javax.servlet.jsp.jspException", exception);
			request.setAttribute("message", message);
			dbm("exception!=null.");
		}	
		RequestDispatcher rd=request.getRequestDispatcher(url);
		dbm("sending user to "+url);
		try{
			rd.forward(request, response);
		} catch (ServletException se){
			dbm("Servlet Exception: "+se.toString());
		} catch (IOException ioe){
			dbm("IO Exception: "+ioe.toString());
		}
	}
	private static void dbm(String msg) {
		System.out.println("CheckoutTag: "+msg);
	}
}
