package com.mideas.rpg.v2.command.spell;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.spell.SpellManager;

public class CommandSendSpellCD extends Command {

	@Override
	public void read() {
		int id = ConnectionManager.getWorldServerConnection().readInt();
		int cd = ConnectionManager.getWorldServerConnection().readInt();
		long cdStart = ConnectionManager.getWorldServerConnection().readLong();
		SpellManager.setCd(id, cdStart, cd);
	}
}
