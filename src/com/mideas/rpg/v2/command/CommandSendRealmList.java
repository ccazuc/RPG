package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.WorldServer;
import com.mideas.rpg.v2.hud.RealmListFrame;

public class CommandSendRealmList extends Command {

	@Override
	public void read() {
		int i = 0;
		int size = ConnectionManager.getAuthConnection().readInt();
		while(i < size) {
			RealmListFrame.addRealm(new WorldServer(ConnectionManager.getAuthConnection().readInt(), ConnectionManager.getAuthConnection().readString()));
			i++;
		}
		RealmListFrame.sortRealmList();
		System.out.println("SENDREALMLIST:REALMLISTRECEIVED");
	}
}
