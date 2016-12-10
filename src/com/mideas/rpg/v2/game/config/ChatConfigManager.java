package com.mideas.rpg.v2.game.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.utils.Color;

public class ChatConfigManager {

	private final static HashMap<String, ConfigList> configMap = new HashMap<String, ConfigList>();
	public final static String FILE_NAME = "chat-config.wtf";
	private final static ConfigList COLORS = new ConfigList("COLORS");
	private final static Config SAY = new Config("SAY", MessageType.SAY);
	private final static Config PARTY = new Config("PARTY", MessageType.PARTY);
	
	public static void initConfigMap() {
		COLORS.add(SAY);
		COLORS.add(PARTY);
		configMap.put(COLORS.getName(), COLORS);
	}
	
	public static void saveConfig() {
		try {
			StringBuilder content = new StringBuilder();
			File file = new File(FILE_NAME);
			if (!file.exists()) {
				file.createNewFile();
			}
			for(ConfigList configList : configMap.values()) {
				content.append(configList.getName()+System.lineSeparator());
				int i = 0;
				while(i < configList.size()) {
					if(configList.getName().equals(COLORS.getName())) {
						content.append(configList.get(i).writeChatColor()+System.lineSeparator());
					}
					i++;
				}
				content.append("END"+System.lineSeparator());
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
			buffer = new BufferedReader(new FileReader(FILE_NAME));
			while((currentLine = buffer.readLine()) != null) {
				list = configMap.get(currentLine);
				if(list == null) {
					System.out.println("Error load "+FILE_NAME+" on line "+j+" line value: \""+currentLine+"\"");
					continue;
				}
				while((currentLine = buffer.readLine()) != null) {
					if(currentLine.equals("END")) {
						break;
					}
					while(i < currentLine.length() && currentLine.charAt(i) != ' ') {
						i++;
					}
					String tmp = currentLine.substring(0, i);
					if(list.contains(tmp)) {
						if(list.getName().equals(COLORS.getName())) {
							list.get(currentLine.substring(0, i)).readChatColor(currentLine);
						}
					}
					else {
						System.out.println("Error load "+FILE_NAME+" on line "+j+" line value: \""+currentLine+"\"");
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
	
	static int parseColor(String value, MessageType type) {
		String[] list = value.split(" ");
		if(list.length == 4) {
			type.setColor(new Color(Integer.valueOf(list[1]), Integer.valueOf(list[2]), Integer.valueOf(list[3])));
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
