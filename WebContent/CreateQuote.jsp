<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Quote</title>
</head>
<body>
    <h1>Create New Quote</h1>

    <form action="createquote" method="post">
      

        <label for="date">Date:</label>
        <input type="text" name="date" id="date" required><br>

        <label for="photo">Upload Photo:</label>
        <input type="file" name="photo" id="photo"><br>
        

        <label for="custnote">Customer Note:</label>
        <textarea name="custnote" id="custnote" rows="4" required></textarea><br>

        <label for="heightFT">Height (in Feet):</label>
        <input type="text" name="heightFT" id="heightFT" required><br>
        
         <label for="diameter_width">Diameter_width:</label>
        <input type="text" name="diameter_width" id="diameter_width" required><br>
        
         <label for="ft_from_house">ft_from_house:</label>
        <input type="text" name="ft_from_house" id="ft_from_house" required><br>
        
         <label for="location">location:</label>
        <input type="text" name="location" id="location" required><br>

        <input type="submit" value="Submit Quote">
    </form>
</body>
</html>
