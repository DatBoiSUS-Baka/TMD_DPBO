package model;

public class User {
    private String id;
    private String nama;
    private String email;

    public User(){

    }

    public void setId(String id){
        this.id = id;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getId(){
        return this.id;
    }
    public String getNama(){
        return this.nama;
    }
    public String getEmail(){
        return this.email;
    }


}
