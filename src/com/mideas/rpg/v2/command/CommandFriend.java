package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.Friend;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.race.Classe;
import com.mideas.rpg.v2.game.race.Race;

public class CommandFriend extends Command {

	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.FRIEND_SEND_INFO) {
			
		}
		else if(packetId == PacketID.FRIEND_ADD) {
			Mideas.joueur1().addFriend(new Friend(ConnectionManager.getConnection().readString(), ConnectionManager.getConnection().readInt(), Race.values()[ConnectionManager.getConnection().readChar()], Classe.values()[ConnectionManager.getConnection().readChar()]));
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
}
