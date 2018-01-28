package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.social.Ignore;

public class CommandIgnore extends Command {
	
	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.IGNORE_ADD) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String name = ConnectionManager.getWorldServerConnection().readString();
			Mideas.joueur1().addIgnore(new Ignore(id, name));
		}
		else if(packetId == PacketID.IGNORE_REMOVE) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			Mideas.joueur1().removeIgnore(id);
		}
		else if(packetId == PacketID.IGNORE_INIT) {
			int length = ConnectionManager.getWorldServerConnection().readInt();
			int i = 0;
			while(i < length) {
				int id = ConnectionManager.getWorldServerConnection().readInt();
				String name = ConnectionManager.getWorldServerConnection().readString();
				Mideas.joueur1().addIgnoreNoSort(new Ignore(id, name));
				i++;
			}
		}
	}
	
	public static void addIgnore(String name) {
		if (name.equalsIgnoreCase(Mideas.joueur1().getName()))
		{
			ChatFrame.addMessage(new Message("You can't ignore yourself.", false, MessageType.SELF, true));
			return;
		}
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.IGNORE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.IGNORE_ADD);
		ConnectionManager.getWorldServerConnection().writeString(name);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void removeIgnore(int id) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.IGNORE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.IGNORE_REMOVE);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
