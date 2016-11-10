package com.mideas.rpg.v2.hud.social;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.utils.AlertBackground;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.InputBar;

public class AddFriendInputFrame {

	static Input input = new Input(TTF2.addingFriendInput, 12, false, false);
	private static InputBar inputBar = new InputBar(Display.getWidth()/2-88*Mideas.getDisplayXFactor(), Display.getHeight()/2-325*Mideas.getDisplayYFactor(), 170*Mideas.getDisplayXFactor());
	private static Button acceptButton = new Button(Display.getWidth()/2-143*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Accept", 13, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandFriend.addFriend(input.getText());
			Interface.setAddFriendStatus(false);
			input.resetText();
		}
	};
	private static Button cancelButton = new Button(Display.getWidth()/2+10*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Cancel", 13, 1) {
		
		@Override
		public void eventButtonClick() {
			Interface.setAddFriendStatus(false);
			input.resetText();
		}
	};
	private static AlertBackground background = new AlertBackground(Display.getWidth()/2-180*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 360*Mideas.getDisplayXFactor(), 120*Mideas.getDisplayYFactor(), 0.6f);
	
	public static void draw() {
		background.draw();
		TTF2.addingFriendTitle.drawStringShadow(Display.getWidth()/2-TTF2.addingFriendTitle.getWidth("Indiquez le nom de l'ami à ajouter :")/2, Display.getHeight()/2-350*Mideas.getDisplayYFactor(), "Indiquez le nom de l'ami à ajouter :", Color.white, Color.black, 1, 0, 0);
		inputBar.draw();
		TTF2.addingFriendInput.drawStringShadow(Display.getWidth()/2-78*Mideas.getDisplayXFactor(), Display.getHeight()/2-318*Mideas.getDisplayYFactor(), input.getText(), Color.white, Color.black, 1, 0, 0);
		if(input.isActive() && System.currentTimeMillis()%1000 < 500) {
			Draw.drawColorQuad(Display.getWidth()/2-78*Mideas.getDisplayXFactor()+input.getCursorShift(), Display.getHeight()/2-317*Mideas.getDisplayYFactor(), 5*Mideas.getDisplayXFactor(), 15*Mideas.getDisplayYFactor(), Color.white);
		}
		acceptButton.draw();
		cancelButton.draw();
	}
	
	public static boolean mouseEvent() {
		return cancelButton.event() || acceptButton.event();
	}
	
	public static boolean event() {
		if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
			Interface.setAddFriendStatus(false);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
			CommandFriend.addFriend(input.getText());
			Interface.setAddFriendStatus(false);
			return true;
		}
		return input.event();
	}
	
	public static void updateSize() {
		background.update(Display.getWidth()/2-180*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 360*Mideas.getDisplayXFactor(), 120*Mideas.getDisplayYFactor());
		acceptButton.update(Display.getWidth()/2-143*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor());
		cancelButton.update(Display.getWidth()/2+10*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor());
		inputBar.update(Display.getWidth()/2-88*Mideas.getDisplayXFactor(), Display.getHeight()/2-325*Mideas.getDisplayYFactor(), 170*Mideas.getDisplayXFactor());
	}
	
	public static Input getInput() {
		return input;
	}
}
