package com.mideas.rpg.v2.files.logs;

public enum LogsType {

	CONNECTION("Connection.log", (byte)0),
	;
	
	private final String path;
	private final byte id;
	
	private LogsType(String path, byte id)
	{
		this.path = path;
		this.id = id;
	}
	
	public String getPath()
	{
		return (this.path);
	}
	
	public byte getId()
	{
		return (this.id);
	}
}
