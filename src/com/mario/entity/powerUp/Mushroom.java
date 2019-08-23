package com.mario.entity.powerUp;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Mushroom extends Entity {

    Random random= new Random();

    public Mushroom(int x, int y, int width, int heigth, Id id, Handler handler) {
        super(x, y, width, heigth, id, handler);

        int dir = random.nextInt(2);
        switch (dir){
            case(0):
                setVelX(-2);
                break;
            case(1):
                setVelX(2);
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.mushroom.getImage(), x, y, width, heigth, null);
    }

    @Override
    public void tick() {

        x+= velX;
        y+= velY;

        for(Tile tile: handler.tiles){
            if(! tile.soild) break;
            if(tile.getId()==Id.wall) {
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (falling) falling = false;
                } else {
                    if (!falling) {
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(2);
                }
                if (getBoundsRigth().intersects(tile.getBounds())) {
                    setVelX(-2);
                }
            }
        }
        if(falling){
            gravity += 0.1;
            setVelY((int) gravity);
        }
    }
}
