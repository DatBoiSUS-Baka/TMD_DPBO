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
import model.BolaManager;
import model.Keranjang;
import presenter.GamePresenter;
import presenter.GameOverListener;
import view.MainGameView;
import view.MenuView;

public class App implements GameOverListener{
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static GamePresenter presenter;
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            // Menggunakan invokeLater agar tidak terjadi error ketika menjalankan App
            public void run(){
                mulaiGame();
            }
        });
    }

    private static void mulaiGame(){
        // Buat Pemain (Temporary)
        Pemain pemain = new Pemain(0, 0);

        // Buat frame
        JFrame frame = new JFrame("TMD Bola-bola");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menggunakan CardLayout untuk merubah halaman
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Instansiasi berbagai macam view yang ada
        MenuView menuView = new MenuView();
        MainGameView gameView = new MainGameView();

        // Tambahkan view ke Panel
        mainPanel.add(menuView, "MENU");
        mainPanel.add(gameView, "GAME");

        // Membuat model dan presenter untuk bagian Game utama (Gameloop)
        BolaManager managerBola = new BolaManager();
        Keranjang keranjang = new Keranjang(0, 0);
        presenter = new GamePresenter(pemain, gameView, managerBola, keranjang);
        presenter.setGameOverListener(new App());
        gameView.setPemain(pemain);

        // Logika untuk mengubah halaman
        gameView.setPresenter(presenter);

        menuView.addStartButtonListener(e -> {
            cardLayout.show(mainPanel, "GAME"); 
            gameView.setFocusable(true);
            gameView.requestFocusInWindow();
            presenter.startGame();
        });

        // Setup dari frame
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Set posisi pemain di tengah layar
        pemain.setPosition((frame.getWidth()/2) - (pemain.getSize() / 2), (frame.getHeight() / 2) - (pemain.getSize() / 2));

        // Set posisi keranjang
        keranjang.setPosition(((frame.getWidth() * 3) / 4) - (keranjang.getHeight() / 2), (frame.getHeight() / 2) - (keranjang.getHeight() / 2));

        // Tunjukkan menu terlebih dahulu
        cardLayout.show(mainPanel, "MENU");

    }

    @Override
    public void gameOver(){
        /*
         * Menunjukkan view game over dari cardlayout
         * serta melakukan reset posisi
         */
        cardLayout.show(mainPanel, "MENU");

        presenter.resetGame();
    }

}
