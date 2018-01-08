package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;

public class CommandPotion extends Command {
	
	@Override
	public void read() {
		PotionManager.storeNewPiece(ConnectionManager.getWorldServerConnection().readPotion());
	}

	@Deprecated
	public static void write(int id) {
		/*if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getWorldServerConnection().writeShort(PacketID.POTION);
			ConnectionManager.getWorldServerConnection().writeInt(id);
			ConnectionManager.getWorldServerConnection().send();
			ConnectionManager.getItemRequested().put(id, new Potion(id));
		}*/
	}
}
