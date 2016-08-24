<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>View Personal Details</title></head>
<body link="black" alink="black" vlink="black">

	<OnlineStore:checksession/>

	<OnlineStore:displayuserdetails/>

	<OnlineStore:innermenu/>

</body>
</html>