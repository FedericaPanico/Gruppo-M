package Boundary;

import Database.ApplicazioneDAO;
import Database.CodiceDAO;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BoundaryClienteTest {
    
    private BoundaryCliente boundaryCliente;
    
    public BoundaryClienteTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        boundaryCliente = new BoundaryCliente();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testAcquistaCodice2() throws Exception {
        ApplicazioneDAO.inserisciApplicazioni();
        CodiceDAO.inserisciCodici();
        System.out.println("acquistaCodice");
        String nomeApp = "qwertyuiopqwertyuiopqwertyuiopqwerty";
        int numCodici = 2;
        String email = "cristinapecoraro2003@gmail.com";
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryCliente.validaNomeApp(nomeApp);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Il nome dell'applicazione è troppo lungo: inserisci meno di 30 caratteri"));
        } finally {
            System.setErr(originalErr); 
        }
        
    }
    
    @org.junit.jupiter.api.Test
    public void testAcquistaCodice3() {
        System.out.println("acquistaCodice");
        String nomeApp = "FitApp";
        int numCodici = 0;
        String email = "cristinapecoraro2003@gmail.com";
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryCliente.validaNumCodici(numCodici);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Numero di codici non valido: non può essere nullo o negativo"));
        } finally {
            System.setErr(originalErr); 
        }
    }
 
    @org.junit.jupiter.api.Test
    public void testAcquistaCodice4() throws IllegalArgumentException {
        System.out.println("acquistaCodice");
        String nomeApp = "FitApp";
        int numCodici = 2;
        String email = "cristinapecoraro2003gmail.com";
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryCliente.validaEmail(email);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Email non valida: deve contenere una @ e almeno un . "));
        } finally {
            System.setErr(originalErr); 
        }
    }
 
    @org.junit.jupiter.api.Test
    public void testAcquistaCodice5() {
        System.out.println("acquistaCodice");
        String nomeApp = "FitApp";
        int numCodici = 2;
        String email = "cristinapecoraro2003@gmailcom";
        boolean inputValido = false;
        
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        
        try {
            inputValido = boundaryCliente.validaEmail(email);
            System.out.println(errStream.toString());
            assertTrue(errStream.toString().contains("Email non valida: deve contenere una @ e almeno un . "));
        } finally {
            System.setErr(originalErr); 
        }
    }
    
}
