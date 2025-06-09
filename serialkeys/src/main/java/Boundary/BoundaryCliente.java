package Boundary;
 
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.sql.Date;
import Control.GestioneSerialKeys;
import DTO.DettagliAcquisto;
import exception.*;
import java.time.LocalDate;
 
public class BoundaryCliente {
 
    static Scanner scan = new Scanner(System.in);
    static GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
    
    public static void main(String[] args) throws DAOException, DBConnectionException {        
       
        int scelta = 0;
        
        do{
        System.out.println("\nBenvenuto Cliente! Scegli quale operazione eseguire!");
        System.out.println("1. Registrazione");
        System.out.println("2. Cerca codici di sblocco per categoria");
        System.out.println("3. Visualizza codici di sblocco");
        System.out.println("4. Acquista codici di sblocco");
        System.out.println("5. Esci");
        System.out.print("Scelta: ");
 
        scelta = scan.nextInt();
 
        switch(scelta){
            case 1:
                System.out.println("Operazione non disponibile\n");
                break;
            case 2:
                System.out.println("Operazione non disponibile\n");
                break;
            case 3:
                System.out.println("Operazione non disponibile\n");
                break;
            case 4:
                try{
                    acquistaCodici();
                }catch (RuntimeException e){
                    System.out.println("Errore durante l'acquisto: " + e.getMessage());
                }
                break;

            case 5:
                System.out.println("Arrivederci!\n");
                break;
            default:
                System.out.println("Input non valido! Scegli una delle opzioni proposte");
            }
        }while(scelta!=5);
    }
    
    public static boolean validaNomeApp(String nomeApp){
        boolean inputValido = false;
        try{
            if(nomeApp.length() > 30){
                inputValido = false;
                throw new IllegalArgumentException("Il nome dell'applicazione è troppo lungo: inserisci meno di 30 caratteri");
            }else{
                inputValido = true;
            }
        }catch(IllegalArgumentException e){
            System.err.println("Errore nell'acquisto: " + e.getMessage());
            inputValido = false;
        }
        return inputValido;
    }
    
    public static boolean validaNumCodici(int numCodici){
        boolean inputValido = false;
        try{
            if(numCodici <= 0){
                inputValido = false;
                throw new IllegalArgumentException("Numero di codici non valido: non può essere nullo o negativo");
            }else{
                inputValido = true;
            }
        }catch(IllegalArgumentException e){
            System.err.println("Errore nell'acquisto: " + e.getMessage());
            inputValido = false;
        }
        return inputValido;
    }
    
    public static boolean validaEmail(String email){
        boolean inputValido = false;
        try{
            if (email.contains("@") && email.contains(".")) {
                inputValido = true;
            }else{
                inputValido = false;
                throw new IllegalArgumentException("Email non valida: deve contenere una @ e almeno un . ");
            }
        }catch(IllegalArgumentException e){
            System.err.println("Errore nell'acquisto: " + e.getMessage());
            inputValido = false;
        }
        return inputValido;
    }
    
    
    public static void acquistaCodici() throws DAOException, DBConnectionException {
 
        Scanner scan = new Scanner(System.in);
        DettagliAcquisto risultato = null;
        GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
        boolean inputValido = false;
        String nomeApp = null;
        int numCodici = 0;
        String email = null;

        System.out.println("Ecco la lista delle applicazioni: ");
        gestioneSerialKeys.stampaApp();
        do{
            System.out.println("Inserisci il nome dell'applicazione di cui vuoi acquistare i codici: ");
            nomeApp = scan.nextLine();
            inputValido = validaNomeApp(nomeApp);    
        }while(!inputValido);
        
        System.out.println("Questo è il numero massimo di codici che puoi acquistare per questa applicazione: ");
        int numCodiciMax = gestioneSerialKeys.stampaNumCodici(nomeApp);
        System.out.println(numCodiciMax);

        do{
            System.out.println("Inserisci il numero di codici da acquistare");
            numCodici= scan.nextInt();
            scan.nextLine();
            inputValido = validaNumCodici(numCodici);
        }while(!inputValido);
        
        do{
            System.out.println("Inserisci l'email su cui ricevere i codici: ");
            email = scan.nextLine();
            inputValido = validaEmail(email);
        }while (!inputValido);

        try{
            
            try{
                risultato = gestioneSerialKeys.acquistaCodice(nomeApp, numCodici, email);
            }catch(DBConnectionException dbEx) {
            //caso di problemi di connessione al DB
                throw new OperationException("DBCONNECTIONEXCEPTION");
            }catch(DAOException ex) {
                throw new OperationException("DAOEXCEPTION");
            }catch(RuntimeException e){
                throw new RuntimeException("RUNTIMEEXCEPTION");
            }
           
            if(risultato == null){
                System.out.println("Acquisto annullato o non riuscito");
                return;
            }
 
            System.out.println("Prezzo: " + risultato.getPrezzoTot() + " euro");
            System.out.println("Confermi l'acquisto? Digita 'si' per continuare, qualsiasi altro carattere per annullare");
            String conferma = scan.nextLine();
 
            if (conferma.equalsIgnoreCase("si")){
 
                gestioneSerialKeys.updateAcquisto(risultato.getAcquistoCorrente(), risultato.getCodiciInAcquisto());
 
                if(risultato.getCartaRegistrata() != null){
                    System.out.println("Pagamento in corso con la carta registrata");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("Pagamento effettuato!");
                    gestioneSerialKeys.inviaCodice(risultato.getAcquistoCorrente().getCodiciAcquistati(), email, risultato.getPrezzoTot());
                }else {
                   
                    do{
                        System.out.println("Inserire il numero di carta:");
 
                        String numeroCarta = scan.nextLine();
                        inputValido = false;
                        try {
                        Long.parseLong(numeroCarta);
 
                            if (numeroCarta.length() == 16) {
                                inputValido = true;
                            } else {
                                System.out.println("Errore inserimento numero carta, deve essere di 16 cifre");
                            }
 
                        } catch (NumberFormatException e) {
                            System.out.println("Errore inserimento numero carta, deve contenere solo numeri");
                        }
                    }while(!inputValido);
 
                    do{
                        inputValido = false;
                        System.out.println("Inserisci il nome del proprietario");
                        String nome = scan.nextLine();
                       
                        try{
                            if(!nome.matches("\\p{L}+")){
                                throw new IllegalArgumentException("La stringa può contenere solo lettere");
                            }else{
                                inputValido = true;
                            }
                        }catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                    }while(!inputValido);
                   
                    do{
                        inputValido = false;
                        System.out.println("Inserisci il cognome del proprietario");
                        String cognome = scan.nextLine();
                       
                        try{
                            if(!cognome.matches("\\p{L}+")){
                                throw new IllegalArgumentException("La stringa può contenere solo lettere");
                            }else{
                                inputValido = true;
                            }
                        }catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                    }while(!inputValido);
 
                    do{
                        inputValido = false;
                        System.out.println("Inserisci la data di scadenza (aaaa-MM-gg)");
                        String dataTemp = scan.nextLine();
                       
                        try {
                            Date data = Date.valueOf(dataTemp);
                            if(data.before(Date.valueOf(LocalDate.now()))){
                                inputValido = false;
                                System.out.println("Data scaduta: riprova");
                            }else{
                                inputValido = true;
                            }
                        } catch (IllegalArgumentException iE) {
                            System.out.println("Errore nell'acquisizione della data, riprova");
                            inputValido = false;
                        }
                    }while(!inputValido);
           
                    do{
                        inputValido = false;
                        System.out.println("Inserisci il codice CVC");
                        String CVC = scan.nextLine();
                        try {
                            Integer.parseInt(CVC);
 
                            if (CVC.length() == 3) {
                                inputValido = true;
                            } else {
                                System.out.println("Errore inserimento codice CVC, deve essere di 3 cifre");
                            }
 
                        } catch (NumberFormatException e) {
                            System.out.println("Errore inserimento codice CVC, deve contenere solo numeri");
                        }
                    }while(!inputValido);
 
                    System.out.println("Pagamento in corso");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("Pagamento effettuato!");
                    gestioneSerialKeys.inviaCodice(risultato.getAcquistoCorrente().getCodiciAcquistati(), email, risultato.getPrezzoTot());
                }
            }else {
                gestioneSerialKeys.annullaAcquisto(risultato.getAcquistoCorrente());
                System.out.println("Acquisto annullato");
                return;
            }
 
        }catch (OperationException oE){
            System.out.println(oE.getMessage());
            System.out.println("Riprovare\n");
        }catch (Exception e) {
            System.out.println("Unexpected exception, riprovare");
            e.printStackTrace();
            System.out.println();
        }
    }
}
 
 
 
