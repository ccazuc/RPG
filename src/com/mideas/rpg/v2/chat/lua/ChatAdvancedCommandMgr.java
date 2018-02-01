package com.mideas.rpg.v2.chat.lua;

import java.util.ArrayList;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.utils.Color;

public class ChatAdvancedCommandMgr {

	private static boolean errorFound = false;
	private static int currentIndex;
	
	public static void handleCommand(String commandLine, boolean shouldPrint)
	{
		currentIndex = -1;
		Object currentObject = null;
		errorFound = false;
		System.out.println(commandLine);
		while (++currentIndex < commandLine.length())
		{
			currentObject = getObject(currentIndex, commandLine, currentObject, true, null);
			if (currentObject == null)
				break;
		}
	}
	
	private static Object getObject(int start, String commandLine, Object objectCall, boolean setPosition, ParseData parseData)
	{
		Object result = null;
		int i = start - 1;
		ArrayList<Object> functionArgs = null;
		while (++i < commandLine.length())
		{
			if (commandLine.charAt(i) == '(')
			{
				String functionName = commandLine.substring(start, i);
				//parseData.setObjectName(functionName);
				if (parseData != null)
					parseData.setObjectName(functionName);
				LuaFunction function = StoreLuaFunctions.getFunction(objectCall, functionName);
				if (function == null)
				{
					//ChatFrame.addMessage(new Message("Function " + functionName + " not found.", false, MessageType.SELF, Color.WHITE, true));
					errorFound = true;
					System.out.println("Null function: " + functionName);
					//return (null);
				}
				System.out.println("Function found: " + functionName);
				start = findFunctionEnd(i, commandLine);
				if (start == -1)
				{
					System.out.println("Function end == -1");
					return (null);
				}
				String functionArgsString = commandLine.substring(i + 1, start);
				functionArgs = parseFunctionArgs(functionArgsString);
				//System.out.println("pos: " + commandLine.substring(0, start));
				if (setPosition)
					currentIndex = start;
				else if (parseData != null)
					parseData.setIndex(start + functionArgsString.length());
				if (function != null)
					result = function.handleFunction(objectCall, functionArgs);
				System.out.println("Function args: " + functionArgsString);
				return (result);
			}
			else if (commandLine.charAt(i) == ':')
			{
				System.out.println("Object found: " + commandLine.substring(start, i));
				String objectName = commandLine.substring(start, i);
				parseData.setObjectName(objectName);
			}
		}
		return (result);
	}
	
	private static ArrayList<Object> parseFunctionArgs(String args)
	{
		ArrayList<Object> result = null;
		int i = -1;
		ParseData parseData = new ParseData();
		while (++i < args.length())
		{
			//System.out.println("charAt(i): " + args.charAt(i));
			if (args.charAt(i) == '"')
			{
				int strEnd = findStringEnd(i + 1, args);
				if (strEnd == -1)
				{
					errorFound = true;
					System.out.println("Parse error, missing \" in a String");
					return (null);
				}
				if (result == null)
					result = new ArrayList<Object>();
				result.add("String: '" + args.substring(i + 1, strEnd) + "'");
				System.out.println("Str Result: '" + result.get(result.size() - 1) + '\'');
				i = findNextArgs(strEnd + 1, args, false, parseData) - 1;
				System.out.println("i: " + i);
				if (i == -2)
				{
					System.out.println("Erorr: unknown symbole '" + args.charAt(parseData.getErrorIndex()) +"'");
					//System.out.println("Unknown symbol near );
					errorFound = true;
					return (null);
				}
			}
			else
			{
				//int end = findArgEnd(i, args);
				//String arg = args.substring(end);
				//System.out.println("nextArgs: '" + arg + '\'');
				Object object = getObject(i, args, null, false, parseData);
				if (object == null)
				{
					errorFound = true;
					System.out.println("Object null");
					return (null);
				}
				//i = parseData.getIndex();
				if (result == null)
					result = new ArrayList<Object>();
				result.add(object);
				//System.out.println("functionEnd: " + args.substring(findFunctionEnd(i, args)));
				i = findFunctionEnd(i, args);
				i = findNextArgs(i + 1, args, true, parseData) - 1;
				if (i == -2)
					return (result);
				System.out.println("Callend: " + args.substring(i));
			}
		}
		if (result != null)
		{
			int j = -1;
			while (++j < result.size())
				System.out.println("Result[" + j + "]: " + result.get(j));
		}
		return (result);
	}
	
	private static int findNextArgs(int start, String args, boolean isFunction, ParseData parseData)
	{
		start -= 1;
		boolean commasFound = false;
		while (++start < args.length())
		{
			if (args.charAt(start) == ' ')
				continue;
			if (args.charAt(start) == ',')
			{
				if (commasFound)
					return (start);
				commasFound = true;
				continue;
			}
			if (!isFunction && !commasFound)
			{
				parseData.setErrorIndex(start);
				return (-1);
			}
			return (start);
		}
		return (start);
	}
	
	private static int findStringEnd(int start, String args)
	{
		start--;
		while (++start < args.length())
			if (args.charAt(start) == '"' && start > 0 && args.charAt(start - 1) != '\\')
				return (start);
		return (-1);
	}
	
	private static int findFunctionEnd(int start, String commandLine)
	{
		--start;
		boolean isInString = false;
		int parenthese = 0;
		boolean found = false;
		while (++start < commandLine.length())
		{
			if (commandLine.charAt(start) == '"' && start > 0 && commandLine.charAt(start - 1) != '\\')
				isInString = !isInString;
			else if (!isInString && commandLine.charAt(start) == ')')
			{
				--parenthese;
				found = true;
			}
			else if (!isInString && commandLine.charAt(start) == '(')
			{
				++parenthese;
				found = true;
			}
			if (found && parenthese == 0)
				return (start);
		}
		return (-1);
	}
}
