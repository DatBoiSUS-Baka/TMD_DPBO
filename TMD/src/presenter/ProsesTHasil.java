package presenter;

import java.util.ArrayList;
import model.TableTHasil;
import model.THasil;

public class ProsesTHasil {
    private String error;
    private TableTHasil tabel;
    private ArrayList<THasil> data;

    public ProsesTHasil(){
        try {
            tabel = new TableTHasil();
            data = new ArrayList<THasil>();
        } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
        }
    }
    
    public void prosesDataUser(){
        try {
            tabel.getUser();
            while (tabel.getResult().next()) {
                THasil user = new THasil();
                user.setId(tabel.getResult().getString(1));
                user.setNama(tabel.getResult().getString(2));
                user.setEmail(tabel.getResult().getString(3));

                data.add(user);
            }
            tabel.closeResult();
            tabel.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
        }
    }

    public String getId(int i){
        return data.get(i).getId();
    }
    public String getNama(int i){
        return data.get(i).getNama();
    }
    public String getEmail(int i){
        return data.get(i).getEmail();
    }

    public int getSize(){
        return data.size();
    }
    public String getError(){
        return this.error;
    }
}
