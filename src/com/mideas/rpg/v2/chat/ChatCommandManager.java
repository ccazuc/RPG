package com.mideas.rpg.v2.chat;

import java.sql.SQLException;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;

public class ChatCommandManager {

	public static boolean chatCommandManager(String message) throws SQLException, NumberFormatException {
		message = message.trim().toLowerCase();
		if(message.length() > 1 && message.substring(0, 1).equals(".") && !message.substring(1, 2).equals(".") && !message.equals(".")) {
			String[] datas = message.split(" ");
			if(datas.length >= 1) {
				if(datas[0].equals(".joueur1")) {
					ChatCommandPlayer.chatCommandPlayer(Mideas.joueur1(), datas);
				}
				else if(datas[0].equals(".joueur2")) {
					ChatCommandPlayer.chatCommandPlayer(Mideas.joueur2(), datas);
				}
				else {
					ChatCommandOther.chatCommandOther(datas);
				}
			}
			return true;
		}
		return false;
	}
	
	public static void UnknwownCommand() {
		ChatFrame.addMessage(new Message("Unknown command. Type .help for help.", false, Color.yellow));
	}
}
