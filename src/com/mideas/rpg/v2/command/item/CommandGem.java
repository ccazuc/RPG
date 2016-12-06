package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemManager;

public class CommandGem extends Command {
	
	@Override
	public void read() {
		GemManager.storeNewPiece(ConnectionManager.getConnection().readGem());
	}

	public static void write(int id) {
		if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getConnection().writeShort(PacketID.GEM);
			ConnectionManager.getConnection().writeInt(id);
			ConnectionManager.getConnection().send();
			ConnectionManager.getItemRequested().put(id, new Gem(id));
		}
	}
}