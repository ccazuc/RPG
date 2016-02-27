package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class CraftManager {

	public static void draw() {
		Draw.drawQuad(Sprites.craft_frame, Display.getWidth()/2-480, Display.getHeight()/2-350);
	}
	
	public static boolean mouseEvent() {
		//mouseHover
		return false;
	}
	
	private static boolean checkNumberItems(int number, Stuff item, int itemNumber) {
		if(itemNumber > number) {
			int i = 0;
			while(i < Mideas.bag().getBag().length) {
				if(Mideas.bag().getBag(i).equals(item)) {
					Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i))-itemNumber);
					return true;
				}
				i++;
			}
		}
		return false;
	}
}
