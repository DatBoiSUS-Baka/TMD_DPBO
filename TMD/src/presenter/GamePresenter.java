package presenter;

import model.Pemain;
import view.MainGameView;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePresenter {
    private Pemain pemain;
    private MainGameView panel;

    private Timer gameLoop;

    public GamePresenter(Pemain pemain, MainGameView panel){
        this.pemain = pemain;
        this.panel = panel;

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
        panel.refreshView();
    }

}
