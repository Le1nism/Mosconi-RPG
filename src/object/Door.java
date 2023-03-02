package object;

import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Door extends SuperObject{

    public Door() {

        name = "Porta";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));

        }catch(IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}
