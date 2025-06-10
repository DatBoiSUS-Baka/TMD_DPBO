package presenter;

import model.Pemain;
import view.MainGameView;

import javax.swing.Timer;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GamePresenter {
    private Pemain pemain;
    private MainGameView panel;

    private Timer gameLoop;

    private final Set<Integer> pressedKeys = new HashSet<>();

    public GamePresenter(Pemain pemain, MainGameView panel){
        this.pemain = pemain;
        this.panel = panel;

        this.panel.addKeyListener(new KeyHandler());

        this.gameLoop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                updateGame();
            }
        });
    }

    public void startGame(){
        gameLoop.start();
    }

    public void updateGame(){
        int dx = 0;
        int dy = 0;

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
            pemain.gerak(dx, dy);
        }

        panel.refreshView();
    }

    private class KeyHandler extends KeyAdapter{
        /*
         * Inner class untuk meng-handle input key mana yang sedang ditekan dan dilepas
         */
        @Override
        public void keyPressed(KeyEvent e){
            pressedKeys.add(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e){
            pressedKeys.remove(e.getKeyCode());
        }
    }

}
