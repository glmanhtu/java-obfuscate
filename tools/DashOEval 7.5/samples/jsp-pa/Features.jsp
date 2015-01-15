<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://preemptive.com/dasho/taglib" prefix="pa"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Features Sample</title>
</head>
<body>
<h1>Features</h1>
<p><tt>RuntimeIntelligence.isApplicationStarted() = <b>${pa:isApplicationStarted()}</b></tt></p>
<ul>
<li>A simple feature tick
<pa:FeatureTick name="myFeature" />
</li>
<li>A feature tick using the page name
<pa:FeatureTick name="<%= request.getRequestURI().substring(1) %>">
	<pa:Property name="remote-host" value="<%= request.getRemoteHost() %>"/>
	<pa:Property name="remote-addr" value="<%= request.getRemoteAddr() %>"/>
</pa:FeatureTick>
</li>
<li>A feature start
<pa:FeatureStart name="otherFeature" />
</li>
<li>A feature stop
<pa:FeatureStop name="otherFeature" />
</li>
</ul>
</body>
</html>