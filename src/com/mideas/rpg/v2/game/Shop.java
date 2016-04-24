package com.mideas.rpg.v2.game;

public class Shop {

	protected int id;
	protected ClassType[] classType;
	protected int sellPrice;
	
	public Shop(int id, ClassType[] classType, int sellPrice) {
		this.id = id;
		this.classType = classType;
		this.sellPrice = sellPrice;
	}
	
	public int getId() {
		return this.id;
	}
	
	public ClassType getClassType(int i) {
		return this.classType[i];
	}
	
	public int getSellPrice() {
		return this.sellPrice;
	}
}
