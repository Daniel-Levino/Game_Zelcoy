package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.Game;

public class UI {
	private int green = 250;
	private int red = 0;
	private int damagesAtual, damages;
	
	
	public void render (Graphics g) {
		
		g.setColor(new Color(setRed(), setGreen(), 80));
		g.fillRect(10, 10, (int)((Game.player.getLife()/Game.player.getMaxLife())*Game.WIDTH/2), 16);
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, Game.WIDTH/2, 16);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(Color.BLUE);
		g.drawString((int)Game.player.getLife()+"/"+(int)Game.player.getMaxLife(), 14, 23);
		
	}
	public int setRed() {
		damages = (int) ((Game.player.getLife()/Game.player.getMaxLife())*500);
		int newRed;
		if (damages != damagesAtual) {
			damagesAtual = damages;
		}
			newRed = 500 - damages;
		
		if(newRed >=0 && newRed <=254) {
			this.red = newRed;
		}
		return this.red;
	}
	public int setGreen() {
		damages = (int) ((Game.player.getLife()/Game.player.getMaxLife())*500);
		int newGreen;
		if (damages != damagesAtual) {
			damagesAtual = damages;
		}
			newGreen = damages;
		
		if(newGreen >=0 && newGreen <=254) {
			this.red = newGreen;
		}
		return this.green;
	}
}
