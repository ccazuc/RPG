package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.command.CommandLogoutCharacter;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;


public class EscapeFrame {
	
	private static float BUTTON_WIDTH_ANCHOR = -90*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT_ANCHOR = -263*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT_SHIFT = 23*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT = 21*Mideas.getDisplayXFactor();
	private static boolean init;
	private static Button videoButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Video", 13, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button soundButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Sound", 13, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button interfaceButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Interface", 13, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button keybindButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Keybind", 13, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button macrosButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Macros", 13, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button logoutButton = new Button(Display.getWidth()/2-99*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 210*Mideas.getDisplayXFactor(), 35*Mideas.getDisplayYFactor(), "Logout", 13, 1, Color.WHITE, Color.WHITE) {
		@Override
		public void eventButtonClick() {
			CommandLogoutCharacter.write();
			Mideas.setJoueur1Null();
			Interface.closeAllFrame();
			Interface.setCharacterLoaded(false);
		}
	};
	private static Button leaveButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Leave game", 13, 1, Color.WHITE, Color.WHITE) {
		@Override
		public void eventButtonClick() {
			Mideas.closeGame();
		}
	};
	private static Button returnButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Return game", 13, 1, Color.WHITE, Color.WHITE) {
		@Override
		public void eventButtonClick() {
			Interface.closeEscapeFrame();
		}
	};
	
	public static void draw() {
		if(!init) {
			updateSize();
			init = true;
		}
		Draw.drawQuad(Sprites.escape_frame, Display.getWidth()/2+BUTTON_WIDTH_ANCHOR-30*Mideas.getDisplayXFactor(), Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR-37*Mideas.getDisplayXFactor());
		videoButton.draw();
		soundButton.draw();
		interfaceButton.draw();
		keybindButton.draw();
		macrosButton.draw();
		logoutButton.draw();
		leaveButton.draw();
		returnButton.draw();
 	}
	
	public static boolean mouseEvent() {
		if(videoButton.event()) return true;
		if(soundButton.event()) return true;
		if(interfaceButton.event()) return true;
		if(keybindButton.event()) return true;
		if(macrosButton.event()) return true;
		if(logoutButton.event()) return true;
		if(leaveButton.event()) return true;
		if(returnButton.event()) return true;
		return false;
	}
	
	public static void keyboardEvent() {
		
	}
	
	public static void updateSize() {
		BUTTON_WIDTH_ANCHOR = -80*Mideas.getDisplayXFactor();
		BUTTON_HEIGHT_ANCHOR = -102*Mideas.getDisplayXFactor();
		BUTTON_HEIGHT_SHIFT = 24*Mideas.getDisplayXFactor();
		BUTTON_HEIGHT = 23*Mideas.getDisplayXFactor();
		videoButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		soundButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		interfaceButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+2*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		keybindButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+3*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		macrosButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+4*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		logoutButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+5*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		leaveButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+6*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
		returnButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+7*BUTTON_HEIGHT_SHIFT+23*Mideas.getDisplayXFactor(), 160*Mideas.getDisplayXFactor(), BUTTON_HEIGHT);
	}
}
