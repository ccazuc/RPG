package com.mideas.rpg.v2.game;

public class WorldServer {

	private int realmId;
	private String realmName = "";
	//private int numberCharacter;
	
	public WorldServer(int realmId, String realmName) {
		this.realmId = realmId;
		this.realmName = realmName;
	}
	
	public int getRealmId() {
		return this.realmId;
	}
	
	public String getRealmName() {
		return this.realmName;
	}
}
