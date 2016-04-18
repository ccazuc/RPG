package com.mideas.rpg.v2.game.shortcut;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.DragSpellManager;

public class StuffShortcut implements Shortcut {

	private Stuff stuff;
	private ShortcutType type;

	public StuffShortcut(Stuff stuff) {
		this.stuff = stuff;
		this.type = ShortcutType.STUFF;
	}
	
	public boolean use(Shortcut shortcut) throws FileNotFoundException, SQLException {
		int i = 0;
		if(DragManager.getDraggedItem() == null && DragSpellManager.getDraggedSpell() == null && DragSpellManager.getDraggedSpellBook() == null) {
			while(i < Mideas.joueur1().getStuff().length) {
				if(stuff.getItemType() == ItemType.STUFF) {
					if(stuff != null && stuff.getType() == DragManager.getStuffType(i) && stuff.canEquipTo(DragManager.convClassType()) && DragManager.canWear(stuff)) {
						if(Mideas.getLevel() >= stuff.getLevel()) {
							if(Mideas.joueur1().getStuff(i) == null) {
								Mideas.joueur1().setStuff(i, stuff);
								DragManager.calcStats(Mideas.joueur1().getStuff(i));
								DragManager.setNullContainer(Mideas.bag().getBag(i));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								break;
							}
							else {
								Item tempItem = Mideas.joueur1().getStuff(i);
								DragManager.calcStatsLess(tempItem);
								Mideas.joueur1().setStuff(i, stuff);
								DragManager.calcStats(Mideas.joueur1().getStuff(i));
								Mideas.bag().setBag(DragManager.checkItemSlot(stuff), tempItem);
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								break;
							}
						}
					}
				}
				else if(stuff.getItemType() == ItemType.WEAPON) {
					if(stuff != null && stuff.getWeaponSlot() == DragManager.getWeaponSlot(i) && stuff.canEquipTo(DragManager.convClassType())) {
						if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel()) {
							if(Mideas.joueur1().getStuff(i) == null) {
								Mideas.joueur1().setStuff(i, stuff);
								DragManager.calcStats(Mideas.joueur1().getStuff(i));
								DragManager.setNullContainer(Mideas.bag().getBag(i));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								break;
							}
							else {
								Item tempItem = Mideas.joueur1().getStuff(i);
								DragManager.calcStatsLess(tempItem);
								Mideas.joueur1().setStuff(i, stuff);
								DragManager.calcStats(Mideas.joueur1().getStuff(i));
								Mideas.bag().setBag(DragManager.checkItemSlot(stuff), tempItem);
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								break;
							}
						}
					}
				}
				i++;
			}
		}
		return false;
	}
	
	public int getId() {
		return stuff.getId();
	}
	
	public Texture getSprite() {
		return IconsManager.getSprite47(stuff.getSpriteId());
	}

	public void setCd(int id, int cd) {
	}
	
	public Stuff getStuff() {
		return stuff;
	}
	
	public ShortcutType getShortcutType() {
		return type;
	}
}
