package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.EntityApplicazione;
import exception.DAOException;
import exception.DBConnectionException;

import java.util.ArrayList;

public class ApplicazioneDAO {
    public static ArrayList<EntityApplicazione> readApplicazioni(String nomeApp) throws DAOException, DBConnectionException{
        
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
    
}
