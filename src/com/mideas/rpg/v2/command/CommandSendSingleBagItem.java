package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class CommandSendSingleBagItem extends Command {

	@Override
	public void read() {
	}
	
	public static void write(int slot, Item item) {
		if(item != null) {
			ConnectionManager.getConnection().writeByte(PacketID.SEND_SINGLE_BAG_ITEM);
			ConnectionManager.getConnection().writeInt(slot);
			if(item.isContainer() || item.isGem()) {
				ConnectionManager.getConnection().writeInt(item.getId());
			}
			else if(item.isStuff() || item.isWeapon()) {
				ConnectionManager.getConnection().writeInt(item.getId());
				writeGem(((Stuff)item).getEquippedGem1());
				writeGem(((Stuff)item).getEquippedGem2());
				writeGem(((Stuff)item).getEquippedGem3());
			}
			else if(item.isItem() || item.isPotion()) {
				ConnectionManager.getConnection().writeInt(item.getId());
				ConnectionManager.getConnection().writeInt(item.getAmount());
			}
		}
	}
	
	private static void writeGem(Gem gem) {
		if(gem != null) {
			ConnectionManager.getConnection().writeInt(gem.getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
	}
}
