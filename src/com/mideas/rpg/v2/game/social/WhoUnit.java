package com.mideas.rpg.v2.game.social;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.utils.Color;

public class WhoUnit {

	private int level;
	private int id;
	private String name;
	private String guildName;
	private String levelString;
	private String classe;
	private String race;
	private Color color;
	//private Area area;
	
	public WhoUnit(int id, String name, String guildName, int level, ClassType classe, Race race) {
		this.classe = Joueur.convClassTypeToString(classe);
		this.color = Color.convClassTypeToColor(classe);
		this.race = Joueur.convRaceToString(race);
		this.guildName = guildName;
		this.levelString = Integer.toString(level);
		this.level = level;
		this.name = name;
		this.id = id;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getGuildName() {
		return this.guildName;
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
	public String getClasse() {
		return this.classe;
	}
	
	public String getRace() {
		return this.race;
	}
} 
