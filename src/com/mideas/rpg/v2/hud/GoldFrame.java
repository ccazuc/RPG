package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.utils.Draw;

public class GoldFrame {
	
	public static void draw() throws FileNotFoundException {
		int x = -202;
		int y = -51;
		TTF2.coin.drawStringShadow(Display.getWidth()+x+57-TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoin())), Display.getHeight()/2+y+166, String.valueOf(Mideas.calcGoldCoin()), Color.white, Color.black, 1, 1, 1);
		Draw.drawQuad(Sprites.gold_coin, Display.getWidth()+x+60, Display.getHeight()/2+y+165);
		TTF2.coin.drawStringShadow(Display.getWidth()+x+90-TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoin())), Display.getHeight()/2+y+166, String.valueOf(Mideas.calcSilverCoin()), Color.white, Color.black, 1, 1, 1);
		Draw.drawQuad(Sprites.silver_coin, Display.getWidth()+x+97, Display.getHeight()/2+y+165);
		TTF2.coin.drawStringShadow(Display.getWidth()+x+130-TTF2.coin.getWidth(String.valueOf(Mideas.getCurrentGold())), Display.getHeight()/2+y+166, String.valueOf(Mideas.getCurrentGold()), Color.white, Color.black, 1, 1, 1);
		Draw.drawQuad(Sprites.copper_coin, Display.getWidth()+x+133, Display.getHeight()/2+y+165);
	}
}
