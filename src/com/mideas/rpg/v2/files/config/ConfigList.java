package com.mideas.rpg.v2.files.config;

import java.util.ArrayList;

public class ConfigList {

	private ArrayList<Config> configList = new ArrayList<Config>();
	private String name;
	
	public ConfigList(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void add(Config config) {
		this.configList.add(config);
	}
	
	public ArrayList<Config> getList() {
		return this.configList;
	}
	
	public Config get(int i) {
		return this.configList.get(i);
	}
	
	public boolean contains(String value) {
		return get(value) != null;
	}
	
	public Config get(String value) {
		int i = 0;
		while(i < this.configList.size()) {
			if(this.configList.get(i).getName().equals(value)) {
				return this.configList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public int size() {
		return this.configList.size();
	}
}
