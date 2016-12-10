package com.mideas.rpg.v2.hud;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.connection.AuthServerConnectionRunnable;
import com.mideas.rpg.v2.utils.Alert;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.CheckBox;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Input;

public class LoginScreen {

	//private static Input account = new Input(FontManager.get("FRIZQT", 21), 10, false, false, true);
	private static Input account = new Input(FontManager.get("ARIALN", 21), 50, false, Display.getWidth()/2-91*Mideas.getDisplayXFactor(), Display.getHeight()/2+12*Mideas.getDisplayYFactor(), 200*Mideas.getDisplayXFactor(), true);
	private static Input password = new Input(FontManager.get("ARIALN", 16), 19, false, false);
	private static String passwordText = "";
	private static Alert alert = new Alert("", -355*Mideas.getDisplayXFactor(), -60*Mideas.getDisplayYFactor(), 700*Mideas.getDisplayXFactor(), 20, "Ok");
	private static StringBuilder passwordBuilder = new StringBuilder();
	static boolean rememberAccountName;
	private static Button leaveButton = new Button(Display.getWidth()/2+753*Mideas.getDisplayXFactor(), Display.getHeight()/2+426*Mideas.getDisplayYFactor(), 185*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor(), "Leave", 16, 2) {
		
		@Override
		public void eventButtonClick() {
			Mideas.closeGame();
		}
	};
	private static Button connectionButton = new Button(Display.getWidth()/2-95*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 197*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Connection", 20, 2) {
		
		@Override
		public void eventButtonClick() {
			connectionEvent();
		}
	};
	private static Button officialWebsiteButton = new Button(26*Mideas.getDisplayXFactor(), Display.getHeight()-222*Mideas.getDisplayYFactor(), 184*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor(), "Official website", 16, 2) {
		
		@Override
		public void eventButtonClick() {
			Sys.openURL("https://github.com/ccazuc/RPG");
		}
	};
	private static CheckBox rememberAccountNameCheckBox = new CheckBox(27*Mideas.getDisplayXFactor(), Display.getHeight()-167*Mideas.getDisplayYFactor(), 22*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor()) {
		
		@Override
		public boolean get() {
			return rememberAccountName;
		}
		
		@Override
		public void set() {
			rememberAccountName = !rememberAccountName;
		}
	};

	private final static String noAccountName = "Veuillez saisir votre nom de compte.";
	private final static String noPassword = "Veuillez saisir votre mot de passe.";
	
	public static void draw() {
		Draw.drawQuadBG(Sprites.login_screen);
		//FontManager.get("FRIZQT", 21).drawString(Display.getWidth()/2-91*Mideas.getDisplayXFactor(), Display.getHeight()/2+12*Mideas.getDisplayYFactor(), account.getText(), Color.WHITE);
		//FontManager.get("ARIALN", 21).drawString(Display.getWidth()/2-91*Mideas.getDisplayXFactor(), Display.getHeight()/2+112*Mideas.getDisplayYFactor(), passwordText, Color.WHITE);
		drawPassword();
		/*if(System.currentTimeMillis()%1000 < 500) {
			if(account.isActive()) {
				//FontManager.loginScreenTick.drawString(Display.getWidth()/2-99*Mideas.getDisplayXFactor()+account.getCursorShift(), Display.getHeight()/2+3*Mideas.getDisplayYFactor(), bar, Color.WHITE);
			}
			else if(password.isActive()) {
				//FontManager.loginScreenTick.drawString(Display.getWidth()/2-100*Mideas.getDisplayXFactor()+FontManager.get("FRIZQT", 16).getWidth(passwordText), Display.getHeight()/2+103*Mideas.getDisplayYFactor(), bar, Color.WHITE);
			}
		}*/
		rememberAccountNameCheckBox.draw();
		officialWebsiteButton.draw();
		account.draw();
		leaveButton.draw();
		connectionButton.draw();
		alert.draw();
		//Draw.drawColorsQuad(0, 0, Display.getWidth(), Display.getHeight(), Colors.WHITE);
		//popup.draw();
	}
	
	public static boolean mouseEvent() {
		if(alert.event()) return true;
		if(!alert.isActive()) {
			if(leaveButton.event()) return true;
			if(connectionButton.event()) return true;
			if(rememberAccountNameCheckBox.event()) return true;
			if(officialWebsiteButton.event()) return true;
			if((Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) && Mouse.getEventButtonState()) {
				if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()/2-105*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+105*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+8*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+43*Mideas.getDisplayYFactor()) {
					account.setIsActive(true);
					Mideas.setHover(false);
				}
				else if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()/2-105*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+105*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+108*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+143*Mideas.getDisplayYFactor()) {
					password.setIsActive(true);
					Mideas.setHover(false);
				}
			}
		}
		return false;
	}
	
	public static void event() {
		//System.out.println((int)Keyboard.getEventCharacter());
		//System.out.println(Keyboard.getEventKey());
		if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
			if(!alert.isActive()) {
				Mideas.closeGame();
			}
		}
		if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
			if(alert.isActive()) {
				alert.setInactive();
			}
			else {
				connectionEvent();
			}
		}
		if(!alert.isActive()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_TAB) {
				if(password.isActive()) {
					account.setIsActive(true);
				}
				else {
					password.setIsActive(true);
				}
			}
		}
		
		if(account.isActive()) {
			if(account.event()) {
				rememberAccountName = false;
			}
		}
		else if(password.isActive()) {
			if(password.event()) {
				updatePasswordText(password.getText().length());
			}
		}
	}
	
	private static void drawPassword() {
		int i = 0;
		float x = Display.getWidth()/2-91*Mideas.getDisplayXFactor();
		while(i < passwordText.length()) {
			FontManager.get("ARIALN", 22).drawChar(x, Display.getHeight()/2+112*Mideas.getDisplayYFactor(), '*', Color.WHITE);
			x+= 8*Mideas.getDisplayXFactor();
			i++;
		}
		if(password.isActive()) {
			if(System.currentTimeMillis()%1000 < 500) {
				Draw.drawColorQuad(x-1, Display.getHeight()/2+112*Mideas.getDisplayYFactor(), 6*Mideas.getDisplayXFactor(), 24*Mideas.getDisplayYFactor(), Color.WHITE);
			}
		}
	}
	
	private static void updatePasswordText(int length) {
		int i = 0;
		passwordBuilder.setLength(0);
		while(i < length) {
			passwordBuilder.append('*');
			i++;
		}
		passwordText = passwordBuilder.toString();
	}
	
	public static Alert getAlert() {
		return alert;
	}
	
	public static void closeInput() {
		password.setIsActive(false);
		account.setIsActive(false);
	}
	
	static void connectionEvent() {
		if(account.getText().equals("")) {
			alert.setText(noAccountName);
			alert.setActive();
		}
		else if(password.getText().equals("")) {
			alert.setText(noPassword);
			alert.setActive();
		}
		else {
			passwordText = "";
			alert.setText("Connection...");
			alert.setActive();
			AuthServerConnectionRunnable.connectToAuthServer(account.getText(), password.getText());
			password.resetText();
		}
	}
	
	public static void loginSuccess() {
		if(!rememberAccountName) {
			account.resetText();
		}
		alert.setInactive();
	}
	
	public static void resetMenuState() {
		if(!rememberAccountName) {
			account.setIsActive(true);
		}
		else {
			password.setIsActive(true);
		}
	}
	
	public static void updateSize() {
		leaveButton.update(Display.getWidth()/2+753*Mideas.getDisplayXFactor(), Display.getHeight()/2+426*Mideas.getDisplayYFactor(), 185*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
		connectionButton.update(Display.getWidth()/2-95*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 197*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor());
		alert.update(Display.getWidth()/2-720*Mideas.getDisplayXFactor()/2, Display.getHeight()/2-60*Mideas.getDisplayYFactor(), 720*Mideas.getDisplayXFactor());
		account.update(Display.getWidth()/2-91*Mideas.getDisplayXFactor(), Display.getHeight()/2+12*Mideas.getDisplayYFactor(), 200*Mideas.getDisplayXFactor());
		officialWebsiteButton.update(26*Mideas.getDisplayXFactor(), Display.getHeight()-222*Mideas.getDisplayYFactor(), 184*Mideas.getDisplayXFactor(), 33*Mideas.getDisplayYFactor());
		rememberAccountNameCheckBox.update(27*Mideas.getDisplayXFactor(), Display.getHeight()-167*Mideas.getDisplayYFactor(), 22*Mideas.getDisplayXFactor(), 18*Mideas.getDisplayYFactor());
		//popup.update(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor());
	}
	
	public static boolean getRememberAccountName() {
		return rememberAccountName;
	}
	
	public static void setRememberAccountName(boolean we) {
		rememberAccountName = we;
	}
	
	public static String getAccountName() {
		return account.getText();
	}
	
	public static void setAccountName(String name) {
		account.setText(name);
		if(!name.equals("")) {
			password.setIsActive(true);
		}
	}
	
	public static void resetPassword() {
		password.resetText();
		passwordText = "";
	}
}
