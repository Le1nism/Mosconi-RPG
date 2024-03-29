package main;

import entity.Entity;
import object.Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font font, finishFont;

    BufferedImage heart_full, heart_half, heart_blank;

    public String currentDialogue = "";

    public int commandNum = 0;

    public UI (GamePanel gp){

        this.gp = gp;

        try {

        InputStream is = getClass().getResourceAsStream("/font/font.ttf");
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
        }catch(Exception e){

            e.printStackTrace();
        }

        try {

            InputStream is = getClass().getResourceAsStream("/font/font.ttf");
            assert is != null;
            finishFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
            }catch(Exception e){
    
                e.printStackTrace();
            }

        // Crea oggetti in HUD
        Entity heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void draw(Graphics2D g2) throws IOException {

        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(Color.white);

        // Title state
        if(gp.gameState == gp.titleState) {

            drawTitleScreen();
        }
        // Play state
        if(gp.gameState == gp.playState) {

            drawPlayerLife();
        }
        // Pause state
        if(gp.gameState == gp.pauseState) {

            drawPauseScreen();
        }
        // Dialogue state
        if(gp.gameState == gp.dialogueState) {

            drawDialogueScreen();
        }
    }

    private void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while(i < gp.player.maxLife/2) {

            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize + 5;
        }

        // Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // Vita attuale
        while(i < gp.player.life) {

            g2.drawImage(heart_half, x, y, null);
            i++;

            if(i < gp.player.life) {

                g2.drawImage(heart_full, x, y, null);
            }

            i++;
            x += gp.tileSize + 5;
        }


    }

    private void drawTitleScreen() throws IOException {

        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Nome titolo
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70f));
        String text = "Mosconi RPG";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 2;

        // Ombra
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // Colore principale
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));
        text = "v0.8a-beta";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        // Immagine sfondo
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/mainMenu.jpg"))), x, y, 128, 128, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));

        text = "Nuova Partita";
        x = getXForCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);

        if(commandNum == 0) {

            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Carica Partita";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if(commandNum == 1) {

            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Esci dal Gioco";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if(commandNum == 2) {

            g2.drawString(">", x-gp.tileSize, y);
        }

    }

    public void drawDialogueScreen() {

        // Finestra
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialogue.split("\n")) {

            g2.drawString(line, x, y);
            y += 40;
        }


    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(80f));
        String text = "PAUSA";
        int x = getXForCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
