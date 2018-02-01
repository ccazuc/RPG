package com.mideas.rpg.v2.chat.lua;

import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.UIElement;

public class StoreLuaFunctions {

	private final static HashMap<String, LuaFunction> functionMap = new HashMap<String, LuaFunction>();
	private final static LuaFunction getMouseFocus = new LuaFunction("GetMouseFocus")
	{
	
		@Override
		public Object handleFunction(Object object, ArrayList<Object> args)
		{
			System.out.println("GetMouseFocus called with list: " + args);
			return (this);
		}
		
		@Override
		public boolean isFunctionVisible(Object object)
		{
			return (object == null);
		}
	};
	private final static LuaFunction getWidth = new LuaFunction("GetWidth")
	{
	
		@Override
		public Object handleFunction(Object object, ArrayList<Object> args)
		{
			return (null);
		}
		
		@Override
		public boolean isFunctionVisible(Object object)
		{
			return (object instanceof UIElement);
		}
	};
	
	public static void initFunctionMap()
	{
		functionMap.put(getMouseFocus.getName(), getMouseFocus);
		functionMap.put(getWidth.getName(), getWidth);
	}
	
	public static LuaFunction getFunction(Object object, String functionName)
	{
		LuaFunction function = functionMap.get(functionName);
		if (function != null && function.isFunctionVisible(object))
			return (function);
		return (null);
	}
}
