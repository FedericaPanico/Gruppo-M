package Entity;

import java.util.ArrayList;

public class EntityClienteRegistrato {
    private String idClienteRegistrato;
    private String nomeUtente;
    private String password;
    private String indirizzoEmail;
    private EntityCartaDiCredito cartaDiCredito;
    private ArrayList<EntityAcquisto> acquisti;

    /*
    public EntityClienteRegistrato() {
            // TO DO
            super();
    }
    */

    public EntityClienteRegistrato(String idClienteRegistrato, String nomeUtente, String password, String indirizzoEmail, EntityCartaDiCredito cartaDiCredito, ArrayList<EntityAcquisto> acquisti) {
        super();
        this.idClienteRegistrato = idClienteRegistrato;
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.indirizzoEmail = indirizzoEmail;
        this.cartaDiCredito = cartaDiCredito;
        this.acquisti = acquisti;
    }
    
    public String getIdClienteRegistrato() {
        return idClienteRegistrato;
    }
    
    public void setIdClienteRegistrato(String idClienteRegistrato) {
        this.idClienteRegistrato = idClienteRegistrato;
    }
    
    public String getNomeUtente() {
        return nomeUtente;
    }
    
    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getIndirizzoEmail() {
            return indirizzoEmail;
    }
    
    public void setIndirizzoEmail(String indirizzoEmail) {
            this.indirizzoEmail = indirizzoEmail;
    }
    
    public EntityCartaDiCredito getCartaDiCredito() {
        return cartaDiCredito;
    }
    
    public void setCartaDiCredito(EntityCartaDiCredito cartaDiCredito) {
        this.cartaDiCredito = cartaDiCredito;
    }
    
    public ArrayList<EntityAcquisto> getAcquisti() {
        return acquisti;
    }
    
    public void setAcquisti(ArrayList<EntityAcquisto> acquisti) {
        this.acquisti = acquisti;
    }

}
