package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.SocialFrameMenu;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.social.WhoUnit;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.hud.social.SocialFrame;
import com.mideas.rpg.v2.hud.social.who.WhoFrame;

public class CommandWho extends Command {
	
	@Override
	public void read() {
		WhoFrame.clearList();
		while(ConnectionManager.getWorldServerConnection().hasRemaining())
		{
			int id = ConnectionManager.getWorldServerConnection().readInt();
			if(id == -1)
				break;
			String name = ConnectionManager.getWorldServerConnection().readString();
			String guildName = ConnectionManager.getWorldServerConnection().readString();
			Race race = Race.values()[ConnectionManager.getWorldServerConnection().readByte()];
			int level = ConnectionManager.getWorldServerConnection().readInt();
			ClassType classe = ClassType.values()[ConnectionManager.getWorldServerConnection().readByte()];
			WhoFrame.addToList(new WhoUnit(id, name, guildName, level, classe, race));
		}
		if (WhoFrame.getList().size() <= 4 && WhoFrame.getList().size() > 0 && !Interface.isSocialFrameActive())
		{
			int i = -1;
			WhoUnit unit;
			while (++i < WhoFrame.getList().size())
			{
				unit = WhoFrame.getList().get(i);
				if (unit.getGuildName() != null && unit.getGuildName().length() != 0)
					ChatFrame.addMessage(new Message(": Level " + unit.getLevelString() + ' ' + unit.getRace() + ' ' + unit.getClasse() + " <" + unit.getGuildName() + "> - Area", unit.getName(), false, MessageType.SELF, false, true));
				else
					ChatFrame.addMessage(new Message(": Level " + unit.getLevelString() + ' ' + unit.getRace() + ' ' + unit.getClasse() + " - Area", unit.getName(), false, MessageType.SELF, false, true));
			}
			if (WhoFrame.getList().size() == 1)
				ChatFrame.addMessage(new Message("1 player total", false, MessageType.SELF, true));
			else
				ChatFrame.addMessage(new Message(WhoFrame.getList().size() + " players total", false, MessageType.SELF, true));
		}
		else if (WhoFrame.getList().size() > 0) 
		{
			SocialFrame.setSelectedMenu(SocialFrameMenu.WHO_FRAME);
			Interface.setSocialFrameStatus(true);
		}
	}
	
	public static void write(String word) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.WHO);
		ConnectionManager.getWorldServerConnection().writeString(word);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
