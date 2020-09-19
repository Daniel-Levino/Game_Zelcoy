package entities;

import java.awt.image.BufferedImage;

import game.Game;

public class ArrowSuperShooting extends ArrowShooting{

	private int dx,dy;
	private double spd = 4;
	private BufferedImage sprite;
	private int shootDistance = 0;
	private int maxShootDistance = 50;
	
	public ArrowSuperShooting(int x, int y, int w, int h, BufferedImage sprite, int dx, int dy) {
		super(x, y, w, h, sprite, dy, dy);
		this.sprite = sprite;
		this.dx = dx;
		this.dy = dy;
	}

	
	public void tick() {
		x+=dx*spd;
		x+=dy*spd;
		shootDistance++;
		if(shootDistance > maxShootDistance) {
			Game.shoots.remove(this);
			return;
		}
		
	}

}
