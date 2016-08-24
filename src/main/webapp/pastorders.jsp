<%@page errorPage="error.jsp"%>
<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<%
// To try and prevent page being cached
response.setHeader("Cache-Control","no-cache"); 	//HTTP 1.1
response.setHeader("Pragma","no-cache"); 	//HTTP 1.0
response.setDateHeader ("Expires", 0);		//prevents caching at the proxy server

%>

<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Past Orders</title></head>
<body link="black" alink="black" vlink="black">
	<HEAD> 
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> 
		<META HTTP-EQUIV="Expires" CONTENT="-1">
	</HEAD> 
	
	<OnlineStore:checksession/>

	<OnlineStore:displaypastorders/>

	<OnlineStore:innermenu/>

</body>
</html>