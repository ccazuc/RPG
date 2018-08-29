package com.mideas.rpg.v2.chat;

import java.util.ArrayList;

public class ChatCommand {

	protected String helpMessage;
	protected String name;
	protected ArrayList<ChatSubCommand> subCommandList;
	
	public ChatCommand(String name, String helpMessage) {
		this.helpMessage = helpMessage;
		this.name = name;
	}
	
	public String printHelpMessage() {
		if (this.subCommandList == null)
			return (this.helpMessage);
		StringBuilder builder = new StringBuilder();
		int i = -1;
		builder.append(this.helpMessage + '\n');
		builder.append("Available sub command for " + this.name + ":\n");
		while (++i < this.subCommandList.size())
			if (i < this.subCommandList.size() - 1)
				builder.append('-' + this.subCommandList.get(i).getName() + '\n');
			else
				builder.append('-' + this.subCommandList.get(i).getName());
		return (builder.toString());
	}
	
	public void addSubCommand(ChatSubCommand command)
	{
		if (this.subCommandList == null)
			this.subCommandList = new ArrayList<ChatSubCommand>();
		this.subCommandList.add(command);
	}
	
	@SuppressWarnings("unused")
	public void handle(String[] command) {}
	
	@SuppressWarnings("unused")
	public void handle(String[] command, String line) {}
	
	public String getName() {
		return this.name;
	}
}
