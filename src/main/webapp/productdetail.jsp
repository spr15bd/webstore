<!-- 	This page enables members to see the details on products they select from the main store page. It includes descriptions on the products and product ids.
-->
<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Online Store</title></head>
<body link="black" alink="black" vlink="black">

	<OnlineStore:checksession/>

	<OnlineStore:storemenu/>

	<OnlineStore:displayproductdetails/>

	<OnlineStore:innermenu/>
	
</body>
</html>