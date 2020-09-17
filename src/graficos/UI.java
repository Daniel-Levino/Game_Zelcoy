package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Enemy;
import game.Game;
import world.Camera;

public class UI {
	private int green = 250;
	private int red = 0;
	private int damagesAtual, damages;
	private static Enemy enemy;
	private static boolean EnemyDamage;
	private static int mms;
	
	
	public void render (Graphics g) {
		
		// Lifebar
		g.setColor(new Color(setRed(), setGreen(), 80));
		g.fillRect(10, 10, (int)((Game.player.getLife()/Game.player.getMaxLife())*Game.WIDTH/2), 16);
		g.setColor(Color.WHITE);
		g.drawRect(10, 10, Game.WIDTH/2, 16);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.BLUE);
		g.drawString((int)Game.player.getLife()+"/"+(int)Game.player.getMaxLife(), 14, 23);
		
		// Arrows UI
		g.setColor(Color.WHITE);
		g.drawString("Arrows: "+Game.player.getArrows(),Game.WIDTH-85,23);
		
		// Enemy feedback damage
		if (EnemyDamage) {
			mms++;
			if(mms == 60) EnemyDamage = false;
			for (int i =0; i< enemy.life; i++) {
				g.setColor(Color.RED);
				g.fillRect(enemy.x+(4*i)-Camera.x, enemy.y-Camera.y-5, 4, 4);
				g.setColor(Color.WHITE);
				g.drawRect(enemy.x+(4*i)-Camera.x, enemy.y-Camera.y-5, 4, 4);
				
			}
		}
		
		// Number of Enemies
		g.setColor(Color.WHITE);
		g.drawString("Enemies "+ Game.enemies.size() , 10,Game.HEIGHT-10);
		
		// FPS
		g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString(Game.fps, Game.WIDTH-40, Game.HEIGHT-2);
        
		
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
	public static void renderEnemyDamage(Enemy e) {
		UI.enemy = e;
		UI.EnemyDamage = true;
		UI.mms = 0;
	}
}
