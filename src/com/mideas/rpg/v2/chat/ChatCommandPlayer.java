package com.mideas.rpg.v2.chat;

import com.mideas.rpg.v2.game.Joueur;

public class ChatCommandPlayer {

	public static void chatCommandPlayer(Joueur joueur, String[] datas) {
		if(datas.length >= 2) {
			if(datas[1].equals("set")) {
				ChatCommandPlayerSet.chatCommandPlayerSet(joueur, datas);
			}
			else if(datas[1].equals("get")) {
				ChatCommandPlayerGet.chatCommandPlayerGet(joueur, datas);
			}
			else if(datas[1].equals("kill")) {
				joueur.setStamina(0);
			}
			else {
				ChatCommandManager.UnknwownCommand();
			}
		}
	}
}
