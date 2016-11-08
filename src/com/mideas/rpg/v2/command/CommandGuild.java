package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandGuild extends Command {
	
	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.GUILD_UPDATE_PERMISSION) {
			int rank_order = ConnectionManager.getConnection().readInt();
			int permission = ConnectionManager.getConnection().readInt();
			Mideas.joueur1().getGuild().getRank(rank_order).setPermission(permission);
		}
		else if(packetId == PacketID.GUILD_INVITE_PLAYER) {
			String player_name = ConnectionManager.getConnection().readString();
			String guild_name = ConnectionManager.getConnection().readString();
			//enable popup
		}
	}
}
