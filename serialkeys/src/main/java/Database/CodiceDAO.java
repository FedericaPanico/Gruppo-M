package Database;

import Entity.EntityAcquisto;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entity.EntityCodice;
import exception.DAOException;
import exception.DBConnectionException;
import java.sql.Date;

import java.time.LocalDate;

public class CodiceDAO {
    
    public static void insertCodice(EntityCodice eC) throws DAOException, DBConnectionException {
		
        try {

            Connection conn = DBManager.getConnection();

            String query = "INSERT INTO CODICE VALUES (?,?,?,?,?,?,NULL);";

            try {
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, eC.getIdCodice());
                stmt.setDate(2, new java.sql.Date(eC.getDataScadenza().getTime()));
                stmt.setBoolean(3, eC.getValidità());
                stmt.setBoolean(4, eC.getDisponibilità());
                stmt.setFloat(5, eC.getPrezzo());
                stmt.setString(6, eC.getNomeApplicazione());
                
                int righeInserite = stmt.executeUpdate();
                
                if(righeInserite > 0) {
                    System.out.println("Inserimento riuscito!");
                } else {
                    System.out.println("Inserimento non riuscito!");
                }

            }catch(SQLException e) {
                e.printStackTrace();
                throw new DAOException("Errore inserimento codice");
            } finally {
                DBManager.closeConnection();
            }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

    }
    
    public static EntityCodice readCodice(String IdCodice) throws DAOException, DBConnectionException {
        EntityCodice codice = null;
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT * FROM CODICE WHERE IDCODICE = ? ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);
                    
                    stmt.setString(1, IdCodice);

                    ResultSet result = stmt.executeQuery();
                    
                    if(result.next()){
                        codice = new EntityCodice(result.getString(1));
                   }
                    
                }catch(SQLException e) {
                    throw new DAOException("Errore readCodiciScaduti");
                } finally {
                    DBManager.closeConnection();
                }

        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }

        return codice;
    }
    
    public static ArrayList<EntityCodice> readCodiciInvalidi() throws DAOException, DBConnectionException {
        
        ArrayList<EntityCodice> eLI = new ArrayList<>();
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT * FROM CODICE WHERE VALIDITÀ = FALSE ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

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
    
    public static ArrayList<EntityCodice> readCodici(String nomeApp) throws DAOException, DBConnectionException{
       
        ArrayList<EntityCodice> eLC = new ArrayList<>();
       
        try {
            Connection conn = DBManager.getConnection();
 
            String query = "SELECT * FROM CODICE WHERE NOMEAPPLICAZIONE = ? AND DISPONIBILITÀ = ? AND VALIDITÀ = ?;";
 
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
 
                stmt.setString(1, nomeApp);
                stmt.setBoolean(2, true);
                stmt.setBoolean(3, true);
 
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
    
    public static EntityAcquisto updateListaCodiciAcquistati(EntityAcquisto eA, ArrayList<EntityCodice> eLC)throws DAOException, DBConnectionException{
       
        try {
 
                Connection conn = DBManager.getConnection();
 
                for(int i = 0; i < eLC.size(); i++){
 
                    eA.getCodiciAcquistati().add(eLC.get(i));
 
                }
 
                try {
                   
                    for(int i = 0; i < eA.getCodiciAcquistati().size(); i++){
 
                    String query = "UPDATE CODICE SET IDACQUISTO = ? WHERE IDCODICE = ?;";
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
                    stmt.setInt(1, eA.getIdAcquisto());
                    stmt.setString(2, eA.getCodiciAcquistati().get(i).getIdCodice());
 
                    stmt.executeUpdate();
                    }
 
 
                }catch(SQLException e) {
 
                    throw new DAOException("Errore updateListaCodiciAcquistati");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
 
 
        return eA;
    }
 
    public static void updateDisponibilità(EntityCodice codice)throws DAOException, DBConnectionException{
         try {
            Connection conn = DBManager.getConnection();
 
            String query = "UPDATE CODICE SET DISPONIBILITÀ = ? WHERE IDCODICE = ?;";
 
            try {
                PreparedStatement stmt = conn.prepareStatement(query);
 
                stmt.setBoolean(1, false);
                stmt.setString(2, codice.getIdCodice());
 
                stmt.executeUpdate();
 
            }catch(SQLException e) {
                throw new DAOException("Errore updateDisponibilità");
            } finally {
                DBManager.closeConnection();
            }
 
        }catch(SQLException e) {
                throw new DBConnectionException("Errore connessione database");
        }
 
        return;
 
    }
    
    public static int readNumCodiciScaduti(String nomeApplicazione, LocalDate dataInizioSettimana, LocalDate dataFineSettimana) throws DAOException, DBConnectionException {
	int numCodiciScaduti = 0;

        try {
                Connection conn = DBManager.getConnection();

                String query = "SELECT COUNT(*) FROM CODICE WHERE NOMEAPPLICAZIONE = ? AND VALIDITÀ = FALSE AND DATASCADENZA BETWEEN ? AND ? ;";

                try {
                    PreparedStatement stmt = conn.prepareStatement(query);

                    stmt.setString(1, nomeApplicazione);
                    stmt.setDate(2, Date.valueOf(dataInizioSettimana));
                    stmt.setDate(3, Date.valueOf(dataFineSettimana));

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
    
    public static void deleteCodice(EntityCodice eC) throws DAOException, DBConnectionException{
       
        try {
 
                Connection conn = DBManager.getConnection();
 
                String query = "DELETE FROM CODICE WHERE IDCODICE = ?;";
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
                    stmt.setString(1, eC.getIdCodice());
 
                    stmt.executeUpdate();
                    
                }catch(SQLException e) {
 
                    throw new DAOException("Errore deleteCodice");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
    }
    
    public static void svuotaCodici(){
        try{
            Connection conn = DBManager.getConnection();

            String query = "DELETE FROM CODICE ;";

            try{
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.executeUpdate();
                
            }catch(SQLException e){
                e.printStackTrace();
            }finally {
                DBManager.closeConnection();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void svuotaCodiciInvalidi(){
        try{
            Connection conn = DBManager.getConnection();

            String query = "DELETE FROM CODICE WHERE VALIDITÀ = FALSE;";

            try{
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.executeUpdate();
                
            }catch(SQLException e){
                e.printStackTrace();
            }finally {
                DBManager.closeConnection();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void svuotaInvalidiSettimana(LocalDate dataInizioSettimana, LocalDate dataFineSettimana) {
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "DELETE FROM CODICE WHERE VALIDITÀ = FALSE AND DATASCADENZA BETWEEN ? AND ? ;";

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
    
    public static void svuotaVendutiSettimana(LocalDate dataInizioSettimana, LocalDate dataFineSettimana) {
        
        try {
                Connection conn = DBManager.getConnection();

                String query = "DELETE FROM CODICE " +
                "WHERE DISPONIBILITÀ = FALSE " +
                "AND IDACQUISTO IN ( " +
                "    SELECT IDACQUISTO FROM ACQUISTO " +
                "    WHERE DATAACQUISTO BETWEEN ? AND ? " +
                ")";
                
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
    
    public static void eliminaTuttiICodiciPerApp(String nomeApp)throws DAOException, DBConnectionException{
        try {
 
                Connection conn = DBManager.getConnection();
 
                String query = "DELETE FROM CODICE WHERE NOMEAPPLICAZIONE = ?;";
 
                try {
 
                    PreparedStatement stmt = conn.prepareStatement(query);
 
                    stmt.setString(1, nomeApp);
 
                    int righeEliminate = stmt.executeUpdate();
                   
                    if(righeEliminate > 0){
                        System.out.println("Eliminazione andata a buon fine");
                    }
                   
                }catch(SQLException e) {
 
                    throw new DAOException("Errore deleteCodice");
 
                } finally {
 
                    DBManager.closeConnection();
 
                }
 
 
        }catch(SQLException e) {
 
                throw new DBConnectionException("Errore connessione database");
 
        }
    }
    
     public static void inserisciCodici() {
        try {
            Connection conn = DBManager.getConnection();

            String query = "INSERT IGNORE INTO Codice (IDCodice, DataScadenza, Validità, Disponibilità, Prezzo, NomeApplicazione, IDAcquisto) VALUES "
                + "('CD001', '2026-01-01', 1, 1, 5.99, 'FitApp', NULL), "
                + "('CD002', '2025-12-15', 1, 1, 5.99, 'FitApp', NULL), "
                + "('CD003', '2025-06-30', 1, 1, 5.99, 'FitApp', NULL), "
                + "('CD004', '2024-12-31', 0, 1, 5.99, 'FitApp', NULL), "
                + "('CD005', '2023-11-01', 0, 1, 5.99, 'FitApp', NULL), "
                + "('CD006', '2026-05-01', 1, 1, 4.49, 'EduKids', NULL), "
                + "('CD007', '2025-08-20', 1, 1, 4.49, 'EduKids', NULL), "
                + "('CD008', '2024-09-01', 0, 1, 4.49, 'EduKids', NULL), "
                + "('CD009', '2026-01-01', 1, 1, 4.49, 'EduKids', NULL), "
                + "('CD010', '2023-05-01', 0, 1, 4.49, 'EduKids', NULL), "
                + "('CD011', '2025-09-01', 1, 1, 6.99, 'SpeedRun', NULL), "
                + "('CD012', '2025-10-10', 1, 1, 6.99, 'SpeedRun', NULL), "
                + "('CD013', '2023-12-01', 0, 1, 6.99, 'SpeedRun', NULL), "
                + "('CD014', '2026-06-06', 1, 1, 6.99, 'SpeedRun', NULL), "
                + "('CD015', '2024-01-01', 0, 1, 6.99, 'SpeedRun', NULL), "
                + "('CD016', '2026-12-01', 1, 1, 9.99, 'MoneySafe', NULL), "
                + "('CD017', '2024-05-01', 0, 1, 9.99, 'MoneySafe', NULL), "
                + "('CD018', '2025-12-31', 1, 1, 9.99, 'MoneySafe', NULL), "
                + "('CD019', '2023-03-15', 0, 1, 9.99, 'MoneySafe', NULL), "
                + "('CD020', '2025-07-20', 1, 1, 9.99, 'MoneySafe', NULL), "
                + "('CD021', '2025-09-15', 1, 1, 7.49, 'NotePro', NULL), "
                + "('CD022', '2025-05-10', 1, 1, 7.49, 'NotePro', NULL), "
                + "('CD023', '2023-08-01', 0, 1, 7.49, 'NotePro', NULL), "
                + "('CD024', '2026-03-01', 1, 1, 7.49, 'NotePro', NULL), "
                + "('CD025', '2024-02-01', 0, 1, 7.49, 'NotePro', NULL), "
                + "('CD026', '2025-06-01', 1, 1, 3.99, 'Foodie', NULL), "
                + "('CD027', '2025-12-25', 1, 1, 3.99, 'Foodie', NULL), "
                + "('CD028', '2026-04-30', 1, 1, 3.99, 'Foodie', NULL), "
                + "('CD029', '2024-10-01', 0, 1, 3.99, 'Foodie', NULL), "
                + "('CD030', '2023-12-12', 0, 1, 3.99, 'Foodie', NULL), "
                + "('CD031', '2026-01-01', 1, 1, 8.50, 'TravelGo', NULL), "
                + "('CD032', '2025-11-11', 1, 1, 8.50, 'TravelGo', NULL), "
                + "('CD033', '2025-06-15', 1, 1, 8.50, 'TravelGo', NULL), "
                + "('CD034', '2023-07-01', 0, 1, 8.50, 'TravelGo', NULL), "
                + "('CD035', '2024-12-30', 0, 1, 8.50, 'TravelGo', NULL), "
                + "('CD036', '2025-09-30', 1, 1, 6.25, 'PhotoEdit', NULL), "
                + "('CD037', '2025-12-01', 1, 1, 6.25, 'PhotoEdit', NULL), "
                + "('CD038', '2024-05-05', 0, 1, 6.25, 'PhotoEdit', NULL), "
                + "('CD039', '2026-06-30', 1, 1, 6.25, 'PhotoEdit', NULL), "
                + "('CD040', '2023-06-06', 0, 1, 6.25, 'PhotoEdit', NULL), "
                + "('CD041', '2026-02-15', 1, 1, 5.00, 'SleepAid', NULL), "
                + "('CD042', '2025-10-10', 1, 1, 5.00, 'SleepAid', NULL), "
                + "('CD043', '2023-04-04', 0, 1, 5.00, 'SleepAid', NULL), "
                + "('CD044', '2025-08-01', 1, 1, 5.00, 'SleepAid', NULL), "
                + "('CD045', '2024-11-11', 0, 1, 5.00, 'SleepAid', NULL), "
                + "('CD046', '2025-12-01', 1, 1, 6.75, 'LangBoost', NULL), "
                + "('CD047', '2025-06-20', 1, 1, 6.75, 'LangBoost', NULL), "
                + "('CD048', '2024-01-01', 0, 1, 6.75, 'LangBoost', NULL), "
                + "('CD049', '2023-09-15', 0, 1, 6.75, 'LangBoost', NULL), "
                + "('CD050', '2026-09-01', 1, 1, 6.75, 'LangBoost', NULL);";


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
