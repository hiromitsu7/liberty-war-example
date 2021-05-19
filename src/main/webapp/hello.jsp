<%@ page import="java.time.LocalTime" %>
<html>

<head>
    <title>Hello JSP</title>
</head>

<body>
    <%
    LocalTime localTime = LocalTime.now();
    %>
    <%= localTime.toString() %>
    <a href="<%= request.getRequestURI() %>">
        <h3>reload</h3>
    </a>
</body>

</html>