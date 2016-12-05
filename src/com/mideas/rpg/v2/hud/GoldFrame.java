package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class GoldFrame {
	
	public static void draw() {
		if(ContainerFrame.getBagOpen(0)) {
			float x = -159*Mideas.getDisplayXFactor();
			float y = -174*Mideas.getDisplayYFactor();
			int gold = Mideas.calcGoldCoin();
			int silver = Mideas.calcSilverCoin();
			int copper = Mideas.joueur1().getGold()-Mideas.calcGoldCoin()*10000-Mideas.calcSilverCoin()*100;
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()+x, Display.getHeight()+y);
			x-= FontManager.get("FRIZQT", 11).getWidth(String.valueOf(copper))+2;
			FontManager.get("FRIZQT", 11).drawStringShadow(Display.getWidth()+x, Display.getHeight()+y+1, String.valueOf(copper), Color.WHITE, Color.BLACK, 1, 0, 0);
			x-= Sprites.silver_coin.getImageWidth()+7;
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()+x, Display.getHeight()+y);
			x-= FontManager.get("FRIZQT", 11).getWidth(String.valueOf(silver))+2;
			FontManager.get("FRIZQT", 11).drawStringShadow(Display.getWidth()+x, Display.getHeight()+y+1, String.valueOf(silver), Color.WHITE, Color.BLACK, 1, 0, 0);
			x-= Sprites.gold_coin.getImageWidth()+7;
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()+x, Display.getHeight()+y);
			x-= FontManager.get("FRIZQT", 11).getWidth(String.valueOf(gold))+2;
			FontManager.get("FRIZQT", 11).drawStringShadow(Display.getWidth()+x, Display.getHeight()+y+1, String.valueOf(gold), Color.WHITE, Color.BLACK, 1, 0, 0);
		}
	}
}
