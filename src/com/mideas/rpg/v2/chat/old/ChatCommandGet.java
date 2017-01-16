package com.mideas.rpg.v2.chat.old;

import com.mideas.rpg.v2.command.chat.CommandGet;
import com.mideas.rpg.v2.connection.PacketID;

public class ChatCommandGet {

	public static void chatCommandGet(String[] datas) {
		if(datas.length >= 2) {
			if(datas[1].equals("id")) {
				if(datas.length >= 3) {
					CommandGet.write(PacketID.CHAT_GET_ID, datas[2]);
				}
			}
			if(datas[1].equals("ip")) {
				if(datas.length >= 3) {
					CommandGet.write(PacketID.CHAT_GET_IP, Integer.parseInt(datas[2]));
				}
			}
		}
	}
}
