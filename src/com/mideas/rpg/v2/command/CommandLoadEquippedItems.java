package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandRequestItem;
import com.mideas.rpg.v2.command.item.RequestItemSlotType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.RequestItem;
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
	}
	
	private static void loadStuff(int index) {
		int id = ConnectionManager.getConnection().readInt();
		boolean hasGem = ConnectionManager.getConnection().readBoolean();
		int gem1Id = 0;
		int gem2Id = 0;
		int gem3Id = 0;
		if(hasGem) {
			gem1Id = ConnectionManager.getConnection().readInt();
			gem2Id = ConnectionManager.getConnection().readInt();
			gem3Id = ConnectionManager.getConnection().readInt();
		}
		if(id != 0 && !StuffManager.exists(id)) {
			Mideas.joueur1().setStuff(index, new Stuff(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CHARACTER, index));
		}
		else if(id != 0) {
			if(StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				Mideas.joueur1().setStuff(index, StuffManager.getClone(id));
			}
		}
		if(Mideas.joueur1().getStuff(index) != null) {
			equipGem(index, 0, gem1Id);
			equipGem(index, 1, gem2Id);
			equipGem(index, 2, gem3Id);
		}
	}
	
	private static void loadItem(int index) {
		int id = ConnectionManager.getConnection().readInt();
		if(!StuffManager.exists(id) && id != 0) {
			Mideas.joueur1().setStuff(index, new Stuff(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CHARACTER, index));
		}
		else if(id != 0) {
			Mideas.joueur1().setStuff(index, StuffManager.getClone(id));
		}
	}
	
	private static void loadWeapon(int index) {
		int id = ConnectionManager.getConnection().readInt();
		boolean hasGem = ConnectionManager.getConnection().readBoolean();
		int gem1Id = 0;
		int gem2Id = 0;
		int gem3Id = 0;
		if(hasGem) {
			gem1Id = ConnectionManager.getConnection().readInt();
			gem2Id = ConnectionManager.getConnection().readInt();
			gem3Id = ConnectionManager.getConnection().readInt();
		}
		if(!WeaponManager.exists(id) && id != 0) {
			Mideas.joueur1().setStuff(index, new Stuff(id));
			CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CHARACTER, index));
		}
		else if(id != 0) {
			Mideas.joueur1().setStuff(index, WeaponManager.getClone(id));
		}
		equipGem(index, 0, gem1Id);
		equipGem(index, 1, gem2Id);
		equipGem(index, 2, gem3Id);
	}
	
	private static void equipGem(int bagSlot, int gemSlot, int id) {
		if(Mideas.joueur1().getStuff(bagSlot) != null) {
			if(id != 0 && GemManager.exists(id)) {
				Mideas.joueur1().getStuff(bagSlot).setEquippedGem(gemSlot, GemManager.getClone(id));
			}
			else if(id != 0) {
				Mideas.joueur1().getStuff(bagSlot).setEquippedGem(gemSlot, new Gem(id));
				CommandRequestItem.write(new RequestItem(id, RequestItemSlotType.CHARACTER, bagSlot, gemSlot));
			}
		}
	}
}
