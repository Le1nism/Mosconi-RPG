package main;

import java.awt.*;
import java.io.InputStream;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font font, finishFont;

    public boolean messageOn = false;
    public String message = "";

    public String currentDialogue = "";

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
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(Color.white);

        // Play state
        if(gp.gameState == gp.playState) {

            // Fai roba del play state dopo
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
