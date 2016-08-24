<%@page isErrorPage="true"%>
<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="OnlineStore"%>
<%@taglib uri="WEB-INF/tlds/storeTags.tld" prefix="go"%>

<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>MainLoginPage</title></head>
<body link="black" alink="black" vlink="black">

<table><tr>
<td>
	
</td>
<td width="575" valign="top">
   	
	<BR>
	       	<fieldset>
	       	<legend align="center"><b>Error</b></legend>
			<TABLE>
		   		<tr>
		     			
					<td><h1>An error has occurred on the page</h1></td>
			
				</tr>
				<tr>
					<td height="35"><td>
				</tr>
				<tr>
					<td><OnlineStore:displayerrordetails/></td>
				</tr>
		   		<tr><br>
		     
					<td><go:BackLink text="Return to previous page"/></td>
		     
		   		</tr>
			</TABLE>
               		</fieldset>
    
	  <BR><BR><BR><BR>
</td>
</tr></table>

</body>
</html>