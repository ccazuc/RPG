package com.mideas.rpg.v2.dungeon;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.utils.Draw;

public class Dungeon {

	private static boolean[] hover = new boolean[10];
	private static boolean dungeonActive;
	static int xLeft = 100;
	static int xShift = 400;
	static int y = 50;
	static int yShift = 230;
	
	public static void draw() {
		Draw.drawQuad(Sprites.resized_bc_bg, 100, 50);
	}
	
	public static void event() throws FileNotFoundException {
		if(BlackTemple.getBlackTempleStatus()) {
			BlackTemple.event();
		}
	}
	
	public static boolean mouseEvent() {
		Arrays.fill(hover, false);
		isHover(xLeft, y, 0);
		if(Mouse.getEventButton() == 0) {
			if(hover[0]) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
				Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
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
	
	public static void closeDungeon() {
		Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
		Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
		LogChat.setStatusText("");
		LogChat.setStatusText2("");
		BlackTemple.closeDungeon();
	}
	
	public static boolean dungeonActive() {
		if(BlackTemple.getBlackTempleStatus()) {
			return true;
		}
		return false;
	}
}
