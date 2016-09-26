package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class PerformanceBarFrame {
	
	private static boolean hoverPerformanceBar;
	private static boolean topPerformanceBarActive = true;

	public static void draw() {
		if(hoverPerformanceBar) {
			Draw.drawQuad(Sprites.tooltip, Display.getWidth()-400, Display.getHeight()-460, 270, 350);
			TTF2.statsName.drawStringShadow(Display.getWidth()-390, Display.getHeight()-450, "used RAM : "+Long.toString(Mideas.getUsedRAM()/(1024L*1024L))+" Mo", Color.white, Color.black, 1);
			TTF2.statsName.drawStringShadow(Display.getWidth()-390, Display.getHeight()-430, "Fps : "+Mideas.getFps(), Color.white, Color.black, 1);
			TTF2.statsName.drawStringShadow(Display.getWidth()-390, Display.getHeight()-410, "Draw time :", Color.white, Color.black, 1);
			TTF2.statsName.drawStringShadow(Display.getWidth()-390, Display.getHeight()-390, "interface : "+(Mideas.getInterfaceDrawTime()/1000)+" 탎", Color.white, Color.black, 1);
			TTF2.statsName.drawStringShadow(Display.getWidth()-390, Display.getHeight()-370, "Container : "+(Interface.getContainerDrawTime()/1000)+" 탎", Color.white, Color.black, 1);
		}
		if(topPerformanceBarActive) {
			Draw.drawColorQuad(0, 0, Display.getWidth(), 15, Color.gray);
			TTF2.font2.drawStringShadow(1, 0, "total : "+(Mideas.getMouseEventTime()/1000)+" 탎", Color.white, Color.black, 1);
			TTF2.font2.drawStringShadow(121, 0, "container : "+(Interface.getContainerMouseEventTime()/1000)+" 탎", Color.white, Color.black, 1);
			TTF2.font2.drawStringShadow(241, 0, "charactere : "+(Interface.getCharacterMouseEventTime()/1000)+" 탎", Color.white, Color.black, 1);
			TTF2.font2.drawStringShadow(361, 0, "spellBar : "+(Interface.getSpellBarMouseEventTime()/1000)+" 탎", Color.white, Color.black, 1);
			TTF2.font2.drawStringShadow(481, 0, "drag : "+(Interface.getDragMouseEventTime()/1000)+" 탎", Color.white, Color.black, 1);
		}
	}
	
	public static boolean mouseEvent() {
		hoverPerformanceBar = false;
		if(Mideas.mouseX()>= Display.getWidth()/2+300*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+325*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()-45*Mideas.getDisplayYFactor()) {
			hoverPerformanceBar = true;
		}
		return false;
	}
	
	public static void setPerformanceBarActive(boolean we) {
		topPerformanceBarActive = we;
	}
	
	public static boolean getPerformanceBarActive() {
		return topPerformanceBarActive;
	}
}
