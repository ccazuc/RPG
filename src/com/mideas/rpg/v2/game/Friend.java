package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.race.Classe;
import com.mideas.rpg.v2.game.race.Race;

public class Friend {
	
	private String name;
	private int level;
	private Race race;
	private Classe classe;
	//private Area zone;
	
	public Friend(String name, int level, Race race, Classe classe) {
		this.name = name;
		this.level = level;
		this.race = race;
		this.classe = classe;
	}
	
	public void setLevel(int level) {
		this.level = level;
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
	
	public Classe getClasse() {
		return this.classe;
	}
}
