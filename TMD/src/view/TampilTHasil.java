package view;

import presenter.ProsesTHasil;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class TampilTHasil extends JFrame{
    private ProsesTHasil prosesHasil;
    private JTextArea displayArea;

    public TampilTHasil(){
        prosesHasil = new ProsesTHasil();

        JPanel panel = new JPanel(new BorderLayout());
        displayArea = new JTextArea();
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        this.add(panel);

        tampil();
    }

    public void tampil(){
        try{
            prosesHasil.prosesDataUser();

            String hasil = "";
            for(int i=0; i<prosesHasil.getSize();i++){
                hasil = hasil + "Id: " + prosesHasil.getId(i) + "\n";
                hasil = hasil + "Nama: " + prosesHasil.getNama(i) + "\n";
                hasil = hasil + "Email: " + prosesHasil.getEmail(i) + "\n";
                hasil = hasil + "=====================================\n";
            }

            displayArea.setText(hasil.toString());
        }catch(Exception e){
            displayArea.setText("Terjadi sebuah kesalahan:\n" + prosesHasil.getError());
            e.printStackTrace();
        }
    }
}