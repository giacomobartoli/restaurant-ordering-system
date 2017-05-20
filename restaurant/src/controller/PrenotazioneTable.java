package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Portata;
import Model.Prenotazione;
import Model.Servizio;

public class PrenotazioneTable {

    private String tableName;
    
    public PrenotazioneTable() {
        tableName="prenotazione";   
    }
    
    public void creaTabella(Connection c) {
	    Statement statement = null;
	    try {
	        statement = c.createStatement ();
	        
	        statement.executeUpdate (
	            "CREATE TABLE IF NOT EXISTS "+ tableName +" ("
	                + "idPrenotazione INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
	                + "nome CHAR(254), " 
	                + "dataOra DATETIME, "
	                + "numeroPosti INT, "
	                + "stato CHAR(15), "
	                + "idServizio INT, "
	                + "numeroTavolo INT"
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
    
    public void aggiungiPrenotazione(Prenotazione p,Connection connection) {    
                
        PreparedStatement statement = null; 
        String insert = "insert into "+ tableName +" ( idPrenotazione,nome, dataOra, numeroPosti, stato, idServizio,numeroTavolo) values (?,?,?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(insert);
            
            statement.setInt(1, p.getIdPrenotazione());
            statement.setString(2, p.getNome());
            statement.setString(3, p.getDataOra());
            statement.setInt(4, p.getNumeroPosti());
            statement.setString(5, p.getStato());
            statement.setInt(6, p.getIdServizio());
            statement.setInt(7, p.getNumeroTavolo());


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
	
    public ArrayList<Prenotazione> cercaPerData(String dataOra,Connection connection)  {
    	ArrayList<Prenotazione> prenotazioni =new ArrayList<Prenotazione>();;
    	
        Prenotazione p=null;
        
        PreparedStatement statement = null;
        
        String data=dataOra.substring(0, 10);
        String query = "select * from "+ tableName +" where dataOra like '" +data+"%%'";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
            	
            	  p=new Prenotazione(
                  		result.getInt("idPrenotazione"),
                  		result.getString("nome"),
                  		result.getString("dataOra"),
                  		result.getInt("numeroPosti"),
                  		result.getString("stato"),
                  		result.getInt("idServizio"),
                  		result.getInt("numeroTavolo"));

                prenotazioni.add(p);
                }
            while(result.next()) {
            	
            	  p=new Prenotazione(
                  		result.getInt("idPrenotazione"),
                  		result.getString("nome"),
                  		result.getString("dataOra"),
                  		result.getInt("numeroPosti"),
                  		result.getString("stato"),
                  		result.getInt("idServizio"),
                  		result.getInt("numeroTavolo"));
                  prenotazioni.add(p);
 	
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
        return prenotazioni;
    }  
    
    
    
    
    public ArrayList<Prenotazione> cercaPerDataInAttesa(String dataOra,Connection connection)  {
    	ArrayList<Prenotazione> prenotazioni =new ArrayList<Prenotazione>();;
    	
        Prenotazione p=null;
        
        PreparedStatement statement = null;
        
        String data=dataOra.substring(0, 10);
        //select * from prenotazione where dataOra like '2014-06-27%' and stato = 'inAttesa'
        String query = "select * from "+ tableName +" where dataOra like '" +data+"%%' and stato = 'inAttesa'";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
            	
            	  p=new Prenotazione(
                    		result.getInt("idPrenotazione"),
                    		result.getString("nome"),
                    		result.getString("dataOra"),
                    		result.getInt("numeroPosti"),
                    		result.getString("stato"),
                    		result.getInt("idServizio"),
                    		result.getInt("numeroTavolo"));


                  prenotazioni.add(p);
                }
            while(result.next()) {
            	
            	  p=new Prenotazione(
                  		result.getInt("idPrenotazione"),
                  		result.getString("nome"),
                  		result.getString("dataOra"),
                  		result.getInt("numeroPosti"),
                  		result.getString("stato"),
                  		result.getInt("idServizio"),
            	  		result.getInt("numeroTavolo"));


                  prenotazioni.add(p);
            	
 	
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
        return prenotazioni;
    }  
    
    
    public void cancellaPrenotazione(Prenotazione prenotazione,Connection connection) {

         PreparedStatement statement = null;
         String insert = "delete from "+ tableName +" where idPrenotazione = "+prenotazione.getIdPrenotazione();
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

    
    public ArrayList<Prenotazione> cercaPrenotazioni(Connection connection)  {
        ArrayList<Prenotazione> prenotazioni = null;
       
        Prenotazione prenotazione = null;
        PreparedStatement statement = null;
        String query = "select * from  prenotazione";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                prenotazioni = new ArrayList<Prenotazione>();
                prenotazione = new Prenotazione(result.getInt("idPrenotazione"),result.getString("nome"),result.getString("dataOra"),result.getInt("numeroPosti"),result.getString("stato"),result.getInt("idServizio"),result.getInt("numeroTavolo"));
               
                prenotazioni.add(prenotazione);
            }
            while(result.next()) {
            	
                prenotazione = new Prenotazione(result.getInt("idPrenotazione"),result.getString("nome"),result.getString("dataOra"),result.getInt("numeroPosti"),result.getString("stato"),result.getInt("idServizio"),result.getInt("numeroTavolo"));
                  
                   prenotazioni.add(prenotazione);
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
        
       
        return prenotazioni;
    }   
	
    
    
 public void prenotazioneInServizio(Servizio s,Prenotazione p,Connection connection){
    	
    	PreparedStatement statement = null;
    	
    	String insert2="update "+ tableName + " SET idServizio = '"+s.getIdServizio()+"' WHERE idPrenotazione = " +p.getIdPrenotazione();  
    	
        try {
        	
            statement = connection.prepareStatement(insert2);

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
 
 
 
public void aggiornaStatoPrenotazione(Prenotazione p,String s,Connection connection){
 	
 	PreparedStatement statement = null;
 	
 	String insert2="update "+ tableName + " SET stato = '"+s+"' WHERE idPrenotazione = " +p.getIdPrenotazione();  
 	
     try {
     	
         statement = connection.prepareStatement(insert2);

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
 

public void tavoloInPrenotazione(Prenotazione p,Connection connection){
	
	PreparedStatement statement = null;
	System.out.println("numero tavolo"+p.getNumeroTavolo());
	String insert2="update "+ tableName + " SET numeroTavolo = "+p.getNumeroTavolo()+" WHERE idPrenotazione = " +p.getIdPrenotazione();  
	
    try {
    	
        statement = connection.prepareStatement(insert2);

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


public Prenotazione cercaPrenotazioniPerTavolo(int numTavolo,Connection connection)  {
   
    Prenotazione prenotazione = null;
    PreparedStatement statement = null;
    String query = "select * from  prenotazione WHERE numeroTavolo = "+numTavolo;
    try {
        statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        if(result.next()) {
            prenotazione = new Prenotazione(result.getInt("idPrenotazione"),result.getString("nome"),result.getString("dataOra"),result.getInt("numeroPosti"),result.getString("stato"),result.getInt("idServizio"),result.getInt("numeroTavolo"));
           
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
    
    return prenotazione;
}   



    
}
