package controller;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Comanda;
import Model.Portata;


public class ComandaTable {

    private String tableComanda;
    private String tablePortata;
    
    public ComandaTable() {
    	tableComanda="comanda";
    	tablePortata="portata";
    }
    
    public void creaTabella(Connection c) {
	 
	    Statement statement = null;
	    try {
	        statement = c.createStatement ();
	        
	        statement.executeUpdate (
	            "CREATE TABLE IF NOT EXISTS "+ tableComanda +" ("
	            	+ "idOrdine INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
	                + "codicePortata INT," 
	                + "tavolo INT, "
	                + "quantita INT, "
	                + "note TEXT, "
	                + "modificato TINYINT"
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
    
    public ArrayList<Portata> cercaPerTipo(String tipoPiatto,Connection connection)  {
    	ArrayList<Portata> portate =new ArrayList<Portata>();;
    	
    	Portata p=null;
       
        PreparedStatement statement = null;
        
        
        String query = "select * from "+ tablePortata +" where tipo like '" +tipoPiatto+"'";
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
            	
                p=new Portata(
                		result.getInt("idPortata"),
                		result.getString("nome"),
                		result.getString("tipo"),
                		result.getFloat("prezzo"),
                		result.getString("dettaglio"));
                portate.add(p);
                }
            while(result.next()) {
            	
            	p=new Portata(
                		result.getInt("idPortata"),
                		result.getString("nome"),
                		result.getString("tipo"),
                		result.getFloat("prezzo"),
                		result.getString("dettaglio"));
                portate.add(p);
            	
 	
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
        return portate;
    }  
    
    
    
    
    public void aggiungiComanda(Comanda c, Connection connection) {
               
        PreparedStatement statement = null;
        String insert = "insert into "+ tableComanda +" ( idOrdine,codicePortata,tavolo, quantita, note,modificato) values (?,?,?,?,?,?)";
        try {
                  
            statement = connection.prepareStatement(insert);
            statement.setInt(1,c.getIdOrdine());
            statement.setInt(2,c.getCodicePortata());
            statement.setInt(3,c.getTavolo());
            statement.setInt(4,c.getQuantita());
            statement.setString(5,c.getNote());
            statement.setBoolean(6,c.isModificato());

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
    
    
    public ArrayList<Comanda> cercaComanda(int numeroTavolo,Connection connection)  {
        ArrayList<Comanda> comande = null;
        Comanda comanda = null;
        PreparedStatement statement = null;
        String query = "select * from "+ tableComanda+" WHERE tavolo = "+numeroTavolo;
        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                comande = new ArrayList<Comanda>();
                comanda = new Comanda(result.getInt("idOrdine"),result.getInt("codicePortata"),result.getInt("tavolo"),result.getInt("quantita"),result.getString("note"),result.getBoolean("modificato"));
               comande.add(comanda);
            }
            while(result.next()) {
            	
                   comanda = new Comanda(result.getInt("idOrdine"),result.getInt("codicePortata"),result.getInt("tavolo"),result.getInt("quantita"),result.getString("note"),result.getBoolean("modificato"));
                   comande.add(comanda);
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
        
        return comande;
    }   
   
    
  public void aggiornaComanda(Comanda c,Connection connection){
    	
    	PreparedStatement statement = null;
    	
    	String insert2="update "+ tableComanda + " SET codicePortata = "+c.getCodicePortata()+", tavolo = "+c.getTavolo()+", quantita = '"+c.getQuantita()+"', note = '"+c.getNote()+"', modificato = "+c.isModificato()+" WHERE idOrdine = " +c.getIdOrdine();  
    	System.out.println(insert2);
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
  
  
  public ArrayList<Comanda> caricaComande(Connection connection)  {
      ArrayList<Comanda> comande = null;
      Comanda comanda = null;
      PreparedStatement statement = null;
      String query = "select * from "+ tableComanda+" ORDER BY idOrdine";
      try {
          statement = connection.prepareStatement(query);
          ResultSet result = statement.executeQuery();
          if(result.next()) {
              comande = new ArrayList<Comanda>();
              comanda = new Comanda(result.getInt("idOrdine"),result.getInt("codicePortata"),result.getInt("tavolo"),result.getInt("quantita"),result.getString("note"),result.getBoolean("modificato"));
             
              comande.add(comanda);
          }
          while(result.next()) {
          	
                 comanda = new Comanda(result.getInt("idOrdine"),result.getInt("codicePortata"),result.getInt("tavolo"),result.getInt("quantita"),result.getString("note"),result.getBoolean("modificato"));
                 comande.add(comanda);
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
      
     
      return comande;
  }   
  
  
  public void cancellaComanda(Comanda comanda,Connection connection) {
     
      PreparedStatement statement = null;
      String insert = "delete from "+ tableComanda +" where tavolo = "+comanda.getTavolo();
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
