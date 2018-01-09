package com.mideas.rpg.v2.stresstest;

import com.mideas.rpg.v2.connection.PacketID;

public class CommandLoginRealm implements Command {

	@Override
	public void read(Stresstest client) {
		short packetId = client.getWorldServerConnection().readShort();
		System.out.println("Client " + client.getId() + " login realm");
		if(packetId == PacketID.LOGIN_REALM_SUCCESS) {
			System.out.println("Client " + client.getId() + " LOGIN:LOGIN_REALM_SUCCESS");
			CommandLoadCharacter.write(client);
		}
	}
}
