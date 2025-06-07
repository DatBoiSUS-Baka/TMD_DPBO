package view;

import presenter.ProsesTHasil;

public class TampilTHasil{
    private ProsesTHasil prosesHasil;
    public TampilTHasil(){
        prosesHasil = new ProsesTHasil();
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

            System.out.println("======================================");
            System.out.println(hasil);
        }catch(Exception e){
            System.out.println(prosesHasil.getError());
        }
    }
}