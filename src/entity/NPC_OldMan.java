package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPC_OldMan extends Entity {


    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {

        // System.out.println("Image loading started");

        try {

            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman_right_2.png")));

        }catch(IOException e) {
            e.printStackTrace();
        }

        // System.out.println("Image loading ended");
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100)+1; // Prendi un numero da 1 a 100

            if(i <= 25) {

                direction = "up";
            }
            if(i > 25 && i <= 50) {

                direction = "down";
            }
            if(i > 50 && i <= 75) {

                direction = "left";
            }
            if(i > 75) {

                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void setDialogue() {

        dialogues[0] = "Bella socione.";
        dialogues[1] = "Sei qui per prendere il tesoro\nda quel bastardo di Pecanius?";
        dialogues[2] = "Peggio brasa della situa!";
        dialogues[3] = "Fai attenzione perchè non\nverrò io a salvarti il culo!";

    }

    public void speak() {

        super.speak();
    }
}
