package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandGem;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class CommandLoadEquippedItems extends Command {

	@Override
	public void read() {
		System.out.println("Equipped items loaded (CommandLoadEquippedItems)");
		int i = 0;
		while(i < 19) {
			if(i != 5 && i != 6 && i < 12) {
				loadStuff(i);
			}
			else if(i >= 12 && i < 16) {
				loadItem(i);
			}
			else if(i >= 16 && i < 19) {
				loadWeapon(i);
			}
			i++;
		}
		Interface.setStuffFullyLoaded(false);
		Mideas.joueur1().loadStuff();
	}
	
	private static void loadStuff(int index) {
		int id = ConnectionManager.getConnection().readInt();
		int gem1Id = ConnectionManager.getConnection().readInt();
		int gem2Id = ConnectionManager.getConnection().readInt();
		int gem3Id = ConnectionManager.getConnection().readInt();
		if(id != 0 && !StuffManager.exists(id)) {
			Mideas.joueur1().setStuff(index, new Stuff(id));
			CommandStuff.write(id);
		}
		else if(id != 0) {
			if(StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				Mideas.joueur1().setStuff(index, StuffManager.getClone(id));
			}
		}
		if(Mideas.joueur1().getStuff(index) != null) {
			if(gem1Id != 0 && GemManager.exists(gem1Id)) {
				Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem1Id));
			}
			else if(gem1Id != 0) {
				Mideas.joueur1().getStuff(index).setEquippedGem1(new Gem(gem1Id));
				CommandGem.write(gem1Id);
			}
			if(gem2Id != 0 && GemManager.exists(gem2Id)) {
				Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem2Id));
			}
			else if(gem2Id != 0) {
				Mideas.joueur1().getStuff(index).setEquippedGem2(new Gem(gem2Id));
				CommandGem.write(gem2Id);
			}
			if(gem3Id != 0 && GemManager.exists(gem3Id)) {
				Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem3Id));
			}
			else if(gem2Id != 0) {
				Mideas.joueur1().getStuff(index).setEquippedGem2(new Gem(gem3Id));
				CommandGem.write(gem3Id);
			}
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
			Mideas.joueur1().setStuff(index, new Stuff(id));
			CommandWeapon.write(id);
		}
		else if(id != 0) {
			Mideas.joueur1().setStuff(index, WeaponManager.getClone(id));
		}
		if(gem1Id != 0 && GemManager.exists(gem1Id)) {
			Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem1Id));
		}
		else if(gem1Id != 0) {
			Mideas.joueur1().getStuff(index).setEquippedGem1(new Gem(gem1Id));
			CommandGem.write(gem1Id);
		}
		if(gem2Id != 0 && GemManager.exists(gem2Id)) {
			Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem2Id));
		}
		else if(gem2Id != 0) {
			Mideas.joueur1().getStuff(index).setEquippedGem2(new Gem(gem2Id));
			CommandGem.write(gem2Id);
		}
		if(gem3Id != 0 && GemManager.exists(gem3Id)) {
			Mideas.joueur1().getStuff(index).setEquippedGem1(GemManager.getClone(gem3Id));
		}
		else if(gem2Id != 0) {
			Mideas.joueur1().getStuff(index).setEquippedGem2(new Gem(gem3Id));
			CommandGem.write(gem3Id);
		}
	}
}
