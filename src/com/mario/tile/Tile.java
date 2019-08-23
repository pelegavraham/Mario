package com.mario.tile;

import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public abstract class Tile {

    public int x, y;
    public int width, heigth;

    public boolean soild;
    public boolean activated= false;

    public int velX, velY;
    public Id id;
    public Handler handler;

    public Tile(int x, int y, int width, int heigth, boolean soild, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.soild = soild;
        this.id = id;
        this.handler=handler;
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public void die(){
        handler.removeTile(this);
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

    public boolean isSoild() {
        return soild;
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

