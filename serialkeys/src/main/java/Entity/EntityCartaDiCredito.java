package Entity;

public class EntityCartaDiCredito {
    private String nomeProprietario;
    private String cognomeProprietario;
    private long numero;
    // private data DataScadenza;
    private int codiceCVC;
    
    public EntityCartaDiCredito(String nomeProprietario, String cognomeProprietario, long numero, int codiceCVC) {
        super();
        this.nomeProprietario = nomeProprietario;
        this.cognomeProprietario = cognomeProprietario;
        this.numero = numero;
        this.codiceCVC = codiceCVC;
    }
    
    
    public String getNomeProprietario() {
        return nomeProprietario;
    }
    
    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }
   
    public String getCognomeProprietario() {
        return cognomeProprietario;
    }
    
    public void setCognomeProprietario(String cognomeProprietario) {
        this.cognomeProprietario = cognomeProprietario;
    }
    
    public long getNumero() {
        return numero;
    }
    
    public void setNumero(long numero) {
        this.numero = numero;
    }
    
    public int getCodiceCVC() {
        return codiceCVC;
    }
    
    public void setCodiceCVC(int codiceCVC) {
        this.codiceCVC = codiceCVC;
    }
    
}
