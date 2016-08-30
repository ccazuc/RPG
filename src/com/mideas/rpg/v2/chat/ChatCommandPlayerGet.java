package com.mideas.rpg.v2.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.game.Joueur;

public class ChatCommandPlayerGet {

	public static void chatCommandPlayerGet(Joueur joueur, String[] datas) {
		if(datas.length >= 3) {
			if(datas[2].equals("stamina")) {
				ChatFrame.addMessage(new Message(Integer.toString(joueur.getStamina()), false, Color.yellow));
			}
			else if(datas[2].equals("mana")) {
				ChatFrame.addMessage(new Message(Integer.toString(joueur.getMana()), false, Color.yellow));
			}
			else if(datas[2].equals("gold")) {
				ChatFrame.addMessage(new Message(Integer.toString(joueur.getGold()), false, Color.yellow));
			}
			else if(datas[2].equals("experience")) {
				ChatFrame.addMessage(new Message(Integer.toString(joueur.getExp()), false, Color.yellow));
			}
			else {
				ChatCommandManager.UnknwownCommand();
			}
		}
	}
}
