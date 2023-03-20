package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity {
    
    public Key(GamePanel gp) {

        super(gp);

        name = "Chiave";
        down1 = setup("/objects/key");
    }
}
