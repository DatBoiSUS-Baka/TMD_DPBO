package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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
