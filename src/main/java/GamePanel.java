import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int originalTileSize = 16; //16x16 tile is the original size
    final int scale= 3;

    final int tileSize = originalTileSize * scale; //48 x 48 tile
    final int maxScreenCol = 16; // change if you want
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;     // Start time to make character move 60 fps: keeps program running until stopping

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


        // Delta/ Accumulator method

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime(); // more precise than millis
        long currentTime;
        // Display FPS
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // in every loop we add the passed time to this timer

            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }
    public void update(){
        // in our case playerSpeed is 4 pixels at start
        if(keyH.upPressed == true){
            playerY -= playerSpeed;
        }else if(keyH.downPressed == true){
            playerY += playerSpeed;
        } else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
        }else if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();

    }
}
