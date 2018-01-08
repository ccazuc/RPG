package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class CommandAddItem extends Command {

	@Override
	public void read() {
		short packetID = ConnectionManager.getWorldServerConnection().readShort();
		if(packetID == PacketID.KNOWN_ITEM) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			int number = ConnectionManager.getWorldServerConnection().readInt();
			Item item = Item.getItem(id);
			Mideas.joueur1().addItem(item, number);
		}
		else if(packetID == PacketID.UNKNOWN_ITEM) {
			int number = ConnectionManager.getWorldServerConnection().readInt();
			ItemType type = ItemType.values()[ConnectionManager.getWorldServerConnection().readByte()];
			if(type == ItemType.CONTAINER) {
				Mideas.joueur1().addItem(ConnectionManager.getWorldServerConnection().readContainer(), number);
			}
			else if(type == ItemType.GEM) {
				Mideas.joueur1().addItem(ConnectionManager.getWorldServerConnection().readGem(), number);
			}
			else if(type == ItemType.STUFF) {
				Mideas.joueur1().addItem(ConnectionManager.getWorldServerConnection().readStuff(), number);
			}
			else if(type == ItemType.WEAPON) {
				Mideas.joueur1().addItem(ConnectionManager.getWorldServerConnection().readWeapon(), number);
			}
			else if(type == ItemType.POTION) {
				Mideas.joueur1().addItem(ConnectionManager.getWorldServerConnection().readPotion(), number);
			}
		}
	}
	
	public static void write(int character_id, int item_id, int number) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.ADD_ITEM);
		ConnectionManager.getWorldServerConnection().writeInt(character_id);
		ConnectionManager.getWorldServerConnection().writeInt(item_id);
		ConnectionManager.getWorldServerConnection().writeInt(number);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
