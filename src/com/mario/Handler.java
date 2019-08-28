package com.mario;

import com.mario.entity.Coin;
import com.mario.entity.Entity;
import com.mario.entity.mob.Goomba;
import com.mario.entity.mob.Player;
import com.mario.entity.mob.TowerBoss;
import com.mario.entity.powerUp.Mushroom;
import com.mario.tile.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {

    public LinkedList<Entity> entities = new LinkedList<>();
    public LinkedList<Tile> tiles = new LinkedList<>();

    public void render(Graphics g){
        for(Entity e: entities)
            e.render(g);
        for(Tile t: tiles)
            t.render(g);
    }

    public void tick(){
        for(Entity e: entities)
            e.tick();
        for(Tile t: tiles)
            t.tick();
    }

    public void addEntity(Entity e){
        entities.add(e);
    }
    public void removeEntity(Entity e){
        entities.remove(e);
    }
    public void addTile(Tile t){ tiles.add(t); }
    public void removeTile(Tile t){
        tiles.remove(t);
    }

    public void createLevel(BufferedImage level){

        int width= level.getWidth();
        int height= level.getHeight();

        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){

                int pixel= level.getRGB(x,y);

                int red= (pixel >> 16) & 0xff;
                int green= (pixel >> 8) & 0xff;
                int blue= (pixel) & 0xff;

                if(red==0 && green==0 && blue==0)
                    addTile(new Wall(x*64,y*64,64,64,true, Id.wall,this));
                if(red==255 && green==255 && blue==0)
                    addTile(new PowerUpBlock(x*64,y*64,64,64,true, Id.powerUp,this,Game.upMushroom,1));
                if(red==0 && green==0 && blue==255)
                    addEntity(new Player(x*64,y*64,48,48,Id.player,this));
                if(red==255 && green==119 && blue==0)
                    addEntity(new Goomba(x*64,y*64,64,64, Id.goomba, this));
                if(red==0 && (green>123 && green<129) && blue==0)
                    addTile(new Pipe(x*64, y*64, 64,64*16,true,Id.pipe, this, 128-green));
                if(red==255 && green==250 && blue==0)
                    addEntity(new Coin(x*64, y*64, 64,64,Id.coin, this));
                if(red==255 && green==0 && blue==255)
                    addEntity(new TowerBoss(x*64, y*64, 64,64, Id.bross, this,3));
            }
        }
    }

    public void clearLevel(){
        entities.clear();
        tiles.clear();
    }
}
