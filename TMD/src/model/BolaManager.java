package model;

/* ===================================================
 * Filename     : BolaManager.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 11 Juni 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package model untuk dapat manage
 *                bola-bola yang akan di-spawn
 * 
 * ===================================================
 */

import java.util.ArrayList;

import model.Bola;

public class BolaManager {
    private ArrayList<Bola> collectionBola = new ArrayList<Bola>();

    public BolaManager(){

    }

    public void spawnBola(int jumlahBola, int screenWidth){
        collectionBola.clear();

        for(int i = 0; i < jumlahBola; i++){
            collectionBola.add(new Bola(screenWidth/3, 3));

        }
    }

    public ArrayList<Bola> getBola(){ return collectionBola; }


}
