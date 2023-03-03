package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.*;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16; // 16x16 pixel
    final int scale = 3;

    // Impostazioni schermo
    public final int tileSize = originalTileSize * scale; // 48x48 pixel
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768 pixel
    public final int screenHeight = maxScreenRow * tileSize; // 576 pixel

    // Impostazioni mondo
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // Sistema
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entità e oggetti
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];


    // Stato di gioco
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        // int drawCount = 0;

        while(gameThread != null) {
            
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                // drawCount++;
            }

            if(timer >= 1000000000) {

                timer = 0;
            }
        }
        
    }

    public void update() {

        if(gameState == playState) {

            // Giocatore
            player.update();

            // NPC
            for (Entity entity : npc) {

                if (entity != null) {
                    entity.update();
                }
            }
        }
        if(gameState == pauseState) {

            // Nulla
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Debug
        long drawStart = 0;
        if(keyH.checkDrawTime) {

            drawStart = System.nanoTime();
        }
        


        // Caselle
        tileM.draw(g2);

        for (SuperObject superObject : obj) {

            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // NPC
        for (Entity entity : npc) {

            if (entity != null) {
                entity.draw(g2);
            }
        }

        // GIOCATORE
        player.draw(g2);

        // UI
        ui.draw(g2);

        // Debug
        if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Caricato in: " + passed + "ns", 10, 400);
            System.out.println("Tempo di caricamento > " + passed);
        }


        g2.dispose();

    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }


}
