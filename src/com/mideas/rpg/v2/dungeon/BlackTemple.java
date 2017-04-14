package com.mideas.rpg.v2.dungeon;

import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import com.mideas.rpg.v2.utils.Texture;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.classes.ClassManager;
import com.mideas.rpg.v2.game.unit.Joueur;
import com.mideas.rpg.v2.hud.EndFightFrame;
import com.mideas.rpg.v2.utils.Color;

public class BlackTemple extends Dungeon {
	
	private static boolean BLACKTempleActive;
	private static Joueur[] joueur = new Joueur[5];
	private static boolean isBlackTempleLoaded;
	private static int maxRoom;
	private static int i;
	
	private final static String BOSS_AREA = "Boss Area";
	private final static String QUIT_DUNGEON = "Quit dungeon";
	private final static String QUIT_GAME = "Quit game";
	private final static String CONTINUE_DUNGEON = "Continue dungeon";
	private final static String DUNGEON_CLEARED = "Dungeon cleared !";
	private final static String RETURN_GAME = "Return game";
	
	
	private final static int BOSS_AREA_WIDTH = FontManager.get("FRIZQT", 21).getWidth(BOSS_AREA);
	private final static int QUIT_DUNGEON_WIDTH = FontManager.get("FRIZQT", 21).getWidth(QUIT_DUNGEON);
	private final static int QUIT_GAME_WIDTH = FontManager.get("FRIZQT", 21).getWidth(QUIT_GAME);
	private final static int CONTINUE_DUNGEON_WIDTH = FontManager.get("FRIZQT", 21).getWidth(CONTINUE_DUNGEON);
	private final static int DUNGEON_CLEARED_WIDTH = FontManager.get("FRIZQT", 21).getWidth(DUNGEON_CLEARED);
	private final static int RETURN_GAME_WIDTH = FontManager.get("FRIZQT", 21).getWidth(RETURN_GAME);
	
	private final static Color DARK = Color.decode("#383838");
	private final static Color GRAY = Color.decode("#7a7a7a");

	public static void draw() {
		Draw.drawColorQuad(Display.getWidth()/2-200, 30, 400, 30, DARK);
		Draw.drawColorQuad(Display.getWidth()/2-160, 35, 310, 20, GRAY);
		Draw.drawColorQuad(Display.getWidth()/2-160, 35, 310*(float)i/maxRoom, 20, Color.GREEN);
		if(i == maxRoom-1) {
			FontManager.get("FRIZQT", 21).drawStringShadow(Display.getWidth()/2-BOSS_AREA_WIDTH/2, 65, BOSS_AREA, Color.RED, Color.BLACK, 1);
		}
		else {
			FontManager.get("FRIZQT", 21).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 21).getWidth("Room"+(i+1))/2, Display.getHeight()/2-400, "Room "+(i+1), Color.WHITE, Color.BLACK, 1, 1, 1);
		}
		FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2+165, 37, Math.round(100*(float)i/maxRoom)+"%", Color.WHITE, Color.BLACK, 1);
		FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-192, 37, Integer.toString(i)+"/"+Integer.toString(maxRoom), Color.WHITE, Color.BLACK, 1);
	}
	
	public static void event() throws FileNotFoundException {
		BlackTemple.draw();
		if(!BlackTemple.getIsBlackTempleLoaded()) {
			BlackTemple.fightOrder();
			//Mideas.setJoueur2(BlackTemple.getJoueur(i));
			BlackTemple.setIsBlackTempleLoaded(true);
		}
		if(Mideas.joueur1().getStamina() <= 0) {
			i = 0;
			Interface.closeDungeonActiveFrame();
			BlackTemple.setIsBlackTempleLoaded(false);
			BlackTemple.setBlackTempleStatus(false);
		}
		/*else if(Mideas.joueur2().getStamina() <= 0) {
			endFight(4);
		}*/
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
	
	public static void endFight(int x) {
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
						//Mideas.setJoueur2(BlackTemple.getJoueur(i));
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
			FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-QUIT_DUNGEON_WIDTH/2-69, Display.getHeight()/2-41, QUIT_DUNGEON, Color.WHITE, Color.BLACK, 1, 1, 1);
			FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-QUIT_GAME_WIDTH/2+69, Display.getHeight()/2-41, QUIT_GAME, Color.WHITE, Color.BLACK, 1, 1, 1);
			FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-CONTINUE_DUNGEON_WIDTH/2, Display.getHeight()/2-73, CONTINUE_DUNGEON, Color.WHITE, Color.BLACK, 1, 1, 1);
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
			FontManager.get("FRIZQT", 16).drawStringShadow(Display.getWidth()/2-DUNGEON_CLEARED_WIDTH/2, Display.getHeight()/2-65, DUNGEON_CLEARED, Color.WHITE, Color.BLACK, 1, 1, 1);
			FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-RETURN_GAME_WIDTH/2-69, Display.getHeight()/2-41, RETURN_GAME, Color.WHITE, Color.BLACK, 1, 1, 1);
			FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-QUIT_GAME_WIDTH/2+69, Display.getHeight()/2-41, QUIT_GAME, Color.WHITE, Color.BLACK, 1, 1, 1);
		}
	}
	
	public static boolean getIsBlackTempleLoaded() {
		return isBlackTempleLoaded;
	}
	
	public static void setIsBlackTempleLoaded(boolean we) {
		isBlackTempleLoaded = we;
	}
	
	public static boolean getBlackTempleStatus() {
		return BLACKTempleActive;
	}

	public static void setBlackTempleStatus(boolean we) {
		BLACKTempleActive = we;
	}
	
	public static Joueur getJoueur(int i) {
		return joueur[i];
	}
	
	private static void resetEndFight() {
		EndFightFrame.setEndFightEvent(false);
	}
}
