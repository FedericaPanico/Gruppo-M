package Database;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.EntityCodice;
import exception.DAOException;
import exception.DBConnectionException;
import java.sql.Date;

public class CodiceDAO {
    
    public static void insertCodice(EntityCodice eC) throws DAOException, DBConnectionException {
		
        try {

            Connection conn = DBManager.getConnection();

            String query = "INSERT INTO CODICE VALUES (?,?,?,true,true);";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, eC.getIdCodice());
                stmt.setDate(2, new java.sql.Date(eC.getDataScadenza().getTime()));
                stmt.setString(3, eC.getNomeApplicazione());
                stmt.setBoolean(4, eC.getValidità());
                stmt.setBoolean(5, eC.getDisponibilità());

                stmt.executeUpdate();

            }catch(SQLException e) {
                throw new DAOException("Errore inserimento codice");
            } finally {
                DBManager.closeConnection();
            }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

    }
    
    public static ArrayList<EntityCodice> readCodiciInvalidi(Boolean validità) throws DAOException, DBConnectionException {
        
        ArrayList<EntityCodice> eLI = new ArrayList<>();
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT * FROM CODICE WHERE VALIDITÀ = false ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setBoolean(1, validità);
                    ResultSet result = stmt.executeQuery();
                    
                    while(result.next()){
                        EntityCodice codice = new EntityCodice(result.getString(1), 
                               result.getDate(2), 
                               result.getBoolean(3), 
                               result.getBoolean(4), 
                               result.getFloat(5), 
                               result.getString(6), 
                               result.getInt(7));
                        eLI.add(codice);
                   }
                    
                }catch(SQLException e) {
                    throw new DAOException("Errore readCodiciScaduti");
                } finally {
                    DBManager.closeConnection();
                }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return eLI;
    }
    
    public static ArrayList<EntityCodice> readCodici(String nomeApp)throws DAOException, DBConnectionException{
        
        ArrayList<EntityCodice> eLC = new ArrayList<>();
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT * FROM CODICE WHERE NOMEAPPLICAZIONE=? ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setString(1, nomeApp);
                    ResultSet result = stmt.executeQuery();
                    
                    while(result.next()){
                        EntityCodice codice = new EntityCodice(result.getString(1), 
                               result.getDate(2), 
                               result.getBoolean(3), 
                               result.getBoolean(4), 
                               result.getFloat(5), 
                               result.getString(6), 
                               result.getInt(7));
                        eLC.add(codice);
                   }
                  
                }catch(SQLException e) {
                    throw new DAOException("Errore readCodici");
                } finally {
                    DBManager.closeConnection();
                }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return eLC;
    }
    
    public static int readNumCodiciScaduti(Boolean validità, Date dataScadenza) throws DAOException, DBConnectionException {
	int numCodiciScaduti = 0;

        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT COUNT(*) FROM CODICE WHERE VALIDITÀ = false AND DATASCADENZA BETWEEN ? AND ? ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setBoolean(1, validità);
                    stmt.setDate(2, dataScadenza);

                    ResultSet result = stmt.executeQuery();

                    if(result.next()) {
                        numCodiciScaduti = result.getInt(1);
                    }
                }catch(SQLException e) {
                    throw new DAOException("Errore readNumCodiciScaduti");
                } finally {
                    DBManager.closeConnection();
                }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return numCodiciScaduti;
    }
    
}
