package com.mideas.rpg.v2.command;

import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.bag.Container;

public class CommandAddItem extends Command {

	@Override
	public void read() {
		byte packetID = ConnectionManager.getConnection().readByte();
		if(packetID == PacketID.KNOWN_ITEM) {
			int id = ConnectionManager.getConnection().readInt();
			int number = ConnectionManager.getConnection().readInt();
			Item item = Item.getItem(id);
			try {
				Mideas.joueur1().addItem(item, number);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		else if(packetID == PacketID.UNKNOWN_ITEM) {
			ItemType type = ItemType.values()[ConnectionManager.getConnection().readChar()];
			if(type == ItemType.CONTAINER) {
				Container temp = ConnectionManager.getConnection().readContainer();
			}
		}
	}
	
	public static void write(int id, int number) {
		ConnectionManager.getConnection().writeByte(PacketID.ADD_ITEM);
		if(Item.exists(id)) {
			ConnectionManager.getConnection().writeByte(PacketID.KNOWN_ITEM);
			ConnectionManager.getConnection().writeInt(id);
			ConnectionManager.getConnection().writeInt(number);
		}
		else {
			ConnectionManager.getConnection().writeByte(PacketID.UNKNOWN_ITEM);
			ConnectionManager.getConnection().writeInt(id);
			ConnectionManager.getConnection().writeInt(number);
		}
	}
}
