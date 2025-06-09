package Database;

import java.sql.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.*;
import exception.DAOException;
import exception.DBConnectionException;
import java.util.ArrayList;

import java.security.SecureRandom;

import java.time.LocalDate;

public class AcquistoDAO {
    
    public static int readNumCodiciVenduti(String nomeApplicazione, LocalDate dataInizioSettimana, LocalDate dataFineSettimana) throws DAOException, DBConnectionException {
	int numCodiciVenduti = 0;

        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT SUM(NUMCODICI) FROM ACQUISTO WHERE NOMEAPPLICAZIONE = ? AND DATAACQUISTO BETWEEN ? AND ? ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setString(1, nomeApplicazione);
                    stmt.setDate(2, Date.valueOf(dataInizioSettimana));
                    stmt.setDate(3, Date.valueOf(dataFineSettimana));
                    
                    ResultSet result = stmt.executeQuery();

                    if(result.next()) {
                        numCodiciVenduti = result.getInt(1);
                    }
                } catch(SQLException e) {
                    throw new DAOException("Errore readNumCodiciVenduti");
                } finally {
                    DBManager.closeConnection();
                }

        } catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return numCodiciVenduti;
    }
    
    public static EntityAcquisto createAcquisto(String nomeApp, int numCodici, String email)throws DAOException, DBConnectionException{
 
        SecureRandom rand = new SecureRandom();
        int ID = rand.nextInt(1000);
        Date dataAcquisto = new Date(2025,1,1);
        String idClienteRegistrato = null;
 
        EntityAcquisto eA = new EntityAcquisto(ID, dataAcquisto, nomeApp, numCodici, email, new ArrayList<>(), idClienteRegistrato);
       
        try {
 
                Connection conn = DBManager.getConnection();
 
 
                String query = "INSERT INTO ACQUISTO (IDACQUISTO, DATAACQUISTO, NOMEAPPLICAZIONE, NUMCODICI, EMAILDESTINAZIONE, IDCLIENTEREGISTRATO) VALUES (?,?,?,?,?,?);";
 
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
 
                    stmt.setInt(1, ID);
                    //stmt.setDate(2, new java.sql.Date(dataAcquisto.getTime()));
                    stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                    stmt.setString(3, nomeApp);
                    stmt.setInt(4, numCodici);
                    stmt.setString(5, email);
                    stmt.setString(6, idClienteRegistrato);
 
                    stmt.executeUpdate();
 
                }catch(SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Errore createAcquisto");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
 
 
        return eA;
    }
 
    public static void deleteAcquisto(EntityAcquisto eA)throws DAOException, DBConnectionException{
       
        try {
 
                Connection conn = DBManager.getConnection();
 
                String query = "DELETE FROM ACQUISTO WHERE IDACQUISTO = ?;";
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
                    stmt.setInt(1, eA.getIdAcquisto());
 
                    stmt.executeUpdate();
 
                }catch(SQLException e) {
 
                    throw new DAOException("Errore deleteAcquisto");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
    }
    
    public static void svuotaAcquistiSettimana(LocalDate dataInizioSettimana, LocalDate dataFineSettimana) {
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "DELETE FROM ACQUISTO WHERE DATAACQUISTO BETWEEN ? AND ? ;";
                
                try {
                    PreparedStatement stmt = conn.prepareStatement(query);
                    
                    stmt.setDate(1, Date.valueOf(dataInizioSettimana));
                    stmt.setDate(2, Date.valueOf(dataFineSettimana));

                    stmt.executeUpdate();
                    
                }catch(SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBManager.closeConnection();
                }
                

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
}
