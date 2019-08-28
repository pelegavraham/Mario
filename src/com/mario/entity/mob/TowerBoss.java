package com.mario.entity.mob;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.states.BossState;
import com.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class TowerBoss extends Entity {

    public int jumpTime = 0;
    public boolean addJumpTime = false;
    public Random random;

    public TowerBoss(int x, int y, int width, int height, Id id, Handler handler, int hp) {
        super(x, y, width, height, id, handler);
        this.hp = hp;
        this.bossState= BossState.IDLE;
        random= new Random();
    }

    @Override
    public void render(Graphics g) {
        if(bossState==BossState.IDLE | bossState==BossState.SPINNING)
            g.drawImage(Game.bross[0].getImage(), x, y, width, height, null);
        else if(bossState==BossState.RECOVERING)
            g.drawImage(Game.bross[1].getImage(), x, y, width, height, null);
        else
            g.drawImage(Game.bross[2].getImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        x+= velX;
        y+= velY;
        if(hp<=0) die();
        phaseTime++;

        if((phaseTime>=180 && bossState==BossState.IDLE ) || (phaseTime>=600 &&bossState==BossState.SPINNING)) chooseState();

        if(phaseTime>=180 && bossState==BossState.RECOVERING){
            bossState=BossState.SPINNING;
            phaseTime=0;
        }

        if(phaseTime>=360 && bossState==BossState.SPINNING){
            bossState=BossState.IDLE;
            phaseTime=0;
        }

        if(bossState==BossState.IDLE || bossState==BossState.RECOVERING){
            setVelX(0);
            setVelY(0);
        }

        attackable = (bossState==BossState.JUMPING || bossState==BossState.RUNNING) ? true : false;

        if(bossState != BossState.JUMPING){ addJumpTime=false; jumpTime=0; }

        if(addJumpTime) {
            jumpTime++;
            if(jumpTime>=30){
                addJumpTime=false;
                jumpTime=0;
            }
            if(!jumping && !falling){
                jumping=true;
                gravity=0.8;
            }
        }

        for(int j=0; j< handler.tiles.size(); j++) {

            Tile tile = handler.tiles.get(j);
            if (tile.isSoild()) {
                if (getBoundsTop().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (jumping) {
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (falling){ falling = false; addJumpTime=true; }
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(0);
                    if(bossState==BossState.RUNNING) setVelX(4);
                    x = tile.getX() + tile.width;
                }
                if (getBoundsRigth().intersects(tile.getBounds())) {
                    setVelX(0);
                    if(bossState==BossState.RUNNING) setVelX(-4);
                    x = tile.getX() - tile.width;
                }
            }
        }

        for(int i=0; i<handler.entities.size(); i++){
            Entity e = handler.entities.get(i);
            if(e.getId()==Id.player){
                if(bossState==BossState.JUMPING){
                    if(jumping || falling){
                        if(getX()>=e.getX()-4 && getX()<=e.getX()+4) setVelX(0);
                        else if(e.getX()<getX()) setVelX(-3);
                        else if(e.getX()>getX()) setVelX(3);
                    }
                    else setVelX(0);
                }
                else if(bossState==BossState.SPINNING){
                    if(e.getX()<getX()) setVelX(-3);
                    else if(e.getX()>getX()) setVelX(3);
                }
            }
        }

        if(jumping){
            gravity -= 0.17;
            setVelY((int) -gravity);
            if(gravity <= 0.5){
                jumping = false;
                falling = true;
            }
        }
        if(falling){
            gravity += 0.17;
            setVelY((int) gravity);
        }
    }
    public void chooseState(){
        int nextPhase = random.nextInt(2);
        if(nextPhase==0){
            bossState= BossState.RUNNING;
            int dir = random.nextInt(2);
            setVelX(dir==0 ? -4 : 4);
        }
        else {
            bossState= BossState.JUMPING;
            jumping=true;
            gravity=8.0;
        }
        phaseTime=0;
    }
}
