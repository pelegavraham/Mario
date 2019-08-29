package com.mario;
import com.mario.entity.Entity;
import com.mario.gfx.Sprite;
import com.mario.gfx.SpriteSheet;
import com.mario.gfx.frames.MainFrame;
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
    //public static final String imagesPath = System.getProperty("user.dir")+"\\images";

    private Thread thread;
    private BufferedImage image;
    public static Handler handler;
    public static Camera camera;

    public static SpriteSheet sheet;

    public static Sprite grass;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    public static Sprite pipe;
    public static Sprite coin;
    public static Sprite mushroom;
    public static Sprite upMushroom;

    public static Sprite[] player= new Sprite[10];
    public static Sprite[] goomba= new Sprite[10];
    public static Sprite[] bross= new Sprite[3];
    public static Sprite[] koopa= new Sprite[6];

    public static int coins = 0;
    public static int lives = 5;
    public static int deathScreenTime = 0;
    private static int playerX, playerY;

    public static boolean showDeathScreen = true;
    public static boolean gameOver = false;
    private boolean running= false;

    public Game(){
        Dimension size= new Dimension(WIDTH*SCALE, HEIGHT *SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init(){
        handler= new Handler();
        camera= new Camera();
        addKeyListener(new keyInput());

        sheet= new SpriteSheet("/mario5.png");
        mushroom= new Sprite(sheet, 2,1);
        upMushroom= new Sprite(sheet, 8,1);
        grass= new Sprite(sheet, 1,1);
        powerUp= new Sprite(sheet,3,1);
        usedPowerUp= new Sprite(sheet, 4,1);
        pipe = new Sprite(sheet, 6,1);
        coin = new Sprite(sheet,7,1);

        for(int i=0; i<player.length; i++){
            player[i]= new Sprite(sheet, i+1, 16);
        }
        for(int i=0; i<goomba.length; i++){
            goomba[i]= new Sprite(sheet, i+1, 15);
        }
        for(int i=0; i<bross.length; i++){
            bross[i]= new Sprite(sheet, i+1, 14);
        }
        for(int i=0; i<koopa.length; i++){
            koopa[i]= new Sprite(sheet, i+1, 13);
        }

        try {
            image= ImageIO.read(getClass().getResource("/levelPiped.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(!showDeathScreen){
            g.drawImage(coin.getImage(),20,20,75,75,null);
            g.setColor(Color.white);
            g.setFont(new Font("Courier",Font.BOLD,35));
            g.drawString("X "+coins, 90,75);
        }
        if(showDeathScreen){
            if(!gameOver) {
                g.setColor(Color.white);
                g.setFont(new Font("Courier", Font.BOLD, 35));
                g.drawImage(player[4].getImage(), 500, 300, 100, 100, null);
                g.drawString("X " + lives, 600, 370);
            }
            else{
                g.setColor(Color.white);
                g.setFont(new Font("Stencil", Font.BOLD, 60));
                g.drawString("GAME OVER!", 370, 370);
            }
        }

        g.translate(camera.getX(), camera.getY());
        if(!showDeathScreen) handler.render(g);
        g.dispose();
        bs.show();
    }

    public void tick(){
        handler.tick();

        for(Entity e: handler.entities){
            if(e.getId()== Id.player)
                camera.tick(e);
        }

        if(showDeathScreen && !gameOver) deathScreenTime++;
        if(deathScreenTime >=180){
            showDeathScreen =false;
            deathScreenTime =0;
            handler.clearLevel();
            handler.createLevel(image);
        }
    }

    public static Rectangle getVisibleArea(){
        for(int i=0; i<handler.entities.size(); i++){
            Entity e= handler.entities.get(i);
            if(e.getId()==Id.player){
                playerX = e.getX();
                playerY= e.getY();
                return new Rectangle(playerX-(getFrameWidth()/2-5), playerY-(getFrameHeight()/2-5),getFrameWidth()+10,getFrameHeight()+10);
            }
        }
        return new Rectangle(playerX-(getFrameWidth()/2-5), playerY-(getFrameHeight()/2-5),getFrameWidth()+10,getFrameHeight()+10);
    }

    public static int getFrameWidth(){
        return getWIDTH()*getSCALE();
    }

    public static int getFrameHeight(){
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
}
