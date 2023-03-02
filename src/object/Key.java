package object;

import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Key extends SuperObject{
    
    public Key() {

        name = "Chiave";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
