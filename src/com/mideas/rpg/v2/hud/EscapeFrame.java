package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;


public class EscapeFrame {
	
	private static float BUTTON_WIDTH_ANCHOR = -90*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT_ANCHOR = -263*Mideas.getDisplayXFactor();
	private static float BUTTON_HEIGHT_SHIFT = 23*Mideas.getDisplayXFactor();
	private static boolean init;
	private static Button videoButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Video", 13) {
	};
	private static Button soundButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Sound", 13) {
	};
	private static Button interfaceButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Interface", 13) {
	};
	private static Button keybindButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Keybind", 13) {
	};
	private static Button macrosButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Macros", 13) {
	};
	private static Button logoutButton = new Button(Display.getWidth()/2-99*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 210*Mideas.getDisplayXFactor(), 35*Mideas.getDisplayYFactor(), "Logout", 13) {
		@Override
		public void eventButtonClick() throws SQLException, NoSuchAlgorithmException {
			Mideas.setCharacterId(0);
			Mideas.setJoueur1Null();
			Interface.closeAllFrame();
		}
	};
	private static Button leaveButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Leave game", 13) {
		@Override
		public void eventButtonClick() throws SQLException {
			Mideas.saveAllStats();
			System.exit(0);
		}
	};
	private static Button returnButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Return game", 13) {
		@Override
		public void eventButtonClick() throws SQLException {
			Interface.closeEscapeFrame();
		}
	};
	
	public static void draw() {
		if(Display.wasResized() || !init) {
			BUTTON_WIDTH_ANCHOR = -90*Mideas.getDisplayXFactor();
			BUTTON_HEIGHT_ANCHOR = -150*Mideas.getDisplayXFactor();
			BUTTON_HEIGHT_SHIFT = 23*Mideas.getDisplayXFactor();
			videoButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			soundButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			interfaceButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+2*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			keybindButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+3*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			macrosButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+4*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			logoutButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+5*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			leaveButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+6*BUTTON_HEIGHT_SHIFT, 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
			returnButton.update(Display.getWidth()/2+BUTTON_WIDTH_ANCHOR, Display.getHeight()/2+BUTTON_HEIGHT_ANCHOR+7*BUTTON_HEIGHT_SHIFT+23*Mideas.getDisplayXFactor(), 160*Mideas.getDisplayXFactor(), 21*Mideas.getDisplayXFactor());
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
	
	public static boolean mouseEvent() throws SQLException, NoSuchAlgorithmException {
		videoButton.event();
		soundButton.event();
		interfaceButton.event();
		keybindButton.event();
		macrosButton.event();
		logoutButton.event();
		leaveButton.event();
		returnButton.event();
		return false;
	}
	
	public static void keyboardEvent() {
		
	}
}
