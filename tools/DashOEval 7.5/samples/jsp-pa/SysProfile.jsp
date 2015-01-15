<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://preemptive.com/dasho/taglib" prefix="pa"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SystemProfile Sample</title>
</head>
<body>
<h1>System Profile</h1>
<p><tt>RuntimeIntelligence.isApplicationStarted() = <b>${pa:isApplicationStarted()}</b></tt></p>
<pa:SystemProfile>
	<p>Sending two properties with profile</p>
	<pa:Property name="theTime" value="<%=new Date().toGMTString()%>" />
	<pa:Property name="bar" nvalue="42" />
</pa:SystemProfile>
</body>
</html>