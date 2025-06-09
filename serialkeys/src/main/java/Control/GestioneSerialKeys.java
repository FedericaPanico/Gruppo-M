package Control;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Database.*;
import Entity.*;
import exception.*;
import exception.DAOException;
import DTO.DettagliAcquisto;
import Service.ServizioEmail;

import java.time.LocalDate;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestioneSerialKeys {

    private static GestioneSerialKeys gS = null;

    public static GestioneSerialKeys getInstance() { 
        if (gS == null) 
                gS = new GestioneSerialKeys(); 

        return gS; 
    }
    
    public static boolean aggiungiCodice(String Nome, String IdCodice, java.util.Date DataDiScadenza, float prezzo) throws OperationException, DAOException, DBConnectionException{
         
        EntityApplicazione eApp = ApplicazioneDAO.readApplicazione(Nome);
        
        if(eApp == null) {
            //throw new OperationException("Applicazione non trovata");
            System.out.println("Applicazione non trovata");
            return false;
        }else{
            EntityCodice codice = new EntityCodice (IdCodice, DataDiScadenza, true, true, prezzo, Nome);
            CodiceDAO.insertCodice(codice);
            return true;
        }
    }
    
    public static boolean controllaIDCodice(String Nome, String IdCodice) throws DAOException, DBConnectionException {
        ArrayList<EntityCodice> codiciEsistenti = CodiceDAO.readCodici(Nome);
        boolean IdPresente = false;
        for (int j=0; j< codiciEsistenti.size(); j++) {
            if (codiciEsistenti.get(j).getIdCodice().equals(IdCodice)){
                IdPresente = true;
                break;
            }
            if (IdPresente==true) 
                System.out.println("L'ID è già presente.");
        }
        return IdPresente;
    }
    
     public static void eliminaCodici() throws DAOException, DBConnectionException, OperationException{
        ArrayList<EntityCodice> eLI = CodiceDAO.readCodiciInvalidi();
        ArrayList<EntityApplicazione> listaApplicazioni = ApplicazioneDAO.readListaApplicazioni();
        ArrayList<EntityCodice> listaCodiciCompleta = new ArrayList<>();
 
        for (int i = 0; i<listaApplicazioni.size(); i++){
            ArrayList<EntityCodice> listaCodici = CodiceDAO.readCodici(listaApplicazioni.get(i).getNome());
        for(int j = 0; j < listaCodici.size(); j++)
            listaCodiciCompleta.add(listaCodici.get(j));
        }

        try {
            if(listaApplicazioni.isEmpty()) {
                throw new OperationException("Non possono essere eliminati codici se non sono presenti applicazioni che ne contengono.");
            } else if (listaCodiciCompleta.isEmpty()) {
                throw new OperationException("Non sono presenti codici all'interno del database.");
            } else if (eLI.isEmpty()) {
                throw new OperationException("Non sono presenti codici invalidi da poter eliminare all'interno del database.");
            } else{
                // Mostra all’utente i codici invalidi prima della rimozione
                System.out.println("Verranno eliminati i seguenti codici:");
                for (EntityCodice eC : eLI) 
                    System.out.println("- " + eC.getIdCodice());

                // Elimina i codici invalidi
                for (EntityCodice eC : eLI) 
                    CodiceDAO.deleteCodice(eC);
                System.out.println("Eliminazione andata a buon fine");
            } 
        }catch (OperationException e) {
            System.err.println("Errore durante l'eliminazione dei codici invalidi: " + e.getMessage());
        }
    }
                  
    public File generaReport(LocalDate dataInizioSettimana, LocalDate dataFineSettimana) throws OperationException {

        ArrayList<EntityApplicazione> listaApplicazioni = new ArrayList<>();
        int numCodiciVenduti = 0;
        int numCodiciScaduti = 0;
        int numCodiciVendutiTOT = 0;
        int numCodiciScadutiTOT = 0;
        
        try {
            File reportFile = new File("report_settimanale.txt");

            try (FileWriter writer = new FileWriter(reportFile)) {

                listaApplicazioni = ApplicazioneDAO.readListaApplicazioni();
                
                if (!listaApplicazioni.isEmpty()) {
                    writer.write("Report settimanale\n");
                    writer.write("Da: " + dataInizioSettimana + "\n");
                    writer.write("A: " + dataFineSettimana + "\n");

                    for (EntityApplicazione app : listaApplicazioni) {
                        String nomeApp = app.getNome();

                        numCodiciVenduti = AcquistoDAO.readNumCodiciVenduti(nomeApp, dataInizioSettimana, dataFineSettimana);

                        numCodiciScaduti = CodiceDAO.readNumCodiciScaduti(nomeApp, dataInizioSettimana, dataFineSettimana);
                        
                        numCodiciVendutiTOT+=numCodiciVenduti;
                        numCodiciScadutiTOT+=numCodiciScaduti;

                        // Metodo aggiornaReport():
                        writer.write("[" + nomeApp + "] Codici venduti: " + numCodiciVenduti + " - Codici scaduti: " + numCodiciScaduti + "\n");
                        
                    }
                    if (numCodiciVendutiTOT == 0 && numCodiciScadutiTOT == 0) {
                        reportFile = null;
                        throw new OperationException("Non ci sono codici venduti o scaduti in questa settimana.");
                    } 
                } else{ 
                    reportFile = null;
                    throw new OperationException("Non ci sono applicazioni registrate.");
                }
            
            }catch (OperationException e) {
                System.err.println("Errore durante la generazione del report: " + e.getMessage());
                return null;
            }
            
            return reportFile;
        }catch (DAOException e) {
            throw new OperationException("Errore nell'accesso ai dati.");
        }catch (Exception e) { 
            throw new OperationException("Errore generico durante la generazione del report.");
        }
 
    }
    
    public static LocalDate getInizioSettimana(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static LocalDate getFineSettimana(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public static boolean inviaReport() {

        try {
                
            GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
            ServizioEmail servizioEmail = ServizioEmail.getInstance();

            LocalDate oggi = LocalDate.now();

            LocalDate inizioSettimana = getInizioSettimana(oggi);
            LocalDate fineSettimana = getFineSettimana(oggi);

            File reportSettimanale = gestioneSerialKeys.generaReport(inizioSettimana, fineSettimana);

            try{
                if (reportSettimanale == null || !reportSettimanale.exists()) {
                    throw new OperationException("Il file contenente il report settimanale è nullo o non esiste.");
                } 
            } catch(OperationException e) {
                System.err.println("Errore durante l'invio del report: " + e.getMessage());
            }

            String fromEmail = "SistemaSerialKeys@gmail.com"; 
            String fromPassword = "ascpzyfoeuoofppm";  
 
            String toEmail = null;
            try {
                toEmail = GestoreDAO.readIDGestore();
            } catch (DBConnectionException e) {
                System.err.println("Errore durante il recupero dell'email del gestore.");
            }
            
            String subject = "Report settimanale.";
            String body = "In allegato è disponibile il report settimanale contenente il numero di codici venduti e scaduti per ogni applicazione.";

            try{
                if(toEmail!=null) {
                    servizioEmail.inviaEmail(toEmail, subject, body, reportSettimanale.getAbsolutePath(), reportSettimanale.getName(), fromEmail, fromPassword);
                    return true;
                } else {
                    throw new OperationException("L'email del Gestore non è registrata.");
                } 
            }catch(OperationException e) {
                System.err.println("Errore durante l'invio del report: " + e.getMessage());
                return false;
            }
           
        }catch (DAOException e) {
            System.err.println("Errore nell'accesso ai dati.");
        }
        catch (Exception e) {
            //System.err.println("Errore generico durante l'invio del report.");
        }
        
        return false;
    }
    
    public static void inviaEmailWarning() {

        //gestioneSerialKeys GestioneSerialKeys = gestioneSerialKeys.getInstance();
        ServizioEmail servizioEmail = ServizioEmail.getInstance();

        String fromEmail = "SistemaSerialKeys@gmail.com"; 
        String fromPassword = "ascpzyfoeuoofppm";  

        String toEmail;
            try {
                toEmail = GestoreDAO.readIDGestore();
            } catch (DAOException | DBConnectionException e) {
                System.err.println("Errore durante il recupero dell'email del gestore.");
                return; 
            }

        String subject = "WARNING!";
        String body = "Mancato invio del report settimanale contenente il numero dei codici venduti e scaduti per ogni applicazione.";

        try {
            servizioEmail.inviaEmail(toEmail, subject, body, null, null, fromEmail, fromPassword);
        } catch (DAOException | OperationException e) {
            System.err.println("Errore durante l'invio dell'email.");
        }
            
    }
    
    public static DettagliAcquisto acquistaCodice(String nomeApp, int numCodici, String email) throws OperationException, DAOException, DBConnectionException {
 
        DettagliAcquisto risultato = null;
        EntityApplicazione eAPP = null;
        ArrayList<EntityCodice> eLC = null;
        ArrayList<EntityCodice> codiciAcquistati = new ArrayList<>();
        EntityAcquisto eA = null;
        EntityClienteRegistrato eC = null;
        float prezzo = 0;
        float prezzoTot = 0;
        EntityCartaDiCredito eCDC = null;
 
        try {
            try{
                //Controllo esistenza entità applicazione
                eAPP = ApplicazioneDAO.readApplicazione(nomeApp);

                if(eAPP == null) {
                    throw new OperationException("Applicazione non trovata");
                }

                //controllo esistenza e recupero dei codici validi e disponibili associati all'applicazione
                eLC = CodiceDAO.readCodici(nomeApp);

                if(eLC == null) {
                    throw new OperationException("Lista codici associati all'applicazione non trovati");
                }

                if(numCodici <= eLC.size()){

                    for(int i = 0; i< numCodici; i++){
                        codiciAcquistati.add(eLC.get(i));
                    }
                    //creazione dell'entità acquisto
                    eA = AcquistoDAO.createAcquisto(nomeApp, numCodici, email);

                    //controllo clienteRegistrato
                    eC = ClienteRegistratoDAO.readClienteRegistrato(email);

                    if (eC != null) {
                        System.out.println("Sei un cliente registrato! Avrai uno sconto del 10% sul prezzo totale!");
                        prezzo = calcolaPrezzo(codiciAcquistati);
                        prezzoTot = applicaSconto(prezzo);
                        eCDC = CartaDiCreditoDAO.readCartaDiCredito(eC.getIdClienteRegistrato());

                    }
                    else {
                        System.out.println("La tua mail non risulta registrata");
                        prezzoTot = calcolaPrezzo(codiciAcquistati);
                    }
                }else{
                    throw new RuntimeException("Numero codici dell'applicazione scelta non disponibili, scegli un numero inferiore");
                }
            }catch(RuntimeException e){
                System.err.println("Errore nell'acquisto: " + e.getMessage());
                return risultato;
            }
        }catch(DBConnectionException dbEx) {
            throw new OperationException("Riscontrato problema di connessione al database.");
        }catch(DAOException e) {
            e.printStackTrace();
            throw new OperationException("Riscontrato problema interno all'applicazione");
           
        }
       
        //Restituisco prezzo, carta di credito (qualora risulti registrato il cliente, altrimenti sarà settata a null)
        //acquisto e codici.
        //Se il cliente nel boundary conferma, viene eseguito il pagamento e inviati i codici
        //altrimenti viene annullato l'acquisto richiamando la funzione annullaAqcuisto
        risultato = new DettagliAcquisto(prezzoTot, eCDC, eA, codiciAcquistati);
        return risultato;
    }
 
    public static float calcolaPrezzo(ArrayList<EntityCodice> codici){
        float prezzo = 0;
        for(int i = 0; i < codici.size(); i++){
                prezzo += codici.get(i).getPrezzo();
        }  
        return prezzo;
    }
 
    public static float applicaSconto(float prezzo){
        float sconto = (prezzo*10)/100;
        float prezzoScontato = prezzo - sconto;
        return prezzoScontato;
    }
 
    public static void annullaAcquisto(EntityAcquisto eA) throws DAOException, DBConnectionException{
        AcquistoDAO.deleteAcquisto(eA);
    }
 
    public static void updateAcquisto(EntityAcquisto eA, ArrayList<EntityCodice> eLC) throws DAOException, DBConnectionException{
        eA = CodiceDAO.updateListaCodiciAcquistati(eA, eLC);
        for(int i = 0; i < eLC.size(); i++){
            CodiceDAO.updateDisponibilità(eLC.get(i));
        }
        return;
    }
 
    public static void inviaCodice(ArrayList<EntityCodice> eLC, String email, float prezzoTot) {
 
        String fromEmail = "SistemaSerialKeys@gmail.com";
        String fromPassword = "ascpzyfoeuoofppm";  
        String toEmail = email;
        String subject = "Riepilogo acquisto";
 
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Gentile Cliente, grazie per aver acquistato i nostri codici!\n")
                .append("Ecco a te il riepilogo dell'acquisto:\n\n");
 
        for (int i = 0; i < eLC.size(); i++) {
            bodyBuilder.append("ID: ").append(eLC.get(i).getIdCodice()).append("\n");
            bodyBuilder.append("Data Scadenza: ").append(eLC.get(i).getDataScadenza()).append("\n");
            bodyBuilder.append("Applicazione: ").append(eLC.get(i).getNomeApplicazione()).append("\n");
            bodyBuilder.append("\n");
        }
 
        bodyBuilder.append("Prezzo Totale: €").append(String.format("%.2f", prezzoTot));
 
        String body = bodyBuilder.toString();
 
        try {
            ServizioEmail.inviaEmail(toEmail, subject, body, null, null, fromEmail, fromPassword);
        }catch (DAOException | OperationException e) {
            System.err.println("Errore durante l'invio dell'email.");
        }
    }  
    
    public static void stampaApp() throws DAOException, DBConnectionException{
        ArrayList<EntityApplicazione> listaApp = new ArrayList<>();
        listaApp = ApplicazioneDAO.readListaApplicazioni();
        for(int i = 0; i < listaApp.size(); i++){
            System.out.println("Applicazione " + (i+1) + ": " + listaApp.get(i).getNome());
        }
    }
 
    public static int stampaNumCodici(String nomeApp) throws DAOException, DBConnectionException{
        ArrayList<EntityCodice> listaCodici = new ArrayList<>();
        int numCodiciMax = 0;
        listaCodici = CodiceDAO.readCodici(nomeApp);
        for(int i = 0; i < listaCodici.size(); i++){
            numCodiciMax++;
        }
        return numCodiciMax;
    }
 
    

}

