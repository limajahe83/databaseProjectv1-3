import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/trees?allowPublicKeyRetrieval=true&useSSL=false&user=root&password=Karimax@2023");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String email, String password) throws SQLException{
    	try {
    		connect_func("root","test");
    		String sql = "select * from User where email = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, email);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/trees?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public List<user> listAllUsers() throws SQLException {
        List<user> listUser = new ArrayList<user>();        
        String sql = "SELECT * FROM User";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int customerid = resultSet.getInt("customerid");
        	String email = resultSet.getString("email");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String birthday = resultSet.getString("birthday");
            String adress_street_num = resultSet.getString("adress_street_num"); 
            String adress_street = resultSet.getString("adress_street"); 
            String adress_city = resultSet.getString("adress_city"); 
            String adress_state = resultSet.getString("adress_state"); 
            String adress_zip_code = resultSet.getString("adress_zip_code"); 
            int cash_bal = resultSet.getInt("cash_bal");
            int PPS_bal = resultSet.getInt("PPS_bal");

             
            user users = new user(customerid,email,firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code, cash_bal,PPS_bal);
            listUser.add(users);
        }        
        resultSet.close();
        disconnect();        
        return listUser;
    }  	 
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(user users) throws SQLException {
    	connect_func("root","test");         
		String sql = "insert into User(email, firstName, lastName, password, birthday,adress_street_num, adress_street,adress_city,adress_state,adress_zip_code,cash_bal,PPS_bal) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, users.getEmail());
			preparedStatement.setString(2, users.getFirstName());
			preparedStatement.setString(3, users.getLastName());
			preparedStatement.setString(4, users.getPassword());
			preparedStatement.setString(5, users.getBirthday());
			preparedStatement.setString(6, users.getAdress_street_num());		
			preparedStatement.setString(7, users.getAdress_street());		
			preparedStatement.setString(8, users.getAdress_city());		
			preparedStatement.setString(9, users.getAdress_state());		
			preparedStatement.setString(10, users.getAdress_zip_code());		
			preparedStatement.setInt(11, users.getCash_bal());		
			preparedStatement.setInt(12, users.getPPS_bal());		

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(user users) throws SQLException {
        String sql = "update User set firstName=?, lastName =?,password = ?,birthday=?,adress_street_num =?, adress_street=?,adress_city=?,adress_state=?,adress_zip_code=?, cash_bal=?, PPS_bal =? where email = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getFirstName());
		preparedStatement.setString(3, users.getLastName());
		preparedStatement.setString(4, users.getPassword());
		preparedStatement.setString(5, users.getBirthday());
		preparedStatement.setString(6, users.getAdress_street_num());		
		preparedStatement.setString(7, users.getAdress_street());		
		preparedStatement.setString(8, users.getAdress_city());		
		preparedStatement.setString(9, users.getAdress_state());		
		preparedStatement.setString(10, users.getAdress_zip_code());		
		preparedStatement.setInt(11, users.getCash_bal());		
		preparedStatement.setInt(12, users.getPPS_bal());
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public user getUser(String email) throws SQLException {
    	user user = null;
        String sql = "SELECT * FROM User WHERE email = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
        	int customerid = resultSet.getInt("customerid");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            String birthday = resultSet.getString("birthday");
            String adress_street_num = resultSet.getString("adress_street_num"); 
            String adress_street = resultSet.getString("adress_street"); 
            String adress_city = resultSet.getString("adress_city"); 
            String adress_state = resultSet.getString("adress_state"); 
            String adress_zip_code = resultSet.getString("adress_zip_code"); 
            int cash_bal = resultSet.getInt("cash_bal");
            int PPS_bal = resultSet.getInt("PPS_bal");
            user = new user(customerid,email, firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code,cash_bal,PPS_bal);
        }
         
        resultSet.close();
        statement.close();
         
        return user;
    }
    
    public boolean checkEmail(String email) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE email = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE password = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    
    
    public boolean isValid(String email, String password) throws SQLException
    {
    	String sql = "SELECT * FROM User";
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("email").equals(email) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("drop database if exists trees");
        statement.executeUpdate("create database trees");
        statement.executeUpdate("use trees");
     
        statement.executeUpdate("DROP TABLE IF EXISTS user, quote, bill, orderofwork, note, service, tree");

        

        // Create new tables
        
        statement.executeUpdate("CREATE TABLE if not EXISTS User (customerid INT PRIMARY KEY AUTO_INCREMENT, email VARCHAR(50) NOT NULL, firstname VARCHAR(10), lastName VARCHAR(10) NOT NULL, phonenumber VARCHAR(10), password VARCHAR(20) NOT NULL, birthday DATE NOT NULL, adress_street_num VARCHAR(4), adress_street VARCHAR(30), adress_city VARCHAR(20), adress_state VARCHAR(2), adress_zip_code VARCHAR(5), cash_bal DECIMAL(13,2) DEFAULT 1000, pps_bal DECIMAL(13,2) DEFAULT 0, cc_num VARCHAR(16) DEFAULT 0000, cc_exp DATE, cc_cvv VARCHAR(3));");
        statement.executeUpdate("CREATE TABLE if not EXISTS orderofwork (orderofworkid INT PRIMARY KEY AUTO_INCREMENT, service VARCHAR(50), date DATE, price_estimate DECIMAL(10,2));");
        statement.executeUpdate("CREATE TABLE if not EXISTS quote (quoteid INT PRIMARY KEY AUTO_INCREMENT, serviceid INT, offer_id INT, customerid INT, date DATE, totalcost DECIMAL(10,2), custnote VARCHAR(50), heightFT INT, diameter_width INT, ft_from_house INT, location VARCHAR(50), clientDecision VARCHAR(50), supplierDecision VARCHAR(50));");     
        statement.executeUpdate("CREATE TABLE if not EXISTS service (serviceid INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), description VARCHAR(50), price DECIMAL(10,2));");
        statement.executeUpdate("CREATE TABLE if not EXISTS bill (orderofworkid INT, finalprice DECIMAL (10,2), service VARCHAR(50));");
        statement.executeUpdate("CREATE TABLE if not EXISTS note (customerid INT, custnote VARCHAR(50), ownernote VARCHAR(50));");
        statement.executeUpdate("CREATE TABLE if not EXISTS tree (treeid INT, pics VARCHAR(50), ft_from_house INT, size_height INT );");
        
      
        
        // Insert data
        statement.executeUpdate("INSERT INTO User (email, firstname, lastname, phonenumber, password, birthday, adress_street_num, adress_street, adress_city, adress_state, adress_zip_code, cash_bal, pps_bal, cc_num, cc_exp, cc_cvv) VALUES ('susie@gmail.com', 'Susie ', 'Guzman', '5842124561', 'susie1234', '2000-06-27', '1234', 'whatever street', 'detroit', 'MI', '48202', '1000', '1000','414123541254145', '2020-01-01', '441'), "+
        		"('susie@gmail.com', 'Susie ', 'Guzman', '5842124561', 'susie1234', '2000-06-27', '1234', 'whatever street', 'detroit', 'MI', '48202', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    		 	"('don@gmail.com', 'Don', 'Cummings', '5842124561', 'don123', '1969-03-20', '1000', 'hi street', 'mama', 'MO', '12345', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    	 	 	"('margarita@gmail.com', 'Margarita', 'Lawson', '5842124561','margarita1234', '1980-02-02', '1234', 'ivan street', 'tata','CO','12561', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    		 	"('jo@gmail.com', 'Jo', 'Brady', '5842124561', 'jo1234', '2002-02-02', '3214','marko street', 'brat', 'DU', '54321', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    		 	"('wallace@gmail.com', 'Wallace', 'Moore','5842124561', 'wallace1234', '1971-06-15', '4500', 'frey street', 'sestra', 'MI', '48202', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    		 	"('amelia@gmail.com', 'Amelia', 'Phillips', '5842124561', 'amelia1234', '2000-03-14', '1245', 'm8s street', 'baka', 'IL', '48000', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    			"('sophie@gmail.com', 'Sophie', 'Pierce', '5842124561', 'sophie1234', '1999-06-15', '2468', 'yolos street', 'ides', 'CM', '24680', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    			"('angelo@gmail.com', 'Angelo', 'Francis', '5842124561', 'angelo1234', '2021-06-14', '4680', 'egypt street', 'lolas', 'DT', '13579', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    			"('rudy@gmail.com', 'Rudy', 'Smith', '5842124561', 'rudy1234', '1706-06-05', '1234', 'sign street', 'samo ne tu','MH', '09876', '1000', '1000', '414123541254145', '2020-01-01', '441'),"+
    			"('jeannette@gmail.com', 'Jeannette ', 'Stone', '5842124561', 'jeannette1234', '2001-04-24', '0981', 'snoop street', 'kojik', 'HW', '87654', '1000', '1000','414123541254145', '2020-01-01', '441'),"+
    			"('root', 'default', 'default','0000000000', 'pass1234', '2020-10-10', '0000', 'Default', 'Default', 'OH', '12345', '1000', '1000', 'default', '2020-02-02', '000');");  
        
	statement.executeUpdate("INSERT INTO quote(quoteid, serviceid, offer_id, customerid, date, totalcost, custnote, heightFT, diameter_width, ft_from_house,location,clientDecision, supplierDecision) VALUES ('10','10','10','10','2020-01-01', '111','Need 3 trees trimmed', '5', '10', 10, 'Right side of house', 'agreement','agreement'),"+
        		"('1','1','1','1','2020-01-01', '111', '1 tree taken down', '8', '3','8','Front of House', 'agreement','disagree'),"+    
        		"('2','2','2','2','2020-01-01', '111', '4 trees removed', '5','2', '8','backyard', 'agreement','disagree')," +
        		"('3','3','3','3','2020-01-01', '111', 'Trees trimmed', '3','5','7','driveway', 'agreement','agreement')," +
        		"('4','4','4','4','2020-01-01', '111', 'Trees trimmed', '12','6','12','driveway', 'Disagree','agreement')," +
        		"('5','5','5','5','2020-01-01', '111','3 trimmed', '7','4', '8', 'front yard right side', 'agreement', 'disagree')," +
        		"('6','6','6','6','2020-01-01', '111', '6 trees trimmed', '5','4','17','backyard', 'Disagree','agreement')," +
        		"('7','7','7','7','2020-01-01', '111', '2 Trees removed and 5 trimmed', '4','5','15','side of house', 'agreement','agreement')," +
        		"('8','8','8','8','2020-01-01', '111','1 tree trimmed', '6','6','17','side of house', 'Disagree','Disagree')," +
        		"('9','9','9','9','2020-01-01', '111', '1 tree trimmed, 2 removed', '6','2','7','front yard','Disagree','agreement');");  


	 statement.executeUpdate("INSERT INTO orderofwork(orderofworkid, service, date, price_estimate) VALUES ('1', 'LAWN MOWING', '2020-01-01', '111.11')," +
        		"('2', 'GARDENING', '2020-01-02', '222.22'),"
        		+ "('3', 'LANDSCAPING', '2020-01-03', '333.33'),"
        		+ "('4', 'TREE REMOVAL', '2020-01-04', '444.44'),"
        		+ "('5', 'HEDGE TRIMMING', '2020-01-05', '555.55'),"
        		+ "('6', 'IRRIGATION', '2020-01-06', '666.66'),"
        		+ "('7', 'FERTILIZATION', '2020-01-07', '777.77'),"
        		+ "('8', 'PATIO INSTALLATION', '2020-01-08', '888.88'),"
        		+ "('9', 'SPRINKLER REPAIR', '2020-01-09', '999.99'),"
        		+ "('10', 'Service 10', '2020-01-10', '1010.10');");
	  statement.executeUpdate("INSERT INTO service (serviceid, name, description, price) VALUES ('1', 'LAWN MOWING', 'Description 1', '100.00')," +
      		 "('2', 'GARDENING', 'Description 2', '150.00'),"
      		+ "('3', 'LANDSCAPING', 'Description 3', '75.50'),"
      		+ "('4', 'TREE REMOVAL', 'Description 4', '200.00'),"
      		+ "('5', 'HEDGE TRIMMING', 'Description 5', '90.00'),"
      		+ "('6', 'IRRIGATION', 'Description 6', '120.00'),"
      		+ "('7', 'FERTILIZATION', 'Description 7', '180.00'),"
      		+ "('8', 'PATIO INSTALLATION', 'Description 8', '50.00'),"
      		+ "('9', 'SPRINKLER REPAIR', 'Description 9', '110.00'),"
      		+ "('10', 'Service 10', 'Description 10', '70.00');");
      		
      
       statement.executeUpdate("INSERT INTO bill(orderofworkid, finalprice, service) VALUES ('1', '100.00', 'TREE TRIM'),"
      		+ "('2', '150.00', 'LAWN MOWING'),"
      		+ "('3', '75.00', 'GARDENING'),"
      		+ "('4', '200.00', 'LANDSCAPING'),"
      		+ "('5', '90.00', 'TREE REMOVAL'),"
      		+ "('6', '120.00', 'HEDGE TRIMMING'),"
      		+ "('7', '180.00', 'IRRIGATION'),"
      		+ "('8', '50.00', 'FERTILIZATION'),"
      		+ "('9', '110.00', 'PATIO INSTALLATION'),"
      		+ "('10', '70.00', 'SPRINKLER REPAIR');");
      
      statement.executeUpdate("INSERT INTO note (customerid, custnote, ownernote) VALUES ('1', 'Customer Note 1', 'Owner Note 1'),"
		        + "('2', 'Customer Note 2', 'Owner Note 2'),"
		        + "('3', 'Customer Note 3', 'Owner Note 3'),"
		        + "('4', 'Customer Note 4', 'Owner Note 4'),"
		        + "('5', 'Customer Note 5', 'Owner Note 5'),"
		        + "('6', 'Customer Note 6', 'Owner Note 6'),"
		        + "('7', 'Customer Note 7', 'Owner Note 7'),"
		        + "('8', 'Customer Note 8', 'Owner Note 8'),"
		        + "('9', 'Customer Note 9', 'Owner Note 9'),"
		        + "('10', 'Customer Note 10', 'Owner Note 10');");
      
      statement.executeUpdate("INSERT INTO tree (treeid, pics, ft_from_house, size_height) VALUES ('1', 'oak_tree', '10', '15'),"
      		+ "('2', 'maple_tree', '20', '25'),"
      		+ "('3', 'pine_tree', '30', '35'),"
      		+ "('4', 'willow_tree', '40', '45'),"
      		+ "('5', 'birch_tree', '50', '55'),"
      		+ "('6', 'cedar_tree', '60', '65'),"
      		+ "('7', 'redwood_tree', '70', '75'),"
      		+ "('8', 'elm_tree', '80', '85'),"
      		+ "('9', 'sequoia_tree', '90', '95'),"
      		+ "('10', 'fir_tree', '100', '105');");     
  }} 
        
        
      
    
    
    
	
	