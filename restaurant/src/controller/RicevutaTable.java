package controller;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Ricevuta;

public class RicevutaTable {

    private String tableName;
    
    public RicevutaTable() {
        tableName="ricevuta";   
    }
    
    public void creaTabella(Connection c) {
	    Statement statement = null;
	    try {
	        statement = c.createStatement ();
	        
	        statement.executeUpdate (
	            "CREATE TABLE IF NOT EXISTS "+ tableName +" ("
	            	+ "idRicevuta INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
	            	+ "data DATE, " 
	                + "totale DOUBLE, "
	                + "sconto INT, "
	                + "idServizio INT "
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
    
    public void inserisci(Ricevuta ricevuta,Connection connection) {       
                
        PreparedStatement statement = null; 
        String insert = "insert into "+ tableName +" (idRicevuta, data, totale, sconto, idServizio) values (?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(insert);
            
            
            statement.setInt(1, ricevuta.getIdRicevuta());
            statement.setString(2, ricevuta.getData());
            statement.setDouble(3, ricevuta.getTotale());
            statement.setInt(4, ricevuta.getSconto());
            statement.setInt(5, ricevuta.getIdServizio());
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
    
    public int ultimaRicevuta(Connection connection){
  	  
  	  int numeroRicevuta=0;
  	  
  	  
  	  PreparedStatement statement = null;
  	  String query="SELECT max(idRicevuta) from ricevuta";

        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
            	numeroRicevuta= result.getInt(1);
            	
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
        
  
  	  return numeroRicevuta;
  	  
    }
    
    
    
    public ArrayList<Ricevuta> cercaPerData(String dataOra,Connection connection)  {
    	ArrayList<Ricevuta> ricevute =new ArrayList<>();;
    	
        Ricevuta r=null;
        
        PreparedStatement statement = null;
        
        System.out.println(dataOra);
        String data=dataOra.substring(0, 10);
        
        String query = "select * from "+ tableName +" where data like '" +data+"%%'";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
            	
                r=new Ricevuta(
                		result.getInt("idRicevuta"),
                		result.getString("data"),
                		result.getDouble("totale"),
                		result.getInt("sconto"),
                		result.getInt("idServizio"));
                		ricevute.add(r);
                }
            while(result.next()) {
            	
            	  r=new Ricevuta(
                  		result.getInt("idRicevuta"),
                  		result.getString("data"),
                  		result.getDouble("totale"),
                  		result.getInt("sconto"),
                  		result.getInt("idServizio"));
                 ricevute.add(r);
            	
 	
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
        return ricevute;
    }   
    
    

    public ArrayList<Ricevuta> cercaPerServizio(int servizio,Connection connection)  {
    	ArrayList<Ricevuta> ricevute =new ArrayList<>();
    	
        Ricevuta r=null;
        
        PreparedStatement statement = null;
        
        
        String query = "select * from "+ tableName +" where idServizio = "+servizio;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
            	
                r=new Ricevuta(
                		result.getInt("idRicevuta"),
                		result.getString("data"),
                		result.getDouble("totale"),
                		result.getInt("sconto"),
                		result.getInt("idServizio"));
                		ricevute.add(r);
                }
            while(result.next()) {
            	
            	  r=new Ricevuta(
                  		result.getInt("idRicevuta"),
                  		result.getString("data"),
                  		result.getDouble("totale"),
                  		result.getInt("sconto"),
                  		result.getInt("idServizio"));
                 ricevute.add(r);
            	
 	
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
        return ricevute;
    }   

	
   
}
