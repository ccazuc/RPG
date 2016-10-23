package com.mideas.rpg.v2.chat;

import org.newdawn.slick.Color;

public enum MessageType {

	SAY((char)0, Color.white, "Say : ", " Say : "),
	GUILD((char)1, new Color(64/255f, 251/255f, 64f/255f), "Guild : ", "[Guild] "),
	PARTY((char)2, new Color(170/255f, 167/255f, 1), "Party : ", "[Party] "),
	RAID((char)3, new Color(1, 125/255f, 0), "Raid : ", "[Raid] "),
	BATTLEGROUND((char)4, Color.white, "Battleground : ", "[Battleground] "),
	YELL((char)5, new Color(1, 63/255f, 64/255f), "Yell : ", " Yell : "),
	DISCUSSION((char)6, new Color(1, 189/255f, 192/255f), "Discussion : ", ""),
	SELF((char)7, new Color(1, 251/255f, 0), "", ""),
	WHISPER((char)8, new Color(1, 126/255f, 1), "Say to ", ""),
	EMOTE((char)9, new Color(1, 251/255f, 1), "", ""),
	PARTY_LEADER((char)10, new Color(118/255f, 197/255f, 1), "Party : ", "[Party leader] "),
	OFFICER_CHAT((char)11, new Color(64/255f, 189/255f, 64/255f), "Officer : ", "[Officer] "),
	GM_ANNOUNCE((char)12, new Color(0, 251/255f, 246/255f), "Announce : ", "[GM announce] ");
	
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
