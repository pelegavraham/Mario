package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public class Pipe extends Tile{

    public Pipe(int x, int y, int width, int heigth, boolean soild, Id id, Handler handler, int facing) {
        super(x, y, width, heigth, soild, id, handler);
        this.facing= facing;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.pipe.getImage(),x, y, width, heigth, null);
    }

    @Override
    public void tick() {

    }
}
