package com.mideas.rpg.v2.command.spell;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandSendGCD extends Command {

	@Override
	public void read() {
		long startTimer = ConnectionManager.getConnection().readLong();
		long endTimer = ConnectionManager.getConnection().readLong();
		Mideas.joueur1().setGCDStartTimer(startTimer);
		Mideas.joueur1().setGCDEndTimer(endTimer);
	}
}
