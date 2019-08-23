package com.mario.tile;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.powerUp.Mushroom;
import com.mario.gfx.Sprite;

import java.awt.*;

public class PowerUpBlock extends Tile {

    private Sprite powerUp;
    private boolean poppedUp = false;
    private int spriteY = getY();

    public PowerUpBlock(int x, int y, int width, int heigth, boolean soild, Id id, Handler handler, Sprite powerUp) {
        super(x, y, width, heigth, soild, id, handler);
        this.powerUp=powerUp;
    }

    @Override
    public void render(Graphics g) {
        if(!poppedUp)
            g.drawImage(powerUp.getImage(),x, spriteY, width, heigth, null);
        if(!activated)
            g.drawImage(Game.powerUp.getImage(),x, y, width, heigth, null);
        else
            g.drawImage(Game.usedPowerUp.getImage(),x, y, width, heigth, null);
    }

    @Override
    public void tick() {
        if(activated && !poppedUp){
            spriteY--;
            if(spriteY<= y-heigth){
                handler.addEntity(new Mushroom(x,spriteY,width,heigth,Id.mushroom, handler));
                poppedUp=true;
            }
        }
    }
}
