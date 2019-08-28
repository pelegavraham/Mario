package com.mario.entity;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.states.BossState;

import java.awt.*;

public abstract class Entity {

    public int x, y;
    public int width, height;
    public int velX, velY;
    public int facing = 0; // 0-left , 1-rigth
    public int hp;  //help points
    public int phaseTime;
    public int type;
    public double gravity = 0.0;

    public Id id;
    public Handler handler;
    public BossState bossState;

    public boolean goingDownPipe= false;
    public boolean jumping = false;
    public boolean falling = true;
    public boolean attackable = false;

    public Entity(int x, int y, int width, int height, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        this.handler = handler;
    }

    public abstract void render(Graphics g);

    public abstract void tick();

    public void die(){
        handler.removeEntity(this);
        if(getId()==Id.player){
            Game.lives--;
            Game.showDeathScreen = true;

            if(Game.lives <= 0) Game.gameOver=true;
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,width, height);
    }

    public Rectangle getBoundsTop(){
        return new Rectangle(x+10,y,width-20,5);
    }

    public Rectangle getBoundsBottom(){
        return new Rectangle(x+10,y+ height -5,width-20,5);
    }

    public Rectangle getBoundsLeft(){
        return new Rectangle(x,y+10,5, height -20);
    }

    public Rectangle getBoundsRigth(){
        return new Rectangle(x+width-5,y+10,5, height -20);
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getType(){
        return type;
    }
}
