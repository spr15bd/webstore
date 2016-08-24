<!-- 	When a member clicks 'buy' on a product in the store, they come to this page. Here, products they selected become an instance of the ItemBean class, which is then stored in the BasketBean class instance.
The shopping basket can be updated using three different parameters (which are passed by query string). These parameters are: 'item' (for adding items), 'remove' (for removing items), and 'quantity' (for quantity changes).
-->
<%@page errorPage="error.jsp"%>
<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Shopping Basket</title></head>
<body link="black" alink="black" vlink="black">

	<OnlineStore:checksession/>
	
	<OnlineStore:storemenu/>

	<OnlineStore:displaybasket/>

	<OnlineStore:innermenu/>

</body>
</html>