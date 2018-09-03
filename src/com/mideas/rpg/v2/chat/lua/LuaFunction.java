package com.mideas.rpg.v2.chat.lua;

import java.util.ArrayList;

public class LuaFunction {

	private final String name;

	public LuaFunction(String name)
	{
		this.name = name;
		StoreLuaFunctions.addFunction(this);
	}
	
	public String getName()
	{
		return (this.name);
	}
	
	public Object handleFunction(Object object, ArrayList<Object> args) { return (null); }
	public boolean isFunctionVisible(Object object) { return (false); }
	
	@Override
	public String toString()
	{
		return ("Function: '" + this.name + "'");
	}
}
