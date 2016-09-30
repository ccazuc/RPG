package com.mideas.rpg.v2.hud;

import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;

public class SocketingFrame {

	static Stuff stuff;
	private static CrossButton closeSocketingFrame = new CrossButton(Display.getWidth()/2+73+CharacterFrame.getMouseX(), Display.getHeight()/2-365+CharacterFrame.getMouseY()) {
		@Override
		protected void eventButtonClick() throws SQLException {
			Interface.setSocketingFrameStatus(false);
			CharacterFrame.setGemFrame(0);
			CharacterFrame.updateButton();
			stuff = null;
		}
	};
	
	public static void draw() {
		Draw.drawQuad(Sprites.socketing_frame, Display.getWidth()/2-300+CharacterFrame.getMouseX(), Display.getHeight()/2-380+CharacterFrame.getMouseY());
		closeSocketingFrame.draw();
		drawGemBackground();
	}
	
	public static boolean mouseEvent() throws SQLException {
		if(Keyboard.getEventKey() == Keyboard.KEY_O) {
			Interface.setSocketingFrameStatus(false);
			CharacterFrame.setGemFrame(0);
			stuff = null;
		}
		closeSocketingFrame.event();
		return false;
	}
	
	public static Stuff getStuff() {
		return stuff;
	}
	
	public static void setStuff(Stuff stuffs) {
		stuff = stuffs;
	}
	
	private static void drawGemBackground() {
		if(stuff.getNumberGemSlot() == 1) {
			
		}
		else if(stuff.getNumberGemSlot() == 2) {
			
		}
		else if(stuff.getNumberGemSlot() == 3) {
			
		}
	}
	
	public static void updateSize() {
		CharacterFrame.setGemFrame((int)(Sprites.socketing_frame.getImageWidth()*Mideas.getDisplayXFactor())+15);
		closeSocketingFrame.update(Display.getWidth()/2+73+CharacterFrame.getMouseX(), Display.getHeight()/2-365+CharacterFrame.getMouseY());
	}
}
