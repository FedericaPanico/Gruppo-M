package Entity;

import java.util.ArrayList;
import java.util.Date;
 
public class EntityApplicazione {
    private String nome;
    private Date dataRilascio;
    private String produttore;
    private String categoria;
    private ArrayList<EntityCodice> codiciAssociati;
 
    public EntityApplicazione(String nome, Date dataRilascio, String produttore, String categoria, ArrayList<EntityCodice> codiciAssociati){
        this.nome = nome;
        this.dataRilascio = dataRilascio;
        this.produttore = produttore;
        this.categoria = categoria;
        this.codiciAssociati = codiciAssociati;
    }
 
    
    public String getNome(){
        return nome;
    }
 
    public void setNome(String nome){
        this.nome = nome;
    }
 
    public Date getDataRilascio(){
        return dataRilascio;
    }
 
    public void setDataRilascio(Date dataRilascio){
        this.dataRilascio = dataRilascio;
    }
 
    public String getProduttore(){
        return produttore;
    }
 
    public void setProduttore(String produttore){
        this.produttore = produttore;
    }
 
    public String getCategoria(){
        return categoria;
    }
 
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
 
    public ArrayList<EntityCodice> getCodiciAssociati(){
        return codiciAssociati;
    }
 
    public void setCodiciAssociati(ArrayList<EntityCodice> codiciAssociati){
        this.codiciAssociati = codiciAssociati;
    }
    
}