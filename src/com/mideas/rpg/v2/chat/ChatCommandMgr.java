package com.mideas.rpg.v2.chat;

import java.util.HashMap;

import com.mideas.rpg.v2.chat.channel.ChannelMgr;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.chat.CommandChannel;
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
	private final static ChatCommand join = new ChatCommand("join", "Type /join [channel_name] [password] to join or create a channel.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] in /join [channel_name] [password]", false, MessageType.SELF));
				return;
			}
			int channelValue = ChannelMgr.generateChannelID();
			if(command.length == 2) {
				CommandChannel.joinChannel(command[1], channelValue);
			}
			else {
				CommandChannel.joinChannel(command[1], channelValue, command[2]);
			}
		}
	};
	
	public static void initCommandMap() {
		commandMap.put(invite.getName(), invite);
		commandMap.put(invite_alias_i.getName(), invite_alias_i);
		commandMap.put(invite_alias_inv.getName(), invite_alias_inv);
		commandMap.put(join.getName(), join);
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
