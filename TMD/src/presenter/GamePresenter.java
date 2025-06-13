package presenter;

/* ===================================================
 * Filename     : GamePresenter.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 11 Juni 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package presenter yang bertugas
 *                mengurus segala hal yang diperlukan
 *                untuk game loop
 * ===================================================
 */

import model.Pemain;
import model.Bola;
import model.BolaManager;
import view.MainGameView;
import presenter.GameOverListener;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.random.RandomGenerator;

public class GamePresenter {
    private Pemain pemain;
    private BolaManager managerBola;
    private MainGameView panel;

    private int currentFrame = 0;
    private int frameCounter = 0;
    private int spawnCountdown;
    private int spawnInterval;
    private RandomGenerator random = RandomGenerator.getDefault();

    private GameOverListener gameOverListener;

    private final Set<Integer> pressedKeys = new HashSet<>();

    public GamePresenter(Pemain pemain, MainGameView panel, BolaManager managerBola){
        this.pemain = pemain;
        this.managerBola = managerBola;
        this.panel = panel;

        this.panel.addKeyListener(new KeyHandler());
    }

    public void setGameOverListener(GameOverListener gameOverListener) { this.gameOverListener = gameOverListener; } //Listener untuk ketika terjadi game over

    public void startGame(){
        /*
         * Method yang dipanggil ketika game pertama dimulai
         */
        panel.startGameLoop();
        spawnCountdown = random.nextInt(30, 60);
        managerBola.spawnBola(10, panel.getWidth(), panel.getHeight());
    }

    public void updateGame(){
        int dx = 0;
        int dy = 0;
        spawnCountdown--;

        // Apabila spawn countdown sudah 0, spawn bola baru
        if (spawnCountdown == 0) {
            managerBola.spawnSatuBola(panel.getWidth(), panel.getHeight());
            spawnCountdown = random.nextInt(30, 60);
        }

        // Gerak ke arah atas
        if (pressedKeys.contains(KeyEvent.VK_UP) || pressedKeys.contains(KeyEvent.VK_W)) {
            dy = -1;
        }
        // Gerak ke arah bawah
        if (pressedKeys.contains(KeyEvent.VK_DOWN) || pressedKeys.contains(KeyEvent.VK_S)) {
            dy = 1;
        }
        // Gerak ke arah kiri
        if (pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A)) {
            dx = -1;
        }
        // Gerak ke arah kanan
        if (pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D)) {
            dx = 1;
        }

        // Memanggil method gerak ketika mendapat arah gerak
        if (dx != 0 || dy != 0) {
            pemain.gerak(dx, dy, panel.getWidth(), panel.getHeight());
        }

        // Cek kolisi bola dengan pemain
        Bola collidedBola = managerBola.checkCollision(pemain);
        if (collidedBola != null) {
            pemain.tambahScore(collidedBola.getValue());
            System.out.println("Terjadi collision!");
        }

        managerBola.updateAllBolas(panel.getWidth()); //Update posisi bola

        if (pemain.getPancingan().getCaughtBola() != null) {
            // Update posisi caught ball terhadap pancingan
            pemain.getPancingan().updateCaughtBallPosition();
        }

        // Apabila bola sudah habis, langsung tambah lagi 5
        if (managerBola.getBola().isEmpty()) {
            managerBola.spawnBola(5, panel.getWidth(), panel.getHeight());
        }

        updateAnimation();

        panel.setBola(managerBola.getBola());

        panel.setCurrentFrame(this.currentFrame);

        panel.setScore(pemain.getScore());

        panel.refreshView();
    }

    public void resetGame(){
        /*
         * Method untuk melakukan reset game
         */
        this.pressedKeys.clear();
        this.currentFrame = 0;
        this.frameCounter = 0;
        this.spawnCountdown = 0;
        this.spawnInterval = 0;

        if (pemain != null) {
            pemain.reset();
            pemain.setPosition((panel.getWidth()/2) - (pemain.getSize() / 2), (panel.getHeight() / 2) - (pemain.getSize() / 2));
        }
        managerBola.spawnBola(10, panel.getWidth(), panel.getHeight());
    }

    private void updateAnimation() {
        // TODO: ubah jadi render sprite pixel
        frameCounter++;
        if (frameCounter > 10) {
            currentFrame = (currentFrame + 1) % 2; // Assuming 2 frames
            frameCounter = 0;
        }
    }

    public void handleMouseClicked(){
        /*
         * Ketika mendapat signal mouseClicked,
         * akan memanggil function tryToCatch
         */
        if (pemain.getPancingan().getState().equals("Empty")) {
            /*
             * Apabila hook sedang kosong, click akan membuatnya
             * mencoba menangkap bola
             */
            Bola caughtBola = pemain.getPancingan().tryToCatch(managerBola.getBola());
            if (caughtBola != null) {
                managerBola.getBola().remove(caughtBola);
            }
        }else if(pemain.getPancingan().getState().equals("Caught")){
            /*
             * Apabila hook sedang menangkap bola, click akan membuatnya
             * melepaskan bola
             */
            pemain.getPancingan().releaseHook();
        }
    }

    public void updateHookPosition(int x, int y){
        // Meng-update posisi dari pancingan/hook
        pemain.getPancingan().setPosition(x, y);
    }

    private class KeyHandler extends KeyAdapter{
        /*
         * Inner class untuk meng-handle input key mana yang sedang ditekan dan dilepas
         */
        @Override
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                panel.stopGameLoop();
                if (gameOverListener != null) {
                    gameOverListener.gameOver();
                }
            }else{
                pressedKeys.add(e.getKeyCode());
            }
        }

        @Override
        public void keyReleased(KeyEvent e){
            pressedKeys.remove(e.getKeyCode());
        }
    }

}
