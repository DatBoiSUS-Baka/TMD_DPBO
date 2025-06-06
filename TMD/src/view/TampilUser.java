package view;

import presenter.prosesUser;

public class TampilUser{
    private prosesUser prosesuser;
    public TampilUser(){
        prosesuser = new prosesUser();
    }

    public void tampil(){
        try{
            prosesuser.prosesDataUser();

            String hasil = "";
            for(int i=0; i<prosesuser.getSize();i++){
                hasil = hasil + "Id: " + prosesuser.getId(i) + "\n";
                hasil = hasil + "Nama: " + prosesuser.getNama(i) + "\n";
                hasil = hasil + "Email: " + prosesuser.getEmail(i) + "\n";
                hasil = hasil + "=====================================\n";
            }

            System.out.println("======================================");
            System.out.println(hasil);
        }catch(Exception e){
            System.out.println(prosesuser.getError());
        }
    }
}