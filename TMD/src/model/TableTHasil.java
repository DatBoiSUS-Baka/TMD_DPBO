package model;

import java.sql.SQLException;

public class TableTHasil extends DB{
    public TableTHasil()throws Exception, SQLException{
        super();
    }

    public void getUser(){
        try {
            String query = "SELECT * FROM thasil";
            createQuery(query);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
    }
}
