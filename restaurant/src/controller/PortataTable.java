package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DbConnection.DBConnection;
import Model.Portata;



public class PortataTable {
	
    private String tableName;
    
    public PortataTable() {
        tableName="portata";   
    }
    
    public void creaTabella(Connection c) {
	
	    Statement statement = null;
	    try {
	        statement = c.createStatement ();
	        
	        statement.executeUpdate (
	            "CREATE TABLE IF NOT EXISTS "+ tableName +" ("
	                + "idPortata INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
	                + "nome CHAR(254), " 
	                + "tipo CHAR(40), "
	                + "prezzo FLOAT, "
	                + "dettaglio TEXT " 
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
    
    
    public void aggiungiPortata(Portata p) {
    	DBConnection dataSource=new DBConnection();
        Connection connection = dataSource.getMsSQLConnection();

        if (cercaPortata(p.getIdPortata(),connection)!=null){
        	 new Exception("Portata esiste");
             System.out.println("Errore"+ "Portata esiste");
        } 
           
                
        PreparedStatement statement = null; 
        String insert = "insert into "+ tableName +" ( idPortata,nome, tipo, prezzo, dettaglio) values (?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(insert);
            
            statement.setInt(1, p.getIdPortata());
            statement.setString(2, p.getNome());
            statement.setString(3, p.getTipo());
            statement.setFloat(4, p.getPrezzo());
            statement.setString(5, p.getDettaglio());
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
                if (connection!= null)
                    connection.close();
            }
            catch (SQLException e) {
            	new Exception(e.getMessage());
	            System.out.println("Errore"+ e.getMessage());
            }
        }
    }
	
    public Portata cercaPortata(int code,Connection connection)  {
    	Portata p = null;
        PreparedStatement statement = null;
        String query = "select * from "+ tableName +" where idPortata=?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                p = new Portata(
                		result.getInt("idPortata"),
                		result.getString("nome"),
                		result.getString("tipo"),
                		result.getFloat("prezzo"),
                		result.getString("dettaglio"));
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
        return p;
    }   
    
    public Portata cercaPortataPerNome(String s,Connection connection)  {
        Portata p = null;
        
        PreparedStatement statement = null;
       String query = "select * from "+ tableName +" where nome = '"+s+"'";


        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                p = new Portata(
                		result.getInt("idPortata"),
                		result.getString("nome"),
                		result.getString("tipo"),
                		result.getFloat("prezzo"),
                		result.getString("dettaglio"));
                
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
        return p;
  }   

    public ArrayList<Portata> cercaPerNome(String s,Connection connection)  {
        ArrayList<Portata> portate=null;
        Portata p = null;
        
        PreparedStatement statement = null;
       String query = "select * from "+ tableName +" where nome like '"+s+"%%'"+" order by nome";


        try {
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                portate = new ArrayList<Portata>();
                p = new Portata(
                		result.getInt("idPortata"),
                		result.getString("nome"),
                		result.getString("tipo"),
                		result.getFloat("prezzo"),
                		result.getString("dettaglio"));
                portate.add(p);
            }
            while(result.next()) {
            	 p = new Portata(
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
    
    
    public void cancellaPortata(Portata p,Connection connection) {

        PreparedStatement statement = null;
       String insert ="delete from "+ tableName + " where idPortata = "+ "'"+p.getIdPortata()+"'";
        try {
        	
            statement = connection.prepareStatement(insert);
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
    
    public void aggiornaPiatto(Portata p,Connection connection){
    	
    	PreparedStatement statement = null;
    	
    	System.out.println(p.getNome());
    	String insert2="update "+ tableName + " SET nome = '"+p.getNome()+"', tipo = '"+p.getTipo()+"', dettaglio = '"+p.getDettaglio()+"', prezzo = '"+p.getPrezzo()+"' WHERE idPortata = " +p.getIdPortata();  
    	
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
    
    
    
    
    

    
}
