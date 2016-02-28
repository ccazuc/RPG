package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class LogChat {
	
	private static String statusText = "";
	private static String statusText2 = "";
	private static String statusText3 = "";
	
	public static void draw() {
		Color bgColor = new Color(0, 0, 0,.35f); 
		Draw.drawColorQuad(30, Display.getHeight()-280, 510, 130, bgColor);
	}
	
	public static void drawStatus() { // player2
		int x = 40;
		int y = -190; 
		TTF2.font4.drawStringShadow(x, Display.getHeight()+y, statusText2, Color.white, Color.black, 1, 1, 1); 
	}
	
	public static void drawStatus2() { // player1
		int x = 40;
		int y = -230; 
		TTF2.font4.drawStringShadow(x, Display.getHeight()+y, statusText, Color.white, Color.black, 1, 1, 1);
	}
	
	public static void drawStatus3() {
		int x = 40;
		int y = -270; 
		TTF2.font4.drawStringShadow(x, Display.getHeight()+y, statusText3, Color.white, Color.black, 1, 1, 1);
	}
	
	public static void setStatusText(String text) {
		statusText = text;
	}
	
	public static void setStatusText2(String text) {
		statusText2 = text;
	}
	
	public static void setStatusText3(String text) {
		statusText3 = text;
	}
}
