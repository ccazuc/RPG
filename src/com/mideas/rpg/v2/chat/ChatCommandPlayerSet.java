package com.mideas.rpg.v2.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.chat.CommandSet;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.Unit;

public class ChatCommandPlayerSet {

	public static void chatCommandPlayerSet(Unit joueur, String[] datas) {
		if(datas.length >= 3) {
			if(datas[2].equals("stamina")) {
				if(datas.length >= 4) {
					if(Mideas.isInteger(datas[3])) {
						int number = Integer.valueOf(datas[3]);
						if(number >= 0 && number <= joueur.getMaxStamina()) {
							CommandSet.write(PacketID.CHAT_SET_STAMINA, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [stamina]", false, Color.yellow));
				}
			}
			else if(datas[2].equals("mana")) {
				if(datas.length >= 4) {
					if(Mideas.isInteger(datas[3])) {
						int number = Integer.valueOf(datas[3]);
						if(number >= 0 && number <= joueur.getMaxMana()) {
							CommandSet.write(PacketID.CHAT_SET_MANA, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [mana]", false, Color.yellow));
				}
			}
			else if(datas[2].equals("gold")) {
				if(datas.length >= 4) {
					if(Mideas.isInteger(datas[3])) {
						int number = Integer.valueOf(datas[3]);
						if(number >= 0) {
							CommandSet.write(PacketID.CHAT_SET_GOLD, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [gold]", false, Color.yellow));
				}
			}
			else if(datas[2].equals("experience")) {
				if(datas.length >= 4) {
					if(Mideas.isInteger(datas[3])) {
						int number = Integer.valueOf(datas[3]);
						if(number >= 0) {
							CommandSet.write(PacketID.CHAT_SET_EXPERIENCE, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [experience]", false, Color.yellow));
				}
			}
		}
		else {
			ChatCommandManager.UnknwownCommand();
		}
	}
}
