package com.mideas.rpg.v2.dungeon;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.Illidan;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.Paladin;
import com.mideas.rpg.v2.game.Shaman;
import com.mideas.rpg.v2.game.Warlock;

public class BlackTemple extends Dungeon {
	
	private static boolean blackTempleActive;
	private static Joueur[] joueur = new Joueur[5];
	private static boolean isBlackTempleLoaded;

	public static void draw() {
		TTF2.spellName.drawStringShadow(Display.getWidth()/2, Display.getHeight()/2-400, "Room "+(Dungeon.getRound()+1), Color.white, Color.black, 1, 1, 1);
	}
	public static void fightOrder() {
		joueur[0] = new Warlock();
		joueur[1] = new Shaman();
		joueur[2] = new Paladin();
		joueur[3] = new Illidan();
	}
	
	public static boolean getIsBlackTempleLoaded() {
		return isBlackTempleLoaded;
	}
	
	public static void setIsBlackTempleLoaded(boolean we) {
		isBlackTempleLoaded = we;
	}
	
	public static boolean getBlackTempleStatus() {
		return blackTempleActive;
	}

	public static void setBlackTempleStatus(boolean we) {
		blackTempleActive = we;
	}
	
	public static Joueur getJoueur(int i) {
		return joueur[i];
	}
}
