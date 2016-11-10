package com.mideas.rpg.v2.game.guild;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.Joueur;

public class GuildMember {

	private int id;
	private String name;
	private int level;
	private GuildRank rank;
	private boolean isOnline;
	private String note = "";
	private String officer_note = "";
	private ClassType classType;
	private String classTypeString;
	private String levelString;
	private Color color;
	private String informationString;
	
	public GuildMember(int id, String name, int level, GuildRank rank, boolean isOnline, String note, String officer_note, ClassType classType) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.levelString = String.valueOf(this.level);
		this.rank = rank;
		this.isOnline = isOnline;
		this.note = note;
		this.officer_note = officer_note;
		this.classType = classType;
		this.color = Joueur.convClassTypeToColor(this.classType);
		this.classTypeString = Joueur.convClassTypeToString(this.classType);
		this.informationString = this.classTypeString+" niveau "+this.levelString;
	}
	
	public int getId() {
		return this.id;
	}
	
	public ClassType getClassType() {
		return this.classType;
	}
	
	public String getClassTypeString() {
		return this.classTypeString;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getInformationString() {
		return this.informationString;
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.levelString = String.valueOf(this.level);
		this.informationString = this.classTypeString+" niveau "+this.levelString;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
	public void setRank(GuildRank rank) {
		this.rank = rank;
	}
	
	public GuildRank getRank() {
		return this.rank;
	}
	
	public void setOnlineStatus(boolean we) {
		this.isOnline = we;
	}
	
	public boolean isOnline() {
		return this.isOnline;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setOfficerNote(String officer_note) {
		this.officer_note = officer_note;
	}
	
	public String getOfficerNote() {
		return this.officer_note;
	}
}
