package com.mideas.rpg.v2.chat;

import java.util.HashMap;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.channel.ChannelMgr;
import com.mideas.rpg.v2.command.CommandGuild;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandWho;
import com.mideas.rpg.v2.command.chat.CommandChannel;
import com.mideas.rpg.v2.command.chat.CommandPlayed;
import com.mideas.rpg.v2.stresstest.StresstestMgr;
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
	private final static ChatCommand channel_join = new ChatCommand("join", "Type /join [channel_name] [password(optionnal)] to join or create a channel.") {
		
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
	private final static ChatCommand channel_leave = new ChatCommand("leave", "Type /leave [channel_name || channel_index] to leave the channel.") {
	
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
	private final static ChatCommand channel_ban = new ChatCommand("ban", "Type /ban [channel_name] [player_name] to ban the player from the channel.") {
	
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
	private final static ChatCommand channel_unban = new ChatCommand("unban", "Type /unban [channel_name] [player_name] to unban the player from the channel.") { //TODO: send player name to server, you can't unban someone who's not in the channel with player id
	
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
	final static ChatCommand channel_mute = new ChatCommand("mute", "Type /mute [channel_name] [player_name] to mute the player from the channel.") {
	
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
	private final static ChatCommand channel_silence = new ChatCommand("silence", "Type /silence [channel_name] [player_name] to mute the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			channel_mute.handle(command);
		}
	};
	final static ChatCommand channel_unmute = new ChatCommand("unmute", "Type /unmute [channel_name] [player_name] to mute the player from the channel.") {
	
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
	private final static ChatCommand channel_unsilence = new ChatCommand("unsilence", "Type /unsilence [channel_name] [player_name] to mute the player from the channel.") {
	
		@Override
		public void handle(String[] command) {
			channel_unmute.handle(command);
		}
	};
	final static ChatCommand channel_moderator = new ChatCommand("moderator", "Type /moderator [channel_name] [player_name] [true || false] to set wether the player should be moderator.") {
	
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
	private final static ChatCommand channel_moderator_alias_mod = new ChatCommand("mod", "Type /mod [channel_name] [player_name] [true || false] to set wether the player should be moderator.") {
	
		@Override
		public void handle(String[] command) {
			channel_moderator.handle(command);
		}
	};
	private final static ChatCommand channel_kick = new ChatCommand("ckick", "Type /ckick [channel_name] [player_name] kick the player from the channel.") {
	
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
	private final static ChatCommand channel_lead = new ChatCommand("lead", "Type /lead [channel_name] [player_name] give the leadership.") {
	
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
			if(Interface.isAuctionFrameOpen())
				Interface.closeAuctionFrame();
			else
				Interface.openAuctionFrame();
		}
	};
	private final static ChatCommand reload = new ChatCommand("reload", "/reload to reload the UI.") {
		
		@Override
		public void handle(String[] command) {
			long timer = System.currentTimeMillis();
			Mideas.loadingScreen();
			ChatFrame.addMessage(new Message("UI reloaded in "+(System.currentTimeMillis()-timer)/1000f+"s.", false, MessageType.SELF));
		}
	};
	private final static ChatCommand stresstest = new ChatCommand("stresstest", "Stresstest is a command used to create client to test server performance.") {
		
		@Override
		public void handle(String[] command) {
			if(command.length < 2) {
				ChatFrame.addMessage(new Message(this.printHelpMessage(), false, MessageType.SELF));
				return;
			}
			int i = 0;
			while(i < this.subCommandList.size()) {
				if(this.subCommandList.get(i).getName().equalsIgnoreCase(command[1])) {
					this.subCommandList.get(i).handle(command);
					return;
				}
				i++;
			}
			ChatFrame.addMessage(new Message("Stresstest has no such subcommand.", false, MessageType.SELF));
		}
	};
	private final static ChatSubCommand stresstest_add = new ChatSubCommand("add", "stresstest", "/stresstest add [int] to add client.")
	{
	
		@Override
		public void handle(String[] command)
		{
			if (command.length < 3)
			{
				ChatFrame.addMessage(new Message(this.helpMessage, false, MessageType.SELF));
				return;
			}
			if (!StringUtils.isInteger(command[2]))
			{
				ChatFrame.addMessage(new Message("Incorrect value, you must specify an integer.", false, MessageType.SELF));
				return;
			}
			StresstestMgr.addClient(Integer.parseInt(command[2]));
			ChatFrame.addMessage(new Message("Added " + command[2] + " clients.", false, MessageType.SELF));
		}
	};
	private final static ChatSubCommand stresstest_remove = new ChatSubCommand("remove", "stresstest", "/stresstest remove [int] to remove client.")
	{
	
		@Override
		public void handle(String[] command)
		{
			if (command.length < 3)
			{
				ChatFrame.addMessage(new Message(this.helpMessage, false, MessageType.SELF));
				return;
			}
			if (!StringUtils.isInteger(command[2]))
			{
				ChatFrame.addMessage(new Message("Incorrect value, you must specify an integer.", false, MessageType.SELF));
				return;
			}
			StresstestMgr.removeClient(Integer.parseInt(command[2]));
			ChatFrame.addMessage(new Message("Removed " + command[2] + " clients.", false, MessageType.SELF));
		}
	};
	private final static ChatSubCommand stresstest_clear = new ChatSubCommand("clear", "stresstest", "/stresstest clear removes all clients.")
	{
	
		@Override
		public void handle(String[] command)
		{
			StresstestMgr.clearClient();
			ChatFrame.addMessage(new Message("Removed all clients.", false, MessageType.SELF));
		}
	};
	private final static ChatSubCommand stresstest_msg = new ChatSubCommand("msg", "stresstest", "/stresstest msg [msg] to send a message from all clients.")
	{
	
		@Override
		public void handle(String[] command)
		{
			if (command.length < 3)
			{
				this.printHelpMessage();
				return;
			}
			StringBuilder builder = new StringBuilder();
			int i = 1;
			while (++i < command.length)
				builder.append(command[i]);
			StresstestMgr.sendMessage(builder.toString());
		}
	};
	private final static ChatCommand help = new ChatCommand("help", "Syntax: /help [command] \n\nDisplay usage instructions for the given command. If no command provided show list of available commands.") {

		@Override
		public void handle(String[] command) {
			if (command.length == 1) {
				StringBuilder builder = new StringBuilder();
				builder.append("Available commands:");
				for (ChatCommand chatCommand : getCommandMap().values()) {
					builder.append("\n").append(chatCommand.getName());
				}
				ChatFrame.addMessage(new Message(builder.toString(), false, MessageType.SELF));
				return;
			}
			if (getCommand(command[1]) == null)
			{
				ChatFrame.addMessage(new Message("This command doesn't exist", false, MessageType.SELF));
				return;
			}
			if (command.length > 2 && getCommand(command[1]).subCommandList != null) {
				int i = -1;
				while (i < getCommand(command[1]).subCommandList.size())
					if (getCommand(command[1]).subCommandList.get(i).getName().equalsIgnoreCase(command[2])) {
						if(command.length > 3 && getCommand(command[1]).subCommandList.get(i).commandList != null) {
							int j = 0;
							while (j < getCommand(command[1]).subCommandList.get(i).commandList.size()) {
								if (getCommand(command[1]).subCommandList.get(i).commandList.get(j).getName().equalsIgnoreCase(command[3])) {
									ChatFrame.addMessage(new Message(getCommand(command[1]).subCommandList.get(i).commandList.get(j).helpMessage, false, MessageType.SELF));
									return;
								}
								j++;
							}
							ChatFrame.addMessage(new Message("This command doesn't exist.", false, MessageType.SELF));
							return;
						}
						ChatFrame.addMessage(new Message(getCommand(command[1]).subCommandList.get(i).printHelpMessage(), false, MessageType.SELF));
						return;
					}
				ChatFrame.addMessage(new Message("This command doesn't exist.", false, MessageType.SELF));
				return;
			}
			if (getCommand(command[1]).subCommandList != null)
				ChatFrame.addMessage(new Message(getCommand(command[1]).printHelpMessage(), false, MessageType.SELF));
			else
				ChatFrame.addMessage(new Message(getCommand(command[1]).printHelpMessage(), false, MessageType.SELF));
		}
	};
	private final static ChatCommand quit = new ChatCommand("quit", "/quit to quit the game.")
	{
	
		@Override
		public void handle(String[] command)
		{
			Mideas.closeGame();
		}
	};
	private final static ChatCommand played = new ChatCommand("played", "Displays the time played on your character.")
	{
		
		@Override
		public void handle(String[] command)
		{
			CommandPlayed.requestPlayed();
		}
	};
	private final static ChatCommand who = new ChatCommand("who", "/who [args] to display list of players.")
	{
		
		@Override
		public void handle(String[] command)
		{
			StringBuilder builder = new StringBuilder();
			int i = 0;
			while (++i < command.length)
				builder.append(command[i]);
			CommandWho.write(builder.toString());
		}
	};
	private final static ChatCommand mail = new ChatCommand("mail", "/mail to open or close the mailbox.")
	{
	
		@Override
		public void handle(String[] command)
		{
			if (Interface.isMailFrameOpen())
				Interface.closeMailFrame();
			else
				Interface.openMailFrame();
		}
	};
	
	public static void initCommandMap() {
		addCommand(invite);
		addCommand(invite_alias_i);
		addCommand(invite_alias_inv);
		addCommand(channel_join);
		addCommand(chan);
		addCommand(channel_leave);
		addCommand(channel_ban);
		addCommand(channel_unban);
		addCommand(channel_moderator);
		addCommand(channel_moderator_alias_mod);
		addCommand(channel_kick);
		addCommand(channel_lead);
		addCommand(gquit);
		addCommand(guildquit);
		addCommand(gmotd);
		addCommand(guildmotd);
		addCommand(ram);
		addCommand(gc);
		addCommand(ah);
		addCommand(channel_mute);
		addCommand(channel_silence);
		addCommand(channel_unmute);
		addCommand(channel_unsilence);
		addCommand(reload);
		stresstest.addSubCommand(stresstest_add);
		stresstest.addSubCommand(stresstest_clear);
		stresstest.addSubCommand(stresstest_msg);
		stresstest.addSubCommand(stresstest_remove);
		addCommand(stresstest);
		addCommand(help);
		addCommand(quit);
		addCommand(played);
		addCommand(who);
		addCommand(mail);
	}
	
	public static void handleChatCommand(String str) {
		str = StringUtils.removeSpaces(str);
		String[] value = str.split(" ");
		if (value.length == 0)
			return;
		String command = value[0].substring(1);
		if (command.length() == 0)
			return;
		if(commandMap.containsKey(command))
			commandMap.get(command).handle(value);
		else
			ChatFrame.addMessage(new Message("Unknown command, type /help for help.", false, MessageType.SELF));
	}
	
	private static void addCommand(ChatCommand command) {
		if(commandMap.containsKey(command.getName()))
			System.out.println("**ERROR** in ChatCommandMgr.addCommand, map already contains key : "+command.getName());
		commandMap.put(command.getName(), command);
	}
	
	static ChatCommand getCommand(String name) {
		return commandMap.get(name);
	}
	
	static HashMap<String, ChatCommand> getCommandMap()
	{
		return (commandMap);
	}
}