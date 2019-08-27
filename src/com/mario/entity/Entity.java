package com.mario.entity;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public abstract class Entity {

    public int x, y;
    public int width, heigth;
    public int velX, velY;
    public Id id;
    public Handler handler;
    public boolean jumping = false;
    public boolean falling = true;
    public double gravity = 0.0;
    public int facing = 0; // 0-left , 1-rigth
    public boolean goingDownPipe= false;

    public Entity(int x, int y, int width, int heigth, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.id = id;

        this.handler = handler;
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public void die(){
        handler.removeEntity(this);
        Game.lives--;
        Game.showDeathScrean = true;

        if(Game.lives <= 0) Game.gameOver=true;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width,heigth);
    }

    public Rectangle getBoundsTop(){
        return new Rectangle(x+10,y,width-20,5);
    }

    public Rectangle getBoundsBottom(){
        return new Rectangle(x+10,y+heigth-5,width-20,5);
    }

    public Rectangle getBoundsLeft(){
        return new Rectangle(x,y+10,5,heigth-20);
    }

    public Rectangle getBoundsRigth(){
        return new Rectangle(x+width-5,y+10,5,heigth-20);
    }

    //==================================================================================================================

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
