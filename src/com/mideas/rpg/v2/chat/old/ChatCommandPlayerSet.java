package com.mideas.rpg.v2.chat.old;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.chat.CommandSet;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.unit.Unit;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatCommandPlayerSet {

	public static void chatCommandPlayerSet(Unit joueur, String[] datas) {
		if(datas.length >= 3) {
			if(datas[2].equals("stamina")) {
				if(datas.length >= 4) {
					if(StringUtils.isInteger(datas[3])) {
						int number = Integer.parseInt(datas[3]);
						if(number >= 0 && number <= joueur.getMaxStamina()) {
							CommandSet.write(PacketID.CHAT_SET_STAMINA, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [stamina]", false, MessageType.SELF));
				}
			}
			else if(datas[2].equals("mana")) {
				if(datas.length >= 4) {
					if(StringUtils.isInteger(datas[3])) {
						int number = Integer.parseInt(datas[3]);
						if(number >= 0 && number <= joueur.getMaxMana()) {
							CommandSet.write(PacketID.CHAT_SET_MANA, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [mana]", false, MessageType.SELF));
				}
			}
			else if(datas[2].equals("gold")) {
				if(datas.length >= 4) {
					if(StringUtils.isInteger(datas[3])) {
						int number = Integer.parseInt(datas[3]);
						if(number >= 0) {
							CommandSet.write(PacketID.CHAT_SET_GOLD, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [gold]", false, MessageType.SELF));
				}
			}
			else if(datas[2].equals("experience")) {
				if(datas.length >= 4) {
					if(StringUtils.isInteger(datas[3])) {
						int number = Integer.parseInt(datas[3]);
						if(number >= 0) {
							CommandSet.write(PacketID.CHAT_SET_EXPERIENCE, joueur.getId(), number, 1);
							return;
						}
					}
					ChatFrame.addMessage(new Message("Incorrect value for [experience]", false, MessageType.SELF));
				}
			}
		}
		else {
			ChatCommandManager.UnknwownCommand();
		}
	}
}
