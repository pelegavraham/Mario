package com.mario.entity.mob;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.tile.Tile;

import java.awt.*;

public class Player extends Entity {

    private int frame = 0;
    private int frameDelay = 0;
    private boolean animate = false;

    public Player(int x, int y, int width, int heigth, Id id, Handler handler) {
        super(x, y, width, heigth, id, handler);
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
        //if(y<0) y=0;
        //if(y+heigth>=771) y=771-heigth;
        if(velX != 0) animate=true;
        else animate=false;

        for(Tile tile: handler.tiles){
            if(! tile.soild) break;
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

                //if(getBoundsTop().intersects(tile.getBounds())) tile.activated=true;
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
                        e.die();
                    }
                }
                else if(e.getId()==Id.goomba) {
                    if(getBoundsBottom().intersects(e.getBoundsTop())){
                        e.die();
                    }
                    else if (getBounds().intersects(e.getBounds())) {
                        die();
                    }
                }
            }
        }
        if(jumping){
            gravity -= 0.1;
            setVelY((int) -gravity);
            if(gravity <= 0.0){
                jumping = false;
                falling = true;
            }
        }
        if(falling){
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
    }
}
/*
public void tick() {
        x += velX;
        y += velY;

        if (velX != 0) animate = true;
        else animate = false;
        for (int i = 0; i < handler.tiles.size(); i++) {
            Tile t = handler.tiles.get(i);
            if (t.isSoild()) {
                if (t.getId() == Id.wall) {
                    if (getBoundsTop().intersects(t.getBounds())) {
                        setVelY(0);
                        if (jumping) {
                            jumping = false;
                            gravity = 0.8;
                            falling = true;
                        }

                    }

                    if (getBoundsBottom().intersects(t.getBounds())) {
                        setVelY(0);
                        if (falling) falling = false;
                    } else {
                        if (!falling && !jumping) {
                            gravity = 0.8;
                            falling = true;
                        }
                    }

                    if (getBoundsLeft().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() + t.width;
                    }

                    if (getBoundsRigth().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() - t.width;
                    }
                }
                if (t.getId() == Id.powerUp) {

                    if (getBoundsTop().intersects(t.getBounds())) {
                        setVelY(0);
                        if (jumping) {
                            jumping = false;
                            gravity = 0.8;
                            falling = true;
                        }
                        t.activated = true;
                    }
                    if (getBoundsBottom().intersects(t.getBounds())) {
                        setVelY(0);
                        if (falling) falling = false;
                    } else {
                        if (!falling && !jumping) {
                            gravity = 0.8;
                            falling = true;
                        }
                    }

                    if (getBoundsLeft().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() + t.width;
                    }

                    if (getBoundsRigth().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() - t.width;
                    }
                }
            }

            for (int j = 0; j < handler.entities.size(); j++) {
                Entity e = handler.entities.get(j);

                if (e.getId() == Id.mushroom) {
                    if (getBounds().intersects(e.getBounds())) {
                        int tpx = getX();
                        int tpy = getY();
                        width *= 2;
                        heigth *= 2;
                        setX(tpx - width);
                        setY(tpy - heigth);
                        e.die();
                    }
                } else if (e.getId() == Id.goomba) {
                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.die();
                    } else if (getBounds().intersects(e.getBounds())) {
                        die();
                    }
                }
            }
        }
        if (jumping) {
            gravity -= 0.1;
            setVelY((int) -gravity);
            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }
        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);
        }
        if (animate) {
            frameDelay++;
            if (frameDelay >= 3) {
                frame++;
                if (frame >= 5) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}

 */



