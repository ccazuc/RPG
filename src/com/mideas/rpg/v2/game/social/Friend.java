package com.mideas.rpg.v2.game.social;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Joueur;

public class Friend {
	
	private String name;
	private int nameWidth15;
	private int level;
	private Race race;
	private int characterId;
	private ClassType classe;
	private boolean isOnline;
	private String infosText;
	private String areaText;
	private final static String levelText = " level ";
	private final static String space = " - ";
	//private Area zone;
	
	public Friend(int character_id, String name, int level, Race race, ClassType classe) { //online friend
		this.characterId = character_id;
		this.isOnline = true;
		this.classe = classe;
		this.level = level;
		setName(name);
		this.race = race;
		this.infosText = Joueur.convClassTypeToString(this.classe)+levelText+this.level;
		this.areaText = space+"Area";
	}
	
	public Friend(int character_id, String name) { //offline friend
		this.characterId = character_id;
		setName(name);
	}
	
	public void updateInformations(String name, int level, Race race, ClassType classe) {
		setName(name);
		setLevel(level);
		this.race = race;
		setClasse(classe);
		setOnlineStatus(true);
		this.areaText = space+"Area";
	}
	
	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}
	
	public int getCharacterId() {
		return this.characterId;
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
	
	public void setName(String name) {
		this.name = name;
		this.nameWidth15 = FontManager.get("FRIZQT", 15).getWidth(this.name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getFriendNameWidth15() {
		return this.nameWidth15;
	}
	
	public void setRace(Race race) {
		this.race = race;
	}
	
	public Race getRace() {
		return this.race;
	}
	
	public void setClasse(ClassType classe) {
		this.classe = classe;
		this.infosText = Joueur.convClassTypeToString(this.classe)+levelText+this.level;
	}
	
	public ClassType getClasse() {
		return this.classe;
	}
	
	public boolean isOnline() {
		return this.isOnline;
	}
	
	public void setOnlineStatus(boolean we) {
		if(this.isOnline != we) {
			this.isOnline = we;
			Mideas.joueur1().sortFriendList();
		}
	}
}
