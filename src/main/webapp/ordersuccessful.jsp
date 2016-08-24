<!-- 	This is the page that is shown when the user has made a successful order.
-->
<%@page errorPage="error.jsp"%>
<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Order Successful!</title></head>
<body link="black" alink="black" vlink="black">

	<OnlineStore:checksession/>

	<OnlineStore:displayordersuccessful/>

	<OnlineStore:innermenu/>
</body>
</html>