package game.rpg;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS

    final int originalTileSize = 16; //16x16 tile default size player/npc/map
    final int scale = 3;

    final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    Thread gameThread;

    KeyHandler keyHandler = new KeyHandler();

    int FPS = 60;

    //Player defauult pos

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //Calls run method of Runnable interface
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawInterval = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            //System.out.println("Game running...");

            // 1 Update info character pos
            update();
            // 2 Draw with updated info
            repaint();

            try {
                double remainingTime = nextDrawInterval - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawInterval += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void update() {

        if (keyHandler.up) {
            playerY -= playerSpeed;
        }

        if (keyHandler.down) {
            playerY += playerSpeed;
        }

        if (keyHandler.left) {
            playerX -= playerSpeed;
        }

        if (keyHandler.right) {
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        g2d.fillRect(playerX, playerY, tileSize, tileSize);

        g2d.dispose();

    }

}