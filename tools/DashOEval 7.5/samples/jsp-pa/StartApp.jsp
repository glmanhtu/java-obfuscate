<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://preemptive.com/dasho/taglib" prefix="pa"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StartApplication Sample</title>
</head>
<body>
<h1>Configuration</h1>
<ul>
<li>Resetting values ${pa:reset()}</li>
<li>Setting endpoint
<pa:ServiceEndpoint value="localhost:8080/sink/Sink" />
<pa:UseSSL value="false" />
</li>
<li>Setting company info
<pa:CompanyId value="11111111-aaaa-bbbb-cccc-555555555555" />
<pa:CompanyName value="PreEmptive Solution" />
</li>
<li>Setting application info
<pa:ApplicaitonId value="00000000-0000-dddd-0000-000000000000" />
<pa:ApplicaitonName value="JSP Test" />
<pa:ApplicaitonType value="JSP" />
<pa:ApplicaitonVersion value="1.0.1" />
</li>
</ul>
<p>After configuration: <tt>RuntimeIntelligence.isApplicationStarted() = <b>${pa:isApplicationStarted()}</b></tt></p>
<h1>Starting application</h1>
<pa:StartApplication>
	<p>Sending two properties with start</p>
	<pa:Property name="theTime" value="<%=new Date().toGMTString()%>" />
	<pa:Property name="bar" nvalue="42" />
</pa:StartApplication>
<p>After start: <tt>RuntimeIntelligence.isApplicationStarted() = <b>${pa:isApplicationStarted()}</b></tt></p>
</body>
</html>