package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTileSize = 16; //16x16 tile is the original size
    final int scale= 3;

    public final int tileSize = originalTileSize * scale; //48 x 48 tile
    final int maxScreenCol = 16; // change if you want
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;     // Start time to make character move 60 fps: keeps program running until stopping
    Player player = new Player(this, keyH);

    int playerX = 100;    // Set Player's default position
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        // Create game loop that will be the core of our game

        // Sleep method
        double drawInterval = 1000000000/FPS; // 1bn ~ 1 second, we use 1bn nanoseconds by 60 FPS -> 16,666,666.667 -> 0.0166666667 draw interval
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread != null){

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000; // by 1 mn to convert into nanoseconds

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // pause the game until this sleepTime is over

                nextDrawTime += drawInterval; /// 0.16666 seconds later

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public void update(){
        player.update(); // call this method

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose();

    }
}
