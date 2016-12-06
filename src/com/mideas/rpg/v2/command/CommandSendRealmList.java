package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.WorldServer;
import com.mideas.rpg.v2.hud.RealmListFrame;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandSendRealmList extends Command {

	@Override
	public void read() {
		int i = 0;
		RealmListFrame.clearRealmList();
		int size = ConnectionManager.getAuthConnection().readInt();
		while(i < size) {
			RealmListFrame.addRealm(new WorldServer(ConnectionManager.getAuthConnection().readInt(), ConnectionManager.getAuthConnection().readString()));
			i++;
		}
		RealmListFrame.sortRealmList();
		SelectScreen.getAlert().setInactive();
		SelectScreen.setRealmScreenActive(true);
		System.out.println("SENDREALMLIST:REALMLISTRECEIVED");
	}
	
	public static void requestRealm() {
		ConnectionManager.getAuthConnection().writeShort(PacketID.SEND_REALM_LIST);
		ConnectionManager.getAuthConnection().send();
	}
}
