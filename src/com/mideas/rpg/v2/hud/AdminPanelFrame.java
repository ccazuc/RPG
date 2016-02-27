package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.utils.Draw;

public class AdminPanelFrame {

	private static boolean[] hover = new boolean[15];
	private static int xLeft = -325;
	private static int xRight = 190;
	private static int y = -250;
	private static int yShift = 25;
	
	public static void draw() {
		Draw.drawQuad(Sprites.admin_panel, Display.getWidth()/2+xLeft-75, Display.getHeight()/2+y-100);
		drawHover(0, xLeft, y);
		drawHover(1, xLeft, y+yShift);
		drawHover(2, xLeft, y+2*yShift);
		drawHover(3, xLeft, y+3*yShift);
		drawHover(4, xRight, y);
		drawHover(5, xRight, y+yShift);
		drawHover(6, xRight, y+2*yShift);
		drawHover(7, xRight, y+3*yShift);
		TTF2.coinContainer.drawStringShadow(Display.getWidth()/2+xLeft+25, Display.getHeight()/2+y-40, "Joueur1", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xLeft+30, Display.getHeight()/2+y+2, "set max HP", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xLeft+22, Display.getHeight()/2+y+yShift+2, "set max mana", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xLeft+55, Display.getHeight()/2+y+2*yShift+2, "kill", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xLeft+30, Display.getHeight()/2+y+3*yShift+2, "clear bag", Color.white, Color.black, 1, 1, 1);
		TTF2.coinContainer.drawStringShadow(Display.getWidth()/2+xRight+25, Display.getHeight()/2+y-40, "Joueur2", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xRight+30, Display.getHeight()/2+y+2, "set max HP", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xRight+22, Display.getHeight()/2+y+yShift+2, "set max mana", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+xRight+55, Display.getHeight()/2+y+2*yShift+2, "kill", Color.white, Color.black, 1, 1, 1);
	}
	
	public static boolean mouseEvent() throws FileNotFoundException {
		Arrays.fill(hover, false);
		isHover(0, xLeft, y);
		isHover(1, xLeft, y+yShift);
		isHover(2, xLeft, y+2*yShift);
		isHover(3, xLeft, y+3*yShift);
		isHover(4, xRight, y);
		isHover(5, xRight, y+yShift);
		isHover(6, xRight, y+2*yShift);
		isHover(7, xRight, y+3*yShift);
		if(Mouse.getEventButtonState()) {
			if(hover[0]) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
			}
			else if(hover[1]) {
				Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
			}
			else if(hover[2]) {
				Mideas.joueur1().setStamina(0);
			}
			else if(hover[3]) {
				Arrays.fill(Mideas.bag().getBag(), null);
				CharacterStuff.setBagItems();
			}
			else if(hover[4]) {
				Mideas.joueur2().setStamina(Mideas.joueur2().getMaxStamina());
			}
			else if(hover[5]) {
				Mideas.joueur2().setMana(Mideas.joueur2().getMaxMana());
			}
			else if(hover[6]) {
				LogChat.setStatusText2("Killed by god");
				Mideas.joueur2().setStamina(0);
			}
		}
		return false;
	}
	
	private static void isHover(int i, int x, int y) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+Sprites.button.getImageWidth() && Mideas.mouseY() >= Display.getHeight()/2+y && Mideas.mouseY() <= Display.getHeight()/2+y+Sprites.button.getImageHeight()) {
			hover[i] = true;
		}
	}
	
	private static void drawHover(int i, int x, int y) {
		if(hover[i]) {
			Draw.drawQuad(Sprites.button_hover, Display.getWidth()/2+x, Display.getHeight()/2+y);
		}
		else {
			Draw.drawQuad(Sprites.button, Display.getWidth()/2+x, Display.getHeight()/2+y);
		}
		Draw.drawQuad(Sprites.cursor, -200, -200);
	}
	
	public static boolean[] getHover() {
		return hover;
	}
}
