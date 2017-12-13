package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.hud.SelectScreen;
import com.mideas.rpg.v2.utils.StringUtils;

public class CommandLoadCharacter extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if (packetId == PacketID.CHARACTER_LOGIN_BANNED)
		{
			boolean permanentBan = ConnectionManager.getConnection().readBoolean();
			if (permanentBan)
				SelectScreen.setAlert("This character is permanently banned.");
			else
			{
				long duration = Mideas.getLoopTickTimer() - ConnectionManager.getConnection().readLong();
				SelectScreen.setAlert("This character is banned for "+StringUtils.convertTimeToStringimple(duration));
			}
		}
		else if (packetId == PacketID.CHARACTER_LOGIN_SUCCESS)
		{
			System.out.println("Character login success");
			//SelectScreen.setLoadingScreen();
		}
		else if (packetId == PacketID.CHARACTER_LOAD_FINISHED)
		{
			//SelectScreen.unsetLoadingScreen();
		}
	}
	
	public static void write(int id) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.CHARACTER_LOGIN);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
