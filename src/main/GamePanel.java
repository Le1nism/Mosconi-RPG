package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // Entità e oggetti
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();


    // Stato di gioco
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    

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
        aSetter.setMonster();
        playMusic(0);
        stopMusic();
        gameState = titleState;
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

            for (Entity entity : monster) {

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

        // Menu principale
        if(gameState == titleState) {

            try {
                ui.draw(g2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else { // Altri

            // Caselle
            tileM.draw(g2);

            // Aggiungi entità alla lista
            entityList.add(player);
            for (Entity entity : npc) {

                if (entity != null) {

                    entityList.add(entity);
                }
            }

            for (Entity entity : obj) {

                if (entity != null) {

                    entityList.add(entity);
                }
            }

            for (Entity value : monster) {

                if (value != null) {

                    entityList.add(value);
                }
            }

            // Ordina
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // Mostra entità
            for (Entity entity : entityList) {

                entity.draw(g2);
            }

            // Svuota lista
            for(int i = 0; i < entityList.size(); i++) {

                entityList.remove(i);
            }

            // UI
            try {

                ui.draw(g2);
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }

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
