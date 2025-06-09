package Boundary;

import Control.GestioneSerialKeys;
import exception.DAOException;
import exception.DBConnectionException;
import exception.OperationException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenu {

    public static void main(String[] args) throws InterruptedException, DAOException, DBConnectionException, OperationException, Exception {
        
        
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();

            if (now.getDayOfWeek() == DayOfWeek.SUNDAY &&
                now.getHour() == 23 &&
                now.getMinute() == 59) {

                try {
                    BoundaryTempo.main(new String[0]);
                } catch (InterruptedException e) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }, 0, 1, TimeUnit.MINUTES);

    
        
        Scanner scanner = new Scanner(System.in);
        int scelta;

        do {
            System.out.println("\n=== MENU PRINCIPALE ===");
            System.out.println("1. Avvia BoundaryGestore");
            System.out.println("2. Avvia BoundaryCliente");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Inserisci un numero valido.");
                scanner.next(); // pulisci input
            }

            scelta = scanner.nextInt();
            
            System.out.println(); 

            switch (scelta) {
                case 1:
                    BoundaryGestore.main(new String[0]);
                    break;
                case 2:
                    BoundaryCliente.main(new String[0]);
                    break;
                case 0:
                    System.out.println("Uscita dal programma.");
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }

        } while (scelta != 0);

        scanner.close();
        
    }
    
}
