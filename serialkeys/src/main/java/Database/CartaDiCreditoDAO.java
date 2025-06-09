package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import exception.DAOException;
import exception.DBConnectionException;
import Entity.EntityCartaDiCredito;

public class CartaDiCreditoDAO {
    
    public static EntityCartaDiCredito readCartaDiCredito(String idCliente)throws DAOException, DBConnectionException{
 
        EntityCartaDiCredito eCDC = null;
 
        try {
 
                Connection conn = DBManager.getConnection();
 
 
                String query = "SELECT * FROM `CARTA DI CREDITO` WHERE IDCLIENTEREGISTRATO = ? ;";
 
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
 
                    stmt.setString(1, idCliente);
 
                    ResultSet result = stmt.executeQuery();
 
                       if(result.next()){
 
                           eCDC = new EntityCartaDiCredito(result.getString(1), result.getString(2), result.getLong(3), result.getDate(4), result.getInt(5));
                       }
 
 
                }catch(SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Errore readCartaDiCredito");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
 
        return eCDC;
 
    }
    
}
