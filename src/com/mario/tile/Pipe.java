package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.mob.Plant;

import java.awt.*;

public class Pipe extends Tile{

    public Pipe(int x, int y, int width, int heigth, boolean soild, Id id, Handler handler, int facing, boolean plant) {
        super(x, y, width, heigth, soild, id, handler);
        this.facing= facing;
        if(plant) handler.addEntity(new Plant(x,y-64,width,64,Id.plant,handler));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.pipe.getImage(),x, y, width, heigth, null);
    }

    @Override
    public void tick() {

    }
}
