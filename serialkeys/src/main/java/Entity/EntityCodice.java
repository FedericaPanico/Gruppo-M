package Entity;

import java.util.Date;

//import database.BigliettoDAO;
//import exception.DAOException;
//import exception.DBConnectionException;

public class EntityCodice {
    private String idCodice;
    private Date dataScadenza;
    private boolean validità;
    private boolean disponibilità;
    private float prezzo;
    private String nomeApplicazione;
    private int idAcquisto;

    public EntityCodice(String idCodice, Date dataScadenza, boolean validità, boolean disponibilità, float prezzo, String nomeApplicazione, int idAcquisto) {
        super();
        this.idCodice = idCodice;
        this.dataScadenza = dataScadenza;
        this.validità = validità;
        this.disponibilità = disponibilità;
        this.prezzo = prezzo;
        this.nomeApplicazione = nomeApplicazione;
        this.idAcquisto = idAcquisto;
    }
    
    public EntityCodice(String idCodice, Date dataScadenza, boolean validità, boolean disponibilità, float prezzo, String nomeApplicazione) {
        super();
        this.idCodice = idCodice;
        this.dataScadenza = dataScadenza;
        this.validità = validità;
        this.disponibilità = disponibilità;
        this.prezzo = prezzo;
        this.nomeApplicazione = nomeApplicazione;
    }
    
    public EntityCodice(String idCodice) {
        super();
        this.idCodice = idCodice;
    }


    public String getIdCodice() {
        return idCodice;
    }

    public void setIdCodice(String idCodice) {
        this.idCodice = idCodice;
    }
    
    public Date getDataScadenza() {
        return dataScadenza;
    }
    
    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean getValidità() {
        return validità;
    }

    public void setValidità(boolean validità) {
        this.validità = validità;
    }

    public boolean getDisponibilità() {
        return disponibilità;
    }

    public void setDisponibilità(boolean disponibilità) {
        this.disponibilità = disponibilità;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getNomeApplicazione() {
        return nomeApplicazione;
    }

    public void setNomeApplicazione(String nomeApplicazione) {
        this.nomeApplicazione = nomeApplicazione;
    }

    public int getIdAcquisto() {
            return idAcquisto;
    }

    public void setIdAcquisto(int idAcquisto) {
            this.idAcquisto = idAcquisto;
    }

    /*
    public void saveBiglietto() throws DAOException, DBConnectionException {

            BigliettoDAO.createBiglietto(this);

    }
    */
	
}
