package com.mideas.rpg.v2.chat;

import com.mideas.rpg.v2.utils.Color;

public enum MessageType {

	SAY((char)0, Color.WHITE, "Say : ", " say : "),
	GUILD((char)1, new Color(64/255f, 1, 64f/255f), "Guild : ", "[Guild] "),
	PARTY((char)2, new Color(170/255f, 170/255f, 1), "Party : ", "[Party] "),
	RAID((char)3, new Color(1, 127/255f, 0), "Raid : ", "[Raid] "),
	BATTLEGROUND((char)4, Color.WHITE, "Battleground : ", "[Battleground] "),
	YELL((char)5, new Color(1, 63/255f, 64/255f), "Yell : ", " Yell : "),
	CHANNEL((char)6, new Color(1, 192/255f, 192/255f), "Channel : ", ""),
	SELF((char)7, new Color(1, 1, 0), "", ""),
	WHISPER((char)8, new Color(1, 128/255f, 1), "Say to ", ""),
	EMOTE((char)9, new Color(1, 251/255f, 1), "", ""),
	PARTY_LEADER((char)10, new Color(118/255f, 197/255f, 1), "Party : ", "[Party leader] "),
	OFFICER((char)11, new Color(64/255f, 192/255f, 64/255f), "Officer : ", "[Officer] "),
	GM_ANNOUNCE((char)12, new Color(0, 251/255f, 246/255f), "Announce : ", "[GM announce] "),
	IGNORE((char)13, new Color(1, 0, 0), "" ,"");
	
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
	
	public void setColor(Color color) {
		this.color = color;
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
