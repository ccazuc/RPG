package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;

public class CommandStuff extends Command {
	
	@Override
	public void read() {
		StuffManager.storeNewPiece(ConnectionManager.getWorldServerConnection().readStuff());
	}
	
	@Deprecated
	public static void write(int id) {
		/*if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getWorldServerConnection().writeShort(PacketID.STUFF);
			ConnectionManager.getWorldServerConnection().writeInt(id);
			ConnectionManager.getWorldServerConnection().send();
			ConnectionManager.getItemRequested().put(id, new Stuff(id));
		}*/
	}
}
