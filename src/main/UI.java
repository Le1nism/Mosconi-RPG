package main;

import java.io.InputStream;
import java.text.DecimalFormat;

import object.Key;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {
    
    GamePanel gp;
    Font font, finishFont;
    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI (GamePanel gp){

        this.gp = gp;

        try {

        InputStream is = getClass().getResourceAsStream("/font/font.ttf");
        font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
        }catch(Exception e){

            e.printStackTrace();
        }

        try {

            InputStream is = getClass().getResourceAsStream("/font/font.ttf");
            finishFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
            }catch(Exception e){
    
                e.printStackTrace();
            }

        Key key = new Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if(gameFinished) {

            g2.setFont(font);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "Il tesoro è tuo!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);

            text = "Completato in: " + dFormat.format(playTime) + "s!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*4);
            g2.drawString(text, x, y);

            g2.setFont(finishFont);
            g2.setColor(Color.yellow);

            text = "Congratulazioni!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }
        else{
            g2.setFont(font);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 55);

            // Tempo
            playTime += (double)1/60;
            g2.drawString("Tempo: "+ dFormat.format(playTime), gp.tileSize*12, 55);
    
            // Messaggio
            if(messageOn) {
    
                g2.setFont(g2.getFont().deriveFont(20f));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*35/3);
    
                messageCounter++;
                if(messageCounter > 120) {
    
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
