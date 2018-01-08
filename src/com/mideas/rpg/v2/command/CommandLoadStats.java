package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandLoadStats extends Command {

	@Override
	public void read() {
		Mideas.joueur1().setId(ConnectionManager.getWorldServerConnection().readInt());
		Mideas.joueur1().setExp(ConnectionManager.getWorldServerConnection().readLong());
		Mideas.joueur1().setGold(ConnectionManager.getWorldServerConnection().readLong());
		Mideas.setRank(ConnectionManager.getWorldServerConnection().readInt());
	}
}
