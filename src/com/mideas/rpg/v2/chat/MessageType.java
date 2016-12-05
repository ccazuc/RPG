package com.mideas.rpg.v2.chat;

import com.mideas.rpg.v2.utils.Colors;

public enum MessageType {

	SAY((char)0, Colors.WHITE, "Say : ", " say : "),
	GUILD((char)1, new Colors(64/255f, 251/255f, 64f/255f), "Guild : ", "[Guild] "),
	PARTY((char)2, new Colors(170/255f, 167/255f, 1), "Party : ", "[Party] "),
	RAID((char)3, new Colors(1, 125/255f, 0), "Raid : ", "[Raid] "),
	BATTLEGROUND((char)4, Colors.WHITE, "Battleground : ", "[Battleground] "),
	YELL((char)5, new Colors(1, 63/255f, 64/255f), "Yell : ", " Yell : "),
	DISCUSSION((char)6, new Colors(1, 189/255f, 192/255f), "Discussion : ", ""),
	SELF((char)7, new Colors(1, 251/255f, 0), "", ""),
	WHISPER((char)8, new Colors(1, 126/255f, 1), "Say to ", ""),
	EMOTE((char)9, new Colors(1, 251/255f, 1), "", ""),
	PARTY_LEADER((char)10, new Colors(118/255f, 197/255f, 1), "Party : ", "[Party leader] "),
	OFFICER((char)11, new Colors(64/255f, 189/255f, 64/255f), "Officer : ", "[Officer] "),
	GM_ANNOUNCE((char)12, new Colors(0, 251/255f, 246/255f), "Announce : ", "[GM announce] ");
	
	private char value;
	private Colors color;
	private String defaultText;
	private String chatDisplay;
	
	private MessageType(char value, Colors color, String defaultText, String chatDisplay) {
		this.value = value;
		this.color = color;
		this.defaultText = defaultText;
		this.chatDisplay = chatDisplay;
	}
	
	public Colors getColor() {
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
