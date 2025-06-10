package model;

public class Bola {
    private int positionX;
    private int positionY;
    private int speed;
    private int value;

    public Bola(int startX, int startY){
        this.positionX = startX;
        this.positionY = startY;
        this.speed = 5;
        this.value = 0;
    }

    public int getPosX() { return this.positionX; }
    public int getPosY() { return this.positionY; }
    public int getSpeed() { return this.speed; }
    public int getValue() { return this.value; }
}
