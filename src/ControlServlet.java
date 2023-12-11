import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private quoteDAO quoteDAO = new quoteDAO();
	    public ControlServlet()
		{
			
		}
		private String currentUser;
	    private HttpSession session=null;
	
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		userDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"");
        		break;
        
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	
        	case "/logout":
        		logout(request,response);
        		break;
        	case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
        	case "/listquote": 
                System.out.println("The action is: list quote");
                rootPagequote(request, response);           	
                break;
        	case "/createquote": 
                System.out.println("The action is: createquote");
                createquote(request, response);           	
                break;
        	case "/editquote": 
                System.out.println("The action is: editquote");
                editquote(request, response);           	
                break;
                
        	case "/customerreply": 
                System.out.println("The action is: editquote");
                clientDecision(request, response);           	
                break;
         
        	
        	case "/supplierreply": 
                System.out.println("The action is: editquote");
                supplierDecision(request, response);           	
                break;
	    	
	    case "/customerquoteedit": 
            System.out.println("The action is: editquote");
            customerquoteedit(request, response);           	
            break;
     
    	
    	
    	
	    case "/supplierquoteedit": 
            System.out.println("The action is: editquote");
            supplierquoteedit(request, response);           	
            break;
            
	    
     
    	
    	
    	}
	    	
        	
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
	    
  private void customerquoteedit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	        
	        String quoteid = request.getParameter("quoteid");
			String customerDecision = request.getParameter("customerDecision");
	      
	        
     
	        
	        quoteDAO.updateCliendDecision(customerDecision,quoteid);
	        response.sendRedirect("listquote");
	    }
  
  private void supplierquoteedit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
  	
      
      String quoteid = request.getParameter("quoteid");
		String supplierDecision = request.getParameter("SupplierDecision");
    
      

      
      quoteDAO.updateSupplierDecision(supplierDecision,quoteid);
      response.sendRedirect("listquote");
  }
	    
	    
	    
	    private void clientDecision(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	
	    	
	    	 String id = request.getParameter("id");
	    	 String date=request.getParameter("date");
	        System.out.println("opening client started: 00000000000000000000000000000000000");
	        request.setAttribute("id", id);
	        request.setAttribute("date", date);
	    
	             
	        RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerReply.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("opening client decision finished: 111111111111111111111111111111111111");}
	    
	    private void supplierDecision(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	  System.out.println("opening supplier started: 00000000000000000000000000000000000");

	     
	        String id = request.getParameter("id");
	    	 String date=request.getParameter("date");
	      
	        request.setAttribute("id", id);
	        request.setAttribute("date", date);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("SupplierReply.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("opening supplier decision finished: 111111111111111111111111111111111111");}
        	
	    private void listquote(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listQuote started: 00000000000000000000000000000000000");

	     
	        List<quote> listquote = quoteDAO.listAllquotes();
	        request.setAttribute("listquote", listquote);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("QuoteList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listQuote finished: 111111111111111111111111111111111111");}
	    
	    private void createquote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	    	
	    	String date = request.getParameter("date");
	        String custnote = request.getParameter("custnote");
	        String heightFTParam = request.getParameter("heightFT");
	        String diameter_width = request.getParameter("diameter_width");
	        String ft_from_house = request.getParameter("ft_from_house");
	        String location = request.getParameter("location");
	        
	   	 	
	    	quote quotes = new quote(date,custnote, heightFTParam, diameter_width, ft_from_house, location);
   	 		quoteDAO.insertquote(quotes);
   	 		response.sendRedirect("login.jsp");	}
	    
	    private void editquote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	        
	        int quoteid = Integer.parseInt(request.getParameter("quoteid"));
			String custnote = request.getParameter("custnote");
	        int totalcost = Integer.parseInt(request.getParameter("totalcost"));
	        String clientDecision = request.getParameter("clientDecision");
	        String supplierDecision = request.getParameter("supplierDecision");
	        
     
	        quote quotes = new quote(quoteid,custnote,totalcost,clientDecision,supplierDecision);
	        quoteDAO.update(quotes);
	        response.sendRedirect("login.jsp");
	    }
	        
	        
	        
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");

	     
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listUser", userDAO.listAllUsers());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    private void rootPagequote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("root view quote");
	    	request.setAttribute("listquote", quoteDAO.listAllquotes());
	    	request.getRequestDispatcher("QuoteList.jsp").forward(request, response);
	    }
	    
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String email = request.getParameter("email");
	    	 String password = request.getParameter("password");
	    	 
	    	 if (email.equals("root") && password.equals("pass1234")) {
				 System.out.println("Login Successful! Redirecting to root");
				 session = request.getSession();
				 session.setAttribute("username", email);
				 rootPage(request, response, "");
	    	 }
	    	 else if(userDAO.isValid(email, password)) 
	    	 {
			 	 
			 	 currentUser = email;
				 System.out.println("Login Successful! Redirecting");
				 request.getRequestDispatcher("activitypage.jsp").forward(request, response);
				 
			 			 			 			 
	    	 }
	    	 else {
	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
	    	 }
	    }
	           
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	    	String email = request.getParameter("email");
	   	 	String firstName = request.getParameter("firstName");
	   	 	String lastName = request.getParameter("lastName");
	   	 	String password = request.getParameter("password");
	   	 	String birthday = request.getParameter("birthday");
	   	 	String adress_street_num = request.getParameter("adress_street_num"); 
	   	 	String adress_street = request.getParameter("adress_street"); 
	   	 	String adress_city = request.getParameter("adress_city"); 
	   	 	String adress_state = request.getParameter("adress_state"); 
	   	 	String adress_zip_code = request.getParameter("adress_zip_code"); 	   	 	
	   	 	String confirm = request.getParameter("confirmation");
	   	 	
	   	 	if (password.equals(confirm)) {
	   	 		if (!userDAO.checkEmail(email)) {
		   	 		System.out.println("Registration Successful! Added to database");
		            user users = new user(email,firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code, 1000,0);
		   	 		userDAO.insert(users);
		   	 		response.sendRedirect("login.jsp");
	   	 		}
		   	 	else {
		   	 		System.out.println("Username taken, please enter new username");
		    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
		    		 request.getRequestDispatcher("register.jsp").forward(request, response);
		   	 	}
	   	 	}
	   	 	else {
	   	 		System.out.println("Password and Password Confirmation do not match");
	   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	   		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
	    }    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}
	
	    

	     
        
	    
	    
	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    