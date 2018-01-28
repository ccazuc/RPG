package com.mideas.rpg.v2.chat;

public class ChatAdvancedCommandMgr {

	public static void handleCommand(String commandLine, boolean shouldPrint)
	{
		String commands[] = commandLine.split(":");
		int i = -1;
		Object currentObject = null;
		while (++i < commands.length)
		{
			if (currentObject == null)
				currentObject = findFirstValue(commands[0]);
			else
				;
		}
		if (shouldPrint)
		{
			
		}
	}
	
	private static Object handleArgument(String command)
	{
		return (null);
	}
	
	private static Object findFirstValue(String value)
	{
		Object object = null;
		if (value.equals("GetMouseFocus()"))
			;
		else
			;
		return (object);
	}
}
