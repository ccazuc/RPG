package com.mideas.rpg.v2.chat;

import com.mideas.rpg.v2.command.chat.CommandGet;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.Unit;

public class ChatCommandPlayerGet {

	public static void chatCommandPlayerGet(Unit joueur, String[] datas) {
		if(datas.length >= 3) {
			if(datas[2].equals("stamina")) {
				CommandGet.write(PacketID.CHAT_GET_STAMINA, joueur.getId());
			}
			else if(datas[2].equals("mana")) {
				CommandGet.write(PacketID.CHAT_GET_MANA, joueur.getId());
			}
			else if(datas[2].equals("gold")) {
				CommandGet.write(PacketID.CHAT_GET_GOLD, joueur.getId());
			}
			else if(datas[2].equals("experience")) {
				CommandGet.write(PacketID.CHAT_GET_EXPERIENCE, joueur.getId());
			}
			else {
				ChatCommandManager.UnknwownCommand();
			}
		}
	}
}
