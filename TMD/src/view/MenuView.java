package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Desktop.Action;
import java.awt.GridBagLayout;
import java.awt.Color;
// import java.awt.Graphics;
// import view.viewContract;
import java.awt.GridBagConstraints;

public class MenuView extends JPanel{
    private JButton startButton;
    private JButton leaderBoardButton;
    
    public MenuView(){
        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titLabel = new JLabel("TMD Bola-Bola");
        add(titLabel, gbc);

        startButton = new JButton("Mulai Game");
        add(startButton, gbc);

        leaderBoardButton = new JButton("Leaderboard");
        add(leaderBoardButton, gbc);
    }

    public void addStartButtonListener(ActionListener listener){
        startButton.addActionListener(listener);
    }
    public void addLeaderboardButtonListener(ActionListener listener){
        leaderBoardButton.addActionListener(listener);
    }

}
