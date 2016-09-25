package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class CastBar {

	private final static int BAR_WIDTH = 214;
	private static float xDraw = -BAR_WIDTH/2;
	private static int yDraw = 250;
	private static Color bgColor = new Color(0, 0, 0, .35f);
	private static Cast currentCast;
	private static ArrayList<Cast> castList = new ArrayList<Cast>();
	private static boolean isCasting;
	private static double startCastTimer;
	
	public static void event() {
		if(!isCasting && currentCast == null && castList.size() > 0) {
			currentCast = castList.get(0);
			isCasting = true;
			startCastTimer = System.currentTimeMillis();
		}
		if(currentCast != null && isCasting && (System.currentTimeMillis()-startCastTimer)/currentCast.getLength() < 1) {
			Draw.drawColorQuad(Display.getWidth()/2+xDraw, Display.getHeight()/2+yDraw, BAR_WIDTH, 12, bgColor);
			Draw.drawQuad(Sprites.cast_bar_progression, Display.getWidth()/2+xDraw, Display.getHeight()/2+yDraw-1, (float)((System.currentTimeMillis()-startCastTimer)/currentCast.getLength())*BAR_WIDTH, 12*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.cast_bar_glow, Display.getWidth()/2+xDraw+(float)((System.currentTimeMillis()-startCastTimer)/currentCast.getLength())*BAR_WIDTH-17, Display.getHeight()/2+yDraw-Sprites.cast_bar_glow.getImageHeight()*Mideas.getDisplayXFactor()+22*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.cast_bar, Display.getWidth()/2+xDraw-11, Display.getHeight()/2+yDraw-15, Sprites.cast_bar.getImageWidth()*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayXFactor());
		}
		else if(currentCast != null && isCasting) {
			currentCast.endCastEvent();
			isCasting = false;
			startCastTimer = 0;
			currentCast = null;
			castList.remove(0);
		}
	}
	
	public static void addCast(Cast cast) {
		castList.add(cast);
	}
	
	public static boolean isCasting() {
		return isCasting;
	}
	
	public static void clearCast() {
		isCasting = false;
		startCastTimer = 0;
		if(castList.size() > 0) {
			castList.clear();
		}
	}
}
