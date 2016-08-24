/* 	EcommerceServlet.java - This is the online store application's servlet - it contains the methods to deal with registering, logging on, displaying the details of all members, the member wanting to update their details, as well as all the shopping transactions.
/*   	Requests to this servlet are dealt with by their 'action' attribute:
/* 
/* 	If action equals 'register' 		- this is where a newcomer is brought to when they wish to gain access to the members' area, having filled in the fields of the register jsp. The mandatory information they provided is validated, and if it's all there, an account is created in the member table, the account details are stored as session attributes, and the new member gains access to membershome.jsp.  
/* 	If action equals 'login'    		- the servlet will check a member's username and password (entered in the login.jsp form). If these are stored (in the same row of the member table), access is granted to the members' homepage. If not, or if they don't fill in one of the two fields, they are sent back to login.jsp with an appropriate message. 
/* 	If action equals 'getOthermemberDetails'- members are brought to the servlet from othermembers.jsp, when they wish to view the details on the other members. The servlet gets this information (from the member database table). As usual, the data is added to three ArrayList objects - one for column names, one for rows (users) and one for the details of each row (each details ArrayList object is added to the users ArrayList object). 
/* 	If action equals 'editmemberDetails'	- here, a member's updated details (from othermembers.jsp) are uploaded to the member database table. Each detail is compared to the user's current session attribute value to see if a change has been made. If it has, the change is appended to a StringBuffer which will then form the basis for the sql query. Address changes made here will override the current delivery address for the member - if they want a different address for delivery, they must specify this on the checkout page. 
/* 	If action equals 'displayCatalogue'	- in this case, the servlet retrieves all the items for sale data from the products table. It uses the 'cat' querystring parameter to select the data from the relevant catalogue. The data is added to three ArrayList objects - one for column names, one for catalogue items, and one for item details (price, quantity, etc). They are displayed on the store pages (store.jsp and productDetail.jsp). 
/* 	If action equals 'pastorders'		- the servlet gets the past orders made by the logged in member. It gets the order information from the orders, orderdetails, and products tables and displays the resultset in the usual way - three ArrayLists, one for column names, one for order rows and one for details on each row - on the pastorders jsp. 
/* 	If action equals 'validatePayment'	- The information given by the member on the checkout page is validated. The mandatory fields must have been supplied, the sort code must be six digits, and the account number must be eight. If successful, the member's order goes through with the member being given a unique order number on the ordersuccessful page. 
/*
/*	Up to four sql queries are executed - one row is added to the orders table, one or many rows are added to the orderdetails table  (one for each different productid), the shipping address is actionally amended (if the member wants a different delivery address), and the current order number is retrieved for the user's benefit. 
 */

package store;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Simple Servlet", description = "This is a simple servlet with annotations", urlPatterns = "/getServlet") 
public class EcommerceServlet extends HttpServlet {
	private String message="";
	private String destination="";
	private String userStatement="";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	} 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action=(String)request.getParameter("action");
		if (action.equals("register")){

			StringBuffer sb=new StringBuffer();
			String user=request.getParameter("user");
			if (user==null || user.trim().length()==0) {
				sb.append("<BR>"+"Username");
			}
			String fname=request.getParameter("fname");
			if (fname==null || fname.trim().length()==0) {
				sb.append("<BR>"+"Firstname");
			}
			String lname=request.getParameter("lname");
			if (lname==null || lname.trim().length()==0) {
				sb.append("<BR>"+"Lastname");
			}
			String address1=request.getParameter("address1");
			if (address1==null || address1.trim().length()==0) {
				sb.append("<BR>"+"Address1");
			}
			String country=request.getParameter("country");
			if (country==null || country.trim().length()==0) {
				sb.append("<BR>"+"Country");
			}
			String password=request.getParameter("password");
			if (password==null || password.trim().length()==0) {
				sb.append("<BR>"+"Password");
			}
		
			String postcode=request.getParameter("postcode");	// Not validated as these
			String address2=request.getParameter("address2");	// aren't mandatory.
			String shipaddr1=request.getParameter("address1");	// (Shipping address is also
			String shipaddr2=request.getParameter("address2");	// stored in member table).

			String temp=sb.toString();
			if (temp.length()>0) {
				message="The following mandatory fields must be filled: "+temp;
				destination=response.encodeURL("register.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			} else {
				Connection conn=Connector.getConnection();
				if (conn!=null) {
					PreparedStatement statement=null;
					try {
						statement=conn.prepareStatement(registerStatement());
						statement.setString(1, user);
						statement.setString(2, password);
						statement.setString(3, fname);
						statement.setString(4, lname);
						statement.setString(5, address1);
						statement.setString(6, address2);
						statement.setString(7, postcode);
						statement.setString(8, country);
						statement.setString(9, shipaddr1);
						statement.setString(10, shipaddr2);
						statement.executeUpdate();
					
						HttpSession session=request.getSession(false);
						if (session!=null) {
							session.invalidate();
						}
						session=request.getSession(true);
						session.setMaxInactiveInterval(60*5);

						Enumeration enu=request.getParameterNames();
						while (enu.hasMoreElements()) {
							String name=(String)enu.nextElement();
							session.setAttribute(name, request.getParameter(name));
						}
						RequestDispatcher rd=request.getRequestDispatcher("membershome.jsp");
						rd.forward(request, response);
						return;
					} catch (SQLException e) {
						message="There was a problem whilst trying to update the database: "+e.toString();
						destination=response.encodeURL("register.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					} finally {
						try {
							statement.close();
							conn.close();
						} catch (SQLException sql) {
							message="Sorry could not close the connection due to the following exception: "+sql.toString();
							destination=response.encodeURL("register.jsp");
							displayMessage(request, response, destination, message, null);
							return;
						}
					}
				} else {
					message="Unable to connect you to the database.";
					destination=response.encodeURL("register.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				} 
			}
		} else if (action.equals("login")){
			String user=request.getParameter("user");
			if (user==null || user.trim().length()==0) {
				String message="You must enter a valid username and password.";
				destination=response.encodeURL("login.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
			String password=request.getParameter("password");
			if (password==null || password.trim().length()==0) {
				String message="You must enter a valid username and password.";
				destination=response.encodeURL("login.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
			Connection conn=Connector.getConnection();
			if (conn!=null) {
				PreparedStatement statement=null;
			
				try {
					statement=conn.prepareStatement(loginStatement());
					statement.setString(1, user);
					statement.setString(2, password);
					ResultSet rs=statement.executeQuery();
					if (rs.next()) {		// If the username and password are in the same row.....
						// They are a member! Invalidate any existing session, and create a new one.
						HttpSession session=request.getSession(false);
						if (session!=null) {
							session.invalidate();
						}
						session=request.getSession(true);
						session.setMaxInactiveInterval(5*60);
					
						// Add all the user's account details to session attributes.
	
						session.setAttribute("user", rs.getString("username"));
						session.setAttribute("password", rs.getString("password"));
						session.setAttribute("fname", rs.getString("first_name"));
						session.setAttribute("lname", rs.getString("last_name"));
						session.setAttribute("address1", rs.getString("address1"));
						session.setAttribute("address2", rs.getString("address2"));
						session.setAttribute("postcode", rs.getString("postcode"));
						session.setAttribute("country", rs.getString("country"));
						session.setAttribute("shipaddr1", rs.getString("shipaddr1"));
						session.setAttribute("shipaddr2", rs.getString("shipaddr2"));
						RequestDispatcher rd=request.getRequestDispatcher("membershome.jsp");
						rd.forward(request, response);
					} else {
						String message="Please enter a valid username and password.";
						destination=response.encodeURL("login.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					}
				} catch (SQLException sql) {
					String message="Sorry, exception occurred whilst trying to logon: "+sql.toString();
					destination=response.encodeURL("login.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				} finally {
					try {
						statement.close();
						conn.close();
					} catch (SQLException sql) {
						String message="Sorry, exception occurred whilst trying to close the database connection: "+sql.toString();
						destination=response.encodeURL("login.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					}
				}
			} else {
				String message="Unable to connect to the database to log you in.";
				destination=response.encodeURL("login.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
		} else if (action.equals("getOtherMembersDetails")){
			Connection con=Connector.getConnection();
			if (con!=null) {
				Statement statement=null;
				try {
					statement=con.createStatement();
					ResultSet rs=statement.executeQuery(memberDetailsStatement());
					ResultSetMetaData rsmd=rs.getMetaData();
					int columnCount=rsmd.getColumnCount();
					ArrayList<String> columnNames=new ArrayList<String>();
					for (int i=1; i<=columnCount; i++) {
						dbm("column name added");
						columnNames.add(new StringUtil().capitalise(rsmd.getColumnName(i)));
					}
					ArrayList<ArrayList> users=new ArrayList<ArrayList>(); //Remember ArrayList is an Arraylist containing ArrayLists of Strings
				
					while (rs.next()) {
						ArrayList<String> details=new ArrayList<String>();
						for (int i=1; i<=columnCount; i++) {
							details.add(rs.getString(i));
						}
						users.add(details);
						dbm("User added");
					}
					request.setAttribute("columnNames", columnNames);
					request.setAttribute("users", users);
					RequestDispatcher rd=request.getRequestDispatcher("othermembers.jsp");
					rd.forward(request, response);
					return;
				} catch (SQLException sql) {
					message="There was an error whilst attempting to access the other user data: "+sql.toString();
					destination=response.encodeURL("othermembers.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				} finally {
					try {
						statement.close();
						con.close();
					} catch (SQLException sql) {
						String message="There was an error whilst trying to close the database connection: "+sql.toString();
						destination=response.encodeURL("othermembers.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					}
				}
			} else {
				String message="Sorry, unable to connect to the database.";
				destination=response.encodeURL("othermembers.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
		} else if (action.equals("editMemberDetails")){
			HttpSession session = request.getSession(true);
			String sqlQuery="";
			StringBuffer query=new StringBuffer();
			StringBuffer missingInput=new StringBuffer();		

			ArrayList<String> changes = new ArrayList<String>();
		
			/* This servlet will make a change to one or more of the values stored if the value in the textbox is different to the value stored as a session attribute AND is not null or empty */
		
			String user=(String)session.getAttribute("user");
			String userchange=(String)request.getParameter("user");
			if (userchange==null||userchange.trim().equals("")) missingInput.append("<br>Username");
			if (!userchange.equals(user) && userchange!=null && userchange.trim().length()!=0) {
				query.append(" username = '"+userchange+"', ");
				changes.add(userchange);
			}
			String fname=(String)session.getAttribute("fname");
			if (fname==null||fname.trim().equals("")) missingInput.append("<br>First Name");
			String fnamechange=(String)request.getParameter("fname");
			if (fnamechange==null||fnamechange.trim().equals("")) missingInput.append("<br>First Name");
			if (!fnamechange.equals(fname) && fnamechange!=null && fnamechange.trim().length()!=0) {
				query.append(" first_name = '"+fnamechange+"', ");
				changes.add(fnamechange);
			}
			String lname=(String)session.getAttribute("lname");
			String lnamechange=(String)request.getParameter("lname");
			if (lnamechange==null||lnamechange.trim().equals("")) missingInput.append("<br>Last Name");
			if (!lnamechange.equals(lname) && lnamechange!=null && lnamechange.trim().length()!=0) {
				query.append(" last_name = '"+lnamechange+"', ");
				changes.add(lnamechange);
			}
			String address1=(String)session.getAttribute("address1");
			String address1change=(String)request.getParameter("address1");
			if (address1change==null||address1change.trim().equals("")) missingInput.append("<br>Address (First Line)");
			if (!address1change.equals(address1) && address1change!=null && address1change.trim().length()!=0) {
				query.append(" address1 = '"+address1change+"', ");
				query.append(" shipaddr1 = '"+address1change+"', ");
				changes.add(address1change);
			}
			String address2=(String)session.getAttribute("address2");
			String address2change=(String)request.getParameter("address2");
			if (!address2change.equals(address2) && address2change!=null && address2change.trim().length()!=0) {
				query.append(" address2 = '"+address2change+"', ");
				query.append(" shipaddr2 = '"+address2change+"', ");
				changes.add(address2change);
			}
			String postcode=(String)session.getAttribute("postcode");
			String postcodechange=(String)request.getParameter("postcode");
			if (!postcodechange.equals(postcode) && postcodechange!=null && postcodechange.trim().length()!=0) {
				query.append(" postcode = '"+postcodechange+"', ");
				changes.add(postcodechange);
			}
			String country=(String)session.getAttribute("country");
			String countrychange=(String)request.getParameter("country");
			if (countrychange==null||countrychange.trim().equals("")) missingInput.append("<br>Country");
			if (!countrychange.equals(country) && countrychange!=null && countrychange.trim().length()!=0) {
				query.append(" country = '"+countrychange+"', ");
				changes.add(countrychange);
			}
			String password=(String)session.getAttribute("password");
			String passwordchange=(String)request.getParameter("password");
			if (passwordchange==null||passwordchange.trim().equals("")) missingInput.append("<br>Password");
			if (!passwordchange.equals(password) && passwordchange!=null && passwordchange.trim().length()!=0) {
				query.append(" password = '"+passwordchange+"', ");
				changes.add(passwordchange);
			}
			String shipaddr1=request.getParameter("address1");	// (If the user's address changes, then
			String shipaddr2=request.getParameter("address2");	// the shipping address will also change).
		
			// Are all the mandatory fields there ?
			if (missingInput.length()>0) {
				message="The following information must be added: "+missingInput.toString();
				destination=response.encodeURL("editdetails.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
			/* If one or more changes have been appended to the stringbuffer object, then the last change will have a comma on the end which must be deleted. Then the changes query can be carried out to update the member table. Also, update the session values.*/
			else if (query.length()>0) {
				sqlQuery=query.substring(0,query.length()-2);
				Connection conn=Connector.getConnection();
				if (conn!=null) {
					PreparedStatement statement=null;
					try {
						statement=conn.prepareStatement(editDetailsStatement(sqlQuery));
						statement.setString(1, user);
						statement.executeUpdate();
	
					
						if (changes.contains(userchange)) session.setAttribute("user", changes.get(changes.indexOf(userchange)));
						if (changes.contains(fnamechange)) session.setAttribute("fname", changes.get(changes.indexOf(fnamechange)));
						if (changes.contains(lnamechange)) session.setAttribute("lname", changes.get(changes.indexOf(lnamechange)));
						if (changes.contains(address1change)) {
							session.setAttribute("address1", changes.get(changes.indexOf(address1change)));
							session.setAttribute("shipaddr1", changes.get(changes.indexOf(address1change)));
						}
						if (changes.contains(address2change)) {
							session.setAttribute("address2", changes.get(changes.indexOf(address2change)));
							session.setAttribute("shipaddr2", changes.get(changes.indexOf(address2change)));
						}
						if (changes.contains(postcodechange)) session.setAttribute("postcode", changes.get(changes.indexOf(postcodechange)));
						if (changes.contains(countrychange)) session.setAttribute("country", changes.get(changes.indexOf(countrychange)));
						if (changes.contains(passwordchange)) session.setAttribute("password", changes.get(changes.indexOf(passwordchange)));

						message="Your details were successfully updated. ";
						destination=response.encodeURL("editdetails.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					} catch (SQLException sql) {
						String message="There was an error whilst attempting to update your user account details: "+sql.toString();
						destination=response.encodeURL("editdetails.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					} finally {
						try {
							statement.close();
							conn.close();
						} catch (SQLException sql) {
							String message="Error occurred whilst attempting to close the connection: "+sql;
							destination=response.encodeURL("editdetails.jsp");
							displayMessage(request, response, destination, message, null);
							return;
						}
					}
				} else {
					System.out.println("Unable to access the database to update your details.");
					message="Unable to access the database to update your details.";
					destination=response.encodeURL("editdetails.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				}
			} else {
				String message="(No modifications made to your registered details).";
				destination=response.encodeURL("editdetails.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
		} else if (action.equals("displayCatalogue")){
			String selectedCat = request.getParameter("cat");
			Connection con=Connector.getConnection();
			if (con!=null) {
				PreparedStatement statement=null;
				try {
					statement=con.prepareStatement(getProductDetails());
					statement.setString(1, selectedCat);
					ResultSet rs=statement.executeQuery();
					ResultSetMetaData rsmd=rs.getMetaData();
					int columnCount=rsmd.getColumnCount();
					ArrayList<String> columnNames=new ArrayList<String>();
					for (int a=1; a<=columnCount; a++) {
						columnNames.add(new StringUtil().capitalise(rsmd.getColumnName(a)));
					}
					HttpSession session=request.getSession();
					session.setAttribute("columnNames", columnNames);
					ArrayList<ArrayList> catalogueItems=new ArrayList<ArrayList>();
					while (rs.next()) {
						ArrayList<String> itemDetails=new ArrayList<String>();
						for (int b=1; b<=columnCount; b++) {
							itemDetails.add(rs.getString(b));
						}
						catalogueItems.add(itemDetails);
					}
					session.setAttribute("catalogueItems", catalogueItems);
					RequestDispatcher rd=request.getRequestDispatcher("store.jsp");
					rd.forward(request, response);
				
				} catch (SQLException sql) {
					message="There was an error whilst trying to access the database: "+sql;
					destination=response.encodeURL("store.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				} finally {
					try {
						statement.close();
						con.close();
					} catch (SQLException sql) {
						System.out.print("SQL error occurred: "+sql);
					}
				}
			} else {
				message="Sorry, unable to establish a connection with the database.";
				destination=response.encodeURL("store.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
		} else if (action.equals("pastOrders")){
			Connection con=Connector.getConnection();
			HttpSession session=request.getSession(false);
			String user=(String)session.getAttribute("user");
			if (con!=null) {
				PreparedStatement statement=null;
				try {
					statement=con.prepareStatement(pastordersStatement());
					statement.setString(1, user);
					ResultSet rs=statement.executeQuery();
					ResultSetMetaData rsmd=rs.getMetaData();
					int columnCount=rsmd.getColumnCount();
					ArrayList<String> columnNames=new ArrayList<String>();
					for (int i=1; i<=columnCount; i++) {
						columnNames.add(new StringUtil().capitalise(rsmd.getColumnName(i)));
					}
					ArrayList<ArrayList> productsOrdered=new ArrayList<ArrayList>();
					ArrayList<String> productDetails;
				
					while (rs.next()) {
						productDetails=new ArrayList<String>();
						for (int i=1; i<=columnCount; i++) {
							productDetails.add(rs.getString(i));
						}
						productsOrdered.add(productDetails);
					}
					session.setAttribute("columns", columnNames);
					session.setAttribute("products", productsOrdered);
					RequestDispatcher rd=request.getRequestDispatcher("pastorders.jsp");
					rd.forward(request, response);
				} catch (SQLException sql) {
					message="There was an error whilst accessing the database: "+sql.toString();
					destination=response.encodeURL("pastorders.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				} finally {
					try {
						statement.close();
						con.close();
					} catch (SQLException sql) {
						message="There was an error whilst attempting to close the database: "+sql.toString();
						destination=response.encodeURL("pastorders.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					}
				}
			} else {
				message="Unable to establish a connection with the database - please try again later.";
				destination=response.encodeURL("pastorders.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
		} else if (action.equals("validatePayment")){
			String shipaddr1=null;
			String shipaddr2=null;
			try{
				StringBuffer sb=new StringBuffer();
				String sortcode=(String)request.getParameter("sortcode");
				if (sortcode==null || sortcode.trim().length()==0) {
					sb.append("The Sort Code field must be filled.<br>");
				} else {
					int sortcodeInt=Integer.parseInt(sortcode);
					if (sortcodeInt>999999 || sortcodeInt<100000) {
						sb.append("The Sort Code must be a 6-digit number with no spaces.<br>");
					}
				}
				String accountnum=(String)request.getParameter("accountnum");
				if (accountnum==null || accountnum.trim().length()==0) {
					sb.append("<br>The Account Number field must be filled.<br>");
				} else {
					int accountInt=Integer.parseInt(accountnum);
					if (accountInt>99999999 || accountInt<10000000) {
						sb.append("<br>The Account Number must be an 8-digit number with no spaces.<br>");
					}
				}
				String accountna=(String)request.getParameter("accountna");
				if (accountna==null || accountna.trim().length()==0) {
					sb.append("<br>The Account Name field must be filled.<br>");
				}
		
				String bank=(String)request.getParameter("bank");
				if (bank==null) bank="";
				shipaddr1=(String)request.getParameter("shipaddr1");
				if (shipaddr1==null || shipaddr1.trim().length()==0) shipaddr1="";
				shipaddr2=(String)request.getParameter("shipaddr2");
				if (shipaddr2==null || shipaddr2.trim().length()==0) shipaddr2="";

				String errors=sb.toString();
				if (errors.length()>0) {
					destination=response.encodeURL("checkout.jsp");
					displayMessage(request, response, destination, errors, null);
					return;
				}
			} catch(NumberFormatException nfe){
					dbm("nfe"+nfe);
					message="Please give numerics for the Sort Code and Account Number.";
					destination=response.encodeURL("checkout.jsp");
					displayMessage(request, response, destination, message, nfe);
					return;
			}
		
			Connection con=Connector.getConnection();
			if (con!=null) {
				PreparedStatement statement=null;
				try {
					HttpSession session=request.getSession(false);
					BasketBean basket=(BasketBean)session.getAttribute("basket");
					statement=con.prepareStatement(getMaxOrderNumStatement());
					statement.executeQuery();
					statement=con.prepareStatement(orderstatement());
					statement.setString(1, (String)session.getAttribute("user"));
					statement.setString(2, new Date().toString());
					statement.setString(3, basket.getTotalPrice()+"");
					// (Needs the "" to convert this to string).
					
					statement.executeUpdate();

					for (int x=0; x<basket.getSize(); x++) {
						statement=con.prepareStatement(getMaxOrderNumStatement());
						statement.executeQuery();
						statement=con.prepareStatement(orderdetailsStatement());
						ItemBean item=(ItemBean)basket.getItemBean(x);
						statement.setString(1, item.getTotalPrice()+"");
						statement.setString(2, item.getQuantity()+"");
						statement.setString(3, item.getProductid()+"");
						
						
						statement.executeUpdate();
					}

					if (shipaddr1!="" && !shipaddr1.equals((String)session.getAttribute("shipaddr1"))) {
						statement=con.prepareStatement(shippingStatement());
						statement.setString(1, shipaddr1);
						statement.setString(2, shipaddr2);
						statement.setString(3, (String)session.getAttribute("user"));
						statement.executeUpdate();
					}

					statement=con.prepareStatement(currentOrderNumStatement());
					ResultSet rs=statement.executeQuery();
					String orderNumber="";
					while (rs.next()) {
						orderNumber=rs.getString(1);
					}
					String message="Thank you for your order. The order has now been processed. For future reference, your order number is: "+orderNumber;
					request.setAttribute("message", message);
					RequestDispatcher rd=request.getRequestDispatcher("ordersuccessful.jsp");
					rd.forward(request, response);
				} catch (SQLException sql) {
					message="Sorry, there was an exception whilst trying to access the database: "+sql.toString();
					destination=response.encodeURL("checkout.jsp");
					displayMessage(request, response, destination, message, null);
					return;
				} finally {
					try {
						statement.close();
						con.close();
					} catch (SQLException sql) {
						message="Failed to close the connection to the database - "+sql.toString();
						destination=response.encodeURL("checkout.jsp");
						displayMessage(request, response, destination, message, null);
						return;
					}
				}
			} else {
				message="Failed to establish a connection and process the order.";
				destination=response.encodeURL("checkout.jsp");
				displayMessage(request, response, destination, message, null);
				return;
			}
		}
	}
	
	private void displayMessage(HttpServletRequest request, HttpServletResponse response, String destination, String message, Exception exception) throws ServletException, IOException {
		Enumeration enu=request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name=(String)enu.nextElement();
			request.setAttribute(name, request.getParameter(name));
		}
		if (exception!=null){
			request.setAttribute("javax.servlet.jsp.jspException", exception);
			dbm("exception!=null.");
		}	
		request.setAttribute("message", message);
		RequestDispatcher rd=request.getRequestDispatcher(destination);
		rd.forward(request, response);
	}

	private static String registerStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO member ");
		sb.append("VALUES");
		sb.append(" ( ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
		sb.append(" ) ");
		return sb.toString();
	}

	private static String loginStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("* ");
		sb.append("FROM ");
		sb.append("member ");
		sb.append("WHERE ");
		sb.append("username = ");
		sb.append("?");
		sb.append(" AND ");
		sb.append("password = ");
		sb.append("?");
	
		return sb.toString();
	}

	private String memberDetailsStatement() {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT ");
		sb.append("username, ");
		sb.append("password, ");
		sb.append("first_name, ");
		sb.append("last_name, ");
		sb.append("address1, ");
		sb.append("address2, ");
		sb.append("postcode, ");
		sb.append("country ");
		sb.append("FROM ");
		sb.append("member");
		return sb.toString();
	}

	private String editDetailsStatement(String sqlQuery) {
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE member ");
		sb.append("SET");
		sb.append(sqlQuery);
		sb.append(" WHERE ");
		sb.append("username = ?");
		return sb.toString();
	}

	private String getProductDetails() {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT ");
		sb.append("* ");
		sb.append("FROM ");
		sb.append("products ");
		sb.append("WHERE type = ?");
		return sb.toString();
	}

	private String pastordersStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("o.orderdate, ");
		sb.append("p.title, ");
		sb.append("d.quantity, ");
		sb.append("d.price, ");
		sb.append("d.ordernum, ");
		sb.append("o.totalprice ");
		sb.append("FROM ");
		sb.append("orders o, ");
		sb.append("orderdetails d, ");
		sb.append("products p ");
		sb.append("WHERE ");
		sb.append("o.username=?");
		sb.append(" AND ");
		sb.append("p.productid=d.productid");
		sb.append(" AND ");
		sb.append("o.ordernum=d.ordernum");
		sb.append(" ORDER BY o.orderdate;");
		return sb.toString();
	}

	private String getMaxOrderNumStatement() {	// To be deleted
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT max(ordernum)+1 AS maximum into @maxOrderNum FROM orders;");
		return sb.toString();
	}
	private String orderstatement() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("INSERT INTO ");
		sb.append("orders (username, orderdate, totalprice) ");
		sb.append("VALUES (");
		sb.append("?, ");
		sb.append("?, ");
		sb.append("? )");
		return sb.toString();
	}
	private String orderdetailsStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append("orderdetails (ordernum, price, quantity, productid) ");
		sb.append("VALUES (");
		sb.append("(SELECT MAX(ordernum) FROM orders), ");	//maxOrderNum incremented by second getMaxOrderNumStatement() query; second query is necessary since the maxOrderNum variable is not always stored between the order and orderdetails updates
		sb.append("?, ");
		sb.append("?, ");
		sb.append("? )");
		return sb.toString();
	}
	private String shippingStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append("member ");
		sb.append("SET ");
		sb.append("shipaddr1=");
		sb.append("?, ");
		sb.append("shipaddr2=");
		sb.append("? ");
		sb.append("WHERE ");
		sb.append("username=?");
		return sb.toString();
	}
	private String currentOrderNumStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("MAX(ordernum) ");
		sb.append("FROM ");
		sb.append("orders ");
		return sb.toString();
	}

	private void dbm(String comment){
		System.out.println("EcommerceServlet: "+comment);
	}
}

