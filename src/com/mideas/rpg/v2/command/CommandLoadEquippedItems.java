package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class CommandLoadEquippedItems extends Command {

	@Override
	public void read() {
		/*int i = 0;
		while(i < 17) {
			if(i < 10) {
				loadStuff(i);
			}
			else if(i >= 10 && i < 14) {
				loadItem(i);
			}
			else if(i >= 14 && i < 17) {
				loadWeapon(i);
			}
			i++;
		}*/
		loadStuff(0);
		loadStuff(1);
		loadStuff(2);
		loadStuff(3);
		loadStuff(4);
		loadStuff(7);
		loadStuff(8);
		loadStuff(9);
		loadStuff(10);
		loadStuff(11);
		loadItem(12);
		loadItem(13);
		loadItem(14);
		loadItem(15);
		loadWeapon(16);
		loadWeapon(17);
		loadWeapon(18);
	}
	
	private static void loadStuff(int index) {
		int id = ConnectionManager.getConnection().readInt();
		int gem1Id = ConnectionManager.getConnection().readInt();
		int gem2Id = ConnectionManager.getConnection().readInt();
		int gem3Id = ConnectionManager.getConnection().readInt();
		if(!StuffManager.exists(id) && id != 0) {
			CommandStuff.write(id);
			Mideas.joueur1().setStuff(index, new Stuff(id));
		}
		else if(id != 0) {
			Mideas.joueur1().setStuff(index, StuffManager.getClone(id));
			Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem1Id));
			Mideas.joueur1().getStuff(index).setEquippedGem2(GemManager.getClone(gem2Id));
			Mideas.joueur1().getStuff(index).setEquippedGem3(GemManager.getClone(gem3Id));
		}
	}
	
	private static void loadItem(int index) {
		int id = ConnectionManager.getConnection().readInt();
		if(!StuffManager.exists(id) && id != 0) {
			CommandStuff.write(id);
			Mideas.joueur1().setStuff(index, new Stuff(id));
		}
		else if(id != 0) {
			Mideas.joueur1().setStuff(index, StuffManager.getClone(id));
		}
		
	}
	
	private static void loadWeapon(int index) {
		int id = ConnectionManager.getConnection().readInt();
		int gem1Id = ConnectionManager.getConnection().readInt();
		int gem2Id = ConnectionManager.getConnection().readInt();
		int gem3Id = ConnectionManager.getConnection().readInt();
		if(!WeaponManager.exists(id) && id != 0) {
			CommandStuff.write(id);
			Mideas.joueur1().setStuff(index, new Stuff(id));
		}
		else if(id != 0) {
			Mideas.joueur1().setStuff(index, WeaponManager.getClone(id));
			Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem1Id));
			Mideas.joueur1().getStuff(index).setEquippedGem2(GemManager.getClone(gem2Id));
			Mideas.joueur1().getStuff(index).setEquippedGem3(GemManager.getClone(gem3Id));
		}
	}
}
