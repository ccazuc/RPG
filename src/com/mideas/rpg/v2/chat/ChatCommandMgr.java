package com.mideas.rpg.v2.chat;

import java.util.HashMap;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.channel.ChannelMgr;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.chat.CommandChannel;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatCommandMgr {

	private final static HashMap<String, ChatCommand> commandMap = new HashMap<String, ChatCommand>();
	
	final static ChatCommand invite = new ChatCommand("invite", "Invite a player in your party.") {
		
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
			invite.handle(command);
		}
	};
	private final static ChatCommand invite_alias_i = new ChatCommand("i", "Invite a player in your party.") {
		
		@Override
		public void handle(String[] command) {
			invite.handle(command);
		}
	};
	private final static ChatCommand cjoin = new ChatCommand("join", "Type /join [channel_name] [password(optionnal)] to join or create a channel.") {
		
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
	private final static ChatCommand chan = new ChatCommand("chan", "Type /chan [channel_name] [password(optionnal)] to join or create a channel.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length == 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] in /chan [channel_name] [password(optionnal)]", false, MessageType.SELF));
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
	private final static ChatCommand cleave = new ChatCommand("leave", "Type /leave [channel_name || channel_index] to leave the channel.") {
	
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
	private final static ChatCommand cban = new ChatCommand("ban", "Type /ban [channel_name] [player_name] to ban the player from the channel.") {
	
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
	private final static ChatCommand cunban = new ChatCommand("unban", "Type /unban [channel_name] [player_name] to unban the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 2) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] in /unban [channel_name] [player_name]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				CommandChannel.unbanPlayer(channelName, playerID);
			}
		}
	};
	final static ChatCommand cmute = new ChatCommand("mute", "Type /mute [channel_name] [player_name] to mute the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 2) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] in /mute [channel_name] [player_name]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				CommandChannel.mutePlayer(channelName, playerID);
			}
		}
	};
	private final static ChatCommand csilence = new ChatCommand("silence", "Type /silence [channel_name] [player_name] to mute the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			cmute.handle(command);
		}
	};
	final static ChatCommand cunmute = new ChatCommand("unmute", "Type /unmute [channel_name] [player_name] to mute the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 2) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] in /unmute [channel_name] [player_name]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				CommandChannel.unmutePlayer(channelName, playerID);
			}
		}
	};
	private final static ChatCommand cunsilence = new ChatCommand("unsilence", "Type /unsilence [channel_name] [player_name] to mute the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			cunmute.handle(command);
		}
	};
	final static ChatCommand cmoderator = new ChatCommand("moderator", "Type /moderator [channel_name] [player_name] [true || false] to set wether the player should be moderator.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 3) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] [true || false] in /moderator [channel_name] [player_name] [true || false]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				boolean b = command[3].equals("true") ? true : false;
				CommandChannel.setModerator(channelName, playerID, b);
			}
		}
	};
	private final static ChatCommand cmoderator_alias_mod = new ChatCommand("mod", "Type /mod [channel_name] [player_name] [true || false] to set wether the player should be moderator.") {
	
		@Override
		public void handle(String[] command) {
			cmoderator.handle(command);
		}
	};
	private final static ChatCommand ckick = new ChatCommand("ckick", "Type /ckick [channel_name] [player_name] kick the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 2) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] in /ckick [channel_name] [player_name]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				CommandChannel.kickPlayer(channelName, playerID);
			}
		}
	};
	private final static ChatCommand clead = new ChatCommand("lead", "Type /lead [channel_name] [player_name] give the leadership.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 2) {
				ChatFrame.addMessage(new Message("Invalid parameter for [channel_name] [player_name] in /lead [channel_name] [player_name]", false, MessageType.SELF));
				return;
			}
			String channelName = ChannelMgr.findChannelName(command[1]);
			if(channelName != null) {
				int playerID = ChannelMgr.getMemberID(channelName, command[2]);
				if(playerID == 0) {
					return;
				}
				CommandChannel.setLeader(channelName, playerID);
			}
		}
	};
	private final static ChatCommand gquit = new ChatCommand("gquit", "Leave your guild.") {
	
		@Override
		public void handle(String[] command) {
			CommandGuild.leaveGuild();
		}
	};
	private final static ChatCommand guildquit = new ChatCommand("guildquit", "Leave your guild.") {
	
		@Override
		public void handle(String[] command) {
			CommandGuild.leaveGuild();
		}
	};
	final static ChatCommand gmotd = new ChatCommand("gmotd", "/gmotd [motd] set the motd of your guild.") {
	
		@Override
		public void handle(String[] command) {
			if(command.length <= 1) {
				ChatFrame.addMessage(new Message("Invalid parameter for [motd] in /gmotd [motd]", false, MessageType.SELF));
				return;
			}
			StringBuilder builder = new StringBuilder();
			int i = 1;
			while(i < command.length) {
				builder.append(command[i]);
				i++;
			}
			CommandGuild.setMotd(builder.toString());
		}
	};
	private final static ChatCommand guildmotd = new ChatCommand("guildmotd", "/guildmotd [motd] set the motd of your guild.") {
	
		@Override
		public void handle(String[] command) {
			gmotd.handle(command);
		}
	};
	private final static ChatCommand ram = new ChatCommand("ram", "/ram display the ram used by the game.") {
	
		@Override
		public void handle(String[] command) {
			ChatFrame.addMessage(new Message("The game is using "+((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024f*1024f))+" Mb of ram.", false, MessageType.SELF));
		}
	};
	private final static ChatCommand gc = new ChatCommand("gc", "/gc Run a gc.") {
	
		@Override
		public void handle(String[] command) {
			ChatFrame.addMessage(new Message("Ram used before gc : "+((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024f*1024f))+" Mb of ram.", false, MessageType.SELF));
			System.gc();
			ChatFrame.addMessage(new Message("Ram used after gc : "+((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024f*1024f))+" Mb of ram.", false, MessageType.SELF));
		}
	};
	private final static ChatCommand ah = new ChatCommand("ah", "/ah Open or close the AuctionHouse.") {
	
		@Override
		public void handle(String[] command) {
			if(Interface.isAuctionFrameOpen()) {
				Interface.openAuctionFrame();
			}
			else {
				Interface.closeAuctionFrame();
			}
		}
	};
	private final static ChatCommand reload = new ChatCommand("reload", "/reload to reload the UI.") {
		
		@Override
		public void handle(String[] command) {
			Mideas.loadingScreen();
		}
	};
	
	public static void initCommandMap() {
		addCommand(invite);
		addCommand(invite_alias_i);
		addCommand(invite_alias_inv);
		addCommand(cjoin);
		addCommand(chan);
		addCommand(cleave);
		addCommand(cban);
		addCommand(cunban);
		addCommand(cmoderator);
		addCommand(cmoderator_alias_mod);
		addCommand(ckick);
		addCommand(clead);
		addCommand(gquit);
		addCommand(guildquit);
		addCommand(gmotd);
		addCommand(guildmotd);
		addCommand(ram);
		addCommand(gc);
		addCommand(ah);
		addCommand(cmute);
		addCommand(csilence);
		addCommand(cunmute);
		addCommand(cunsilence);
		addCommand(reload);
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
		if(commandMap.containsKey(command.getName())) {
			System.out.println("**ERROR** in ChatCommandMgr.addCommand, map already contains key : "+command.getName());
		}
		commandMap.put(command.getName(), command);
	}
	
	static ChatCommand getCommand(String name) {
		return commandMap.get(name);
	}
}
