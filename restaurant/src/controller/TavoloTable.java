package controller;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Tavolo;

public class TavoloTable {
	
    private String tableName;
    
    public TavoloTable() {
        tableName="tavolo";   
    }
    
    public void creaTabella(Connection c) {
	    Statement statement = null;
	    try {
	        statement = c.createStatement ();
	        
	        statement.executeUpdate (
	            "CREATE TABLE IF NOT EXISTS "+ tableName +" ("
	                + "numeroTavolo INT NOT NULL PRIMARY KEY,"
	                + "numeroCoperti INT, " 
	                + "tavoliUsati INT"
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

    
    public void aggiungiTavolo(Tavolo t,Connection connection) {     
        System.out.println("INSERT");
     
        PreparedStatement statement = null;
        String insert = "insert into "+ tableName +" (numeroTavolo, numeroCoperti, tavoliUsati) values (?,?,?)";
        try {
            statement = connection.prepareStatement(insert);
            statement.setInt(1, t.getNumeroTavolo());
            statement.setInt(2,t.getNumeroCoperti());
            statement.setInt(3, t.getTavoliUsati());
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
    
    
    public Tavolo cercaTavolo(int code,Connection connection)  {
        Tavolo tavolo = null;
               
        PreparedStatement statement = null;
        String query = "select * from "+ tableName +" where numeroTavolo=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
               
                tavolo = new Tavolo(result.getInt("numeroTavolo"),result.getInt("numeroCoperti"),result.getInt("tavoliUsati"));
               
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
        return tavolo;
    }   
    
    
  public void aggiornatTavolo(Tavolo t,Connection connection){
    

    	PreparedStatement statement = null;
    	    	
    	String insert2="update "+ tableName + " SET numeroCoperti = "+t.getNumeroCoperti()+", tavoliUsati = "+t.getTavoliUsati()+" WHERE numeroTavolo = " +t.getNumeroTavolo();  
    	
        try {
        	
            statement = connection.prepareStatement(insert2);
            System.out.println(statement.toString());
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
    
    
  
public int contaTavoli(Connection connection){
	  
	  int numeroTavoli=0;
	  
	  
	  PreparedStatement statement = null;
	  String query="SELECT numeroTavolo, COUNT(numeroTavolo) FROM tavolo";

      try {
          statement = connection.prepareStatement(query);
          ResultSet result = statement.executeQuery();
          if(result.next()) {
            numeroTavoli= result.getInt(2);
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
	  
	  return numeroTavoli;
	  
  }
    
public void cancellaTavolo(Tavolo tavolo,Connection connection) {

    PreparedStatement statement = null;
    String insert = "delete from "+ tableName +" where numeroTavolo = "+tavolo.getNumeroTavolo();
    try {
        statement = connection.prepareStatement(insert);
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

}
