package com.mideas.rpg.v2.command;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
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
			Mideas.joueur1().addFriend(new Friend(ConnectionManager.getConnection().readInt(), ConnectionManager.getConnection().readString(), ConnectionManager.getConnection().readInt(), Race.values()[ConnectionManager.getConnection().readChar()], ClassType.values()[ConnectionManager.getConnection().readChar()], ConnectionManager.getConnection().readBoolean()));
		}
		else if(packetId == PacketID.FRIEND_OFFLINE) {
			String name = ConnectionManager.getConnection().readString();
			int i = 0;
			while(i < Mideas.joueur1().getFriendList().size()) {
				if(Mideas.joueur1().getFriendList().get(i).equals(name)) {
					Mideas.joueur1().getFriendList().get(i).setOnlineStatus(false);
					ChatFrame.addMessage(new Message(name+" vient de se déconnecter.", false, Color.yellow));
				}
				i++;
			}
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
				//can't add yourself as friend
			}
		}
		else {
			//friendlist full
		}
	}
	
	public static void removeFriend(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.FRIEND);
		ConnectionManager.getConnection().writeByte(PacketID.FRIEND_REMOVE);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
		
	}
}
