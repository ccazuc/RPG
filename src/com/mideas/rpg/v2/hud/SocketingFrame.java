package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class SocketingFrame {

	private static Stuff stuff;
	
	public static void draw() {
		if(Display.wasResized()) {
			CharacterFrame.setGemFrame((int)(Sprites.socketing_frame.getImageWidth()*Mideas.getDisplayXFactor())+15);
		}
		Draw.drawQuad(Sprites.socketing_frame, Display.getWidth()/2-300+CharacterFrame.getMouseX(), Display.getHeight()/2-380+CharacterFrame.getMouseY());
	}
	
	public static boolean mouseEvent() {
		if(Keyboard.getEventKey() == Keyboard.KEY_O) {
			Interface.setSocketingFrameStatus(false);
			CharacterFrame.setGemFrame(0);
			stuff = null;
		}
		return false;
	}
	
	public static Stuff getStuff() {
		return stuff;
	}
	
	public static void setStuff(Stuff stuffs) {
		stuff = stuffs;
	}
}
