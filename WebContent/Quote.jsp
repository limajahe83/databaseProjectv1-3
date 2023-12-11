<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<a href="login.jsp"target ="_self" > logout</a><br><br> 

<h1>List all quotes</h1>
    <div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Quotes</h2></caption>
            <tr>
                <th>QuoteID</th>
                <th>serviceID</th>
                <th>CustomerID</th>
                <th>Date</th>
                <th>TotalCost</th>
                <th>CustomerNote</th>
                <th>HeightFT</th>
                
            </tr>
            <c:forEach var="quote" items="${get_quote}">
                <tr style="text-align:center">
                    <td>"${quote.quoteid}" </td>
                    <td>"${quote.serviceid}"</td>
                    <td>"${quote.customerid}"</td>
                    <td>"${quote.date}"</td>
                    <td>"${quote.totalcost}"</td>
                    <td>"${quote.custnote}"</td>
                    <td>"${quote.heightFT}"</td>
                    
                 </tr>
            </c:forEach>
          </table>
	</div>
<body>

</body>
</html>