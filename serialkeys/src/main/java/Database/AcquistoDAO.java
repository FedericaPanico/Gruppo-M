package Database;

import java.sql.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.EntityAcquisto;
import exception.DAOException;
import exception.DBConnectionException;

public class AcquistoDAO {
    public static int readNumCodiciVenduti(String nomeApplicazione, Date dataAcquisto) throws DAOException, DBConnectionException {
	int numCodiciVenduti = 0;

        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT COUNT(*) FROM ACQUISTO WHERE NOMEAPPLICAZIONE=? AND DATAACQUISTO BETWEEN ? AND ? ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setString(1, nomeApplicazione);
                    stmt.setDate(2, dataAcquisto);

                    ResultSet result = stmt.executeQuery();

                    if(result.next()) {
                        numCodiciVenduti = result.getInt(1);
                    }
                }catch(SQLException e) {
                    throw new DAOException("Errore readNumCodiciVenduti");
                } finally {
                    DBManager.closeConnection();
                }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return numCodiciVenduti;
    }
    
}
