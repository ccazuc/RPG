package com.mideas.rpg.v2.stresstest;

import com.mideas.rpg.v2.connection.PacketID;

public class CommandLoadCharacter implements Command {

	@Override
	public void read(Stresstest client) {
		short packetId = client.getWorldServerConnection().readShort();
		if (packetId == PacketID.CHARACTER_LOGIN_BANNED)
		{
			boolean permanentBan = client.getWorldServerConnection().readBoolean();
		}
		else if (packetId == PacketID.CHARACTER_LOGIN_SUCCESS)
		{
			
		}
		else if (packetId == PacketID.CHARACTER_LOAD_FINISHED)
		{
			
		}
	}
	
	public static void write(Stresstest client) {
		client.getWorldServerConnection().startPacket();
		client.getWorldServerConnection().writeShort(PacketID.CHARACTER_LOGIN);
		client.getWorldServerConnection().writeInt(client.getAccountId() + 16);
		client.getWorldServerConnection().endPacket();
		client.getWorldServerConnection().send();
	}
}
