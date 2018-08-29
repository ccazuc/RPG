package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.files.config.ChatConfigManager;
import com.mideas.rpg.v2.files.logs.LogsMgr;

public class CommandLogoutCharacter extends Command {

	@Override
	public void read()
	{
		ChatConfigManager.saveConfig();
		Mideas.setJoueur1Null();
		Interface.closeAllFrame();
		Interface.setCharacterLoaded(false);
		LogsMgr.writeConnectionLog("Character logout accepted.");
	}

	public static void write()
	{
		LogsMgr.writeConnectionLog("Requested character logout.");
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHARACTER_LOGOUT);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
