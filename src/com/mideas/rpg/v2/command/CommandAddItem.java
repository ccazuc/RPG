package com.mideas.rpg.v2.command;

import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandRequestItem;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;

public class CommandAddItem extends Command {

	@Override
	public void read() {
		if(ConnectionManager.getConnection().readByte() == PacketID.ADD_ITEM_CONFIRMED) {
			int id = ConnectionManager.getConnection().readInt();
			int number = ConnectionManager.getConnection().readInt();
			if(Item.exists(id)) {
				Item item = Item.getItem(id);
				try {
					if(number == 1) {
						Mideas.joueur1().addItem(item, number);
					}
					else if(number > 1) {
						if(item.isStackable()) {
							Mideas.joueur1().addItem(item, number);
						}
						else {
							Mideas.joueur1().addMultipleUnstackableItem(id, number);
						}
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				CommandRequestItem.write(id);
			}
		}
	}
	
	public static void write(int id, int number) {
		ConnectionManager.getConnection().writeByte(PacketID.ADD_ITEM);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().writeInt(number);
	}
}
