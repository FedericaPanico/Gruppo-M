package Boundary;

import Control.GestioneSerialKeys;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;


public class BoundaryTempo {
    

    public static void main(String[] args) throws InterruptedException{
        
        GestioneSerialKeys gestioneSerialKeys = GestioneSerialKeys.getInstance();
        gestioneSerialKeys.inviaReport();
                    
        boolean confermato = BoundaryGestore.confermaRicezioneMail();

        if (confermato) {
            System.out.println("AVVISO: Il report settimanale è stato ricevuto dal Gestore!");
        } else {
            System.out.println("ERRORE: Il report settimanale non è stato ricevuto dal Gestore.");
            confermato = BoundaryGestore.riprova();

            if(!confermato) {
                gestioneSerialKeys.inviaEmailWarning();
            }
        
        }
    }
    
}
