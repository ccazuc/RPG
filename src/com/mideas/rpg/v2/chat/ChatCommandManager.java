package com.mideas.rpg.v2.chat;

import com.mideas.rpg.v2.Mideas;

public class ChatCommandManager {

	public static boolean chatCommandManager(String message) {
		message = message.trim().toLowerCase();
		if(message.length() > 2) {
			if(message.startsWith("/")) {
				String[] datas = message.split(" ");
				if(datas.length >= 1) {
					if(datas[0].equals("/joueur1")) {
						ChatCommandPlayer.chatCommandPlayer(Mideas.joueur1(), datas);
					}
					else if(datas[0].equals("/target")) {
						ChatCommandPlayer.chatCommandPlayer(Mideas.joueur1().getTarget(), datas);
					}
					else if(datas[0].equals("/get")) {
						ChatCommandGet.chatCommandGet(datas);
					}
					else {
						ChatCommandOther.chatCommandOther(datas);
					}
				}
			}
		}
		return false;
	}
	
	public static void UnknwownCommand() {
		ChatFrame.addMessage(new Message("Unknown command. Type .help for help.", false, MessageType.SELF));
	}
}
