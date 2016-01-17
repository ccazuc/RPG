package com.mideas.rpg.v2.dungeon;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.utils.Draw;

public class Dungeon {

	private static boolean[] hover = new boolean[10];
	private static boolean dungeonActive;
	private static int i = 0;
	static int xLeft = 100;
	static int xShift = 400;
	static int y = 50;
	static int yShift = 230;
	
	public static void draw() {
		Draw.drawQuad(Sprites.resized_bc_bg, 100, 50);
	}
	
	public static void event() {
		if(BlackTemple.getBlackTempleStatus()) {
			BlackTemple.fightOrder();
			BlackTemple.draw();
			if(!BlackTemple.getIsBlackTempleLoaded()) {
				Mideas.setJoueur2(BlackTemple.getJoueur(i));
				BlackTemple.setIsBlackTempleLoaded(true);
			}
			if(Mideas.joueur1().getStamina() <= 0) {
				i = 0;
				Interface.closeDungeonActiveFrame();
				BlackTemple.setIsBlackTempleLoaded(false);
				BlackTemple.setBlackTempleStatus(false);
			}
			else if(Mideas.joueur2().getStamina() <= 0) {
				Draw.drawQuad(Sprites.alert, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-105, Display.getHeight()/2-80);
				if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-3 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
					Draw.drawQuad(Sprites.button_hover, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
					if(Mouse.getEventButtonState()) {
						if(Mouse.getEventButton() == 0) {
							i++;
							if(i >= 4) {
								closeDungeon();
							}
							else {
								Mideas.setJoueur2(BlackTemple.getJoueur(i));
							}
						}
					}
				}
				else {
					Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
				}
				if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
					if(Mouse.getEventButtonState()) {
						if(Mouse.getEventButton() == 0) {
							closeDungeon();
						}
					}
					Draw.drawQuad(Sprites.button_hover2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
				}
				else {
					Draw.drawQuad(Sprites.button2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
				}
				TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Continue")/2-69, Display.getHeight()/2-41, "Continue", Color.white, Color.black, 1, 1, 1);
				TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Quit")/2+69, Display.getHeight()/2-41, "Quit", Color.white, Color.black, 1, 1, 1);
			}
		}
	}
	
	public static boolean mouseEvent() {
		Arrays.fill(hover, false);
		isHover(xLeft, y, 0);
		if(Mouse.getEventButton() == 0) {
			if(hover[0]) {
				BlackTemple.setBlackTempleStatus(true);
				Interface.closeDungeonActiveFrame();
			}
		}
		return false;
	}
			
	public static boolean getDungeonStatus() {
		return dungeonActive;
	}

	private static void isHover(int x, int y, int i) {
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+350 && Mideas.mouseY() >= y && Mideas.mouseY() <= y+219) {
			hover[i] = true;
		}
	}
	
	private static void closeDungeon() {
		Mideas.setJoueur2(Mideas.getRandomClass(2));
		Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
		Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
		LogChat.setStatusText("");
		LogChat.setStatusText2("");
		BlackTemple.setIsBlackTempleLoaded(false);
		BlackTemple.setBlackTempleStatus(false);
		i = 0;
	}
	
	public static int getRound() {
		return i;
	}
}
