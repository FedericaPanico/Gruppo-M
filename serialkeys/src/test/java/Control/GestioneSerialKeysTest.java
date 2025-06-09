package Control;

import Boundary.BoundaryCliente;
import DTO.DettagliAcquisto;
import Entity.EntityAcquisto;
import Entity.EntityCodice;
import Database.*;
import Entity.EntityApplicazione;
import exception.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GestioneSerialKeysTest {
    
    private GestioneSerialKeys control;
    
    public GestioneSerialKeysTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        control = new GestioneSerialKeys();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @org.junit.jupiter.api.Test
    public void testInviaReport() throws Exception {
    
        ApplicazioneDAO.inserisciApplicazioni();
        GestoreDAO.inserisciGestore();
        CodiceDAO.inserisciCodici();
        String nomeApp = "FitApp";
        int numCodici = 1;
        String email = "federica.panico31@gmail.com";
        DettagliAcquisto risultato = control.acquistaCodice(nomeApp, numCodici, email);
        CodiceDAO.updateListaCodiciAcquistati(risultato.getAcquistoCorrente(), risultato.getCodiciInAcquisto());
        
        System.out.println("\ninvia report");
        System.out.println("Report inviato correttamente per email al Gestore del sistema!");

        control.inviaReport();

        File report = new File("report_settimanale.txt");
        assertTrue(report.exists(), "Il report non è stato generato ed inviato correttamente.");

    }
    
    @org.junit.jupiter.api.Test
    public void testInviaReport_SenzaEmailGestore() throws Exception {
        
        GestoreDAO.svuotaGestore();
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        String nomeApp = "FitApp";
        int numCodici = 1;
        String email = "federica.panico31@gmail.com";
        DettagliAcquisto risultato = control.acquistaCodice(nomeApp, numCodici, email);
        CodiceDAO.updateListaCodiciAcquistati(risultato.getAcquistoCorrente(), risultato.getCodiciInAcquisto());
        
        System.out.println("\ninvia report");
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));

        try {
            control.inviaReport();
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("L'email del Gestore non è registrata."));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testInviaReport_SenzaApplicazioni() throws Exception {
        
        ApplicazioneDAO.svuotaApplicazioni();
        GestoreDAO.inserisciGestore();
        
        System.out.println("\ninvia report");
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            control.inviaReport();
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Non ci sono applicazioni registrate."));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testGeneraReport_SenzaCodiciInvalidiVenduti() throws Exception {
        
        GestoreDAO.inserisciGestore();
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        CodiceDAO.svuotaInvalidiSettimana(LocalDate.now().minusDays(7), LocalDate.now());
        CodiceDAO.svuotaVendutiSettimana(LocalDate.now().minusDays(7), LocalDate.now());
        AcquistoDAO.svuotaAcquistiSettimana(LocalDate.now().minusDays(7), LocalDate.now());
        
        System.out.println("\ninvia report");
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            control.inviaReport();
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Non ci sono codici venduti o scaduti in questa settimana."));
        } finally {
            System.setErr(originalErr); 
        }
    }
    

    @org.junit.jupiter.api.Test
    public void testAcquistaCodice1() throws Exception {
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        System.out.println("\nacquistaCodice");
        String nomeApp = "FitApp";
        int numCodici = 2;
        String email = "cristinapecoraro2003@gmail.com";
        DettagliAcquisto result = control.acquistaCodice(nomeApp, numCodici, email);
        assertNotNull(result, "Test fallito: la funzione restituisce null");
    }
 
 
    @org.junit.jupiter.api.Test
    public void testAcquistaCodice6() throws DAOException, DBConnectionException, OperationException {
        Date data = new Date(2026, 01, 01);
        EntityCodice eC = new EntityCodice("CD00675", data, true, true, 3.1F, "FitApp");
        CodiceDAO.eliminaTuttiICodiciPerApp("FitApp");
        CodiceDAO.insertCodice(eC);
        System.out.println("\nacquistaCodice");
        String nomeApp = "FitApp";
        int numCodici = 2;
        String email = "cristinapecoraro2003@gmail.com";
 
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            DettagliAcquisto risultato = control.acquistaCodice(nomeApp, numCodici, email);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Numero codici dell'applicazione scelta non disponibili, scegli un numero inferiore"));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    
    
    @org.junit.jupiter.api.Test
    public void testEliminaCodici_senzaApplicazioni() throws Exception {
        ApplicazioneDAO.svuotaApplicazioni();
        
        System.out.println("\nelimina codici invalidi");
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            control.eliminaCodici();
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Non possono essere eliminati codici se non sono presenti applicazioni che ne contengono."));
        } finally {
            System.setErr(originalErr);
        }
    }

    
    @org.junit.jupiter.api.Test
    public void testEliminaCodici_senzaCodici() throws Exception {
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.svuotaCodici();
        
        System.out.println("\nelimina codici invalidi");
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            control.eliminaCodici();
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Non sono presenti codici all'interno del database."));
        } finally {
            System.setErr(originalErr);
        }
         
    }
    
        
    @org.junit.jupiter.api.Test
    public void testEliminaCodici_senzaCodiciInvalidi() throws Exception {
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        CodiceDAO.svuotaCodiciInvalidi();    
        
        System.out.println("\nelimina codici invalidi");
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            control.eliminaCodici();
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Non sono presenti codici invalidi da poter eliminare all'interno del database."));
        } finally {
            System.setErr(originalErr);
        }
    } 
    
    @org.junit.jupiter.api.Test
    public void testEliminaCodici() throws Exception {
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        
        System.out.println("\nelimina codici invalidi");
        control.eliminaCodici();
        
        List<EntityCodice> codiciInvalidiDopo = CodiceDAO.readCodiciInvalidi();
        assertTrue(codiciInvalidiDopo.isEmpty(), "Errore: ci sono ancora codici invalidi dopo l'eliminazione.");
    }
    
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice1() throws Exception {
        
        ApplicazioneDAO.inserisciApplicazioni();
        
        System.out.println("\naggiungiCodice");
        
        String Nome = "FitApp";
        String IdCodice = "LC324";
        Date DataDiScadenza = new Date(2027,06,05);
        float prezzo = 7.12F;
        control.aggiungiCodice(Nome, IdCodice, DataDiScadenza, prezzo);
        
        EntityCodice codiceInserito = CodiceDAO.readCodice(IdCodice);
        
        assertNotNull(codiceInserito);
        assertEquals(IdCodice, codiceInserito.getIdCodice());
    }
    
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice2() throws Exception {
        
        ApplicazioneDAO.inserisciApplicazioni();
        
        System.out.println("\naggiungiCodice");
        
        String Nome = "Word";
        String IdCodice = "LC324";
        Date DataDiScadenza = new Date(2027,06,05);
        float prezzo = 31.4F;
        
        boolean risultato = control.aggiungiCodice(Nome, IdCodice, DataDiScadenza, prezzo);
        
        assertFalse(risultato, "Test fallito: l'applicazione non esiste");
        
    }
   
}
