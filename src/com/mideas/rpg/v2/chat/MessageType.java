package com.mideas.rpg.v2.chat;

import org.newdawn.slick.Color;

public enum MessageType {

	SAY((char)0, Color.white, "Say : ", " Say : "),
	GUILD((char)1, Color.decode("#40FF40"), "Guild : ", "[Guild] "),
	PARTY((char)2, Color.decode("#797FBC"), "Party : ", "[Party] "),
	RAID((char)3, Color.decode("#FF7F00"), "Raid : ", "[Raid] "),
	BATTLEGROUND((char)4, Color.white, "Battleground : ", "[Battleground] "), //TODO: aller en BG pour avoir le couleur
	YELL((char)5, Color.decode("#D23D44"), "Yell : ", " Yell : "),
	DISCUSSION((char)6, Color.decode("#E3AFB3"), "Discussion : ", ""),
	SELF((char)7, Color.yellow, "", ""),
	WHISPER((char)8, Color.decode("#FF80FF"), "Say to ", "");
	
	private char value;
	private Color color;
	private String defaultText;
	private String chatDisplay;
	
	private MessageType(char value, Color color, String defaultText, String chatDisplay) {
		this.value = value;
		this.color = color;
		this.defaultText = defaultText;
		this.chatDisplay = chatDisplay;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public char getValue() {
		return this.value;
	}
	
	public String getDefaultText() {
		return this.defaultText;
	}
	
	public String getChatDisplay() {
		return this.chatDisplay;
	}
}
