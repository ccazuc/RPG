package com.mideas.rpg.v2.hud;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.DropManager;
import com.mideas.rpg.v2.utils.Draw;

public class EndFightFrame {

	private static boolean endFightEvent;
	
	private final static String retry = "Retry";
	private final static String quit = "Quit";
	private final static String player1Won = "Player 1 won";
	private final static String player2Won = "Playe 2 won";

	public static void draw() throws SQLException {
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
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth(retry)/2-69, Display.getHeight()/2-41, retry, Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth(quit)/2+69, Display.getHeight()/2-41, quit, Color.white, Color.black, 1, 1, 1);
		if(Mideas.joueur1().getStamina() <= 0) {
			TTF2.font4.drawStringShadow(Display.getWidth()/2-50, Display.getHeight()/2-66, player2Won, Color.white, Color.black, 1, 1, 1);
		}
		else if(Mideas.joueur2().getStamina() <= 0) {
			TTF2.font4.drawStringShadow(Display.getWidth()/2-50, Display.getHeight()/2-66, player1Won, Color.white, Color.black, 1, 1, 1);
			if(!endFightEvent) {
				doEndFightEvent();
				endFightEvent = true;
			}
		}
		
	}
	
	public static void mouseEvent() throws SQLException {
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
					System.exit(1);
					CharacterStuff.setBagItems();
					CharacterStuff.setEquippedBags();
					CharacterStuff.setEquippedItems();
					Mideas.setConfig();
					Mideas.setExp(Mideas.joueur2().getExpGained());
					Mideas.setGold(Mideas.getGold());
				}
				else if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-3 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
					Mideas.setJoueur2(Mideas.getRandomClass(2));
					Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
					Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
					LogChat.setStatusText("");
					LogChat.setStatusText2("");
					endFightEvent = false;
				}
			}
		}
	}
	
	private static void dropManager() throws SQLException {
		DropManager.loadDropTable(Mideas.joueur2().getId());
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
		}
	}
	
	public static boolean getEndFightEventState() {
		return endFightEvent;
	}
	
	public static void setEndFightEvent(boolean we) {
		endFightEvent = we;
	}
	
	public static void doEndFightEvent() throws SQLTimeoutException, SQLException {
		Mideas.setExp(Mideas.getExp()+Mideas.joueur2().getExpGained());
		Mideas.getLevel();
		Mideas.setGold(Mideas.getGold()+Mideas.joueur2().getGoldGained());
		dropManager();
	}
}

