package Entity;

public class EntityGestore {
    private String idGestore;
    private String password;
    
    public EntityGestore(String idGestore, String password) {
        super();
        this.idGestore = idGestore;
        this.password = password;
    }
    
    
    public String getIdGestore() {
        return idGestore;
    }

    public void setIdGestore(String idGestore) {
        this.idGestore = idGestore;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
