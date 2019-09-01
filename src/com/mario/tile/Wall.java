package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public class Wall extends Tile{

    private boolean grass;

    public Wall(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler, boolean grass) {
        super(x, y, width, heigth,solid, id, handler);
        this.grass = grass;
    }

    @Override
    public void render(Graphics g) {
        if(grass) g.drawImage(Game.grass.getImage(),x, y, width, heigth, null);
        else g.drawImage(Game.ground.getImage(),x, y, width, heigth, null);
    }

    @Override
    public void tick() {

    }
}
