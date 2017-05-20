package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Ricevuta;
import Model.Servizio;

public class ServizioTable {

    private String tableName;

    public ServizioTable() {
        tableName="servizio";   
    }
    
    
    public void creaTabella(Connection c) {
	    Statement statement = null;
	    try {
	        statement = c.createStatement ();
	        
	        statement.executeUpdate (
	            "CREATE TABLE IF NOT EXISTS "+ tableName +" ("
	            	+ "idServizio INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
	            	+ "pranzoCena CHAR(10), " 
	                + "data DATETIME, "
	                + "tavoliTotali INT"
	            + ") "
	        );
	        statement.close ();

	    }
	    catch (SQLException e) {
	    	 new Exception(e.getMessage());
	         System.out.println("Errore"+ e.getMessage());
	    }
	    finally {
	        try {
	            if (statement != null) 
	                statement.close();
	        }
	        catch (SQLException e) {
	        	 new Exception(e.getMessage());
	             System.out.println("Errore"+ e.getMessage());
	        }
	    }
    }
    
    
    public void inserisciServizio(Servizio servizio,Connection connection) {       
        
        PreparedStatement statement = null; 
        String insert = "insert into "+ tableName +" (idServizio, pranzoCena, data, tavoliTotali) values (?,?,?,?)";
        try {
            statement = connection.prepareStatement(insert);
            
            
            statement.setInt(1, servizio.getIdServizio());
            statement.setString(2, servizio.getPranzoCena());
            statement.setString(3, servizio.getData());
            statement.setInt(4, servizio.getTavoliTotali());

            statement.executeUpdate();
        }
        catch (SQLException e) {
           	new Exception(e.getMessage());
            System.out.println("Errore"+ e.getMessage());
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
            }
            catch (SQLException e) {
            	new Exception(e.getMessage());
	            System.out.println("Errore"+ e.getMessage());
            }
        }
    }
    
    
    public ArrayList<Servizio> cercaPerData(String data,Connection connection)  {
    	ArrayList<Servizio> servizi =new ArrayList<>();
    	
        Servizio s=null;
        
        PreparedStatement statement = null;
        
        String dataTemp=data.substring(0, 10);
        
        String query = "select * from "+ tableName +" where data like '" +data+"%%'";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
            	
                s=new Servizio(
                		result.getInt("idServizio"),
                		result.getString("pranzoCena"),
                		result.getString("data"),
                		result.getInt("tavoliTotali"));
                		
                
                		servizi.add(s);
                }
            while(result.next()) {
            	
            	 s=new Servizio(
                 		result.getInt("idServizio"),
                 		result.getString("pranzoCena"),
                 		result.getString("data"),
                 		result.getInt("tavoliTotali"));
                 
                 		servizi.add(s);
            	
 	
            }
        }
        catch (SQLException e) {
        	new Exception(e.getMessage());
            System.out.println("Errore"+ e.getMessage());
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
            } catch (SQLException e) {
            	new Exception(e.getMessage());
	             System.out.println("Errore"+ e.getMessage());
            }
        }
        return servizi;
    }   
    
    
}
