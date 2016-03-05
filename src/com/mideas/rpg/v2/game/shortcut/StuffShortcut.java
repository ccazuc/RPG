package com.mideas.rpg.v2.game.shortcut;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.StuffType;
import com.mideas.rpg.v2.hud.DragManager;

public class StuffShortcut implements Shortcut {

	private Stuff stuff;
	
	public Texture getSprite() {
		return IconsManager.getSprite54(stuff.getSpriteId());
	}

	public StuffShortcut(Stuff stuff) {
		this.stuff = stuff;
	}
	
	public boolean use(Shortcut shortcut) {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(stuff.getType() == DragManager.getStuffType(i) && Mideas.joueur1().getStuff(i) == null) {
				Mideas.joueur1().setStuff(i, stuff);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public void setCd(int id, int cd) {
	}
	
	public Stuff getStuff() {
		return stuff;
	}
}