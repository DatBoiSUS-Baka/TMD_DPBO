package model;

import java.sql.SQLException;

public class TableUser extends DB{
    public TableUser()throws Exception, SQLException{
        super();
    }

    public void getUser(){
        try {
            String query = "SELECT * FROM user";
            createQuery(query);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
    }
}
