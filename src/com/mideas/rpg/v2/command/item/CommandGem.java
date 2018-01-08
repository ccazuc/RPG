package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.gem.GemManager;

public class CommandGem extends Command {
	
	@Override
	public void read() {
		GemManager.storeNewPiece(ConnectionManager.getWorldServerConnection().readGem());
	}

	@Deprecated
	public static void write(int id) {
		/*if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getWorldServerConnection().writeShort(PacketID.GEM);
			ConnectionManager.getWorldServerConnection().writeInt(id);
			ConnectionManager.getWorldServerConnection().send();
			ConnectionManager.getItemRequested().put(id, new Gem(id));
		}*/
	}
}