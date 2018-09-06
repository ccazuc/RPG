package com.mideas.rpg.v2.files.config;

import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.utils.Color;

public class Config
{

	protected String name;
	protected MessageType type;
	
	public Config(String name)
	{
		this.name = name;
	}
	
	public Config(String name, MessageType type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName()
	{
		return (this.name);
	}
	
	public MessageType getMessageType()
	{
		return (this.type);
	}
	
	@SuppressWarnings("unused")
	public void read(String value, int index) {}
	
	@SuppressWarnings("unused")
	public void read(String value) {}
	
	public void readChatColor(String value) {
		if (parseColor(value, this.type) == -1)
			System.out.println(ChatConfigManager.FILE_NAME+" error on load "+this.name);
	}
	
	//To override
	public String write()
	{
		return (null);
	}
	
	public String writeChatColor()
	{
		return (this.name+' '+this.type.getColor().getRed()+' '+this.type.getColor().getGreen()+' '+this.type.getColor().getBlue());
	}
	
	private static int parseColor(String value, MessageType type)
	{
		String[] list = value.split(" ");
		if(list.length == 4)
		{
			type.setColor(new Color(Float.valueOf(list[1]), Float.valueOf(list[2]), Float.valueOf(list[3])));
			return (0);
		}
		return -1;
	}
}
