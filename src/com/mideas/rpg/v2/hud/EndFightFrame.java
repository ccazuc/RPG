package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Unit;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class EndFightFrame {

	private static boolean endFightEvent;
	
	private final static String retry = "Retry";
	private final static String quit = "Quit";
	private final static String player1Won = "Player 1 won";
	private final static String player2Won = "Playe 2 won";

	public static void draw() {
		if(Interface.getAdminPanelFrameStatus()) {
			Interface.closeAdminPanelFrame();
		}
		if(Interface.getCharacterFrameStatus()) {
			CharacterFrame.setHoverFalse();
		}
		if(Interface.getContainerFrameStatus()) {
			ContainerFrame.setHoverFalse();
		}
		if(Interface.isSpellBookFrameActive()) {
			Arrays.fill(SpellBookFrame.getHoverBook(), false);
		}
		//TODO: use popup class
		Draw.drawQuad(Sprites.alert, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-105, Display.getHeight()/2-80);
		if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-3 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
			Draw.drawQuad(Sprites.button_hover, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
		}
		else {
			Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
			Draw.drawQuad(Sprites.button_hover2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
		}
		else {
			Draw.drawQuad(Sprites.button2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
		}
		FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 13).getWidth(retry)/2-69, Display.getHeight()/2-41, retry, Color.WHITE, Color.BLACK, 1, 1, 1);
		FontManager.get("FRIZQT", 13).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 13).getWidth(quit)/2+69, Display.getHeight()/2-41, quit, Color.WHITE, Color.BLACK, 1, 1, 1);
		if(Mideas.joueur1().getStamina() <= 0) {
			FontManager.get("FRIZQT", 16).drawStringShadow(Display.getWidth()/2-50, Display.getHeight()/2-66, player2Won, Color.WHITE, Color.BLACK, 1, 1, 1);
		}
		else if(Mideas.joueur1().getTarget().getStamina() <= 0) {
			FontManager.get("FRIZQT", 16).drawStringShadow(Display.getWidth()/2-50, Display.getHeight()/2-66, player1Won, Color.WHITE, Color.BLACK, 1, 1, 1);
			if(!endFightEvent) {
				doEndFightEvent();
				endFightEvent = true;
			}
		}
		
	}
	
	public static void mouseEvent() {
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
					Mideas.closeGame();
				}
				else if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-3 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
					Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
					Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
					LogChat.setStatusText("");
					LogChat.setStatusText2("");
					endFightEvent = false;
					Mideas.joueur1().setTarget(new Unit(100, 10000, 10000, 3000, 3000, 1, "", ClassType.NPC));
				}
			}
		}
	}
	
	private static void dropManager() {
		/*DropManager.loadDropTable(Mideas.target().getId());
		int i = 0;
		double random = Math.random();
		while(i < DropManager.getList(Mideas.joueur2().getId()).size()) {
			if(random >= DropManager.getList(Mideas.joueur2().getId()).get(i).getDropRate()) {
				if(DropManager.getList(Mideas.joueur2().getId()).get(i).getAmount() > 1) {
					Mideas.joueur1().addItem(DropManager.getList(Mideas.joueur2().getId()).get(i).getItem(), DropManager.getList(Mideas.joueur2().getId()).get(i).getAmount());
				}
				else {
					Mideas.joueur1().addItem(DropManager.getList(Mideas.joueur2().getId()).get(i).getItem(), 1);
				}
			}
			i++;
		}*/
	}
	
	public static boolean getEndFightEventState() {
		return endFightEvent;
	}
	
	public static void setEndFightEvent(boolean we) {
		endFightEvent = we;
	}
	
	public static void doEndFightEvent() {
		//Mideas.joueur1().setExp(Mideas.joueur1().getExp()+Mideas.joueur2().getExpGained());
		//Mideas.joueur1().setGold(Mideas.joueur1().getGold()+Mideas.joueur2().getGoldGained());
		dropManager();
	}
}

