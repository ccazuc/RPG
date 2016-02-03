package com.mideas.rpg.v2.hud;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.stuff.Stuff;

public class CraftManager {

	public static void draw() {
		
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
					Mideas.joueur1().setNumberPotion(Mideas.bag().getBag(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i))-itemNumber);
					return true;
				}
				i++;
			}
		}
		return false;
	}
}
