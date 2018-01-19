package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandChangeGold extends Command {

	@Override
	public void read()
	{
		Mideas.joueur1().setGold(ConnectionManager.getWorldServerConnection().readLong());
	}
}
