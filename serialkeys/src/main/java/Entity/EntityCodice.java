package Entity;

//import database.BigliettoDAO;
//import exception.DAOException;
//import exception.DBConnectionException;

public class EntityCodice {
    private String idCodice;
    //private data DataScadenza;
    private boolean validità;
    private boolean disponibilità;
    private float prezzo;
    private String nomeApplicazione;
    private String idGestore; 
    private int idAcquisto;

    private EntityApplicazione app;
    private EntityGestore gst;
    private EntityAcquisto acq;

//	public EntityBiglietto(int idBiglietto, float costo, int numPosto, int idProiezione, int idCliente) {
//		super();
//		this.idBiglietto = idBiglietto;
//		this.costo = costo;
//		this.numPosto = numPosto;
//		this.prz = new EntityProiezione(idProiezione);
//		this.cln = new EntityClienteRegistrato(idCliente);
//	}

    public EntityCodice(String idCodice, boolean validità, boolean disponibilità, float prezzo, String nomeApplicazione, String idGestore, int idAcquisto) {
        super();
        this.idCodice = idCodice;
        this.validità = validità;
        this.disponibilità = disponibilità;
        this.prezzo = prezzo;
        this.nomeApplicazione = nomeApplicazione;
        this.idGestore = idGestore;
        this.idAcquisto = idAcquisto;
    }


    public String getIdCodice() {
        return idCodice;
    }

    public void setIdCodice(String idCodice) {
        this.idCodice = idCodice;
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

    public String getIdGestore() {
        return idGestore;
    }

    public void setIdGestore(String idGestore) {
        this.idGestore = idGestore;
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
