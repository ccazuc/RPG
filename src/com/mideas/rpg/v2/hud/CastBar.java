package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class CastBar {
	
	private static double startCastTimer;
	private static float castTime;
	private static String name;
	private static float barWidth = 250;
	private static int barHeight = 20;
	private static float xDraw = -barWidth/2;
	private static int yDraw = 250;
	
	public static boolean draw() {
		if(castTime > 0 && (System.currentTimeMillis()-startCastTimer)/castTime < 1 && SpellBarFrame.getIsCastingSpell()) {
			Draw.drawColorQuad(Display.getWidth()/2+xDraw, Display.getHeight()/2+yDraw, (float)((System.currentTimeMillis()-startCastTimer)/castTime)*barWidth, barHeight, Color.yellow);
			TTF2.statsName.drawStringShadow(Display.getWidth()/2+xDraw+barWidth/2-TTF2.statsName.getWidth(name)/2, Display.getHeight()/2+yDraw, name, Color.white, Color.black, 1, 1, 1);
			Draw.drawColorQuadBorder(Display.getWidth()/2+xDraw, Display.getHeight()/2+yDraw, barWidth, barHeight, Color.black);
			return false;
		}
		else if(castTime > 0) {
			castTime = 0;
			startCastTimer = 0;
			name = "";
			return true;
		}
		return false;
	}

	public static void event(int length, String names) {
		castTime = length*1000;
		startCastTimer = System.currentTimeMillis();
		name = names;
	}
	
	
	
	
	
	private static Cast currentCast;
	private static ArrayList<Cast> castList = new ArrayList<Cast>();
	private static boolean isCasting;
	private static double startCAstTimer;
	
	public static void event() {
		if(!isCasting && currentCast == null && castList.size() > 0) {
			currentCast = castList.get(0);
			isCasting = true;
			startCAstTimer = System.currentTimeMillis();
		}
		if(currentCast != null && isCasting && (System.currentTimeMillis()-startCAstTimer)/currentCast.getLength() < 1) {
			Draw.drawColorQuad(Display.getWidth()/2+xDraw, Display.getHeight()/2+yDraw, (float)((System.currentTimeMillis()-startCAstTimer)/currentCast.getLength())*barWidth, barHeight, Color.yellow);
			TTF2.statsName.drawStringShadow(Display.getWidth()/2+xDraw+barWidth/2-TTF2.statsName.getWidth(currentCast.getName())/2, Display.getHeight()/2+yDraw, currentCast.getName(), Color.white, Color.black, 1, 1, 1);
			Draw.drawColorQuadBorder(Display.getWidth()/2+xDraw, Display.getHeight()/2+yDraw, barWidth, barHeight, Color.black);
		}
		else if(currentCast != null && isCasting) {
			currentCast.endCastEvent();
			isCasting = false;
			startCAstTimer = 0;
			currentCast = null;
			castList.remove(0);
		}
	}
	
	public static void addCast(Cast cast) {
		castList.add(cast);
	}
}
