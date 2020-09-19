package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Sound;

public class Menu {
	
	private int w = Game.WIDTH;
	private int h = Game.HEIGHT;
	private Color color;
	private Font font;
	private int select = 1;
	private boolean up, down, selected;
	
	public boolean back = false;
	
	
	public void tick() {
		
		if (up) {
			select--;
			if (select < 0) select = 4;
			if (!back && select == 0) select = 4;
			up=!up;
		}else if (down) {
			select++;
			if (select > 4) select = 0;
			if (!back && select == 0) select++;
			down=!down;
		}
		
		if (selected) enterOption(select);
		
	}
	
	public void returnOption(Graphics g) {
		if (back) { 
			color = (select == 0)?Color.RED : Color.WHITE;
			font = (select == 0)? new Font("Arial", Font.BOLD, 20) : new Font("Arial", Font.PLAIN, 20);
			w = (select == 0) ? Game.WIDTH-4 : Game.WIDTH;
			g.setColor(color);
			g.setFont(font);
			g.drawString("Voltar", w/2-25, h/2-28);
		}
	}
	
	public void zelcoy(Graphics g) {
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("Zelcoy", Game.WIDTH/3, Game.HEIGHT/4);
		g.drawLine(Game.WIDTH/3-15, Game.HEIGHT/4+2, 298, Game.HEIGHT/4+2);
		g.drawLine(310, Game.HEIGHT/4+2, 335, Game.HEIGHT/4+2);
		g.drawLine(320, Game.HEIGHT/4-6, 335, Game.HEIGHT/4+2);
		g.drawLine(320, Game.HEIGHT/4+10, 335, Game.HEIGHT/4+2);
	}
	
	public void newOption(Graphics g) {
		color = (select == 1) ?Color.RED : Color.WHITE;
		font = (select == 1) ? new Font("Arial", Font.BOLD, 20) : new Font("Arial", Font.PLAIN, 20);
		w = (select == 1) ? Game.WIDTH-8 : Game.WIDTH;
		g.setColor(color);
		g.setFont(font);
		g.drawString("Novo Jogo", w/2-46, h/2);
	}
	
	public void loadOption(Graphics g) {
		color = (select == 2)?Color.RED : Color.WHITE;
		font = (select == 2)? new Font("Arial", Font.BOLD, 20) : new Font("Arial", Font.PLAIN, 20);
		w = (select == 2) ? Game.WIDTH-12 : Game.WIDTH;
		g.setColor(color);
		g.setFont(font);
		g.drawString("Carregar Jogo", w/3+15, h/2+28);
	}
	
	public void optionOption(Graphics g) {
		color = (select == 3)?Color.RED : Color.WHITE;
		font = (select == 3)? new Font("Arial", Font.BOLD, 20) : new Font("Arial", Font.PLAIN, 20);
		w = (select == 3) ? Game.WIDTH-4 : Game.WIDTH;
		g.setColor(color);
		g.setFont(font);
		g.drawString("Opções", w/2-36, h/2+56);
	}
	
	public void exitOption(Graphics g) {
		color = (select == 4)?Color.RED : Color.WHITE;
		font = (select == 4)? new Font("Arial", Font.BOLD, 20) : new Font("Arial", Font.PLAIN, 20);
		w = (select == 4) ? Game.WIDTH-2 : Game.WIDTH;
		g.setColor(color);
		g.setFont(font);
		g.drawString("Sair", w/2-18, h/2+85);
	}
	
	public void render(Graphics g) {
		
		g.setColor(new Color(50,50,50));
		g.fillRect(0, 0, w, h);
		
		g.setColor(new Color(60,60,60));
		for (int i = 0; i<10; i++) {
			for (int j = 0; j<10; j++) {
				g.drawRect(0+(j*(w/10)), 0+(i*(h/10)), w/10, h/10);
			}
		}
		
		// ZELCOY
		zelcoy(g);
		
		// CONTINUE
		returnOption(g);
		
		// NEW
		newOption(g);
		
		// LOAD
		loadOption(g);
		
		// OPTION
		optionOption(g);
		
		// EXIT
		exitOption(g);
			
		// BY LEVINO
		w = Game.WIDTH;
		h = Game.HEIGHT;
		g.setColor(new Color(100,100,255));
		g.setFont(new Font("Daniel", Font.PLAIN,12));
		g.drawString("By Levino",w-70,h-15);
		
		// COPYCAT SONG BY IC3M4N
		g.setColor(new Color(180,180,255));
		g.setFont(new Font("Daniel", Font.PLAIN,10));
		g.drawString("Music: Copycat song excerpt - Ic3m4n",10,h-15);
		
	}
	
	public void setUp(boolean arg) {
		Sound.NEXT_LEVEL.play();
		up = arg;
	}
	
	public void setDown(boolean arg) {
		Sound.NEXT_LEVEL.play();
		down = arg;	
	}
	public void setSelected(boolean arg) {
		selected = arg;
	}
	
	public void enterOption(int option) {
		switch(option) {
		case 0 :
			Game.gameState = "NORMAL";
			break;
		case 1 :
			if (!back) {
				Game.gameState = "NORMAL";
				break;
			}else {
				Game.gameState = "NORMAL";
				Game.initGame(1);
				break;
			}
		case 2 : 
			break;
		case 3 : 
			Sound.BG_MUSIC.stop();
			break;
		case 4 :
			System.exit(1);
			break;
		default:
			System.out.println("Opção "+option+" ainda não está ativa!");
		}
		
		selected=false;
	}
	
}
