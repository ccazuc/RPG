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
	private final static ChatCommand join = new ChatCommand("join", "Type /join [channel_name] [password(optionnal)] to join or create a channel.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] in /join [channel_name] [password(optionnal)]", false, MessageType.SELF));
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
	private final static ChatCommand leave = new ChatCommand("leave", "Type /leave [channel_name || channel_index] to leave the channel.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name || channel_index] in /leave [channel_name || channel_index]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				CommandChannel.leaveChannel(channelName);
			}
		}
	};
	private final static ChatCommand ban = new ChatCommand("ban", "Type /ban [channel_name] [player_name] to ban the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 2) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] in /ban [channel_name] [player_name]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				CommandChannel.banPlayer(channelName, playerID);
			}
		}
	};
	
	public static void initCommandMap() {
		addCommand(invite);
		addCommand(invite_alias_i);
		addCommand(invite_alias_inv);
		addCommand(join);
		addCommand(leave);
		addCommand(ban);
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
	
	private static void addCommand(ChatCommand command) {
		commandMap.put(command.getName(), command);
	}
}
