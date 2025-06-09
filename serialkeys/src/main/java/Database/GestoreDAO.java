package Database;

import Entity.EntityGestore;

import exception.DAOException;
import exception.DBConnectionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestoreDAO {
    
    public static String readIDGestore() throws DAOException, DBConnectionException {
	String idGestore = null;

        try {
            Connection conn = DBManager.getConnection();

            String query = "SELECT IDGESTORE FROM GESTORE ;";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    idGestore = result.getString(1);
                }
            } catch(SQLException e) {
                throw new DAOException("Errore readIDGestore");
            } finally {
                DBManager.closeConnection();
            }
        } catch(SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }

        return idGestore;
    }
    
    public static void svuotaGestore() {
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "DELETE FROM GESTORE ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

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
    
    public static void inserisciGestore() {
        try {
            Connection conn = DBManager.getConnection();

            String query = "INSERT IGNORE INTO gestore (IDGestore, Password) VALUES "
                    + "('SistemaSerialKeys@gmail.com', 'GruppoMFVCG23');";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
