package com.mario.entity.mob;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.states.PlayerState;
import com.mario.tile.Tile;

import java.awt.*;

public class Player extends Entity {

    private PlayerState state;

    private int frame = 0;
    private int frameDelay = 0;
    private boolean animate = false;
    private int pixelsTravelled = 0;

    public Player(int x, int y, int width, int heigth, Id id, Handler handler) {
        super(x, y, width, heigth, id, handler);
        state= PlayerState.SMALL;
    }

    @Override
    public void render(Graphics g) {
        if (facing == 0) {
            g.drawImage(Game.player[frame + 5].getImage(), x, y, width, heigth, null);
        } else {
            g.drawImage(Game.player[frame].getImage(), x, y, width, heigth, null);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(goingDownPipe)
            pixelsTravelled += velY;

        //if(y<0) y=0;
        //if(y+heigth>=771) y=771-heigth;
        if(velX != 0) animate=true;
        else animate=false;

        for(Tile tile: handler.tiles){
            if(!tile.soild) break;
            if(tile.getId()==Id.wall){
                if(getBoundsTop().intersects(tile.getBounds())){
                    setVelY(0);
                    if(jumping) {
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if(getBoundsBottom().intersects(tile.getBounds())){
                    setVelY(0);
                    if(falling) falling=false;
                }
                else{
                    if(!falling && !jumping){
                        gravity=0.8;
                        falling=true;
                    }
                }
                if(getBoundsLeft().intersects(tile.getBounds())){
                    setVelX(0);
                    x= tile.getX()+ tile.width;
                }
                if(getBoundsRigth().intersects(tile.getBounds())){
                    setVelX(0);
                    x= tile.getX()- tile.width;
                }
            }

            if(tile.getId()== Id.powerUp){

                if (getBoundsTop().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (jumping) {
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                    tile.activated = true;
                }
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (falling) falling = false;
                } else {
                    if (!falling && !jumping) {
                        gravity = 0.8;
                        falling = true;
                    }
                }

                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(0);
                    x = tile.getX() + tile.width;
                }

                if (getBoundsRigth().intersects(tile.getBounds())) {
                    setVelX(0);
                    x = tile.getX() - tile.width;
                }
            }

            for(int i=0; i<handler.entities.size(); i++){
                Entity e= handler.entities.get(i);
                if(e.getId()==Id.mushroom){
                    if(getBounds().intersects(e.getBounds())){
                        int tpX =getX();
                        int tpY =getY();
                        width *= 2;
                        heigth *= 2;
                        setX(tpX-width);
                        setY(tpY-heigth);
                        if(state==PlayerState.SMALL) state= PlayerState.BIG;
                        e.die();
                    }
                }
                else if(e.getId()==Id.goomba) {
                    if(getBoundsBottom().intersects(e.getBoundsTop())){
                        e.die();
                    }
                    else if (getBounds().intersects(e.getBounds())) {
                        if(state==PlayerState.BIG){
                            state=PlayerState.SMALL;
                            width /= 2;
                            heigth /= 2;
                            x+=width;
                            y+=heigth;
                        }
                        else if(state==PlayerState.SMALL) {
                            state = PlayerState.DEAD;
                            die();
                        }
                    }
                }
            }
        }
        if(jumping && !goingDownPipe){
            gravity -= 0.1;
            setVelY((int) -gravity);
            if(gravity <= 0.0){
                jumping = false;
                falling = true;
            }
        }
        if(falling && !goingDownPipe){
            gravity += 0.1;
            setVelY((int) gravity);
        }
        if(animate) {
            frameDelay++;
            if (frameDelay >= 3) {
                frame++;
                if (frame >= 5) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        for(Tile tile: Game.handler.tiles){
            if(tile.getId()==Id.pipe){
                if(getBoundsBottom().intersects(tile.getBounds())) {
                    switch (tile.facing) {
                        case (0):
                            setVelY(-5);
                            setVelX(0);
                            break;
                        case (2):
                            setVelY(5);
                            setVelX(0);
                            break;
                    }
                    if (pixelsTravelled > tile.heigth + heigth) goingDownPipe = false;
                }
            }
        }
    }
}




