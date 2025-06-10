package view;

import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import model.Pemain;
import view.GameLoopContract;
import java.awt.Graphics;
import java.awt.Color;

public class MainGameView extends JPanel implements GameLoopContract{
    private Pemain pemain;

    public MainGameView(){
        setPreferredSize(new Dimension(1000, 800));
    }

    public void setPemain(Pemain pemain) { this.pemain = pemain; }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if (pemain != null) {
            g.setColor(Color.GREEN);    
            g.fillRect(pemain.getPosX(), pemain.getPosY(), pemain.getSize(), pemain.getSize());
        }
    }

    @Override
    public void refreshView(){
        repaint();
    }

    @Override
    public void addKeyListener(KeyListener key){
        super.addKeyListener(key);
    }
}
