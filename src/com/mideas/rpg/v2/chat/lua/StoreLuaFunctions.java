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
	private final static LuaFunction random = new LuaFunction("Random")
	{
	
		@Override
		public Object handleFunction(Object object, ArrayList<Object> args)
		{
			System.out.println("Random called with list: " + args);
			System.out.println(args.get(0).getClass());
			if (args.size() == 2)
			{
				if (args.get(0) instanceof Integer && args.get(1) instanceof Integer)
					return ((int)args.get(0) + ((int)args.get(1) - (int)args.get(0)) * Math.random());
			}
			return (Math.random());
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
		functionMap.put(random.getName(), random);
	}
	
	public static LuaFunction getFunction(Object object, String functionName)
	{
		LuaFunction function = functionMap.get(functionName);
		if (function != null && function.isFunctionVisible(object))
			return (function);
		return (null);
	}
}
