<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Edit Quotes</title>
</head>
<body>
    <h1>EditQuote</h1>

    <form action="editquote" method="post">
      

        

        <label for="quoteid">Quoteid:</label>
        <input type="text" name="quoteid" id="quoteid" required><br>
        
         <label for="totalcost">totalcost</label>
        <input type="text" name="totalcost" id="totalcost" required><br>
        
        <label for="custnote">Customer Note:</label>
        <textarea name="custnote" id="custnote" rows="4" required></textarea><br>
        
         <label for="Decision">'Agree', 'Disagree', or 'Quit':</label>
        <input type='text' name="supplierDecision" id="supplierDecision" required><br>
        

 
        
      

        <input type="submit" value="Submit Quote">
    </form>
</body>
</html>
