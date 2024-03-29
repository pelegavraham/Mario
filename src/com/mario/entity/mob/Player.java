package com.mario.entity.mob;

import com.mario.Game;
import com.mario.Handler;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.states.BossState;
import com.mario.states.KoopaState;
import com.mario.states.PlayerState;
import com.mario.tile.Tile;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {

    private PlayerState state;

    private int frame = 0;
    private int frameDelay = 0;
    private boolean animate = false;
    private int pixelsTravelled = 0;

    private Random random= new Random();

    public Player(int x, int y, int width, int heigth, Id id, Handler handler) {
        super(x, y, width, heigth, id, handler);
        state= PlayerState.SMALL;
    }

    @Override
    public void render(Graphics g) {
        if (facing == 0) {
            g.drawImage(Game.player[frame + 5].getImage(), x, y, width, height, null);
        } else {
            g.drawImage(Game.player[frame].getImage(), x, y, width, height, null);
        }
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        if(goingDownPipe)
            pixelsTravelled += velY;

        if(velX != 0) animate=true;
        else animate=false;

        for(int j=0; j< handler.tiles.size(); j++) {

            Tile tile = handler.tiles.get(j);
            if (tile.isSoild() && !goingDownPipe) {
                if(getBounds().intersects(tile.getBounds())){
                    if(tile.getId()== Id.flag){
                        Game.switchLevel();
                        Game.levelEnd.play();
                    }
                }
                if (getBoundsTop().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (jumping) {
                        jumping = false;
                        gravity = 0.8;
                        falling = true;
                    }
                    if (tile.getId() == Id.powerUp) tile.activated = true;
                }
                if (getBoundsBottom().intersects(tile.getBounds())) {
                    setVelY(0);
                    if (falling) falling = false;
                    if (goingDownPipe) goingDownPipe = false;
                } else {
                    if (!falling && !jumping) {
                        gravity = 0.8;
                        falling = true;
                    }
                }
                if (getBoundsLeft().intersects(tile.getBounds())) {
                    setVelX(0);
                    x = tile.getX() + width;
                }
                if (getBoundsRigth().intersects(tile.getBounds())) {
                    setVelX(0);
                    x = tile.getX() - width;
                }
            }
        }

        for(int i=0; i<handler.entities.size(); i++){
            Entity e= handler.entities.get(i);
            if(e.getId()==Id.mushroom){
                switch (e.getType()){
                    case(0):
                        if(getBounds().intersects(e.getBounds())){
                            int tpX =getX();
                            int tpY =getY();
                            width += (width/3);
                            height += (height /3);
                            setX(tpX-width);
                            setY(tpY- height);
                            if(state==PlayerState.SMALL) state= PlayerState.BIG;
                            e.die();
                            Game.thePowerUp.play();
                        }
                        break;
                    case(1):
                        if(getBounds().intersects(e.getBounds())) {
                            Game.lives++;
                            e.die();
                            Game.thePowerUp.play();
                        }
                        break;
                }
            }
            else if(e.getId()==Id.goomba || e.getId()==Id.bross || e.getId()==Id.plant) {
                if(getBoundsBottom().intersects(e.getBoundsTop())){
                    if(e.getId()==Id.goomba) {
                        e.die();
                        Game.stomp.play();
                    }
                    else if(e.attackable){
                        e.hp--;
                        e.falling=true;
                        e.gravity= 3.0;
                        e.bossState= BossState.RECOVERING;
                        e.attackable= false;
                        e.phaseTime=0;

                        jumping=true;
                        falling=false;
                        gravity=3.5;
                    }
                }
                else if (getBounds().intersects(e.getBounds())) {
                    if(state==PlayerState.BIG){
                        state=PlayerState.SMALL;
                        width -= width/4;
                        height -= height /4;
                        x+=width;
                        y+= height;
                    }
                    else if(state==PlayerState.SMALL) {
                        state = PlayerState.DEAD;
                        die();
                    }
                }
            }
            else if(e.getId()==Id.coin && getBounds().intersects(e.getBounds())){
                Game.coins++;
                if(Game.coins>=100){
                    Game.lives++;
                    Game.coins=0;
                    Game.thePowerUp.play();
                }
                e.die();
                Game.collectCoin.play();
            }
            else if(e.getId()==Id.koopa){

                if (e.koopaState==KoopaState.WALKING) {

                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaState.SHELL;

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    } else if (getBounds().intersects(e.getBounds())) {
                        if(state==PlayerState.BIG){
                            state=PlayerState.SMALL;
                            width -= width/4;
                            height -= height /4;
                            x+=width;
                            y+= height;
                        }
                        else if(state==PlayerState.SMALL) {
                            state = PlayerState.DEAD;
                            die();
                        }
                    }

                }
                else if(e.koopaState==KoopaState.SHELL) {

                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaState.SPINNING;

                        int dir = random.nextInt(2);
                        switch (dir) {
                            case (0):
                                e.setVelX(-8);
                                facing = 1;
                                break;
                            case (1):
                                e.setVelX(8);
                                facing = 0;
                                break;
                        }

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    } else if (getBoundsLeft().intersects(e.getBoundsRigth())) {
                        e.setVelX(-8);
                        e.koopaState = KoopaState.SPINNING;
                    } else if (getBoundsRigth().intersects(e.getBoundsLeft())) {
                        e.setVelX(8);
                        e.koopaState = KoopaState.SPINNING;
                    }

                }
                else if(e.koopaState==KoopaState.SPINNING) {

                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaState.SHELL;

                        jumping = true;
                        falling = false;
                        gravity = 3.5;
                    } else if (getBounds().intersects(e.getBounds())) {
                        if(state==PlayerState.BIG){
                            state=PlayerState.SMALL;
                            width -= width/4;
                            height -= height /4;
                            x+=width;
                            y+= height;
                        }
                        else if(state==PlayerState.SMALL) {
                            state = PlayerState.DEAD;
                            die();
                        }
                    }

                }
            }
        }


        if(jumping && !goingDownPipe){
            gravity -= 0.17;
            setVelY((int) -gravity);
            if(gravity <= 0.5){
                jumping = false;
                falling = true;
            }
        }
        if(falling && !goingDownPipe){
            gravity += 0.17;
            setVelY((int) gravity);
        }
        if(animate) {
            frameDelay++;
            if (frameDelay >= 3) {
                frame++;
                if (frame >= 5) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        if(goingDownPipe) {
            for(int k=0; k< Game.handler.tiles.size(); k++){
                Tile tile= Game.handler.tiles.get(k);
                if (tile.getId() == Id.pipe) {
                    if (getBoundsBottom().intersects(tile.getBounds())) {
                        switch (tile.facing) {
                            case (0):
                                setVelY(-5);
                                setVelX(0);
                                break;
                            case (2):
                                setVelY(5);
                                setVelX(0);
                                break;
                            case (3):
                                setVelY(0);
                                break;
                        }
                        if (pixelsTravelled > tile.heigth*2 + height){ goingDownPipe = false; }
                    }
                }
            }
        }
    }
}




