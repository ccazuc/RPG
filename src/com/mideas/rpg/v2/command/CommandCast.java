package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandCast {

	public static void cast(int spellId) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.SPELL_CAST);
		ConnectionManager.getConnection().writeInt(spellId);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
