package com.mideas.rpg.v2.files.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.callback.CallbackMgr;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.chat.channel.ChannelMgr;
import com.mideas.rpg.v2.chat.channel.ChatChannel;
import com.mideas.rpg.v2.chat.new_chat.ChatFrame;
import com.mideas.rpg.v2.chat.new_chat.ChatFrameTab;
import com.mideas.rpg.v2.command.chat.CommandChannel;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatConfigManager
{

	private final static boolean DEBUG_READ = false;
	private final static boolean DEBUG_WRITE = false;
	private final static LinkedHashMap<String, ConfigList> configMap = new LinkedHashMap<String, ConfigList>();
	public final static String FILE_NAME = "chat-config.wtf";
	private final static ConfigList COLORS = new ConfigList("COLORS", ChatConfigType.COLORS)
	{
		
		@Override
		public void write(StringBuilder builder)
		{
			for (int i = 0; i < size(); ++i)
			{
				if(DEBUG_WRITE) 
					System.out.println(get(i).writeChatColor());
				builder.append(get(i).writeChatColor() + System.lineSeparator());
			}
		}
	};
	private final static Config SELF = new Config("SELF", MessageType.SELF);
	private final static Config SAY = new Config("SAY", MessageType.SAY);
	private final static Config PARTY = new Config("PARTY", MessageType.PARTY);
	private final static Config RAID = new Config("RAID", MessageType.RAID);
	private final static Config GUILD = new Config("GUILD", MessageType.GUILD);
	private final static Config OFFICER = new Config("OFFICER", MessageType.OFFICER);
	private final static Config YELL = new Config("YELL", MessageType.YELL);
	private final static Config WHISPER = new Config("WHISPER", MessageType.WHISPER);
	private final static Config EMOTE = new Config("EMOTE", MessageType.EMOTE);
	private final static Config CHANNEL = new Config("CHANNEL", MessageType.CHANNEL);
	private final static Config IGNORE = new Config("IGNORE", MessageType.IGNORE);
	private final static ConfigList CHANNELS = new ConfigList("CHANNELS", ChatConfigType.CHANNELS)
	{
		
		@SuppressWarnings("synthetic-access")
		@Override
		public void write(StringBuilder builder)
		{
			writeChannels(builder);
		}
	};
	private final static ConfigList CHAT_FRAME = new ConfigList("CHAT_FRAME", ChatConfigType.CHAT_FRAME)
	{
		
		@Override
		public void write(StringBuilder builder)
		{
			writeChatFrame(builder);
		}
	};
	
	public static void initConfigMap()
	{
		COLORS.add(SELF);
		COLORS.add(SAY);
		COLORS.add(PARTY);
		COLORS.add(RAID);
		COLORS.add(GUILD);
		COLORS.add(OFFICER);
		COLORS.add(YELL);
		COLORS.add(WHISPER);
		COLORS.add(EMOTE);
		COLORS.add(CHANNEL);
		COLORS.add(IGNORE);
		configMap.put(COLORS.getName(), COLORS);
		configMap.put(CHANNELS.getName(), CHANNELS);
		configMap.put(CHAT_FRAME.getName(), CHAT_FRAME);
	}
	
	public static void saveConfig()
	{
		if (Mideas.joueur1() == null)
			return;
		try
		{
			StringBuilder content = new StringBuilder();
			File folder = new File("WTF/Account/" + Mideas.getAccountName() + '/' + ConnectionManager.getWorldServer().getRealmName() + '/' + Mideas.joueur1().getName());
			if (!folder.exists())
				folder.mkdirs();
			File file = new File("WTF/Account/" + Mideas.getAccountName() + '/' + ConnectionManager.getWorldServer().getRealmName() + '/' + Mideas.joueur1().getName() + '/' + FILE_NAME);
			if (DEBUG_WRITE)
				System.out.println(file.mkdirs() + " WTF/" + ConnectionManager.getWorldServer().getRealmName() + '/'+FILE_NAME);
			if (!file.exists())
				file.createNewFile();
			for (ConfigList configList : configMap.values())
			{
				//ChatConfigType type = configList.getType();
				content.append(configList.getName() + System.lineSeparator() + System.lineSeparator());
				configList.write(content);
				System.out.println("Config Type: " + configList.getType());
				/*if (type == ChatConfigType.CHANNELS)
				{
					content.append(writeChannels());
				}
				else if (type == ChatConfigType.COLORS)
				{
					for (int i = 0; i < configList.size(); ++i)
					{
						if(DEBUG_WRITE) 
							System.out.println(configList.get(i).writeChatColor());
						content.append(configList.get(i).writeChatColor() + System.lineSeparator());
					}
				}
				else if (type == ChatConfigType.CHAT_FRAME)
				{
					
				}*/
				content.append("END" + System.lineSeparator() + System.lineSeparator());
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(content.toString());
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loadConfig()
	{
		try
		{
			BufferedReader buffer = null;
			int i = 0;
			int j = 0;
			String currentLine;
			ConfigList list;
			File folder = new File("WTF/Account/" + Mideas.getAccountName() + '/' + ConnectionManager.getWorldServer().getRealmName() + '/' + Mideas.joueur1().getName() + '/');
			if (!folder.exists())
				folder.mkdirs();
			File file = new File("WTF/Account/" + Mideas.getAccountName() + '/' + ConnectionManager.getWorldServer().getRealmName() + '/' + Mideas.joueur1().getName() + '/' + FILE_NAME);
			if (!file.exists())
				file.createNewFile();
			buffer = new BufferedReader(new FileReader("WTF/Account/" + Mideas.getAccountName() + '/' + ConnectionManager.getWorldServer().getRealmName() + '/' + Mideas.joueur1().getName() + '/' + FILE_NAME));
			while ((currentLine = buffer.readLine()) != null)
			{
				list = configMap.get(currentLine);
				if (list == null)
				{
					if (currentLine.length() != 0)
						System.out.println("Error load " + FILE_NAME + " on line " + j + " line value: \"" + currentLine + "\"");
					continue;
				}
				if (list.getType() == ChatConfigType.CHAT_FRAME)
				{
					readChatFrame(buffer);
					++j;
					continue;
				}
				while ((currentLine = buffer.readLine().trim()) != null)
				{
					if (currentLine.length() == 0)
						continue;
					i = 0;
					if (currentLine.equals("END"))
						break;
					while (i < currentLine.length() && currentLine.charAt(i) != ' ')
						i++;
					String tmp = currentLine.substring(0, i);
					if (list.contains(tmp))
					{
						if (DEBUG_READ)
							System.out.println(tmp);
						if (list.getType() == ChatConfigType.COLORS)
							list.get(currentLine.substring(0, i)).readChatColor(currentLine);
					}
					else if (list.getType() == ChatConfigType.CHANNELS)
						readChannel(currentLine);
					else
						System.out.println("Error load " + FILE_NAME + " on line " + j + " line value: \"" + currentLine + "\", listType: " + list.getType());
					j++;
				}
				j++;
			}
			buffer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		CallbackMgr.onConfigLoaded();
	}
	
	private static int readChannel(String value)
	{
		String[] list = value.split(" ");
		if (list.length < 2)
			return (-1);
		String channelName = list[0];
		String channelValueString = list[1];
		if (!StringUtils.isInteger(channelValueString))
		{
			System.out.println(ChatConfigManager.FILE_NAME+" error on load : channelValue isn't an integer : "+channelValueString);
			return (-1);
		}
		int channelValue = Integer.parseInt(channelValueString);
		if (list.length >= 3)
			CommandChannel.joinChannel(channelName, channelValue, list[2]);
		else
			CommandChannel.joinChannel(channelName, channelValue);
		return (0);
 	}
	
	private static void writeChannels(StringBuilder builder)
	{
		for (ChatChannel channel : ChannelMgr.getChannelMap().values())
			builder.append(channel.getName() + " " + channel.getValue() + " " + channel.getPassword() + System.lineSeparator());
		builder.append(System.lineSeparator());
	}
	
	static void writeChatFrame(StringBuilder builder)
	{
		for (int i = 0; i < Interface.getChatFrameMgr().getChatFrameList().size(); ++i)
		{
			ChatFrame frame = Interface.getChatFrameMgr().getChatFrameList().get(i);
			builder.append("\tWINDOW " + i + System.lineSeparator());
			builder.append("\tX " + frame.getXSave() + System.lineSeparator());
			builder.append("\tY " + frame.getYSave() + System.lineSeparator());
			builder.append("\tWIDTH " + frame.getWidthSave() + System.lineSeparator());
			builder.append("\tHEIGHT " + frame.getHeightSave() + System.lineSeparator());
			builder.append("\tLOCKED " + writeBoolean(frame.getIsLocked()) + System.lineSeparator() + System.lineSeparator());
			for (int j = 0; j < frame.getTabList().size(); ++j)
			{
				System.out.println("Tab found");
				ChatFrameTab tab = frame.getTabList().get(j);
				builder.append("\t\tTAB " + j + System.lineSeparator());
				builder.append("\t\t\tNAME " + tab.getName() + System.lineSeparator());
				builder.append("\t\t\tMESSAGES" + System.lineSeparator());
				for (MessageType type : tab.getAllowedMessageType())
					builder.append("\t\t\t" + type.toString() + System.lineSeparator());
				builder.append("\t\t\tEND" + System.lineSeparator());
				builder.append("\t\tEND" + System.lineSeparator() + System.lineSeparator());
			}
			builder.append("\tEND" + System.lineSeparator() + System.lineSeparator());
		}
	}
	
	static void readChatFrame(BufferedReader buffer)
	{
		try
		{
			System.out.println("Read ChatFrame");
			String currentLine;
			ChatFrame frame = null;
			int index = 1;
			while ((currentLine = buffer.readLine()) != null)
			{
				currentLine = currentLine.trim();
				System.out.println("CurrentLine: '" + currentLine + "'");
				if (currentLine.startsWith("WINDOW "))
				{
					frame = readChatFrameWindow(buffer, index);
					if (frame == null)
						continue;
					Interface.getChatFrameMgr().addChatFrame(frame);
					++index;
				}
			}
			printFrameDebug();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void printFrameDebug()
	{
		for (int i = 0; i < Interface.getChatFrameMgr().getChatFrameList().size(); ++i)
		{
			ChatFrame frame = Interface.getChatFrameMgr().getChatFrameList().get(i);
			System.out.println("Frame: name: '" + frame.getName() + "', width: " + frame.getWidth() + ", height: " + frame.getHeight() + ", x: " + frame.getX() + ", y: " + frame.getY());
			for (int j = 0; j < frame.getTabList().size(); ++j)
			{
				ChatFrameTab tab = frame.getTabList().get(j);
				System.out.print("\tTab: name: '" + tab.getName() + "', allowedType: ");
				for (MessageType type : tab.getAllowedMessageType())
					System.out.print(type + ", ");
				System.out.println("\n");
			}
		}
	}
	
	private static ChatFrame readChatFrameWindow(BufferedReader buffer, int windowIndex)
	{
		try
		{
			ArrayList<ChatFrameTab> tabList = new ArrayList<ChatFrameTab>();
			String currentLine;
			int width = 0;
			int height = 0;
			int x = 0;
			int y = 0;
			int tabIndex = 1;
			boolean isLocked = true;
			System.out.println("Read ChatFrameWindow");
			while ((currentLine = buffer.readLine()) != null)
			{
				currentLine = currentLine.trim();
				if (currentLine.startsWith("WIDTH "))
				{
					width = parseChatFrameWindowValue(currentLine, ChatFrame.DEFAULT_WIDTH, ChatFrame.MIN_WIDTH);
				}
				else if (currentLine.startsWith("HEIGHT "))
				{
					height = parseChatFrameWindowValue(currentLine, ChatFrame.DEFAULT_HEIGHT, ChatFrame.MIN_HEIGHT);
				}
				else if (currentLine.startsWith("X "))
				{
					x = parseChatFrameWindowValue(currentLine, ChatFrame.DEFAULT_X, 0);	
				}
				else if (currentLine.startsWith("Y "))
				{
					y = parseChatFrameWindowValue(currentLine, ChatFrame.DEFAULT_Y, 0);	
				}
				else if (currentLine.startsWith("LOCKED "))
				{
					String[] result = currentLine.split(" ");
					if (result.length == 2 && result[1].length() == 1)
						isLocked = readBoolean(result[1].charAt(0));						
				}
				else if (currentLine.startsWith("TAB "))
				{
					ChatFrameTab tab = readChatFrameTab(buffer, windowIndex, tabIndex);
					if (tab == null)
						continue;
					tabList.add(tab);
					++tabIndex;
				}
				else if (currentLine.startsWith("END"))
					break;
			}
			if (width == 0)
				width = ChatFrame.MIN_WIDTH;
			if (height == 0)
				height = ChatFrame.MIN_HEIGHT;
			ChatFrame frame = new ChatFrame("CHAT_FRAME" + windowIndex, x, y, width, height, isLocked);
			for (int i = 0; i < tabList.size(); ++i)
			{
				tabList.get(i).setParentFrame(frame);
				frame.addChatFrameTab(tabList.get(i));
			}
			return (frame);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (null);
	}
	
	private static int parseChatFrameWindowValue(String line, int defaultValue, int minValue)
	{
		String result[] = line.split(" ");
		if (result.length < 2 || !StringUtils.isInteger(result[1]))
			return (defaultValue);
		int value = Integer.parseInt(result[1]);
		if (value < minValue)
			value = minValue;
		return (value);
	}
	
	private static ChatFrameTab readChatFrameTab(BufferedReader buffer, int windowIndex, int tabIndex)
	{
		try
		{
			String currentLine = null;
			String name = null;
			ArrayList<MessageType> messageTypeList = null;
			System.out.println("Read ChatFrameTab");
			while ((currentLine = buffer.readLine()) != null)
			{
				currentLine = currentLine.trim();
				if (currentLine.startsWith("NAME "))
				{
					currentLine = currentLine.trim();
					int index = StringUtils.findNextNonSpaceChar(currentLine, 5);
					if (index == -1)
						name = "default_name";
					else
						name = currentLine.substring(index);
				}
				else if (currentLine.startsWith("END"))
				{
					break;
				}
				else if (currentLine.startsWith("MESSAGES"))
				{
					messageTypeList = readChatFrameTabAllowedMessageType(buffer);
				}
			}
			ChatFrameTab tab = new ChatFrameTab(null, "CHAT_FRAME" + windowIndex + "TAB" + tabIndex, name);
			if (messageTypeList != null)
				for (int i = 0; i < messageTypeList.size(); ++i)
				{
					tab.addAcceptedMessageType(messageTypeList.get(i));
					System.out.println("Debug type: " + messageTypeList.get(i));
				}
			return (tab);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (null);
	}
	
	private static ArrayList<MessageType> readChatFrameTabAllowedMessageType(BufferedReader buffer)
	{
		try
		{
			String currentLine = null;
			ArrayList<MessageType> typeList = new ArrayList<MessageType>();
			System.out.println("Read ChatFrameTabAllowedMessageType");
			while ((currentLine = buffer.readLine()) != null)
			{
				currentLine = currentLine.trim();
				if (currentLine.startsWith("END"))
				{
					break;
				}
				MessageType type = MessageType.getMessageType(currentLine);
				if (type != null)
					typeList.add(type);
				System.out.println("CurrentLine: '" + currentLine + "', type: " + type);
			}
			return (typeList);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (null);
	}
	
	static int parseColor(String value, MessageType type)
	{
		String[] list = value.split(" ");
		if (list.length == 4)
		{
			type.setColor(new Color(Integer.parseInt(list[1]), Integer.parseInt(list[2]), Integer.parseInt(list[3])));
			return (0);
		}
		return (-1);
	}
	
	static int writeBoolean(boolean we)
	{
		return (we ? 1 : 0);
	}
	
	static boolean readBoolean(char c)
	{
		return (c == '1' ? true : false);
	}
}
