/*
 * ===================================================
 * Filename     : DB.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 6 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : File App untuk dapat bisa menjalankan game
 * 
 * ===================================================
 */

import view.TampilUser;

public class App {
    public static void main(String[] args){
        // Memanggil modul view untuk menampilkan user
        TampilUser tampiluser = new TampilUser();
        tampiluser.tampil();
    }
}
