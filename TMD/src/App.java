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

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Pemain;
import presenter.GamePresenter;
import view.MainGameView;
import view.MenuView;

public class App {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            // Menggunakan invokeLater agar tidak terjadi error ketika menjalankan App
            public void run(){
                mulaiGame();
            }
        });
    }

    private static void mulaiGame(){
        // Buat frame terlebih dahulu
        JFrame frame = new JFrame("TMD Bola-bola");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menggunakan CardLayout untuk merubah halaman
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Instansiasi berbagai macam view yang ada
        MenuView menuView = new MenuView();
        MainGameView gameView = new MainGameView();
        
        // Tambahkan view ke Panel
        mainPanel.add(menuView, "MENU");
        mainPanel.add(gameView, "GAME");

        // Membuat model dan presenter untuk bagian Game utama (Gameloop)
        Pemain pemain = new Pemain(50, 50);
        GamePresenter presenter = new GamePresenter(pemain, gameView);
        gameView.setPemain(pemain);

        // Logika untuk mengubah halaman
        menuView.addStartButtonListener(e -> {
            cardLayout.show(mainPanel, "GAME"); 
            gameView.setFocusable(true);
        	gameView.requestFocusInWindow();
            presenter.startGame();
        });

        // Setup dari frame
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Tunjukkan menu terlebih dahulu
        cardLayout.show(mainPanel, "MENU");

    }

}
