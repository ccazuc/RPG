package com.mideas.rpg.v2.command.spell;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandSpellUnlocked extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.SPELL_UNLOCKED_ADD) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			Mideas.joueur1().addSpellUnlocked(id);
		}
		else if(packetId ==  PacketID.SPELL_UNLOCKED_INIT) {
			short length = ConnectionManager.getWorldServerConnection().readShort();
			int i = 0;
			while(i < length) {
				Mideas.joueur1().addSpellUnlocked(ConnectionManager.getWorldServerConnection().readInt());
				i++;
			}
		}
		else if(packetId == PacketID.SPELL_UNLOCKED_REMOVE) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			Mideas.joueur1().removeSpellUnlocked(id);
		}
	}
}
