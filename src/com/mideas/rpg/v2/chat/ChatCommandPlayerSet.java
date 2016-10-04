package com.mideas.rpg.v2.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.Joueur;

public class ChatCommandPlayerSet {

	public static void chatCommandPlayerSet(Joueur joueur, String[] datas) {
		if(datas.length >= 3) {
			if(datas[2].equals("stamina")) {
				if(datas.length >= 4) {
					int number = Integer.valueOf(datas[3]);
					if(number >= 0 && number <= joueur.getMaxStamina()) {
						joueur.setStamina(number);
					}
					else {
						ChatFrame.addMessage(new Message("Incorrect value for set stamina", false, Color.yellow));
					}
				}
			}
			else if(datas[2].equals("mana")) {
				if(datas.length >= 4) {
					int number = Integer.valueOf(datas[3]);
					if(number >= 0 && number <= joueur.getMaxMana()) {
						joueur.setMana(number);
					}
					else {
						ChatFrame.addMessage(new Message("Incorrect value for set mana", false, Color.yellow));
					}
				}
			}
			else if(datas[2].equals("gold")) {
				if(datas.length >= 4) {
					int number = Integer.valueOf(datas[3]);
					if(number >= 0) {
						Mideas.joueur1().setGold(number);
					}
					else {
						ChatFrame.addMessage(new Message("Incorrect value for set gold", false, Color.yellow));
					}
				}
			}
			else if(datas[2].equals("experience")) {
				if(datas.length >= 4) {
					int number = Integer.valueOf(datas[3]);
					if(number >= 0) {
						Mideas.joueur1().setExp(number);
					}
					else {
						ChatFrame.addMessage(new Message("Incorrect value for set experience", false, Color.yellow));
					}
				}
			}
		}
		else {
			ChatCommandManager.UnknwownCommand();
		}
	}
}
