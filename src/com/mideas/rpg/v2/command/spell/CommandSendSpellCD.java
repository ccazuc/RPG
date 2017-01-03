package com.mideas.rpg.v2.command.spell;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.spell.SpellManager;

public class CommandSendSpellCD extends Command {

	@Override
	public void read() {
		int id = ConnectionManager.getConnection().readInt();
		int cd = ConnectionManager.getConnection().readInt();
		long cdStart = ConnectionManager.getConnection().readLong();
		SpellManager.setCd(id, cdStart, cd);
	}
}
