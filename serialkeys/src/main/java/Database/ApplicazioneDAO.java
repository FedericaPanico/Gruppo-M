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
    public static ArrayList<EntityApplicazione> readListaApplicazioni() throws DAOException, DBConnectionException{
        
        ArrayList<EntityApplicazione> listaApplicazioni = new ArrayList<>();
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT NOMEAPPLICAZIONE FROM APPLICAZIONE ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    ResultSet result = stmt.executeQuery();
                    
                    while(result.next()){
                        EntityApplicazione applicazione = new EntityApplicazione(result.getString(1));
                        listaApplicazioni.add(applicazione);
                   }
                    
                }catch(SQLException e) {
                    throw new DAOException("Errore readApplicazioni");
                } finally {
                    DBManager.closeConnection();
                }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return listaApplicazioni;
    }
    
    public static EntityApplicazione readApplicazione(String nomeApp)throws DAOException, DBConnectionException{
       
        EntityApplicazione eAPP = null;
        try {
 
                Connection conn = DBManager.getConnection();
 
 
                String query = "SELECT * FROM APPLICAZIONE WHERE NOMEAPPLICAZIONE = ? ;";
 
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
                    stmt.setString(1, nomeApp);
 
                    ResultSet result = stmt.executeQuery();
 
                    if(result.next()){
                        eAPP = new EntityApplicazione(
                            result.getString(1), 
                            result.getDate(2), 
                            result.getString(3), 
                            result.getString(4)
                        );
                    }
 
 
                }catch(SQLException e) {
                    throw new DAOException("Errore readApplicazione");
                }
                finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
 
 
        return eAPP;
    }
    
    public static void svuotaApplicazioni() {
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "DELETE FROM APPLICAZIONE ;";

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
    
    public static void inserisciApplicazioni() {
        try {
            Connection conn = DBManager.getConnection();

            String query = "INSERT IGNORE INTO applicazione (NomeApplicazione, DataRilascio, Produttore, Categoria) VALUES "
                    + "('FitApp', '2023-01-01', 'WellnessCorp', 'Salute'), "
                    + "('EduKids', '2022-05-15', 'Learnify', 'Educazione'), "
                    + "('SpeedRun', '2021-11-20', 'GameX', 'Giochi'), "
                    + "('MoneySafe', '2024-03-10', 'BankTech', 'Finanza'), "
                    + "('NotePro', '2020-09-25', 'OfficeSoft', 'Produttività'), "
                    + "('Foodie', '2023-07-12', 'ChefTech', 'Cucina'), "
                    + "('TravelGo', '2021-06-30', 'Explore Inc.', 'Viaggi'), "
                    + "('PhotoEdit', '2022-02-11', 'PixLab', 'Fotografia'), "
                    + "('SleepAid', '2022-12-01', 'DreamSoft', 'Salute'), "
                    + "('LangBoost', '2024-01-20', 'LinguaWorld', 'Educazione');";

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
