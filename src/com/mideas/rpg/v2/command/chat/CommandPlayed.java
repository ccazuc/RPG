package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.utils.StringUtils;

public class CommandPlayed extends Command {

	@Override
	public void read()
	{
		long playedTimer = ConnectionManager.getWorldServerConnection().readLong();
		long playedLevelTimer = ConnectionManager.getWorldServerConnection().readLong();
		ChatFrame.addMessage(new Message("Total time played: " + StringUtils.convertTimeToString(playedTimer), false, MessageType.SELF, true));
		ChatFrame.addMessage(new Message("Time played this level: " + StringUtils.convertTimeToString(playedLevelTimer), false, MessageType.SELF, true));
	}
	
	public static void requestPlayed()
	{
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.PLAYED);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
