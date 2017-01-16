package com.mideas.rpg.v2.chat;

import java.util.HashMap;

import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatCommandMgr {

	private final static HashMap<String, ChatCommand> commandMap = new HashMap<String, ChatCommand>();
	
	private final static ChatCommand invite = new ChatCommand("invite", "Invite a player in your party.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [name] in /invite [name].", false, MessageType.SELF));
				return;
			}
			CommandParty.invitePlayer(command[1]);
		}
	};
	private final static ChatCommand invite_alias_inv = new ChatCommand("inv", "Invite a player in your party.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [name] in /inv [name].", false, MessageType.SELF));
				return;
			}
			CommandParty.invitePlayer(command[1]);
		}
	};
	private final static ChatCommand invite_alias_i = new ChatCommand("i", "Invite a player in your party.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [name] in /i [name].", false, MessageType.SELF));
				return;
			}
			CommandParty.invitePlayer(command[1]);
		}
	};
	private final static ChatCommand join = new ChatCommand("join", "Invite a player in your party.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Type /join [channel_name] [password] to join or create a channel.", false, MessageType.SELF));
				return;
			}
			
		}
	};
	
	public static void initCommandMap() {
		commandMap.put(invite.getName(), invite);
		commandMap.put(invite_alias_i.getName(), invite_alias_i);
		commandMap.put(invite_alias_inv.getName(), invite_alias_inv);
	}
	
	public static void handleChatCommand(String str) {
		str = StringUtils.removeSpaces(str);
		String[] value = str.split(" ");
		String command = value[0].substring(1);
		if(commandMap.containsKey(command)) {
			commandMap.get(command).handle(value);
		}
		else {
			ChatFrame.addMessage(new Message("Unknown command, type /help for help.", false, MessageType.SELF));
		}
	}
}
