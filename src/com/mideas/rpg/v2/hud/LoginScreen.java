package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.connection.AuthServerConnectionRunnable;
import com.mideas.rpg.v2.utils.Alert;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Input;

public class LoginScreen {

	private static Input account = new Input(TTF2.loginScreenAccount, 10, false, false, true);
	private static Input password = new Input(TTF2.loginScreenPassword, 19, false, false);
	private static String passwordText = "";
	private static Alert alert = new Alert("", -355*Mideas.getDisplayXFactor(), -60*Mideas.getDisplayYFactor(), 700*Mideas.getDisplayXFactor(), 130*Mideas.getDisplayXFactor(), 230*Mideas.getDisplayXFactor(), 38*Mideas.getDisplayYFactor(), Display.getHeight()+30, 20, "Ok");
	private static StringBuilder passwordBuilder = new StringBuilder();
	private static Button leaveButton = new Button(Display.getWidth()/2+753*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor(), "Leave", 16, 2) {
		@Override
		public void eventButtonClick() {
			Mideas.saveAllStats();
			Display.destroy();
		}
	};
	private static Button connectionButton = new Button(Display.getWidth()/2-105*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 210*Mideas.getDisplayXFactor(), 35*Mideas.getDisplayYFactor(), "Connection", 16, 2) {
		@Override
		public void eventButtonClick() {
			connectionEvent();
		}
	};
	//private static Popup popup = new Popup(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor(), "Ceci est un test");

	private final static String noAccountName = "Veuillez saisir votre nom de compte.";
	private final static String noPassword = "Veuillez saisir votre mot de passe.";
	private final static String bar = "|";
	private final static String star = "*";
	private final static String empty = "";
	
	public static void draw() {
		Draw.drawQuadBG(Sprites.login_screen);
		TTF2.loginScreenAccount.drawString(Display.getWidth()/2-93*Mideas.getDisplayXFactor(), Display.getHeight()/2+12*Mideas.getDisplayYFactor(), account.getText(), Color.white);
		TTF2.loginScreenPassword.drawString(Display.getWidth()/2-93*Mideas.getDisplayXFactor(), Display.getHeight()/2+112*Mideas.getDisplayYFactor(), passwordText, Color.white);
		if(System.currentTimeMillis()%1000 < 500) {
			if(account.isActive()) {
				TTF2.loginScreenTick.drawString(Display.getWidth()/2-99*Mideas.getDisplayXFactor()+account.getCursorShift(), Display.getHeight()/2+3*Mideas.getDisplayYFactor(), bar, Color.white);
			}
			else {
				TTF2.loginScreenTick.drawString(Display.getWidth()/2-100*Mideas.getDisplayXFactor()+TTF2.loginScreenPassword.getWidth(passwordText), Display.getHeight()/2+103*Mideas.getDisplayYFactor(), bar, Color.white);
			}
		}
		leaveButton.draw();
		connectionButton.draw();
		alert.draw();
		//Draw.drawColorQuad(0, 0, Display.getWidth(), Display.getHeight(), Color.white);
		//popup.draw();
	}
	
	public static boolean mouseEvent() {
		if(!Interface.getHasLoggedIn()) {
			alert.event();
			if(!alert.isActive()) {
				leaveButton.event();
				connectionButton.event();
				if((Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) && Mouse.getEventButtonState()) {
					if(Mideas.mouseX() >= Display.getWidth()/2-105*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+105*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+8*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+43*Mideas.getDisplayYFactor()) {
						account.setIsActive(true);
					}
					else if(Mideas.mouseX() >= Display.getWidth()/2-105*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+105*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+108*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+143*Mideas.getDisplayYFactor()) {
						password.setIsActive(true);
					}
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
				System.exit(0);
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
			account.event();
		}
		else if(password.isActive()) {
			if(password.event()) {
				updatePasswordText(password.getText().length());
			}
		}
	}
	
	private static void updatePasswordText(int length) {
		int i = 0;
		passwordBuilder.setLength(0);
		while(i < length) {
			passwordBuilder.append(star);
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
		if(account.getText().equals(empty)) {
			alert.setText(noAccountName);
			alert.setActive();
		}
		else if(password.getText().equals(empty)) {
			alert.setText(noPassword);
			alert.setActive();
		}
		else {
			/*if(LoginManager.checkLogin(account.getText(), password.getText())) {
				loginSuccess();
			}*/
			passwordText = "";
			alert.setText("Connection...");
			alert.setActive();
			AuthServerConnectionRunnable.setShouldConnect(true, account.getText(), password.getText());
			password.resetText();
		}
	}
	
	public static void loginSuccess() {
		account.setIsActive(false);
		password.setIsActive(true);
		alert.setInactive();
	}
	
	public static void updateSize() {
		leaveButton.update(Display.getWidth()/2+753*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
		connectionButton.update(Display.getWidth()/2-105*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 210*Mideas.getDisplayXFactor(), 35*Mideas.getDisplayYFactor());
		alert.setX(-355*Mideas.getDisplayXFactor());
		alert.setY(-60*Mideas.getDisplayYFactor());
		alert.setWidth(700*Mideas.getDisplayXFactor());
		//popup.update(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor());
	}
	
	public static void setPasswordActive() {
		password.setIsActive(true);
	}
	
	public static void resetPassword() {
		password.resetText();
		passwordText = "";
	}
}
