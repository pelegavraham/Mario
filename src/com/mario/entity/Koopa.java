package com.mario.entity;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.states.KoopaState;
import com.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Koopa extends Entity {

    private Random random= new Random();

    private int shellCount;

    public Koopa(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

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

        this.koopaState= KoopaState.WALKING;
    }

    @Override
    public void render(Graphics g) {
        if(facing==0) {
            switch (koopaState){
                case WALKING:
                    g.drawImage(Game.koopa[1].getImage(),x, y, width, height, null);
                    break;
                case SHELL:
                    g.drawImage(Game.koopa[3].getImage(),x, y, width, height, null);
                    break;
                case SPINNING:
                    g.drawImage(Game.koopa[5].getImage(),x, y, width, height, null);
                    break;
            }
        }
        else{
            switch (koopaState){
                case WALKING:
                    g.drawImage(Game.koopa[0].getImage(),x, y, width, height, null);
                    break;
                case SHELL:
                    g.drawImage(Game.koopa[2].getImage(),x, y, width, height, null);
                    break;
                case SPINNING:
                    g.drawImage(Game.koopa[4].getImage(),x, y, width, height, null);
                    break;
            }
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(koopaState==KoopaState.SHELL) {
            setVelX(0);
            shellCount++;

            if(shellCount>=300){
                shellCount=0;
                koopaState=KoopaState.WALKING;
            }
        }

        if(koopaState==KoopaState.WALKING || koopaState==KoopaState.SPINNING){
            shellCount=0;

            if(velX==0){
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
        }

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
                    setVelX(koopaState==KoopaState.SPINNING ? 8 : 3);
                    facing=1;
                }
                if (getBoundsRigth().intersects(tile.getBounds())) {
                    setVelX(koopaState==KoopaState.SPINNING ? -8 : -3);
                    facing=0;
                }
            }
        }
        if(falling){
            gravity += 0.1;
            setVelY((int) gravity);
        }
    }
}
