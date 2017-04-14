package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class CastBar {

	private final static int BAR_WIDTH = 214;
	private static float xDraw = -BAR_WIDTH/2;
	private static int yDraw = 250;
	private final static Color bgColor = new Color(0, 0, 0, .35f);
	private static boolean isCasting;
	private static long startCastTimer;
	private static String castName;
	private static int castNameWidth;
	private static float castLength;
	private final static TTF font = FontManager.get("FRIZQT", 14);
	
	public static void event() {
		if(isCasting && (Mideas.getLoopTickTimer()-startCastTimer)/castLength < 1) {
			Draw.drawColorQuad(Display.getWidth()/2+xDraw*Mideas.getDisplayXFactor(), Display.getHeight()/2+yDraw*Mideas.getDisplayYFactor(), BAR_WIDTH*Mideas.getDisplayXFactor(), 12*Mideas.getDisplayXFactor(), bgColor);
			Draw.drawQuad(Sprites.cast_bar_progression, Display.getWidth()/2+xDraw*Mideas.getDisplayXFactor(), Display.getHeight()/2+yDraw*Mideas.getDisplayYFactor()-1, ((Mideas.getLoopTickTimer()-startCastTimer)/castLength)*BAR_WIDTH*Mideas.getDisplayXFactor(), 12*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.cast_bar_glow, Display.getWidth()/2+xDraw*Mideas.getDisplayXFactor()+((Mideas.getLoopTickTimer()-startCastTimer)/castLength)*BAR_WIDTH*Mideas.getDisplayXFactor()-17*Mideas.getDisplayXFactor(), Display.getHeight()/2+yDraw*Mideas.getDisplayYFactor()-Sprites.cast_bar_glow.getImageHeight()*Mideas.getDisplayXFactor()+22*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.cast_bar, Display.getWidth()/2+(xDraw-11)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(yDraw-15)*Mideas.getDisplayYFactor(), Sprites.cast_bar.getImageWidth()*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayXFactor());
			font.drawStringShadow(Display.getWidth()/2+xDraw*Mideas.getDisplayXFactor()+BAR_WIDTH/2*Mideas.getDisplayXFactor()-castNameWidth/2, Display.getHeight()/2+(yDraw-3)*Mideas.getDisplayYFactor(), castName, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else if(isCasting) {
			isCasting = false;
		}
	}
	
	public static void castSpell(Spell spell, long startCastTimer, int castLength) {
		castName = spell.getName();
		CastBar.castLength = castLength;
		CastBar.startCastTimer = startCastTimer;
		castNameWidth = font.getWidth(castName);
		isCasting = true;
	}
	 
	public static boolean isCasting() {
		return isCasting;
	}
	
	public static void clearCast() {
		isCasting = false;
	}
}
