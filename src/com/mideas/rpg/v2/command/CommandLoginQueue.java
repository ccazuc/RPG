package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.hud.RealmListFrame;
import com.mideas.rpg.v2.hud.SelectScreen;

public class CommandLoginQueue extends Command {

	@Override
	public void read()
	{
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if (packetId == PacketID.LOGIN_QUEUE_JOINED)
		{
			int size =  ConnectionManager.getWorldServerConnection().readInt();
			SelectScreen.joinedLoginQueue(size);
		}
		else if (packetId == PacketID.LOGIN_QUEUE_UPDATE_POSITION)
		{
			int position = ConnectionManager.getWorldServerConnection().readInt();
			int size = ConnectionManager.getWorldServerConnection().readInt();
			SelectScreen.updateLoginQueuePosition(position, size);
		}
		else if (packetId == PacketID.LOGIN_QUEUE_ACCEPTED)
		{
			System.out.println("LOGIN:LOGIN_QUEUE_SUCCESS");
			ConnectionManager.setIsLoggedOnWorldServer(true);
			ConnectionManager.setWorldServer(RealmListFrame.getSelectedRealm());
			SelectScreen.setAlertLoadingCharacter();
			CommandSelectScreenLoadCharacters.write();
		}
	}
}
