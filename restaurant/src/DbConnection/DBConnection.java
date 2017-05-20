package DbConnection;



import java.sql.*;

public class DBConnection {
    
    private String dbName = "Ristorante"; //Inserire nome database
    private String address = "localhost"; //Inserire URI db
    private String port = "8889";	//Inserire porta
    private String username = "root"; //username
    private String password = "root"; //password
    
    
    private DBConnection dataSource;
   
    
    public Connection getMsSQLConnection()  {  
        	
    	System.out.println("Avvio connessione DB!");
    	
        	String driver = "com.mysql.jdbc.Driver";
        	String dbUri = "jdbc:mysql://" + address + ":" + port + "/"+ dbName;
        	
            Connection connection = null;
             try {
                 System.out.println("DataSource.getConnection() driver = "+driver);
                 Class.forName(driver);
                 System.out.println("DataSource.getConnection() dbUri = "+dbUri);
                 connection = DriverManager.getConnection(dbUri, username, password);
             }
             catch (ClassNotFoundException e) {
                 new Exception(e.getMessage());
                 System.out.println("ErroreCNF"+ e.getMessage());
             }
             catch(SQLException e) {
                 new Exception(e.getMessage());
                 System.out.println(e);
                 System.out.println("ErroreSQL"+ e.getMessage());
             }
             if (connection!=null) {
            	 System.out.println("Connessione avvenuta con successo!");
             }
             return connection;
         }
    


}
