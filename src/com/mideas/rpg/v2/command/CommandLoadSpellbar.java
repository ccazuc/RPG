package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandPotion;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;

public class CommandLoadSpellbar extends Command {

	@Override
	public void read() {
		System.out.println("Spellbar loaded (CommandSpellbar)");
		int i = 0;
		int amount = ConnectionManager.getConnection().readInt();
		while(i < amount) {
			loadItem(i);
			i++;
		}
		Interface.setSpellbarFullyLoaded(false);
		//Mideas.joueur1().loadSpellbar();
	}
	
	private static void loadItem(int index) {
		int id = ConnectionManager.getConnection().readInt();
		ItemType type = ItemType.values()[ConnectionManager.getConnection().readChar()];
		if(id != 0) {
			if(type == ItemType.STUFF) {
				loadStuff(index, id);
			}
			else if(type == ItemType.WEAPON) {
				loadWeapon(index, id);
			}
			else if(type == ItemType.POTION) {
				loadPotion(index, id);
			}
		}
	}
	
	private static void loadStuff(int index, int id) {
		if(id != 0 && StuffManager.exists(id)) {
			Mideas.joueur1().setSpells(index, new StuffShortcut(StuffManager.getClone(id)));
		}
		else if(id != 0) {
			Mideas.joueur1().setSpells(index, new StuffShortcut(new Stuff(id)));
			Mideas.joueur1().getSpells(index).setIsLoaded(false);
			CommandStuff.write(id);
		}
	}
	
	private static void loadWeapon(int index, int id) {
		if(id != 0 && WeaponManager.exists(id)) {
			Mideas.joueur1().setSpells(index, new StuffShortcut(WeaponManager.getClone(id)));
		}
		else if(id != 0) {
			Mideas.joueur1().setSpells(index, new StuffShortcut(new Stuff(id)));
			Mideas.joueur1().getSpells(index).setIsLoaded(false);
			CommandWeapon.write(id);
		}
	}
	
	private static void loadPotion(int index, int id) {
		if(id != 0 && PotionManager.exists(id)) {
			Mideas.joueur1().setSpells(index, new PotionShortcut(PotionManager.getClone(id)));
		}
		else if(id != 0) {
			Mideas.joueur1().setSpells(index, new PotionShortcut(new Potion(id)));
			Mideas.joueur1().getSpells(index).setIsLoaded(false);
			CommandPotion.write(id);
		}
	}
}
