package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        // Status personaggio
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {

        // System.out.println("Image loading started");

            up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
            up2 = setup ("/player/up_2", gp.tileSize, gp.tileSize);
            down1 = setup ("/player/down_1", gp.tileSize, gp.tileSize);
            down2 = setup ("/player/down_2", gp.tileSize, gp.tileSize);
            left1 = setup ("/player/left_1", gp.tileSize, gp.tileSize);
            left2 = setup ("/player/left_2", gp.tileSize, gp.tileSize);
            right1 = setup ("/player/right_1", gp.tileSize, gp.tileSize);
            right2 = setup ("/player/right_2", gp.tileSize, gp.tileSize);

        // System.out.println("Image loading ended");
    }

    public void getPlayerAttackImage() {

        attackUp1 = setup("/player/attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/attack_right_2", gp.tileSize*2, gp.tileSize);

    }

    public void update() {

        if(attacking) {

            attacking();
        }
        else {

            if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

                if(keyH.upPressed) {
                    direction = "up";
                }
                else if(keyH.downPressed) {
                    direction = "down";
                }
                else if(keyH.leftPressed) {
                    direction = "left";
                }
                else if(keyH.rightPressed){
                    direction = "right";
                }

                // Controlla collisione con caselle
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // Controlla collisione con oggetti
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                // Controlla collisione con NPC
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                // Controlla collisione con mostri
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                contactMonster(monsterIndex);

                // Controlla eventi
                gp.eHandler.checkEvent();

                // Se la collisione è falsa, il giocatore può muoversi
                if(!collisionOn) {

                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                gp.keyH.enterPressed = false;

                spriteCounter++;
                if(spriteCounter > 12) {
                    if(spriteNum == 1) {
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }

            // DEVE essere fuori dall'if sopra
            if(invincible) {

                invincibleCounter++;

                if(invincibleCounter > 60) {

                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    public void attacking() {

        spriteCounter++;
        if(spriteCounter <= 5) {

            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {

            spriteNum = 2;
        }
        if(spriteCounter > 25) {

            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }


    private void contactMonster(int i) {

        if(i != 999) {

            if(!invincible) {

                life -= 1;
                invincible = true;
            }
        }
    }

    private void interactNPC(int npcIndex) {

        if(gp.keyH.enterPressed) {

            if(npcIndex != 999) {

                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
            }
            else  {

                attacking = true;
            }
        }
    }

    public void pickUpObject(int i) {

        if(i != 999) {

        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if(!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if(attacking) {

                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
            }
            case "down" -> {
                if(!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if(attacking) {

                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
            }
            case "left" -> {
                if(!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if(attacking) {

                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
            }
            case "right" -> {
                if(!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if(attacking) {

                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
            }
        }

        if(invincible) {

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        // Resetta alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
