package main;

import java.awt.*;
import java.io.InputStream;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font font, finishFont;
    // BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;

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

        // Key key = new Key();
        // keyImage = key.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {

            // Fai roba del play state dopo
        }
        if(gp.gameState == gp.pauseState) {

            drawPauseScreen();
        }
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
