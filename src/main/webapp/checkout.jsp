<!-- On this page, a member's order is validated before the order is accepted. The mandatory fields must be filled, sort code must be six digits and account number eight -->

<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Checkout</title></head>
<body link="black" alink="black" vlink="black">

	<OnlineStore:checksession/>
	
	<OnlineStore:storemenu/>

	<OnlineStore:displaycheckout/>
	
	<OnlineStore:innermenu/>

</body>
</html>