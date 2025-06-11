package model;

/*
 * 11 Juni 2025
 */

public class Bola {
    private int positionX;
    private int positionY;
    private int speed;
    private int value;
    private int size;

    public Bola(int startX, int startY){
        this.positionX = startX;
        this.positionY = startY;
        this.speed = 5;
        this.value = 0;
        this.size = 50;
    }

    public int getPosX() { return this.positionX; }
    public int getPosY() { return this.positionY; }
    public int getSpeed() { return this.speed; }
    public int getValue() { return this.value; }
    public int getSize() { return this.size; }

    public void gerak(int dx, int dy){
        /*
         * Method untuk membuat bola dapat bergerak
         */
        int newX = this.positionX + (dx * speed);
        int newY = this.positionY + (dy * speed);
    }
}
