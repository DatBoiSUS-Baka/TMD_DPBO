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
import java.lang.Math;

import model.Bola;

public class Pancingan {
    // Posisi dari ujung hook
    private int positionHookX;
    private int positionHookY;
    private int hookSpeed;

    private String state; // Terdiri dari "IDLE", "RETRACTING_CAUGHT", "RETRACTING_EMPTY" dan "SHOOTING"
    private Bola caughtBola;

    public Pancingan(int x, int y){
        this.positionHookX = x;
        this.positionHookY = y;
        this.state = "IDLE";
        this.hookSpeed = 10;
    }

    public int getPosX(){ return this.positionHookX; }
    public int getPosY(){ return this.positionHookY; }
    public Bola getCaughtBola() { return this.caughtBola; }
    public String getState(){ return this.state; }

    public void setPosition(int x, int y){
        this.positionHookX = x;
        this.positionHookY = y;
    }

    public Bola tryToCatch(ArrayList<Bola> allBolas){
        /*
         * Method untuk mencoba menangkap bola dengan Hook
         */
        for (Bola currentBola : allBolas) {
            Rectangle hitBoxBola = new Rectangle(currentBola.getPosX(), currentBola.getPosY(), currentBola.getSize(), currentBola.getSize());
            if (hitBoxBola.contains(positionHookX, positionHookY)) {
                this.caughtBola = currentBola;
                this.state = "Caught";
                return this.caughtBola;
            }
        }
        return null;
    }

    public void updateState(int targetX, int targetY, int startX, int startY, ArrayList<Bola> collectionBola, int playerX, int playerY){
        if (this.state.equals("SHOOTING")) {
            // Hitung vector
            int travelX = targetX - this.positionHookX;
            int travelY = targetY - this.positionHookY;
            double distance = Math.sqrt((travelX * travelX) + (travelY * travelY));
            double directionX = travelX / distance;
            double directionY = travelY / distance;

            this.positionHookX += directionX * hookSpeed;
            this.positionHookY += directionY * hookSpeed;

            // Collision detection
            Rectangle hitBoxHook = new Rectangle(this.positionHookX, this.positionHookY, 20, 20);
            for (Bola bola : collectionBola) {
                Rectangle hitBoxBola = new Rectangle(bola.getPosX(), bola.getPosY(), bola.getSize(), bola.getSize());
                if (hitBoxHook.intersects(hitBoxBola)) {
                    this.caughtBola = bola;
                    this.state = "RETRACTING_CAUGHT";
                }
            }

            int remainingX = targetX - this.positionHookX;
            int remainingY = targetY - this.positionHookY;
            distance = Math.sqrt((remainingX * remainingX) + (remainingY * remainingY));
            directionX = remainingX / distance;
            directionY = remainingY / distance;
            
            if (distance <= this.hookSpeed) {
                this.state = "RETRACTING_EMPTY";
            }

        }else if(this.state.equals("RETRACTING_CAUGHT")){
            int travelX = playerX - this.positionHookX;
            int travelY = playerY - this.positionHookY;
            double distance = Math.sqrt((travelX * travelX) + (travelY * travelY));
            double directionX = travelX / distance;
            double directionY = travelY / distance;

            this.positionHookX += directionX * hookSpeed;
            this.positionHookY += directionY * hookSpeed;

            this.caughtBola.updatePosition(this.positionHookX, this.positionHookY);

            int remainingX = playerX - this.positionHookX;
            int remainingY = playerY - this.positionHookY;
            distance = Math.sqrt((remainingX * remainingX) + (remainingY * remainingY));
            directionX = remainingX / distance;
            directionY = remainingY / distance;
            
            if (distance <= this.hookSpeed) {
                this.state = "IDLE";
            }
            
        }else if(this.state.equals("RETRACTING_EMPTY")){

        }
    }

    public void updateCaughtBallPosition(){
        /*
         * Method untuk caughtBola mengikuti
         * posisi pancingan
         */
        this.caughtBola.updatePosition(positionHookX, positionHookY);
    }

    public void releaseHook(){
        this.state = "Empty";
        this.caughtBola = null;
    }
}
