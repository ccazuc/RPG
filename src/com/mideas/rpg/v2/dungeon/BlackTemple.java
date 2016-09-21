package com.mideas.rpg.v2.dungeon;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.classes.ClassManager;
import com.mideas.rpg.v2.hud.EndFightFrame;
import com.mideas.rpg.v2.utils.Draw;

public class BlackTemple extends Dungeon {
	
	private static boolean blackTempleActive;
	private static Joueur[] joueur = new Joueur[5];
	private static boolean isBlackTempleLoaded;
	private static int maxRoom;
	private static int i;

	public static void draw() {
		Draw.drawColorQuad(Display.getWidth()/2-200, 30, 400, 30, Color.decode("#383838"));
		Draw.drawColorQuad(Display.getWidth()/2-160, 35, 310, 20, Color.decode("#7a7a7a"));
		Draw.drawColorQuad(Display.getWidth()/2-160, 35, 310*(float)i/maxRoom, 20, Color.green);
		if(i == maxRoom-1) {
			TTF2.spellName.drawStringShadow(Display.getWidth()/2-TTF2.spellName.getWidth("Boss Area")/2, 65, "Boss Area", Color.red, Color.black, 1);
		}
		else {
			TTF2.spellName.drawStringShadow(Display.getWidth()/2-TTF2.spellName.getWidth("Room"+(i+1))/2, Display.getHeight()/2-400, "Room "+(i+1), Color.white, Color.black, 1, 1, 1);
		}
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2+165, 37, Math.round(100*(float)i/maxRoom)+"%", Color.white, Color.black, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-192, 37, Integer.toString(i)+"/"+Integer.toString(maxRoom), Color.white, Color.black, 1);
	}
	
	public static void event() throws FileNotFoundException, SQLException {
		BlackTemple.draw();
		if(!BlackTemple.getIsBlackTempleLoaded()) {
			BlackTemple.fightOrder();
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
			endFight(4);
		}
	}
	
	public static void fightOrder() {
		joueur[0] = ClassManager.getPlayer("Warlock");
		joueur[1] = ClassManager.getPlayer("Shaman");
		joueur[2] = ClassManager.getPlayer("Paladin");
		joueur[3] = ClassManager.getPlayer("Illidan");
	}
	
	public static void closeDungeon() {
		BlackTemple.setIsBlackTempleLoaded(false);
		BlackTemple.setBlackTempleStatus(false);
		i = 0;
	}
	
	public static boolean button(int x_mouse, int y_mouse, int x_frame, int y_frame, Texture sprite) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x_mouse && Mideas.mouseX() <= Display.getWidth()/2+x_mouse+127 && Mideas.mouseY() <= Display.getHeight()/2+y_mouse && Mideas.mouseY() >= Display.getHeight()/2+y_mouse-19) {
			Draw.drawQuad(sprite, Display.getWidth()/2-sprite.getImageWidth()/2+x_frame, Display.getHeight()/2+y_frame);
			return true;
		}
		return false;
	}
	
	public static void endFight(int x) throws SQLException {
		maxRoom = x;
		if(!EndFightFrame.getEndFightEventState()) {
			EndFightFrame.doEndFightEvent();
			EndFightFrame.setEndFightEvent(true);
		}
		if(i != maxRoom-1) {
			Draw.drawQuad(Sprites.alert_dungeon, Display.getWidth()/2-Sprites.alert_dungeon.getImageWidth()/2+38, Display.getHeight()/2-150);
			if(button(-Sprites.button.getImageWidth()/2, -50, 0, -75, Sprites.button_hover)) {
				if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
					resetEndFight();
					i++;
					if(i >= maxRoom) {
						closeDungeon();
					}
					else {
						Mideas.setJoueur2(BlackTemple.getJoueur(i));
					}
				}
			}
			else {
				Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2, Display.getHeight()/2-75);
			}
			if(button(-130, -18, -70, -43, Sprites.button_hover2)) {
				if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
					Dungeon.closeDungeon();
				}
			}
			else {
				Draw.drawQuad(Sprites.button2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
			}
			if(button(7, -18, 70, -43, Sprites.button_hover)) {
				if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
					System.exit(1);
				}
			}
			else {
				Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
			}
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Quit dungeon")/2-69, Display.getHeight()/2-41, "Quit dungeon", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Quit game")/2+69, Display.getHeight()/2-41, "Quit game", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Continue dungeon")/2, Display.getHeight()/2-73, "Continue dungeon", Color.white, Color.black, 1, 1, 1);
		}
		else {
			Draw.drawQuad(Sprites.alert, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-105, Display.getHeight()/2-80);
			if(button(-130, -18, -70, -43, Sprites.button_hover2)) {
				if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
					resetEndFight();
					Dungeon.closeDungeon();
				}
			}
			else {
				Draw.drawQuad(Sprites.button2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
			}
			if(button(7, -18, 70, -43, Sprites.button_hover)) {
				if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
					System.exit(1);
				}
			}
			else {
				Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
			}
			TTF2.font4.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Dungeon cleared !")/2, Display.getHeight()/2-65, "Dungeon cleared !", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Return game")/2-69, Display.getHeight()/2-41, "Return game", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Quit game")/2+69, Display.getHeight()/2-41, "Quit game", Color.white, Color.black, 1, 1, 1);
		}
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
	
	private static void resetEndFight() {
		EndFightFrame.setEndFightEvent(false);
	}
}
