package Entity;

import java.util.ArrayList;

public class EntityAcquisto {
    private int idAcquisto;
    // private data dataAcquisto;
    private String nomeApplicazione;
    private int numCodici;
    private String emailDestinazione;
    private ArrayList<EntityCodice> codiciAcquistati;
    private String nomeUtente;
    
    private EntityCodice cd;
    private EntityClienteRegistrato cr;
    
    public EntityAcquisto(int idAcquisto, String nomeApplicazione, int numCodici, String emailDestinazione, ArrayList<EntityCodice> codiciAcquistati, String nomeUtente) {
        super();
        this.idAcquisto = idAcquisto;
        this.nomeApplicazione = nomeApplicazione;
        this.numCodici = numCodici;
        this.emailDestinazione = emailDestinazione;
        this.codiciAcquistati = codiciAcquistati;
        this.nomeUtente = nomeUtente;   
    }
    
    
    public int getIdAcquisto() {
        return idAcquisto;
    }

    public void setIdAcquisto(int idAcquisto) {
        this.idAcquisto = idAcquisto;
    }
    
    public String getNomeApplicazione() {
        return nomeApplicazione;
    }

    public void setNomeApplicazione(String nomeApplicazione) {
        this.nomeApplicazione = nomeApplicazione;
    }

    public int getNumCodici() {
        return numCodici;
    }

    public void setNumCodici(int numCodici) {
        this.numCodici = numCodici;
    }
    
    public String getEmailDestinazione() {
        return emailDestinazione;
    }

    public void setEmailDestinazione(String emailDestinazione) {
        this.emailDestinazione = emailDestinazione;
    }
    
    public ArrayList<EntityCodice> getCodiciAcquistati() {
        return codiciAcquistati;
    }
    
    public void setCodiciAcquistati(ArrayList<EntityCodice> codiciAcquistati) {
        this.codiciAcquistati = codiciAcquistati;
    }
    
    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
    
}
