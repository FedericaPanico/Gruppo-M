package Boundary;

import Database.ApplicazioneDAO;
import Database.CodiceDAO;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoundaryGestoreTest {
    
    private BoundaryGestore boundaryGestore;
    
    public BoundaryGestoreTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        boundaryGestore = new BoundaryGestore();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testAggiungiCodice3() throws Exception {
        
        System.out.println("\naggiungiCodice");
        
        ApplicazioneDAO.inserisciApplicazioni();
        String Nome = "FitApp";
        String IdCodice = "LC324";
        Date DataDiScadenza = new Date(2027,06,05);
        float prezzo = -3F;
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryGestore.validaPrezzo(prezzo);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("ERRORE: Il prezzo non può essere <=0"));
        } finally {
            System.setErr(originalErr); 
        }
    }
   
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice4() throws Exception {
        System.out.println("\naggiungiCodice");
        
        ApplicazioneDAO.inserisciApplicazioni();
        
        String Nome = "FitApp";
        String IdCodice = "LC324";
        String dataString = "AAAAAAAAAAAA";
        float prezzo = 7.12F;
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryGestore.validaData1(dataString);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("ERRORE: Formato data non valido."));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice5() throws Exception {
        System.out.println("\naggiungiCodice");
        
        ApplicazioneDAO.inserisciApplicazioni();
        
        String Nome = "FitApp";
        String IdCodice = "LC324";
        Date DataDiScadenza = java.sql.Date.valueOf(LocalDate.of(2022, 6, 5));
        float prezzo = 7.12F;
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryGestore.validaData2(DataDiScadenza);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("ERRORE: Data scaduta."));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice6() throws Exception {
        System.out.println("\naggiungiCodice");
        
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        
        String Nome = "FitApp";
        String IdCodice = "CD001";
        Date DataDiScadenza = new Date(2027,06,05);
        float prezzo = 7.12F;
        
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryGestore.validaIDCodice3(IdCodice, Nome);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("ERRORE: L'ID è già presente"));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice7() throws Exception {
        System.out.println("\naggiungiCodice");
        
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        
        String Nome = "FitApp";
        String IdCodice = "MOLPIHIK90909";
        Date DataDiScadenza = new Date(2027,06,05);
        float prezzo = 7.12F;
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryGestore.validaIDCodice(IdCodice);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("ERRORE: L'ID può essere massimo di 10 caratteri, reinserisci l'IdCodice"));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testAggiungiCodice8() throws Exception {
        System.out.println("\naggiungiCodice");
        
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        
        String Nome = "FitApp";
        String IdCodice = "";
        Date DataDiScadenza = new Date(2027,06,05);
        float prezzo = 7.12F;
        boolean inputValido = false;

        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryGestore.validaIDCodice2(IdCodice);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("ERRORE: L'ID non può essere vuoto"));
        } finally {
            System.setErr(originalErr); 
        }
    }
}
    
 
