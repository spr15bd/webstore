/* ViewBasketTag.java - a tag to display the content for viewbasket.jsp */

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

public class ViewBasketTag extends TagSupport {
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

		try{
			out.print("<h1>Shopping Basket</h1>");
			out.print("<br>");
			String message="You can add more items to the basket by clicking on the catalogue you wish to return to.";
			out.print(message+"<br><br><br>");

			/* This class declaration ensures numbers are displayed with two decimal places (in the beans they are stored as double, resulting in a lot more than two decimal places). */
			DecimalFormat formatter = new DecimalFormat("0.00");
			// Create or obtain the shopping basket instance.
			store.BasketBean basket=(store.BasketBean)session.getAttribute("basket");
			if (basket==null) {
				basket=new store.BasketBean();
			}
	
			String itemStr=request.getParameter("item");			// Used when an item is added from the catalogue
			String remove=request.getParameter("remove");			// Used when an item is removed from the basket
	
			/* If there's a quantity parameter in the querystring, the member has chosen to change the quantity of an item already in the basket. Update the quantity in the item's ItemBean object. (If it's zero, remove the item from the basket). */
			for (int i=0; i<basket.getSize(); i++) {
				String quantity=request.getParameter("quantity"+i);	// Used to deal with quantity changes
				if (quantity!=null) {
					if (Integer.parseInt(quantity)==0) {
						basket.removeItemBean(i);
				
					} else {
						store.ItemBean item=basket.getItemBean(i);
						item.setQuantity(Integer.parseInt(quantity));
					}
				}
			}
	
			/* If there's an item parameter in the querystring, the member has selected an item to add to the basket. Get the item selected from the catalogue list session attribute, create and populate a new instance of ItemBean, and add it to the BasketBean object. */
			if (itemStr!=null) {
				int itemNum=Integer.parseInt(itemStr);
				ArrayList catalogueItems=(ArrayList)session.getAttribute("catalogueItems");
				if (catalogueItems!=null) {
					ArrayList selectedItem=(ArrayList)catalogueItems.get(itemNum);
					store.ItemBean item=new store.ItemBean((String)selectedItem.get(0), (String)selectedItem.get(2), Double.parseDouble((String)selectedItem.get(3)), Integer.parseInt((String)selectedItem.get(5)), 1);
					basket.setItemBean(item);
			
				}
			}
	
			// If there's a remove parameter in the querystring, remove the item selected by the member.
			if (remove!=null) {
				basket.removeItemBean(Integer.parseInt(remove));
			}
			session.setAttribute("basket", basket);
	
			// Display basket contents.....This table uses some of the beans' set and get methods to summarise the info.
			out.print("<table>");
			if (basket.getSize()==0) {
				out.print("<tr><td>");
				out.print("There are no items in your basket.");
				out.print("</tr></td>");
			} else {
				out.print("<tr>");
		
				out.print("<td>");
				out.print("<b>");
				out.print("Item");
				out.print("</b>");
				out.print("</td>");
				out.print("<td>");
				out.print("<b>");
				out.print("Publisher");
				out.print("</b>");
				out.print("</td>");
				out.print("<td>");
				out.print("<b>");
				out.print("Quantity&nbsp&nbsp&nbsp");
				out.print("</b>");
				out.print("</td>");
				out.print("<td>");
				out.print("</td>");
				out.print("<td>");
				out.print("<b>");
				out.print("Price");
				out.print("</b>");
				out.print("</td>");
				out.print("</tr>");
				for (int x=0; x<basket.getSize(); x++) {
					String changeQuantity=response.encodeURL("viewbasket.jsp");
					out.print("<tr>");
					out.print("<form action=\""+changeQuantity+"\" method=post>");
					out.print("<td>");
					store.ItemBean item = basket.getItemBean(x);
					out.print(item.getTitle());
					out.print("&nbsp&nbsp&nbsp</td>");
					out.print("<td>");
					out.print(item.getPublisher());
					out.print("&nbsp&nbsp&nbsp</td>");
					out.print("<td>");
					out.print("<input type=text name=quantity"+x+" value="+item.getQuantity()+" size=2>");
					out.print("</td>");
					out.print("<td align=right>");
			
					out.print(formatter.format(item.getTotalPrice()));
			
					out.print("</td>");
					out.print("</form>");
					out.print("<td>&nbsp&nbsp");
					String removeItem=response.encodeURL("viewbasket.jsp?remove="+x);
					out.print("<a href=\""+removeItem+"\">Remove</a>");
					out.print("</td>");
					out.print("</tr>");
				}
				out.print("<b>");
				out.print("<tr>");
				out.print("<td>");
				out.print("<b>");
				out.print("Total");
				out.print("</b>");
				out.print("</td>");
				out.print("<td>");
				out.print("</td>");
				out.print("<td>");
				out.print("</td>");
				out.print("<td>");
				out.print("</td>");
				out.print("<td align=right>");
				out.print("<b>");
				out.print(formatter.format(basket.getTotalPrice()));
				out.print("</b>");
				out.print("</td>");
				out.print("</tr>");
				out.print("</b>");
			}
			out.print("</table>");
			out.print("<br>");



			out.print("<br>");
			out.print("<br>");
			out.print("<br>");	
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("ViewBasketTag: "+msg);
	}
}
