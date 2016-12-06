package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.social.Ignore;

public class CommandIgnore extends Command {
	
	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.IGNORE_ADD) {
			int id = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			Mideas.joueur1().addIgnore(new Ignore(id, name));
		}
		else if(packetId == PacketID.IGNORE_REMOVE) {
			int id = ConnectionManager.getConnection().readInt();
			Mideas.joueur1().removeIgnore(id);
		}
		else if(packetId == PacketID.IGNORE_INIT) {
			int length = ConnectionManager.getConnection().readInt();
			int i = 0;
			while(i < length) {
				int id = ConnectionManager.getConnection().readInt();
				String name = ConnectionManager.getConnection().readString();
				Mideas.joueur1().addIgnoreNoSort(new Ignore(id, name));
				i++;
			}
		}
	}
	
	public static void addIgnore(String name) {
		ConnectionManager.getConnection().writeShort(PacketID.IGNORE);
		ConnectionManager.getConnection().writeShort(PacketID.IGNORE_ADD);
		ConnectionManager.getConnection().writeString(name);
		ConnectionManager.getConnection().send();
	}
	
	public static void removeIgnore(int id) {
		ConnectionManager.getConnection().writeShort(PacketID.IGNORE);
		ConnectionManager.getConnection().writeShort(PacketID.IGNORE_REMOVE);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
