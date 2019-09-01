package com.mario.input;

import com.mario.Game;
import com.mario.Id;
import com.mario.entity.Entity;
import com.mario.tile.Tile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyInput implements KeyListener{
    @Override
    public void keyTyped(KeyEvent e) {
        //not using
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key= e.getKeyCode();
        for(Entity entity: Game.handler.entities) {
            if(entity.getId()== Id.player){
                if(entity.goingDownPipe) return;
                switch (key) {
                    case KeyEvent.VK_W:
                        for(Tile t :Game.handler.tiles) {
                            if (t.isSoild())
                                if (entity.getBoundsBottom().intersects(t.getBounds()))
                                    if (!entity.jumping) {
                                        entity.jumping = true;
                                        entity.gravity = 8.0;
                                        Game.jump.play();
                                    }
                        }
                        break;
                    case KeyEvent.VK_S:
                        for(Tile t :Game.handler.tiles){
                            if(t.getId()==Id.pipe)
                                if(entity.getBoundsBottom().intersects(t.getBounds()))
                                    if(! entity.goingDownPipe) entity.goingDownPipe=true;
                        }
                        break;
                    case KeyEvent.VK_A:
                        entity.setVelX(-5);
                        entity.facing =0;
                        break;
                    case KeyEvent.VK_D:
                        entity.setVelX(5);
                        entity.facing =1;
                        break;
                    case KeyEvent.VK_Q:
                        entity.die();
                        break;
                    case KeyEvent.VK_P:
                        Game.theme.pause();
                        break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key= e.getKeyCode();
        for(Entity entity: Game.handler.entities) {
            if(entity.getId()==Id.player) {
                switch (key) {
                    case KeyEvent.VK_W:
                        entity.setVelY(0);
                        break;
                    //case KeyEvent.VK_S:
                    //    entity.setVelY(0);
                    //    break;
                    case KeyEvent.VK_A:
                        entity.setVelX(0);
                        break;
                    case KeyEvent.VK_D:
                        entity.setVelX(0);
                        break;
                }
            }
        }
    }
}
