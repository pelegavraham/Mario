package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public class Wall extends Tile{

    public Wall(int x, int y, int width, int heigth, boolean solid, Id id, Handler handler) {
        super(x, y, width, heigth,solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.grass.getImage(),x, y, width, heigth, null);
    }

    @Override
    public void tick() {

    }
}
