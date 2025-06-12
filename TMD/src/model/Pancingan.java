package model;

/* ===================================================
* Filename     : Pancingan.java
* Programmer   : Kasyful Haq Bachariputra
* Date         : 11 Juni 2025
* Email        : kasyfulhaqb@upi.edu
* Deskripsi    : Package model sebagai representasi 
*                sebuah pancingan/hook dan perilakunya
* ===================================================
*/

import java.awt.Rectangle;
import java.util.ArrayList;

import model.Bola;

public class Pancingan {
    // Posisi dari ujung hook
    private int positionHookX;
    private int positionHookY;

    private String state; // Terdiri dari "Empty", "Active", dan "Caught"
    private Bola caughtBola;

    public Pancingan(){
        this.state = "Empty";
    }

    public int getPosX(){ return this.positionHookX; }
    public int getPosY(){ return this.positionHookY; }

    public void setPosition(int x, int y){
        this.positionHookX = x;
        this.positionHookY = y;
    }

    public Bola tryToCatch(ArrayList<Bola> allBolas){
        /*
         * Method untuk mencoba menangkap bola dengan Hook
         */
        this.state = "Active";
        for (Bola currentBola : allBolas) {
            Rectangle hitBoxBola = new Rectangle(currentBola.getPosX(), currentBola.getPosY(), currentBola.getSize(), currentBola.getSize());
            if (this.positionHookX > currentBola.getPosX() && this.positionHookX <= currentBola.getPosX() + currentBola.getSize() || this.positionHookY > currentBola.getPosY() && this.positionHookY <= currentBola.getPosY() + currentBola.getSize()) {
                this.caughtBola = currentBola;
                this.state = "Caught";
                return this.caughtBola;
            }
        }
        return null;
    }

    public void releaseHook(){
        this.state = "Empty";
        this.caughtBola = null;
    }
}
