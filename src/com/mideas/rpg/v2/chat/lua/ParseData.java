package com.mideas.rpg.v2.chat.lua;

public class ParseData {

	private int index;
	private String objectName;
	private int errorIndex;
	private boolean errorFound;
	
	public boolean getErrorFound()
	{
		return (this.errorFound);
	}
	
	public void setErrorFound(boolean we)
	{
		this.errorFound = we;
	}
	
	public int getErrorIndex()
	{
		return (this.errorIndex);
	}
	
	public void setErrorIndex(int index)
	{
		this.errorIndex = index;
	}
	
	public String getObjectName()
	{
		return (this.objectName);
	}
	
	public void setObjectName(String name)
	{
		this.objectName = name;
	}
	
	public int getIndex()
	{
		return (this.index);
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
}
