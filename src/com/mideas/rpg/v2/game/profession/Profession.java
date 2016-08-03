package com.mideas.rpg.v2.game.profession;

import java.util.ArrayList;

public class Profession {

	private ArrayList<Category> categoryList = new ArrayList<Category>();
	private String name;
	private int id;
	
	public Profession(int id, String name, Category category1, Category category2, Category category3, Category category4, Category category5, Category category6, Category category7, Category category8) {
		this.id = id;
		this.name = name;
		this.categoryList.add(category1);
		this.categoryList.add(category2);
		this.categoryList.add(category3);
		this.categoryList.add(category4);
		this.categoryList.add(category5);
		this.categoryList.add(category6);
		this.categoryList.add(category7);
		this.categoryList.add(category8);
	}
	
	public ArrayList<Category> getCategoryList() {
		return this.categoryList;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
}
