package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public class Coin extends Tile{

    public Coin(int x, int y, int width, int heigth, boolean soild, Id id, Handler handler) {
        super(x, y, width, heigth, soild, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.coin.getImage(),x, y, width, heigth, null);
    }

    @Override
    public void tick() {

    }
}
