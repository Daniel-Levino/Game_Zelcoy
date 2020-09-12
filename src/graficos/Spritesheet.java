package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	
	public Spritesheet(String enderecoImagem) {
		try {
			this.spritesheet=ImageIO.read(getClass().getResource(enderecoImagem));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int w, int h) {
		return this.spritesheet.getSubimage(x, y, w, h);
	}
	
}
