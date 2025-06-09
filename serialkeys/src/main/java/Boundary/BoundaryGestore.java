package Boundary;

import Control.GestioneSerialKeys;
import Database.CodiceDAO;
import Database.GestoreDAO;
import Service.ServizioEmail;
import exception.*;
import Entity.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Scanner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryGestore {

    static Scanner scanner = new Scanner(System.in);

    static GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
    
    public static boolean invioRichiestaMail() throws Exception {
        System.out.print("Il report contenente il numero di codici venduti e scaduti per ogni applicazione a partire da lunedì è disponibile."
                + " Vuole riceverlo? (si/no): ");
        String risposta = scanner.nextLine().trim().toLowerCase();

        while (!risposta.equals("si") && !risposta.equals("no")) {
            System.out.println("Risposta non valida. Inserisci 'si' o 'no': ");
            risposta = scanner.nextLine().trim().toLowerCase();
        }
        
        if(risposta.equals("si"))
            try{
                boolean inviato = gestioneSerialKeys.inviaReport();
                if (!inviato) {
                    return false;
                }
                return inviato;
            }catch(Exception e) {
                System.err.println("Errore durante l'invio del report: " + e.getMessage());
                return false;
            }
        else {
            return false;
        }
          
    }
    
    public static boolean confermaRicezioneMail() {
        System.out.print("L'email contenente il report settimanale è stata ricevuta? (si/no): ");
        String risposta = scanner.nextLine().trim().toLowerCase();

        while (!risposta.equals("si") && !risposta.equals("no")) {
            System.out.println("Risposta non valida. Inserisci 'si' o 'no': ");
            risposta = scanner.nextLine().trim().toLowerCase();
        }

        return risposta.equals("si");
    }
    
    public static boolean riprova() { 

        boolean confermato = false;
        
        for(int i = 0; i < 3; i++) {
            
            try{
                gestioneSerialKeys.inviaReport();
            }catch(Exception e) {
                System.err.println("Errore durante l'invio del report: " + e.getMessage());
            }
            
            confermato = confermaRicezioneMail();

            if (confermato) {
                break;
            } 
        }
        
        return confermato;
    }
    
    public static void richiediReport() throws OperationException, Exception {
        
        GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
      
        if(gestioneSerialKeys.generaReport(LocalDate.now().minusDays(7), LocalDate.now())!=null) {
            
            boolean noException = invioRichiestaMail();
            
            if(noException == true) {

                boolean confermato = confermaRicezioneMail();

                if (confermato) {
                    System.out.println("AVVISO: Il report settimanale è stato ricevuto dal Gestore!");
                } else {
                    System.out.println("ERRORE: Il report settimanale non è stato ricevuto dal Gestore.");
                    confermato = riprova();

                    if(!confermato) {

                        System.out.println("WARNING: Mancato invio del report settimanale al Gestore.");
                        gestioneSerialKeys.inviaEmailWarning();
                    }
                }
            }
        } 
    }
   
    
    public static void main(String[] args) throws DAOException, DBConnectionException, OperationException, Exception {
        
        Scanner scan = new Scanner(System.in);
        GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
        
        int scelta = 0;
        
        do{
        System.out.println("\nBentornato Gestore! Scegli quale operazione eseguire!");
        System.out.println("1. Aggiungi codice");
        System.out.println("2. Elimina codici invalidi");
        System.out.println("3. Richiedi report settimanale");
        System.out.println("4. Esci");
        System.out.print("Scelta: ");
 
        scelta = scan.nextInt();
 
        switch(scelta){
            case 1:
                aggiungiCodice();
                break;
            case 2:
                try{
                    eliminaCodici();
                }catch (OperationException e){
                    System.out.println("Errore eliminazione: " + e.getMessage());
                }
                break;
            case 3:
                richiediReport();
                break;
            case 4:
                System.out.println("Arrivederci!\n");
                break;
            default:
                System.out.println("Input non valido! Scegli una delle opzioni proposte");
            }
        }while(scelta!=4);
        
    }
    
    public static boolean validaPrezzo(float prezzo) {
        boolean inputValido = false;
        try{
            if(prezzo<=0){
                inputValido = false;
                throw new IllegalArgumentException("ERRORE: Il prezzo non può essere <=0");
            }else{
                inputValido = true;
            }
        }catch (IllegalArgumentException e){
            inputValido = false;
            System.err.println("Errore nell'inserimento: " + e.getMessage());
        }
        
        return inputValido;
    }
    
    public static boolean validaIDCodice(String IdCodice){
        boolean inputValido = false;
        try{
            if (IdCodice.length()>10) {
                inputValido= false;
                    throw new IllegalArgumentException("ERRORE: L'ID può essere massimo di 10 caratteri, reinserisci l'IdCodice");
                }
            else{
                inputValido = true;
            }
            }catch (IllegalArgumentException e){
            inputValido = false;
            System.err.println("Errore nell'inserimento: " + e.getMessage());
        }
        
        return inputValido;
        }
    public static boolean validaIDCodice2(String IdCodice) throws DAOException, DBConnectionException{
        boolean inputValido = false;
        try{
            
            if(IdCodice.isEmpty() == true){
                inputValido = false;
                throw new IllegalArgumentException("ERRORE: L'ID non può essere vuoto");
            }else{
                inputValido = true;
            }

        }catch (IllegalArgumentException e){
            inputValido = false;
            System.err.println("Errore nell'inserimento: " + e.getMessage());
        }
        
        return inputValido;
    }
    public static boolean validaIDCodice3(String IdCodice, String Nome) throws DAOException, DBConnectionException{
        boolean valido2= false;
        boolean inputValido = false;
        try{
            valido2 = gestioneSerialKeys.controllaIDCodice(Nome, IdCodice);
            if(valido2==true){
                inputValido = false;
                throw new IllegalArgumentException("ERRORE: L'ID è già presente");
            }else{
                inputValido = true;
            }
            
        }catch (IllegalArgumentException e){
            inputValido = false;
            System.err.println("Errore nell'inserimento: " + e.getMessage());
        }
        
        return inputValido;
    }
    
    public static boolean validaData1(String input) throws DAOException, DBConnectionException, ParseException{
        boolean inputValido = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date DataDiScadenza = sdf.parse(input);
            inputValido = true;
        }catch (ParseException e) {
            inputValido = false;
            System.err.println("ERRORE: Formato data non valido.");
        }
        return inputValido;
    }
    
    public static boolean validaData2(Date DataDiScadenza) throws DAOException, DBConnectionException{
        boolean inputValido = false;
        try {
            if(DataDiScadenza.before(java.sql.Date.valueOf(LocalDate.now()))) {
                inputValido = false;
                throw new IllegalArgumentException("Il codice è scaduto.");
            } else {
                inputValido = true;
            }
        }catch (IllegalArgumentException e) {
            inputValido = false;
            System.err.println("ERRORE: Data scaduta.");
        }
        return inputValido;
    }
    

    public static void aggiungiCodice() throws DAOException, DBConnectionException, OperationException, ParseException{
        Date DataDiScadenza = null;
        int numcodici = 0;
        String IdCodice = null;
        float prezzo = 0;
        boolean valido = false;
        boolean valido1 = false;
        boolean valido2 = false;
        boolean risultato = false;
        GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ecco la lista di applicazioni per cui puoi inserire dei codici:");
        GestioneSerialKeys.stampaApp();
        System.out.println("Inserisci Applicazione:");
        String Nome = scanner.nextLine();
        
        do{
            System.out.println("Quanti codici vuoi inserire? ");
            numcodici = scanner.nextInt();
            scanner.nextLine();
            
            if (numcodici<=0){
               System.out.println("Il numero di codici da inserire non può essere <=0, reinseriscilo");
            }
        }while (numcodici <=0);
        
        do{
            System.out.println("Inserisci il prezzo (nel caso di un numero decimale scriverlo con la ,):");
            prezzo = scanner.nextFloat();
            scanner.nextLine();
            valido = validaPrezzo(prezzo);
        }while(!valido);
        
        for (int i=0; i<numcodici; i++){
           
            do{
                System.out.println("Inserisci ID Codice: ");
                IdCodice = scanner.nextLine().trim();
                valido = validaIDCodice(IdCodice);
                valido1 = validaIDCodice2(IdCodice);
                valido2 = validaIDCodice3(IdCodice, Nome);
            }while(!valido || !valido1 || !valido2);       
        
            
            do{
                System.out.println("Inserisci una data (formato aaaa-mm-gg): ");
                String input = scanner.nextLine();
                valido1 = validaData1(input);
                if(valido1 == true){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    DataDiScadenza = sdf.parse(input);  
                    valido = validaData2(DataDiScadenza);
                }
            }while(!valido || !valido1);
        }
        
        gestioneSerialKeys.aggiungiCodice(Nome, IdCodice, DataDiScadenza, prezzo);
    }

    public static void eliminaCodici() throws DAOException, DBConnectionException, OperationException{ 
        Scanner scan = new Scanner(System.in);
        GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
        System.out.println("Sei sicuro di voler eliminare tutti i codici invalidi di ciascuna applicazione?");
        String conferma = scan.nextLine();
        if (conferma.equalsIgnoreCase("si")) {
            try{
                gestioneSerialKeys.eliminaCodici();
            }catch (DBConnectionException dbEx){
                throw new OperationException("DBConnectionException");
            }catch(DAOException ex){
                throw new OperationException("DAOException");
            }catch(OperationException op){
                throw new OperationException("OperationException");
            }
        }
    }
}



