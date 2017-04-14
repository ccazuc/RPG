package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.AlertBackground;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.TTF;


public class EscapeFrame {
	
	private static boolean shouldUpdateSize;
	private final static TTF BUTTON_FONT = FontManager.get("FRIZQT", 14);
	private static float BUTTON_WIDTH_ANCHOR = -80*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT_ANCHOR = -263*Mideas.getDisplayYFactor();
	private static float BUTTON_HEIGHT_SHIFT = 23*Mideas.getDisplayYFactor();
	private static float BUTTON_WIDTH = 160*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT = 21*Mideas.getDisplayYFactor();
	private final static AlertBackground background = new AlertBackground(Display.getWidth()/2-220*Mideas.getDisplayXFactor()/2, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR-24*Mideas.getDisplayYFactor(), 220*Mideas.getDisplayXFactor(), 255*Mideas.getDisplayYFactor(), .7f, "Options", 150*Mideas.getDisplayXFactor());
	private static Button videoButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR, BUTTON_WIDTH, BUTTON_HEIGHT, "Video", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button soundButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT, "Sound", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button interfaceButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+2*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT, "Interface", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button keybindButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+3*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT, "Keybind", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button macrosButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+4*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT, "Macros", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
	};
	private static Button logoutButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+5*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT, "Logout", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
		@Override
		public void eventButtonClick() {
			ConnectionManager.logoutCharacter();
		}
	};
	private static Button leaveButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+6*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT, "Leave game", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
		@Override
		public void eventButtonClick() {
			Mideas.closeGame();
		}
	};
	private static Button returnButton = new Button(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+7*BUTTON_HEIGHT_SHIFT+23*Mideas.getDisplayXFactor(), BUTTON_WIDTH, BUTTON_HEIGHT, "Return game", BUTTON_FONT, 1, Color.WHITE, Color.WHITE) {
		@Override
		public void eventButtonClick() {
			Interface.closeEscapeFrame();
		}
	};
	
	public static void draw() {
		//Draw.drawQuad(Sprites.escape_frame, Display.getWidth()/2-Sprites.escape_frame.getImageWidth()*Mideas.getDisplayXFactor()/2, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR-37*Mideas.getDisplayYFactor());
		updateSize();
		background.draw();
		videoButton.drawTexture();
		soundButton.drawTexture();
		interfaceButton.drawTexture();
		keybindButton.drawTexture();
		macrosButton.drawTexture();
		logoutButton.drawTexture();
		leaveButton.drawTexture();
		returnButton.drawTexture();
		BUTTON_FONT.drawBegin();
		videoButton.drawText();
		soundButton.drawText();
		interfaceButton.drawText();
		keybindButton.drawText();
		macrosButton.drawText();
		logoutButton.drawText();
		leaveButton.drawText();
		returnButton.drawText();
		BUTTON_FONT.drawEnd();
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
		if(!shouldUpdateSize) {
			return;
		}
		BUTTON_WIDTH_ANCHOR = -80*Mideas.getDisplayXFactor();
		BUTTON_HEIGHT_ANCHOR = -102*Mideas.getDisplayYFactor();
		BUTTON_HEIGHT_SHIFT = 24*Mideas.getDisplayYFactor();
		BUTTON_HEIGHT = 23*Mideas.getDisplayYFactor();
		BUTTON_WIDTH = 160*Mideas.getDisplayXFactor();
		background.update(Display.getWidth()/2-220*Mideas.getDisplayXFactor()/2, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR-24*Mideas.getDisplayYFactor(), 220*Mideas.getDisplayXFactor(), 255*Mideas.getDisplayYFactor(), 150*Mideas.getDisplayXFactor());
		videoButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR, BUTTON_WIDTH, BUTTON_HEIGHT);
		soundButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT);
		interfaceButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+2*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT);
		keybindButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+3*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT);
		macrosButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+4*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT);
		logoutButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+5*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT);
		leaveButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+6*BUTTON_HEIGHT_SHIFT, BUTTON_WIDTH, BUTTON_HEIGHT);
		returnButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+7*BUTTON_HEIGHT_SHIFT+23*Mideas.getDisplayXFactor(), BUTTON_WIDTH, BUTTON_HEIGHT);
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
}
