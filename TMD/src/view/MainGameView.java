package view;

/* ===================================================
 * Filename     : MainGameView.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 11 Juni 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package view yang bertugas untuk
 *                menampilkan view ketika game loop 
 *                berjalan
 * ===================================================
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Pemain;
import model.Bola;
import model.BolaManager;
import presenter.GamePresenter;
import view.GameLoopContract;

public class MainGameView extends JPanel implements GameLoopContract, Runnable{
    private Pemain pemain;
    private ArrayList<Bola> bolas;
    private GamePresenter presenter;

    // Variabel Thread
    private Thread gameThread;
    private volatile boolean running = false;
    private final int FPS = 60;

    private Image[] playerWalkingFrames;
    private int currentFrame = 0;
    private int scoreToShow = 0;

    public MainGameView(){
        setPreferredSize(new Dimension(1000, 800));
        loadAnimationFrames();

        bolas = new ArrayList<Bola>();
    }

    public void loadAnimationFrames(){
        playerWalkingFrames = new Image[2];
    }

    public void setPemain(Pemain pemain) { this.pemain = pemain; }
    public void setPresenter(GamePresenter presenter) { this.presenter = presenter; }
    public void setBola(ArrayList<Bola> bola) { this.bolas = bola; }
    public void setCurrentFrame(int frame) { this.currentFrame = frame; }
    public void setScore(int score) { this.scoreToShow = score; }

    public void startGameLoop(){
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stopGameLoop(){
        if (running) {
            running = false;
            gameThread = null;
        }
    }

    @Override
    public void run(){
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Loop hanya akan menyuruh presenter untuk melakukan update game
                if (presenter != null) {
                    presenter.updateGame();
                }
                delta--;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if (pemain != null) {
            if (currentFrame == 0) {
                g.setColor(Color.BLACK);    
            }else{
                g.setColor(Color.RED);
            }
            g.fillRect(pemain.getPosX(), pemain.getPosY(), pemain.getSize(), pemain.getSize());
        }

        for (Bola bola : bolas) {
            if (bola != null) {
                g.setColor(Color.ORANGE);
                g.fillOval(bola.getPosX(), bola.getPosY(), bola.getSize(), bola.getSize());
            }
            String value = String.valueOf(bola.getValue());
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));

            int ballCenterX = bola.getPosX() + (bola.getSize() / 2);
            int ballCenterY = bola.getPosY() + (bola.getSize() / 2);
            int textWidth = g.getFontMetrics().stringWidth(value);

            g.drawString(value, ballCenterX - (textWidth / 2), ballCenterY + 5);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("Score: " + scoreToShow, 50, this.getHeight() / 2);
    }

    @Override
    public void refreshView(){
        SwingUtilities.invokeLater(() -> repaint());
    }

    @Override
    public void addKeyListener(KeyListener key){
        super.addKeyListener(key);
    }
}
