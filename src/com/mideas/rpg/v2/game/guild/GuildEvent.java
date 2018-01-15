package com.mideas.rpg.v2.game.guild;

import com.mideas.rpg.v2.utils.StringUtils;

public class GuildEvent {

	private final long timer;
	//private final GuildJournalEventType type;
	//private final String player1Name;
	private final String eventText;
	private String eventTimerText;
	
	public GuildEvent(long timer, GuildJournalEventType type, String player1Name, String player2Name, String rankName) {
		this.timer = timer;
		//this.type = type;
		//this.player1Name = player1Name;
		this.eventTimerText = builderTimerString(timer);
		this.eventText = buildEventString(type, player1Name, player2Name, rankName);
	}
	
	public long getTimer() {
		return this.timer;
	}
	
	public String getEventText() {
		return this.eventText;
	}
	
	public String getEventTimerText() {
		return this.eventTimerText;
	}
	
	public void updateTimerString() {
		this.eventTimerText = builderTimerString(this.timer);
	}
	
	private static String builderTimerString(long timer) {
		return "("+StringUtils.convertTimeToStringSimple(timer)+" ago)";
	}
	
	private static String buildEventString(GuildJournalEventType type, String player1Name, String player2Name, String rankName) {
		if(type == GuildJournalEventType.MEMBER_DEMOTED) {
			return player1Name+" has demoted "+player2Name+" to the rank "+rankName;
		}
		else if(type == GuildJournalEventType.MEMBER_INVITED) {
			return player1Name+" has invited "+player2Name;
		}
		else if(type == GuildJournalEventType.MEMBER_JOINED) {
			return player1Name+" joined the guild";
		}
		else if(type == GuildJournalEventType.MEMBER_KICKED) {
			return player1Name+" kicked "+player2Name+" out of the guild";
		}
		else if(type == GuildJournalEventType.MEMBER_LEFT) {
			return player1Name+" left the guild";
		}
		else if(type == GuildJournalEventType.MEMBER_PROMOTED) {
			return player1Name+" has promoted "+player2Name+" to the rank "+rankName;
		}
		return null;
	}
}
