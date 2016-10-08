package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandGem;
import com.mideas.rpg.v2.command.item.CommandPotion;
import com.mideas.rpg.v2.command.item.CommandStuff;
import com.mideas.rpg.v2.command.item.CommandWeapon;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class CommandLoadBagItems extends Command {

	@Override
	public void read() {
		System.out.println("Bag items loaded (CommandLoadBagItems)");
		/*int i = 0;
		int amount = ConnectionManager.getConnection().readInt();
		while(i < amount) {
			loadItem(i);
			i++;
		}*/
		
		int i = 0;
		int amount = ConnectionManager.getConnection().readInt();
		while(i < amount) {
			loadItem();
			i++;
		}
		Interface.setBagFullyLoaded(false);
		Mideas.joueur1().loadBag();
		Mideas.joueur1().bag().setBagChange(true);
	}
	
	private static void loadItem() {
		int number;
		int gem1Id;
		int gem2Id;
		int gem3Id;
		int slot = ConnectionManager.getConnection().readInt();
		int id = ConnectionManager.getConnection().readInt();
		ItemType type = ItemType.values()[ConnectionManager.getConnection().readChar()];
		if(type == ItemType.STUFF) {
			gem1Id = ConnectionManager.getConnection().readInt();
			gem2Id = ConnectionManager.getConnection().readInt();
			gem3Id = ConnectionManager.getConnection().readInt();
			loadStuff(slot, id, gem1Id, gem2Id, gem3Id);
		}
		else if(type == ItemType.WEAPON) {
			gem1Id = ConnectionManager.getConnection().readInt();
			gem2Id = ConnectionManager.getConnection().readInt();
			gem3Id = ConnectionManager.getConnection().readInt();
			loadWeapon(slot, id, gem1Id, gem2Id, gem3Id);
		}
		else if(type == ItemType.GEM) {
			loadGem(slot, id);
		}
		else if(type == ItemType.CONTAINER) {
			loadContainer(slot, id);
		}
		else if(type == ItemType.POTION) {
			number = ConnectionManager.getConnection().readInt();
			loadPotion(slot, id, number);
		}
	}
	
	private static void loadStuff(int index, int id, int gem1Id, int gem2Id, int gem3Id) {
		if(id != 0 && StuffManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, StuffManager.getClone(id));
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Stuff(id));
			CommandStuff.write(id);
		}
		if(gem1Id != 0 && GemManager.exists(gem1Id)) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(GemManager.getClone(gem1Id));
		}
		else if(gem1Id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(new Gem(gem1Id));
			CommandGem.write(gem1Id);
		}
		if(gem2Id != 0 && GemManager.exists(gem2Id)) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(GemManager.getClone(gem2Id));
		}
		else if(gem2Id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem2(new Gem(gem2Id));
			CommandGem.write(gem2Id);
		}
		if(gem3Id != 0 && GemManager.exists(gem3Id)) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(GemManager.getClone(gem3Id));
		}
		else if(gem2Id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem2(new Gem(gem3Id));
			CommandGem.write(gem3Id);
		}
 	}
	
	private static void loadWeapon(int index, int id, int gem1Id, int gem2Id, int gem3Id) {
		if(id != 0 && WeaponManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, WeaponManager.getClone(id));
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Stuff(id));
			CommandWeapon.write(id);
		}
		if(gem1Id != 0 && GemManager.exists(gem1Id)) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(GemManager.getClone(gem1Id));
		}
		else if(gem1Id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(new Gem(gem1Id));
			CommandGem.write(gem1Id);
		}
		if(gem2Id != 0 && GemManager.exists(gem2Id)) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(GemManager.getClone(gem2Id));
		}
		else if(gem2Id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem2(new Gem(gem2Id));
			CommandGem.write(gem2Id);
		}
		if(gem3Id != 0 && GemManager.exists(gem3Id)) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem1(GemManager.getClone(gem3Id));
		}
		else if(gem2Id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(index)).setEquippedGem2(new Gem(gem3Id));
			CommandGem.write(gem3Id);
		}
 	}
	
	private static void loadGem(int index, int id) {
		if(id != 0 && GemManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, GemManager.getClone(id));
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Gem(id));
			CommandGem.write(id);
		}
	}
	
	private static void loadContainer(int index, int id) {
		
	}
	
	private static void loadPotion(int index, int id, int number) {
		if(id != 0 && PotionManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, PotionManager.getClone(id), number);
			Mideas.joueur1().bag().getNumberStack().put(Mideas.joueur1().bag().getBag(index), number);
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Potion(id));
			Mideas.joueur1().bag().getNumberStack().put(Mideas.joueur1().bag().getBag(index), number);
			CommandPotion.write(id);
		}
	}
}
