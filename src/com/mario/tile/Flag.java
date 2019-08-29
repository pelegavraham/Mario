package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public class Flag extends Tile {

    public Flag(int x, int y, int width, int heigth, boolean soild, Id id, Handler handler) {
        super(x, y, width, heigth, soild, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.flag[0].getImage(), getX(), getY(), width, 64, null);
        g.drawImage(Game.flag[1].getImage(), getX(), getY()+64, width, 64, null);
        g.drawImage(Game.flag[1].getImage(), getX(), getY()+128, width, 64, null);
        g.drawImage(Game.flag[1].getImage(), getX(), getY()+192, width, 64, null);
        g.drawImage(Game.flag[2].getImage(), getX(), getY()+heigth-64, width, 64, null);

    }

    @Override
    public void tick() {

    }
}
