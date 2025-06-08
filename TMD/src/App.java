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

import javax.swing.SwingUtilities;
import view.TampilTHasil;

public class App {
    public static void main(String[] args){
        // Memanggil modul view untuk menampilkan user
        SwingUtilities.invokeLater(new Runnable() {
            // Menggunakan invokeLater agar tidak terjadi error ketika menjalankan App
            public void run(){
                TampilTHasil tampiluser = new TampilTHasil();
                tampiluser.setVisible(true);

            }
        });
    }
}
