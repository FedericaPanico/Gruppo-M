package Entity;

public class EntityClienteRegistrato {
    // private int idClienteRegistrato;
    private String nomeUtente;
    private String password;
    private String indirizzoEmail;
    private EntityCartaDiCredito cartaDiCredito;

    /*
    public EntityClienteRegistrato() {
            // TO DO
            super();
    }
    */

    public EntityClienteRegistrato(String nomeUtente, String password, String indirizzoEmail, EntityCartaDiCredito cartaDiCredito) {
        super();
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.indirizzoEmail = indirizzoEmail;
        this.cartaDiCredito = cartaDiCredito;
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

}
