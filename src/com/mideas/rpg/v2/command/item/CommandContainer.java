package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.bag.Container;
import com.mideas.rpg.v2.game.item.bag.ContainerManager;

public class CommandContainer extends Command {
	
	@Override
	public void read() {
		ContainerManager.storeNewPiece(ConnectionManager.getConnection().readContainer());
	}

	@Deprecated
	public static void write(int id) {
		if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getConnection().writeShort(PacketID.CONTAINER);
			ConnectionManager.getConnection().writeInt(id);
			ConnectionManager.getConnection().send();
			ConnectionManager.getItemRequested().put(id, new Container(id));
		}
	}
}
