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
import java.sql.PreparedStatement;
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
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(Query);
        }catch (SQLException es){
            throw es;
        }
    }
    
    public void createUpdate(String Query)throws Exception, SQLException{
        try {
            stmt = conn.createStatement();
            int hasil = stmt.executeUpdate(Query);
        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet getResult()throws Exception{
        ResultSet Temp = null;
        try {
            return result;
        } catch (Exception e) {
            return Temp;
        }
    }

    public void closeResult()throws SQLException, Exception{
        if (result != null) {
            try{
                result.close();
            }catch(SQLException ex){
                result = null;
                throw ex;
            }
        }

        if (stmt != null) {
            try{
                stmt.close();
            }catch (SQLException ex){
                stmt = null;
                throw ex;
            }
        }
    }
    
    public void closeConnection() throws SQLException{
        if (conn != null) {
            try{
                conn.close();
            }catch (SQLException sqlex){
                conn = null;
            }
        }
    }

    public Connection getConnection(){
        return this.conn;
    }

    // !!CODINGAN COBA-COBA APUY

    // public void selectQuery(String Query) throws SQLException{
    //     PreparedStatement pstmt = conn.prepareStatement(Query);
    //     result = pstmt.executeQuery();
    // }

    // public boolean addUser(String nama, String email){
    //     this.stmt = "INSERT INTO user (nama, email) VALUES (?, ?)";
    //     PreparedStatement pstmt = null;
    //     try{
    //         if (this.conn == null || this.conn.isClosed()) {
    //             System.err.println("Database Connection tidak ada:");
    //             return false;
    //         }

    //         pstmt = this.conn.prepareStatement(stmt);

    //         pstmt.setString(1, nama);
    //         pstmt.setString(2, email);

    //         int rowsAffected = pstmt.executeUpdate();

    //         if (rowsAffected > 0) {
    //             System.out.println("Mahasiswa " + nama + " berhasil ditambahkan.");
    //             return true;
    //         }else{
    //             System.out.println("Gagal menambahkan data. Tidak ada baris yang terpengaruh.");
    //             return false;
    //         }
    //     }catch (SQLException e){
    //         System.err.println("Waduh error pas nambahin mahasiswa: " + e.getMessage());
    //         return false;
    //     }finally{
    //         if (pstmt != null) {
    //             try{
    //                 pstmt.close();
    //             }catch(SQLException e){
    //                 System.out.println("Error pas nutup : " + e.getMessage());
    //             }
    //         }
    //     }
    // }

    //     public static void main(String[] args) {
    //         DB database = null;
    //         try {
    //             database = new DB(); // Establishes connection
    
    //             // Example data
    //             boolean success1 = database.addUser("Budi Santoso", "Fasilkom");
    //             boolean success2 = database.addUser("Ani Lestari", "FEB");
    
    //             System.out.println("Penambahan Budi berhasil: " + success1);
    //             System.out.println("Penambahan Ani berhasil: " + success2);
    
    
    //         } catch (ClassNotFoundException e) {
    //             System.err.println("MySQL JDBC Driver not found! Pastikan JAR ada di classpath.");
    //             e.printStackTrace();
    //         } catch (SQLException e) {
    //             System.err.println("Database error: " + e.getMessage());
    //             e.printStackTrace();
    //         } finally {
    //             if (database != null) {
    //                 try {
    //                     database.closeConnection(); // Tutup connection
    //                 } catch (SQLException e) {
    //                     System.err.println("Error closing database: " + e.getMessage());
    //                 } catch (Exception e){
    //                     System.err.println("Error closing database: " + e.getMessage());
    //                 }
    //             }
    //         }
    //     }
}
