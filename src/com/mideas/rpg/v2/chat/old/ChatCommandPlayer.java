package com.mideas.rpg.v2.chat.old;

import com.mideas.rpg.v2.command.chat.CommandSet;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.unit.Unit;

public class ChatCommandPlayer {

	public static void chatCommandPlayer(Unit joueur, String[] datas) {
		if(datas.length >= 2) {
			if(datas[1].equals("set")) {
				ChatCommandPlayerSet.chatCommandPlayerSet(joueur, datas);
			}
			else if(datas[1].equals("get")) {
				ChatCommandPlayerGet.chatCommandPlayerGet(joueur, datas);
			}
			else if(datas[1].equals("kill")) {
				CommandSet.write(PacketID.CHAT_SET_STAMINA, joueur.getId(), 0, 1);
			}
			else {
				ChatCommandManager.UnknwownCommand();
			}
		}
	}
}
