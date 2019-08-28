package com.mario.entity;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;

import java.awt.*;

public class Coin extends Entity {

    public Coin(int x, int y, int width, int heigth, Id id, Handler handler) {
        super(x, y, width, heigth, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.coin.getImage(),x, y, width, height, null);
    }

    @Override
    public void tick() {

    }
}
