package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.social.WhoUnit;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.hud.social.who.WhoFrame;

public class CommandWho extends Command {
	
	@Override
	public void read() {
		WhoFrame.clearList();
		while(ConnectionManager.getWorldServerConnection().hasRemaining()) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			if(id == -1) {
				return;
			}
			String name = ConnectionManager.getWorldServerConnection().readString();
			String guildName = ConnectionManager.getWorldServerConnection().readString();
			Race race = Race.values()[ConnectionManager.getWorldServerConnection().readByte()];
			int level = ConnectionManager.getWorldServerConnection().readInt();
			ClassType classe = ClassType.values()[ConnectionManager.getWorldServerConnection().readByte()];
			WhoFrame.addToList(new WhoUnit(id, name, guildName, level, classe, race));
		}
	}
	
	public static void write(String word) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.WHO);
		ConnectionManager.getWorldServerConnection().writeString(word);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
