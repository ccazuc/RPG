package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Tooltip;

public class PerformanceBarFrame {
	
	private static boolean shouldUpdateSize;
	private static boolean hoverPerformanceBar;
	private static boolean topPerformanceBarActive;
	
	private final static String usedRam = "used RAM : ";
	private final static String fps = "Fps : ";
	private final static String ping = "Ping : ";
	private final static String drawTime = "Draw time : ";
	private final static String interfaces = "Interface : ";
	private final static String container = "Container : ";
	private final static String total = "Total : ";
	private final static String character = "Character : ";
	private final static String spellbar = "Spellbar : ";
	private final static String drag = "Drag : ";
	private final static String 탎 = " 탎";
	private final static String mo = " Mo";
	
	private final static int x = -390;
	private final static int y = -450;
	private final static int yShift = 20;
	
	private static Tooltip tooltip = new Tooltip(Display.getWidth()+x-10, Display.getHeight()+y-10, 270, 350, 0.7f);

	public static void draw() {
		updateSize();
		if(hoverPerformanceBar) {
			tooltip.draw();
			TTF font = FontManager.get("FRIZQT", 15);
			font.drawBegin();
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y, usedRam+Long.toString(Mideas.getUsedRAM()/(1024l*1024l))+mo, Color.WHITE, Color.BLACK, 1);
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y+yShift, fps.concat(Mideas.getFPSString()), Color.WHITE, Color.BLACK, 1);
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y+2*yShift, ping+Mideas.getPing(), Color.WHITE, Color.BLACK, 1);
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y+3*yShift, drawTime, Color.WHITE, Color.BLACK, 1);
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y+4*yShift, interfaces+(Mideas.getInterfaceDrawTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y+5*yShift, container+(Interface.getContainerDrawTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			font.drawStringShadowPart(Display.getWidth()+x, Display.getHeight()+y+6*yShift, "Social : "+(Interface.getSocialDrawTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			font.drawEnd();
		}
		if(topPerformanceBarActive) {
			Draw.drawColorQuad(0, 0, Display.getWidth(), 15, Color.GREY);
			FontManager.font2.drawBegin();
			FontManager.font2.drawStringShadowPart(1, 0, total+(Mideas.getMouseEventTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			FontManager.font2.drawStringShadowPart(121, 0, container+(Interface.getContainerMouseEventTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			FontManager.font2.drawStringShadowPart(241, 0, character+(Interface.getCharacterMouseEventTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			FontManager.font2.drawStringShadowPart(361, 0, spellbar+(Interface.getSpellBarMouseEventTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			FontManager.font2.drawStringShadowPart(481, 0, drag+(Interface.getDragMouseEventTime()/1000)+탎, Color.WHITE, Color.BLACK, 1);
			FontManager.font2.drawEnd();
		}
	}
	
	public static boolean mouseEvent() {
		if(Mideas.mouseX()>= Display.getWidth()/2+300*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+325*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()-45*Mideas.getDisplayYFactor()) {
			hoverPerformanceBar = true;
		}
		else {
			hoverPerformanceBar = false;
		}
		return false;
	}
	
	public static void setPerformanceBarActive(boolean we) {
		topPerformanceBarActive = we;
	}
	
	public static boolean getPerformanceBarActive() {
		return topPerformanceBarActive;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		tooltip.updatePosition(Display.getWidth()+x-10, Display.getHeight()+y-10);
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
}
