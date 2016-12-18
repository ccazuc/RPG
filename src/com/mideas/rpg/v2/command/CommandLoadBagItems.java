package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandRequestItem;
import com.mideas.rpg.v2.command.item.RequestItemSlotType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.game.item.bag.ContainerManager;
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
		int i = 0;
		int amount = ConnectionManager.getConnection().readInt();
		while(i < amount) {
			loadItem();
			i++;
		}
	}
	
	private static void loadItem() {
		int number;
		int gem1Id = 0;
		int gem2Id = 0;
		int gem3Id = 0;
		int slot = ConnectionManager.getConnection().readInt();
		int id = ConnectionManager.getConnection().readInt();
		ItemType type = ItemType.values()[ConnectionManager.getConnection().readByte()];
		if(type == ItemType.STUFF) {
			boolean hasGem = ConnectionManager.getConnection().readBoolean();
			if(hasGem) {
				gem1Id = ConnectionManager.getConnection().readInt();
				gem2Id = ConnectionManager.getConnection().readInt();
				gem3Id = ConnectionManager.getConnection().readInt();
			}
			loadStuff(slot, id, gem1Id, gem2Id, gem3Id);
		}
		else if(type == ItemType.WEAPON) {
			boolean hasGem = ConnectionManager.getConnection().readBoolean();
			if(hasGem) {
				gem1Id = ConnectionManager.getConnection().readInt();
				gem2Id = ConnectionManager.getConnection().readInt();
				gem3Id = ConnectionManager.getConnection().readInt();
			}
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
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CONTAINER, index));
		}
		equipGem(index, 0, gem1Id);
		equipGem(index, 1, gem2Id);
		equipGem(index, 2, gem3Id);
 	}
	
	private static void loadWeapon(int index, int id, int gem1Id, int gem2Id, int gem3Id) {
		if(id != 0 && WeaponManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, WeaponManager.getClone(id));
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Stuff(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CONTAINER, index));
		}
		equipGem(index, 0, gem1Id);
		equipGem(index, 1, gem2Id);
		equipGem(index, 2, gem3Id);
 	}
	
	private static void equipGem(int bagSlot, int gemSlot, int id) {
		if(id != 0 && GemManager.exists(id)) {
			((Stuff)Mideas.joueur1().bag().getBag(bagSlot)).setEquippedGem(gemSlot, GemManager.getClone(id));
		}
		else if(id != 0) {
			((Stuff)Mideas.joueur1().bag().getBag(bagSlot)).setEquippedGem(gemSlot, new Gem(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CONTAINER, bagSlot, gemSlot));
			System.out.println("EQUIP GEM bagslot : "+bagSlot+" id "+id);
		}
	}
	
	private static void loadGem(int index, int id) {
		if(id != 0 && GemManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, GemManager.getClone(id));
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Gem(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CONTAINER, index));
		}
	}
	
	private static void loadContainer(int index, int id) {
		if(id != 0 && ContainerManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, ContainerManager.getClone(id));
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Gem(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CONTAINER, index));
		}
	}
	
	private static void loadPotion(int index, int id, int number) {
		if(id != 0 && PotionManager.exists(id)) {
			Mideas.joueur1().bag().setBag(index, PotionManager.getClone(id), number);
			Mideas.joueur1().bag().getBag(index).setAmount(number);
		}
		else if(id != 0) {
			Mideas.joueur1().bag().setBag(index, new Potion(id));
			Mideas.joueur1().bag().getBag(index).setAmount(number);
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CONTAINER, index));
		}
	}
}
