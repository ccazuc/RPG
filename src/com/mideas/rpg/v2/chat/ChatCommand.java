package com.mideas.rpg.v2.chat;

public class ChatCommand {

	protected String helpMessage;
	protected String name;
	
	public ChatCommand(String name, String helpMessage) {
		this.helpMessage = helpMessage;
		this.name = name;
	}
	
	public String printHelpMessage() {
		return this.helpMessage;
	}
	
	@SuppressWarnings("unused")
	public void handle(String[] command) {}
	
	public String getName() {
		return this.name;
	}
}
