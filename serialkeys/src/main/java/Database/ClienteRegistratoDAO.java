package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import Entity.EntityClienteRegistrato;
import exception.DAOException;
import exception.DBConnectionException;

public class ClienteRegistratoDAO {
    
    public static EntityClienteRegistrato readClienteRegistrato(String email)throws DAOException, DBConnectionException{
 
        EntityClienteRegistrato eCR = null;
 
        try {
 
                Connection conn = DBManager.getConnection();
 
 
                String query = "SELECT * FROM `CLIENTE REGISTRATO` WHERE EMAIL = ?;";
 
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
 
                    stmt.setString(1, email);
 
                    ResultSet result = stmt.executeQuery();
 
                       if(result.next()){
 
                            eCR = new EntityClienteRegistrato(result.getString(1), result.getString(2), result.getString(3), result.getString(4), null, null);
                       }
 
 
                }catch(SQLException e) {
 
                    throw new DAOException("Errore readClienteRegistrato");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
 
 
        return eCR;
    }
    
}
