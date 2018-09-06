package com.mideas.rpg.v2.files.config;

import java.util.ArrayList;

public class ConfigList
{

	private final ArrayList<Config> configList;
	private final String name;
	private final ChatConfigType type;
	
	public ConfigList(String name, ChatConfigType type)
	{
		this.name = name;
		this.configList = new ArrayList<Config>();
		this.type = type;
	}
	
	public ChatConfigType getType()
	{
		return (this.type);
	}
	
	public String getName()
	{
		return (this.name);
	}
	
	public void add(Config config)
	{
		this.configList.add(config);
	}
	
	public ArrayList<Config> getList()
	{
		return (this.configList);
	}
	
	public Config get(int i)
	{
		return (this.configList.get(i));
	}
	
	public boolean contains(String value)
	{
		return (get(value) != null);
	}
	
	public Config get(String value)
	{
		for (int i = 0; i < this.configList.size(); ++i)
			if(this.configList.get(i).getName().equals(value))
				return (this.configList.get(i));
		return (null);
	}
	
	@SuppressWarnings("unused")
	public void write(StringBuilder builder)
	{
		
	}
	
	public int size()
	{
		return (this.configList.size());
	}
}
