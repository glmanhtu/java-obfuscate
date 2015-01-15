<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://preemptive.com/dasho/taglib" prefix="pa"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PerformanceProbe Sample</title>
</head>
<body>
<h1>PerformanceProbe</h1>
<p><tt>RuntimeIntelligence.isApplicationStarted() = <b>${pa:isApplicationStarted()}</b></tt></p>
<ul>
<li>Requesting initial probe
<pa:PerformanceProbe>
	<pa:Property name="when-taken" value="before"/>
</pa:PerformanceProbe>
</li>
<li>Perform some calculation:
<table>
	<tr><th align="right">n</th><th align="right">n!</th></tr>
    <%
    	for(int i = 1; i < 20; i++){
    		long rc = 1;
    		for(int j = i; j > 1; j--){
    			rc *= j;
    			try {
    				Thread.sleep(2);
    			}
    			catch(InterruptedException e){
    			}
    		}
    		out.println("<tr><td align=\"right\">" + i + "</td><td align=\"right\">&nbsp;" + rc + "</td></tr>");
    	}
    %>
</table>
</li>
<li>Requesting second probe
<pa:PerformanceProbe>
	<pa:Property name="when-taken" value="after"/>
</pa:PerformanceProbe>
</li>
</ul>
</body>
</html>