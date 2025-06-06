package presenter;

import java.util.ArrayList;
import model.TableUser;
import model.User;

public class prosesUser {
    private String error;
    private TableUser tabel;
    private ArrayList<User> data;

    public prosesUser(){
        try {
            tabel = new TableUser();
            data = new ArrayList<User>();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    public void prosesDataUser(){
        try {
            tabel.getUser();
            while (tabel.getResult().next()) {
                User user = new User();
                user.setId(tabel.getResult().getString(1));
                user.setNama(tabel.getResult().getString(2));
                user.setEmail(tabel.getResult().getString(3));

                data.add(user);
            }
            tabel.closeResult();
            tabel.closeConnection();
        } catch (Exception e) {
            // TODO: handle exception
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
