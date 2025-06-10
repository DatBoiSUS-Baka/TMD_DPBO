package view;

import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Pemain;
import presenter.GamePresenter;
import view.GameLoopContract;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

public class MainGameView extends JPanel implements GameLoopContract, Runnable{
    private Pemain pemain;
    private GamePresenter presenter;

    // Variabel Thread
    private Thread gameThread;
    private volatile boolean running = false;
    private final int FPS = 60;

    private Image[] playerWalkingFrames;
    private int currentFrame = 0;

    public MainGameView(){
        setPreferredSize(new Dimension(1000, 800));
        loadAnimationFrames();
    }

    public void loadAnimationFrames(){
        playerWalkingFrames = new Image[2];
    }

    public void setPemain(Pemain pemain) { this.pemain = pemain; }
    public void setPresenter(GamePresenter presenter) { this.presenter = presenter; }
    public void setCurrentFrame(int frame) { this.currentFrame = frame; }

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
