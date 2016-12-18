package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;

public class CommandPotion extends Command {
	
	@Override
	public void read() {
		PotionManager.storeNewPiece(ConnectionManager.getConnection().readPotion());
	}

	@Deprecated
	public static void write(int id) {
		if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getConnection().writeShort(PacketID.POTION);
			ConnectionManager.getConnection().writeInt(id);
			ConnectionManager.getConnection().send();
			ConnectionManager.getItemRequested().put(id, new Potion(id));
		}
	}
}
