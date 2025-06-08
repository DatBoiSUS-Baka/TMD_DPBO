/*
 * ===================================================
 * Filename     : DB.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 6 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : Package model untuk mengakses database
 * 
 * ===================================================
 */

 // package model/kelas yang mengakses database
 package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DB{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tmd_dpbo";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet result = null;

    public DB() throws ClassNotFoundException, SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (this.conn != null) {
                System.out.println("Koneksi sukses.");
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            }
        }catch(SQLException e){
            System.out.print("Terjadi Error: ");
            throw e;
        }
    }

    public void createQuery(String Query)throws Exception, SQLException{
        // Membuat query untuk mengambil data tanpa mengubah tabel atau data (SELECT)
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(Query);
        }catch (SQLException es){
            throw es;
        }
    }
    
    public void createUpdate(String Query)throws Exception, SQLException{
        // Membuat query untuk melakukan perubahan pada data dalam tabel (INSERT, UPDATE, DELETE, ALTER, CREATE)
        try {
            stmt = conn.createStatement();
            int hasil = stmt.executeUpdate(Query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult()throws Exception{
        // Fungsi getter untuk Set Result
        ResultSet Temp = null;
        try {
            return result;
        } catch (Exception e) {
            return Temp;
        }
    }

    public void closeResult()throws SQLException, Exception{
        // Fungsi untuk close result dan statement
        if (result != null) {
            try{
                result.close();
            }catch(SQLException ex){
                result = null;
                throw ex;
            }finally{
                result = null;
            }
        }

        if (stmt != null) {
            try{
                stmt.close();
            }catch (SQLException ex){
                stmt = null;
                throw ex;
            }finally{
                stmt = null;
            }
        }
    }
    
    public void closeConnection() throws SQLException{
        // Fungsi untuk melakukan close connection
        if (conn != null) {
            try{
                conn.close();
            }catch (SQLException sqlex){
                conn = null;
                sqlex.printStackTrace();
            }finally{
                conn = null;
            }
        }
    }

    public Connection getConnection(){
        // Fungsi untuk mendapatkan koneksi
        return this.conn;
    }
}
