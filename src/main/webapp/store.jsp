<!-- 	This is the first page of the online store. When users first come to this page, it just contains the store menu (allowing members to choose between two catalogue listings, their shopping basket, and checkout page), and the main members' menu. If they choose a catalogue, they go to the DisplayCatalogue servlet and then back here once the catalogue items are retrieved. The items for sale are then displayed.
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

	<OnlineStore:displaystore/>
	
	<OnlineStore:innermenu/>

</body>
</html>