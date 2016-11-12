package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Alert;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Input;

public class LoginScreen {

	private static Input account = new Input(TTF2.loginScreenAccount, 10, false, false, true);
	private static Input password = new Input(TTF2.loginScreenPassword, 19, false, false);
	private static Alert alert = new Alert("", Display.getWidth()/2-355*Mideas.getDisplayXFactor(), -20, 700*Mideas.getDisplayXFactor(), 130, 230, 38, Display.getHeight()+30, 20, "Ok");
	//private static boolean alertActive;
	private static boolean init;
	private static Button leaveButton = new Button(Display.getWidth()/2+773*Mideas.getDisplayXFactor(), Display.getHeight()/2+428*Mideas.getDisplayYFactor(), 185, 34, "Leave", 16, 2) {
		@Override
		public void eventButtonClick() {
			Mideas.saveAllStats();
			Display.destroy();
		}
	};
	private static Button connectionButton = new Button(Display.getWidth()/2-113*Mideas.getDisplayXFactor(), Display.getHeight()/2+185*Mideas.getDisplayYFactor(), 210, 35, "Connection", 16, 2) {
		@Override
		public void eventButtonClick() {
			connectionEvent();
		}
	};
	//private static DropDownMenu menu = new DropDownMenu(Display.getWidth()/2-100, Display.getHeight()/2-15, 200, Display.getWidth()/2-110, Display.getHeight()/2+5, 130, 13, .6f);

	private final static String noAccountName = "Veuillez saisir votre nom de compte.";
	private final static String noPassword = "Veuillez saisir votre mot de passe.";
	private final static String bar = "|";
	private final static String star = "*";
	private final static String empty = "";
	
	public static void draw() {
		/*if(!init) {
			int i = 0;
			float yShift = 18;
			while(i < 5) {
				menu.addMenu(new TextMenu(Display.getWidth()/2-90, Display.getHeight()/2+25+i*yShift, 110, 25, String.valueOf(i), 13, 1, 40) {
					
					@Override
					public void eventButtonClick() {
						System.out.println(this.value);
					}
				});
				i++;
			}
			init = true;
		}*/
		if(!init) {
			updateSize();
			init = true;
		}
		Draw.drawQuadBG(Sprites.login_screen);
		TTF2.loginScreenAccount.drawString(Display.getWidth()/2-93*Mideas.getDisplayXFactor(), Display.getHeight()/2+12*Mideas.getDisplayYFactor(), account.getText(), Color.white);
		TTF2.loginScreenPassword.drawString(Display.getWidth()/2-93*Mideas.getDisplayXFactor(), Display.getHeight()/2+112*Mideas.getDisplayYFactor(), drawPassword(password.getText().length()), Color.white);
		if(System.currentTimeMillis()%1000 < 500) {
			if(account.isActive()) {
				TTF2.loginScreenTick.drawString(Display.getWidth()/2-99*Mideas.getDisplayXFactor()+account.getCursorShift(), Display.getHeight()/2+3*Mideas.getDisplayYFactor(), bar, Color.white);
			}
			else {
				TTF2.loginScreenTick.drawString(Display.getWidth()/2-100*Mideas.getDisplayXFactor()+TTF2.loginScreenPassword.getWidth(password.getText()), Display.getHeight()/2+103*Mideas.getDisplayYFactor(), bar, Color.white);
			}
		}
		leaveButton.draw();
		connectionButton.draw();
		alert.draw();
		//Draw.drawQuadBG(Sprites.login_screen);
		//Draw.drawColorQuad(0, 0, Display.getWidth(), Display.getHeight(), Color.white);
		//menu.draw();
		//Draw.drawQuadBend(Sprites.button_menu_hover, Display.getWidth()/2, Display.getHeight()/2);
		//tooltip.draw();
	}
	
	public static boolean mouseEvent() {
		if(!Interface.getHasLoggedIn()) {
			alert.event();
			if(!alert.isActive()) {
				leaveButton.event();
				connectionButton.event();
				if((Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) && Mouse.getEventButtonState()) {
					if(Mideas.mouseX() >= Display.getWidth()/2-105*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+105*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+8*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+43*Mideas.getDisplayYFactor()) {
						password.setIsActive(false);
						account.setIsActive(true);
					}
					else if(Mideas.mouseX() >= Display.getWidth()/2-105*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+105*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+108*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+143*Mideas.getDisplayYFactor()) {
						password.setIsActive(true);
						account.setIsActive(false);
					}
				}
			}
		}
		//menu.event();
		return false;
	}
	
	public static void event() {
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
			password.event();
		}
	}
	
	private static String drawPassword(int length) {
		int i = 0;
		String pw = empty;
		while(i < length) {
			pw+= star;
			i++;
		}
		return pw;
	}
	
	public static Alert getAlert() {
		return alert;
	}
	
	public static Input getPassword() {
		return password;
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
			if(LoginManager.checkLogin(account.getText(), password.getText())) {
				loginSuccess();
			}
		}
	}
	
	public static void loginSuccess() {
		password.resetText();
		account.setIsActive(false);
		password.setIsActive(true);
	}
	
	public static void updateSize() {
		leaveButton.setX(Display.getWidth()/2+753*Mideas.getDisplayXFactor());
		leaveButton.setY(Display.getHeight()/2+428*Mideas.getDisplayYFactor());
		leaveButton.setButtonWidth(185);
		leaveButton.setButtonHeight(34);
		connectionButton.setX(Display.getWidth()/2-105*Mideas.getDisplayXFactor());
		connectionButton.setY(Display.getHeight()/2+185*Mideas.getDisplayYFactor());
		connectionButton.setButtonWidth(210);
		connectionButton.setButtonHeight(38);
		alert.setX(-355*Mideas.getDisplayXFactor());
		alert.setY(-60);
		alert.setWidth(700*Mideas.getDisplayXFactor());
	}
}
