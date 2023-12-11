<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Customer Reply   </title>
</head>
<body>
<%
    String id = (String) request.getAttribute("id");
    String date = (String) request.getAttribute("date");
%>
    <h1>Reply for Quote Dated   <%= date %></h1>

    <form action="supplierquoteedit" method="post">
      

        

        <label for="quoteid">Quoteid:</label>
        <input type="text" name="quoteid" id="quoteid"   value=" <%= id %>" type="hidden"><br>
        
        
        
        
        
         <label for="Decision">'Agree', 'Disagree', or 'Quit':</label>
        <input type='text' name="SupplierDecision" id="SupplierDecision" required><br>
        

 
        
      

        <input type="submit" value="Submit Value">
    </form>
</body>
</html>
