package com.mario.entity.mob;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.tile.Tile;

import java.awt.*;

public class Plant extends Entity {

    private int wait = 0;
    private int pixelsTravelled = 0;

    private boolean moving=false;
    private boolean insidePipe=true;

    public Plant(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.plant.getImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        y+= velY;
        if(!moving) wait++;

        if(wait>=180){
            insidePipe= insidePipe ? false : true;
            moving=true;
            wait=0;
        }

        if(moving){
            setVelY(insidePipe ? -3 : 3);
            pixelsTravelled += velY;

            if(pixelsTravelled >= getHeight() || pixelsTravelled<= -getHeight()){
                pixelsTravelled=0;
                moving=false;
                setVelY(0);
            }
        }
    }
}
