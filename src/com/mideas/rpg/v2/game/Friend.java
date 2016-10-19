package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.race.Race;

public class Friend {
	
	private String name;
	private int level;
	private Race race;
	private ClassType classe;
	private boolean isOnline;
	private String infosText;
	private String areaText;
	private final static String levelText = " level ";
	private final static String space = " - ";
	//private Area zone;
	
	public Friend(String name, int level, Race race, ClassType classe, boolean isOnline) {
		this.isOnline = isOnline;
		this.classe = classe;
		this.level = level;
		this.name = name;
		this.race = race;
		this.infosText = Joueur.convClassTypeToString(this.classe)+levelText+this.level;
		this.areaText = space+"Area";
	}
	
	public String getInfosText() {
		return this.infosText;
	}
	
	public String getAreaText() {
		return this.areaText;
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.infosText = Joueur.convClassTypeToString(this.classe)+levelText+this.level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Race getRace() {
		return this.race;
	}
	
	public ClassType getClasse() {
		return this.classe;
	}
	
	public boolean isOnline() {
		return this.isOnline;
	}
	
	public void setOnlineStatus(boolean we) {
		this.isOnline = we;
	}
}
