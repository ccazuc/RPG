package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class GoldFrame {
	
	public static void draw() {
		if(ContainerFrame.getBagOpen(0)) {
			int x = -195;
			int y = -101;
			TTF2.coin.drawStringShadow(Display.getWidth()+x+70-TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoin())), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoin()), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()+x+73, Display.getHeight()+y);
			TTF2.coin.drawStringShadow(Display.getWidth()+x+103-TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoin())), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoin()), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()+x+103, Display.getHeight()+y);
			TTF2.coin.drawStringShadow(Display.getWidth()+x+133-TTF2.coin.getWidth(String.valueOf(Mideas.getCurrentGold()-Mideas.calcGoldCoin()*10000-Mideas.calcSilverCoin()*100)), Display.getHeight()+y, String.valueOf(Mideas.getCurrentGold()-Mideas.calcGoldCoin()*10000-Mideas.calcSilverCoin()*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()+x+133, Display.getHeight()+y);
		}
	}
}
