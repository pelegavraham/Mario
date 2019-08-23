package com.mario.entity.mob;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Goomba extends Entity{

    private int frame = 0;
    private int frameDelay = 0;
    private boolean animate = false;

    Random random= new Random();

    public Goomba(int x, int y, int width, int heigth, Id id, Handler handler) {
        super(x, y, width, heigth, id, handler);

        int dir = random.nextInt(2);
        switch (dir){
            case(0):
                setVelX(-2);
                facing=1;
                break;
            case(1):
                setVelX(2);
                facing=0;
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        if(facing==0) {
            g.drawImage(Game.goomba[frame+5].getImage(),x, y, width, heigth, null);
        }
        else{
            g.drawImage(Game.goomba[frame].getImage(),x, y, width, heigth, null);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(velX != 0) animate=true;
        else animate=false;

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
                    facing=1;
                }
                if (getBoundsRigth().intersects(tile.getBounds())) {
                    setVelX(-2);
                    facing=0;
                }
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
