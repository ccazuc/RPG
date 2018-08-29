package com.mideas.rpg.v2.chat.lua;

import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.utils.UIElement;

public class StoreLuaFunctions {

	private final static HashMap<String, LuaFunction> functionMap = new HashMap<String, LuaFunction>();
	private final static LuaFunction getMouseFocus = new LuaFunction("GetMouseFocus")
	{
	
		@Override
		public Object handleFunction(Object object, ArrayList<Object> args)
		{
			System.out.println("GetMouseFocus called with list: " + args);
			System.out.println("Focus: " + Mideas.getHoveredElement());
			return (Mideas.getHoveredElement());
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
	private final static LuaFunction getName = new LuaFunction("GetName")
	{
	
		@Override
		public Object handleFunction(Object object, ArrayList<Object> args)
		{
			System.out.println("GetName called with list: " + args);
			return (((UIElement)object).getName());
		}
		
		@Override
		public boolean isFunctionVisible(Object object)
		{
			return (object instanceof UIElement);
		}
	};
	private final static LuaFunction setName = new LuaFunction("SetName")
	{
	
		@Override
		public Object handleFunction(Object object, ArrayList<Object> args)
		{
			if (args.size() == 0)
				return (null);
			((UIElement)object).setName((String)args.get(0));
			return (null);
		}
		
		@Override
		public boolean isFunctionVisible(Object object)
		{
			return (object instanceof UIElement);
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
	private final static LuaFunction setWidth = new LuaFunction("SetWidth")
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
		functionMap.put(getName.getName(), getName);
		functionMap.put(setName.getName(), setName);
	}
	
	public static LuaFunction getFunction(Object object, String functionName)
	{
		System.out.println("StoreLuaFunctions:GetFunction:functionName: '" + functionName + "', object: '" + object + "'");
		LuaFunction function = functionMap.get(functionName);
		if (function != null && function.isFunctionVisible(object))
			return (function);
		return (null);
	}
}
