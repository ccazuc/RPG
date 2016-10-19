package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandLoadStats extends Command {

	@Override
	public void read() {
		Mideas.joueur1().setExp(ConnectionManager.getConnection().readInt());
		Mideas.joueur1().setGold(ConnectionManager.getConnection().readInt());
		Mideas.setRank(ConnectionManager.getConnection().readInt());
		System.out.println(Mideas.getRank());
	}
}
