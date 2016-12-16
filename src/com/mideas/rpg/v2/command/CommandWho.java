package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.social.WhoUnit;
import com.mideas.rpg.v2.hud.social.who.WhoFrame;

public class CommandWho extends Command {
	
	@Override
	public void read() {
		WhoFrame.clearList();
		while(ConnectionManager.getConnection().hasRemaining()) {
			int id = ConnectionManager.getConnection().readInt();
			if(id == -1) {
				return;
			}
			String name = ConnectionManager.getConnection().readString();
			String guildName = ConnectionManager.getConnection().readString();
			Race race = Race.values()[ConnectionManager.getConnection().readChar()];
			int level = ConnectionManager.getConnection().readInt();
			ClassType classe = ClassType.values()[ConnectionManager.getConnection().readChar()];
			WhoFrame.addToList(new WhoUnit(id, name, guildName, level, classe, race));
		}
	}
	
	public static void write(String word) {
		ConnectionManager.getConnection().writeShort(PacketID.WHO);
		ConnectionManager.getConnection().writeString(word);
		ConnectionManager.getConnection().send();
	}
}