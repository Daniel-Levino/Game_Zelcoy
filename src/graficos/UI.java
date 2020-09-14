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
		this.setRedGreen();
		g.setColor(new Color(red, green, 80));
		g.fillRect(10, 10, (int)((Game.player.getLife()/Game.player.getMaxLife())*Game.WIDTH/2), 16);
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, Game.WIDTH/2, 16);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(Color.BLUE);
		g.drawString((int)Game.player.getLife()+"/"+(int)Game.player.getMaxLife(), 14, 23);
		
	}
	public void setRedGreen() {
		damages = (int) ((Game.player.getLife()/Game.player.getMaxLife())*500);
		if (damages != damagesAtual) {
			damagesAtual = damages;
			//System.out.println(damages);
			
			if (red<250) {
				if (red-(500-damages)<254) {
					red = 500 - (int)(damages);
				}else {
					red = 254;
				}
				
				//System.out.println("r = "+red+", g = "+green);
			}else if (damages > 0) {
				green = (int)((Game.player.getLife()/Game.player.getMaxLife())*500);
				//System.out.println("r = "+red+", g = "+green);
			
			}
			
		}
		
		
	}
}
