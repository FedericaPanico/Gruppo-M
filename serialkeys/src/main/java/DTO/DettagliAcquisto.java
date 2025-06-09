package DTO;
 
import Entity.EntityCartaDiCredito;
import Entity.EntityAcquisto;
import Entity.EntityCodice;
 
import java.util.ArrayList;
 
//si è creata questa classe solo per trasferire dati eterogenei dal control al boundary cliente e viceversa
//per pulizia si è creato un package apposito separato
 
public class DettagliAcquisto{
    private float prezzoTot;
    private EntityCartaDiCredito cartaRegistrata;
    private EntityAcquisto acquistoCorrente;
    private ArrayList<EntityCodice> codiciInAcquisto;
 
    public DettagliAcquisto(float prezzoTot, EntityCartaDiCredito cartaRegistrata, EntityAcquisto acquistoCorrente, ArrayList<EntityCodice> codiciInAcquisto){
        super();
        this.prezzoTot = prezzoTot;
        this.cartaRegistrata = cartaRegistrata;
        this.acquistoCorrente = acquistoCorrente;
        this.codiciInAcquisto = codiciInAcquisto;
    }
 
    public float getPrezzoTot() {
        return prezzoTot;
    }
 
    public void setPrezzoTot(float prezzoTot) {
        this.prezzoTot = prezzoTot;
    }
 
    public EntityCartaDiCredito getCartaRegistrata() {
        return cartaRegistrata;
    }
 
    public void setCartaRegistrata(EntityCartaDiCredito cartaRegistrata) {
        this.cartaRegistrata = cartaRegistrata;
    }
 
    public EntityAcquisto getAcquistoCorrente(){
        return acquistoCorrente;
    }
 
    public void setAcquistoCorrente(EntityAcquisto acquistoCorrente){
        this.acquistoCorrente = acquistoCorrente;
    }
 
    public ArrayList<EntityCodice> getCodiciInAcquisto(){
        return codiciInAcquisto;
    }
 
    public void setCodiciInAcquisto(ArrayList<EntityCodice> codiciInAcquisto){
        this.codiciInAcquisto = codiciInAcquisto;
    }
 
}