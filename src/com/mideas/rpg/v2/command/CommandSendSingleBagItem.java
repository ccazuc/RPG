package com.mideas.rpg.v2.command;

public class CommandSendSingleBagItem extends Command {

	@Override
	public void read() {
	}
	
	/*public static void write(int slot, Item item) {
		if(item != null) {
			ConnectionManager.getConnection().startPacket();
			ConnectionManager.getConnection().writeShort(PacketID.SEND_SINGLE_BAG_ITEM);
			ConnectionManager.getConnection().writeInt(slot);
			if(item.isContainer() || item.isGem()) {
				ConnectionManager.getConnection().writeInt(item.getId());
			}
			else if(item.isStuff() || item.isWeapon()) {
				ConnectionManager.getConnection().writeInt(item.getId());
				writeGem(((Stuff)item).getEquippedGem(1));
				writeGem(((Stuff)item).getEquippedGem(2));
				writeGem(((Stuff)item).getEquippedGem(3));
			}
			else if(item.isItem() || item.isPotion()) {
				ConnectionManager.getConnection().writeInt(item.getId());
				ConnectionManager.getConnection().writeInt(item.getAmount());
			}
			ConnectionManager.getConnection().endPacket();
		}
	}
	
	private static void writeGem(Gem gem) {
		if(gem != null) {
			ConnectionManager.getConnection().writeInt(gem.getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
	}*/
}
