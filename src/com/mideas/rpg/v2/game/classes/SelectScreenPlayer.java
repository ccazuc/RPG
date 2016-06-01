package com.mideas.rpg.v2.game.classes;

public class SelectScreenPlayer {

	private String name;
	private int level;
	private String classe;
	private String race;
	private int id;
	
	public SelectScreenPlayer(int id, String name, int level, String classe, String race) {
		this.name = name;
		this.id = id;
		this.level = level;
		this.classe = classe;
		this.race = race;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getClasse() {
		return this.classe;
	}
	
	public String getRace() {
		return this.race;
	}
}
