package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Star extends SuperObject{
	
public OBJ_Star() {
		
		name = "Star";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/Star.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
