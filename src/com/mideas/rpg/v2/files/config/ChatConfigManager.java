package com.mideas.rpg.v2.files.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.chat.channel.ChannelMgr;
import com.mideas.rpg.v2.chat.channel.ChatChannel;
import com.mideas.rpg.v2.command.chat.CommandChannel;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatConfigManager {

	private final static boolean DEBUG_READ = false;
	private final static boolean DEBUG_WRITE = false;
	private final static LinkedHashMap<String, ConfigList> configMap = new LinkedHashMap<String, ConfigList>();
	public final static String FILE_NAME = "chat-config.wtf";
	private final static ConfigList COLORS = new ConfigList("COLORS", ChatConfigType.COLORS);
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
	private final static ConfigList CHANNELS = new ConfigList("CHANNELS", ChatConfigType.CHANNELS);
	
	public static void initConfigMap() {
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
	}
	
	public static void saveConfig() {
		if(Mideas.joueur1() == null) {
			return;
		}
		try {
			StringBuilder content = new StringBuilder();
			File folder = new File("WTF/Account/"+Mideas.getAccountName()+'/'+ConnectionManager.getWorldServer().getRealmName()+'/'+Mideas.joueur1().getName());
			if(!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File("WTF/Account/"+Mideas.getAccountName()+'/'+ConnectionManager.getWorldServer().getRealmName()+'/'+Mideas.joueur1().getName()+'/'+FILE_NAME);
			if(DEBUG_WRITE)
				System.out.println(file.mkdirs()+" "+("WTF/"+ConnectionManager.getWorldServer().getRealmName()+'/'+FILE_NAME));
			if (!file.exists()) {
				file.createNewFile();
			}
			for(ConfigList configList : configMap.values()) {
				ChatConfigType type = configList.getType();
				content.append(configList.getName()+System.lineSeparator());
				int i = 0;
				if(type == ChatConfigType.CHANNELS) {
					content.append(writeChannels());
				}
				else {
					while(i < configList.size()) {
						if(type == ChatConfigType.COLORS) {
							if(DEBUG_WRITE) 
								System.out.println(configList.get(i).writeChatColor());
							content.append(configList.get(i).writeChatColor()+System.lineSeparator());
						}
						i++;
					}
				}
				content.append("END"+System.lineSeparator()+System.lineSeparator());
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(content.toString());
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadConfig() {
		BufferedReader buffer = null;
		try {
			int i = 0;
			int j = 0;
			String currentLine;
			ConfigList list;
			File folder = new File("WTF/Account/"+Mideas.getAccountName()+'/'+ConnectionManager.getWorldServer().getRealmName()+'/'+Mideas.joueur1().getName()+'/');
			if(!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File("WTF/Account/"+Mideas.getAccountName()+'/'+ConnectionManager.getWorldServer().getRealmName()+'/'+Mideas.joueur1().getName()+'/'+FILE_NAME);
			if(!file.exists()) {
				file.createNewFile();
			}
			buffer = new BufferedReader(new FileReader("WTF/Account/"+Mideas.getAccountName()+'/'+ConnectionManager.getWorldServer().getRealmName()+'/'+Mideas.joueur1().getName()+'/'+FILE_NAME));
			while((currentLine = buffer.readLine()) != null) {
				list = configMap.get(currentLine);
				if(list == null) {
					if(currentLine.length() != 0)
						System.out.println("Error load "+FILE_NAME+" on line "+j+" line value: \""+currentLine+"\"");
					continue;
				}
				while((currentLine = buffer.readLine()) != null) {
					i = 0;
					if(currentLine.equals("END")) {
						break;
					}
					while(i < currentLine.length() && currentLine.charAt(i) != ' ') {
						i++;
					}
					String tmp = currentLine.substring(0, i);
					if(list.contains(tmp)) {
						if(DEBUG_READ)
							System.out.println(tmp);
						if(list.getType() == ChatConfigType.COLORS) {
							list.get(currentLine.substring(0, i)).readChatColor(currentLine);
						}
					}
					else if(list.getType() == ChatConfigType.CHANNELS) {
						readChannel(currentLine);
					}
					else {
						System.out.println("Error load "+FILE_NAME+" on line "+j+" line value: \""+currentLine+"\".");
					}
					j++;
				}
				j++;
			}
			buffer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int readChannel(String value) {
		String[] list = value.split(" ");
		if(list.length < 2) {
			return -1;
		}
		String channelName = list[0];
		String channelValueString = list[1];
		if(!StringUtils.isInteger(channelValueString)) {
			System.out.println(ChatConfigManager.FILE_NAME+" error on load : channelValue isn't an integer : "+channelValueString);
			return -1;
		}
		int channelValue = Integer.parseInt(channelValueString);
		if(list.length >= 3) {
			CommandChannel.joinChannel(channelName, channelValue, list[2]);
		}
		else {
			CommandChannel.joinChannel(channelName, channelValue);
		}
		return 0;
 	}
	
	private static String writeChannels() {
		StringBuilder builder = new StringBuilder();
		for(ChatChannel channel : ChannelMgr.getChannelMap().values()) {
			builder.append(channel.getName()+" "+channel.getValue()+" "+channel.getPassword()+System.lineSeparator());
		}
		builder.append(System.lineSeparator());
		return builder.toString();
	}
	
	static int parseColor(String value, MessageType type) {
		String[] list = value.split(" ");
		if(list.length == 4) {
			type.setColor(new Color(Integer.parseInt(list[1]), Integer.parseInt(list[2]), Integer.parseInt(list[3])));
			return 0;
		}
		return -1;
	}
	
	static int writeBoolean(boolean we) {
		return we ? 1 : 0;
	}
	
	static boolean readBoolean(char c) {
		return c == '1' ? true : false;
	}
}
