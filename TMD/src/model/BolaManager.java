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
import java.util.Iterator;
import java.util.random.RandomGenerator;
import java.awt.Rectangle;

import model.Bola;

public class BolaManager {
    private ArrayList<Bola> collectionBola = new ArrayList<Bola>();
    private RandomGenerator random = RandomGenerator.getDefault();

    public BolaManager(){

    }

    public void spawnBola(int jumlahBola, int screenWidth, int screenHeight){
        collectionBola.clear();
        
        for(int i = 0; i < jumlahBola; i++){
            spawnSatuBola(screenWidth, screenHeight);
        }
    }

    public void spawnSatuBola(int screenWidth, int screenHeight){
        boolean spawnLeft = random.nextBoolean();
        int direction;
        int startX;
        int startY;
        
        if (spawnLeft) {
            startX = -50;
            startY = screenHeight / 8;
            direction = 1;
        }else{
            startX = screenWidth + 50;
            startY = screenHeight * 7 / 8;
            direction = -1;
        }

        int randomValue = random.nextInt(1, 101);

        collectionBola.addLast(new Bola(startX, startY));
        collectionBola.getLast().setDirection(direction);
        collectionBola.getLast().setValue(randomValue);
    }

    public void updateAllBolas(int screenWidth){
        Iterator<Bola> it = collectionBola.iterator();
        while (it.hasNext()) {
            Bola currentBola = it.next();
            currentBola.gerak();
            if (currentBola.getPosX() > screenWidth && currentBola.getDirection() == 1) {
                it.remove();
            }else if(currentBola.getPosX() < (-1 * currentBola.getPosX()) && currentBola.getDirection() == -1){
                it.remove();
            }
        }
    }

    public Bola checkCollision (Pemain pemain){
        /*
         * Method untuk mengecek kolisi dari bola
         * dan pemain
         */
        Iterator<Bola> it = collectionBola.iterator();
        Rectangle hitBoxPemain = new Rectangle(pemain.getPosX(), pemain.getPosY(), pemain.getSize(), pemain.getSize());
        while (it.hasNext()) {
            Bola currentBola = it.next();
            Rectangle hitBoxBola = new Rectangle(currentBola.getPosX(), currentBola.getPosY(), currentBola.getSize(), currentBola.getSize());
            if (hitBoxPemain.intersects(hitBoxBola)) {
                it.remove();
                return currentBola;    
            }
        }
        return null;
    }

    public ArrayList<Bola> getBola(){ return collectionBola; }


}
