package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandLoginRealm extends Command {

	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.LOGIN_REALM_SUCCESS) {
			System.out.println("LOGIN:LOGIN_REALM_SUCCESS");
			SelectScreen.setIsConnectedToWorldServer(true);
			SelectScreen.getAlert().setInactive();
			SelectScreen.setRealmScreenActive(false);
		}
	}
}
