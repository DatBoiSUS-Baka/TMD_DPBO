package model;
/*
 * ===================================================
 * Filename     : Pemain.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 9 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package model untuk representasi pemeran utama
 * 
 * ===================================================
 */

public class Pemain {
    private int positionX;
    private int positionY;
    private int SPD;
    private int size;
    private int score;

    public Pemain(int startX, int startY){
        this.positionX = startX;
        this.positionY = startY;
        this.size = 100;
        this.SPD = 10;
        this.score = 0;
    }

    public int getPosX(){
        return this.positionX;
    }

    public int getPosY(){
        return this.positionY;
    }

    public int getScore(){
        return this.score;
    }

    public int getSize() { return this.size; }
    public int getSPD() { return this.SPD; }

    public void gerak(int dx, int dy, int boundX, int boundY){
        // Method untuk berpindah posisi
        int newX = this.positionX + (dx * this.SPD);
        int newY = this.positionY + (dy * this.SPD);

        if (newX >= 0 && (newX + this.size) <= boundX) {
            this.positionX = newX;
        }
        if (newY >= 0 && (newY + this.size) <= boundY) {
            this.positionY = newY;
        }
    }

    public void tambahScore(int newScore){
        // Method untuk menambahkan score pemeran utama
        this.score += newScore;
    }
}
