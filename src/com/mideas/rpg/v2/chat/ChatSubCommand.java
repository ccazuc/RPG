package com.mideas.rpg.v2.chat;

import java.util.ArrayList;

public class ChatSubCommand {

	protected String helpMessage;
	protected String parentName;
	protected String name;
	protected ArrayList<ChatSubCommand> commandList;
	
	public ChatSubCommand(String name, String parentName) {
		this.name = name;
		this.parentName = parentName;
	}
	
	public ChatSubCommand(String name, String parentName, String helpMessage) {
		this.name = name;
		this.parentName = parentName;
		this.helpMessage = helpMessage;
	}
	
	public void addSubCommand(ChatSubCommand command) {
		if(this.commandList == null) {
			this.commandList = new ArrayList<ChatSubCommand>();
		}
		this.commandList.add(command);
	}
	
	public String printHelpMessage() {
		if(this.helpMessage == null) {
			StringBuilder result = new StringBuilder();
			int i = 0;
			boolean initHeader = false;
			while(i < this.commandList.size()) {
				if(!initHeader) {
					result.append("Command "+this.parentName+' '+this.name+" have subcommands:");
					initHeader = true;
				}
				result.append("\n"+this.commandList.get(i).getName());
				i++;
			}
			if(!initHeader) {
				result.append("No command available for "+this.parentName+' '+this.name);
			}
			return result.toString();
		}
		return this.helpMessage;
	}
	
	public String printSubCommandError() {
		StringBuilder result = new StringBuilder();
		int i = 0;
		boolean initHeader = false;
		while(i < this.commandList.size()) {
			if(!initHeader) {
				result.append("Command "+this.parentName+' '+this.name+" have subcommands:");
				initHeader = true;
			}
			result.append("\n"+this.commandList.get(i).getName());
			i++;
		}
		if(!initHeader) {
			result.append("No command available for "+this.parentName+' '+this.name);
		}
		return result.toString();
	}
	
	public String getName() {
		return this.name;
	}
	
	@SuppressWarnings("unused")
	public void handle(String[] command) {}
}
