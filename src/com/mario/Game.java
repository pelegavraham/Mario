package com.mario;
import com.mario.entity.Entity;
import com.mario.gfx.Sprite;
import com.mario.gfx.SpriteSheet;
import com.mario.input.keyInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH =270;
    public static final int HEIGHT =192;
    public static final int SCALE =4;
    public static final String NAME="Super-Mario";

    private Thread thread;
    private boolean running= false;
    private BufferedImage image;
    public static Handler handler;
    public static SpriteSheet sheet;

    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    public static Sprite pipe;

    public static Sprite[] player= new Sprite[10];
    public static Sprite[] goomba= new Sprite[10];

    public static Sprite mushroom;
    public static Camera camera;


    public Game(){
        Dimension size= new Dimension(WIDTH*SCALE, HEIGHT *SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init(){
        handler= new Handler();
        sheet= new SpriteSheet("/mario5.png");
        camera= new Camera();
        mushroom= new Sprite(sheet, 2,1);
        addKeyListener(new keyInput());
        grass= new Sprite(sheet, 1,1);
        powerUp= new Sprite(sheet,3,1);
        usedPowerUp= new Sprite(sheet, 4,1);
        pipe = new Sprite(sheet, 6,1);
        for(int i=0; i<player.length; i++){
            player[i]= new Sprite(sheet, i+1, 16);
        }
        for(int i=0; i<goomba.length; i++){
            goomba[i]= new Sprite(sheet, i+1, 15);
        }

        try {
            image= ImageIO.read(getClass().getResource("/levelPiped.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.createLevel(image);
    }

    public synchronized void start(){
        if(running) return;
        running=true;
        thread= new Thread(this, "thread");
        thread.start();
    }

    public synchronized void stop(){
        if(! running) return;
        running=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int ticks = 0;

        while (running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer += 1000;
                System.out.println(frames+ " Frames per second , "+ ticks +" Updates per second");
                ticks=0;
                frames=0;
            }
        }
        stop();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g= bs.getDrawGraphics();
        g.setColor(Color.blue);
        g.fillRect(0,0,getWidth(),getHeight());
        g.translate(camera.getX(), camera.getY());
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public void tick(){
        handler.tick();

        for(Entity e: handler.entities){
            if(e.getId()== Id.player)
                camera.tick(e);
        }
    }

    public int getFrameWidth(){
        return getWIDTH()*getSCALE();
    }

    public int getFrameHeight(){
        return getHEIGHT()*getSCALE();
    }

    public static void main (String [] args){
        Game game=new Game();
        JFrame frame= new JFrame(NAME);
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }

    //==================================================================================================================

    public static int getWIDTH() {
        return WIDTH*getSCALE();
    }

    public static int getHEIGHT() {
        return HEIGHT*getSCALE();
    }

    public static int getSCALE() {
        return SCALE;
    }

    public static String getNAME() {
        return NAME;
    }

}
