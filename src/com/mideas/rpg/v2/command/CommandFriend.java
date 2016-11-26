package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.race.Race;

public class CommandFriend extends Command {

	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.FRIEND_SEND_INFO) {
			
		}
		else if(packetId == PacketID.FRIEND_ADD) {
			boolean online = ConnectionManager.getConnection().readBoolean();
			if(online) {
				Mideas.joueur1().addFriend(new Friend(ConnectionManager.getConnection().readInt(), ConnectionManager.getConnection().readString(), ConnectionManager.getConnection().readInt(), Race.values()[ConnectionManager.getConnection().readChar()], ClassType.values()[ConnectionManager.getConnection().readChar()]));
			}
			else {
				Mideas.joueur1().addFriend(new Friend(ConnectionManager.getConnection().readInt(), ConnectionManager.getConnection().readString()));
			}
		}
		else if(packetId == PacketID.FRIEND_OFFLINE) {
			int id = ConnectionManager.getConnection().readInt();
			int i = 0;
			while(i < Mideas.joueur1().getFriendList().size()) {
				if(Mideas.joueur1().getFriendList().get(i).getCharacterId() == id) {
					ChatFrame.addMessage(new Message(Mideas.joueur1().getFriendList().get(i).getName()+" is now offline.", false, MessageType.SELF));
					Mideas.joueur1().getFriendList().get(i).setOnlineStatus(false);
					return;
				}
				i++;
			}
		}
		else if(packetId == PacketID.FRIEND_ONLINE) {
			int id = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			int level = ConnectionManager.getConnection().readInt();
			Race race = Race.values()[ConnectionManager.getConnection().readChar()];
			ClassType classe = ClassType.values()[ConnectionManager.getConnection().readChar()];
			int i = 0;
			while(i < Mideas.joueur1().getFriendList().size()) {
				if(Mideas.joueur1().getFriendList().get(i).getCharacterId() == id) {
					ChatFrame.addMessage(new Message(" is now online.", name, false, MessageType.SELF));
					Mideas.joueur1().getFriendList().get(i).updateInformations(name, level, race, classe);
					return;
				}
				i++;
			}
		}
		else if(packetId == PacketID.FRIEND_LOAD_ALL) {
			int i = 0;
			int length = ConnectionManager.getConnection().readInt();
			while(i < length) {
				int id = ConnectionManager.getConnection().readInt();
				boolean isOnline = ConnectionManager.getConnection().readBoolean();
				String name = ConnectionManager.getConnection().readString();
				if(isOnline) {
					int level = ConnectionManager.getConnection().readInt();
					Race race = Race.values()[ConnectionManager.getConnection().readChar()];
					ClassType classe = ClassType.values()[ConnectionManager.getConnection().readChar()];
					Mideas.joueur1().addFriend(new Friend(id, name, level, race, classe));
				}
				else {
					Mideas.joueur1().addFriend(new Friend(id, name));
				}
				i++;
			}
			Mideas.joueur1().sortFriendList();
		}
		else if(packetId == PacketID.FRIEND_REMOVE) {
			int id = ConnectionManager.getConnection().readInt();
			Mideas.joueur1().removeFriend(id);
		}
	}
	
	public static void addFriend(String name) {
		if(Mideas.joueur1().getFriendList().size() < Joueur.MAXIMUM_AMOUNT_FRIENDS) {
			if(!name.equals(Mideas.joueur1().getName())) {
				ConnectionManager.getConnection().writeByte(PacketID.FRIEND);
				ConnectionManager.getConnection().writeByte(PacketID.FRIEND_ADD);
				ConnectionManager.getConnection().writeString(name);
				ConnectionManager.getConnection().send();
			}
			else {
				ChatFrame.addMessage(new Message("Can't add yourself as friend.", false, MessageType.SELF));
			}
		}
		else {
			ChatFrame.addMessage(new Message("Your friendlist is full.", false, MessageType.SELF));
		}
	}
	
	public static void removeFriend(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.FRIEND);
		ConnectionManager.getConnection().writeByte(PacketID.FRIEND_REMOVE);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
