package com.mideas.rpg.v2.game.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.mideas.rpg.v2.hud.LoginScreen;

public class ConfigManager {
	
	private final static HashMap<String, Config> configMap = new HashMap<String, Config>();
	public final static String FILE_NAME = "config.wtf";
	private final static Config REMEMBER_ACCOUNT_NAME = new Config("remember_account_name") {
		
		@Override
		public void read(String value, int i) {
			while(i < value.length()) {
				if(value.charAt(i) == '"') {
					LoginScreen.setRememberAccountName(readBoolean(value.charAt(i+1)));
					return;
				}
				i++;
			}
		}
		
		@Override
		public String write() {
			return this.name+" \""+writeBoolean(LoginScreen.getRememberAccountName())+'"';
		}
	};
	private final static Config ACCOUNT_NAME = new Config("account_name") {
		
		@Override
		public void  read(String value, int i) {
			while(i < value.length()) {
				if(value.charAt(i) != '"') {
					i++;
					continue;
				}
				i++;
				int j = i;
				while(j < value.length()) {
					if(value.charAt(j) == '"') {
						if(!LoginScreen.getRememberAccountName()) {
							return;
						}
						LoginScreen.setAccountName(value.substring(i, j));
						return;
					}
					j++;
				}
			}
		}
		
		@Override
		public String write() {
			return LoginScreen.getRememberAccountName() ? this.name+" \""+LoginScreen.getAccountName()+'"' : this.name+" \"\"";
		}
	};
	
	public static void initConfigMap() {
		configMap.put(REMEMBER_ACCOUNT_NAME.getName(), REMEMBER_ACCOUNT_NAME);
		configMap.put(ACCOUNT_NAME.getName(), ACCOUNT_NAME);
	}
	
	public static void saveConfig() {
		try {
			StringBuilder content = new StringBuilder();
			File file = new File(FILE_NAME);
			if (!file.exists()) {
				file.createNewFile();
			}
			for(Config config : configMap.values()) {
				content.append(config.write()+System.lineSeparator());
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
			buffer = new BufferedReader(new FileReader(FILE_NAME));
			while((currentLine = buffer.readLine()) != null) {
				i = 0;
				while(i < currentLine.length()) {
					if(currentLine.charAt(i) != ' ') {
						i++;
						continue;
					}
					String tmp = currentLine.substring(0, i);
					if(configMap.containsKey(tmp)) {
						configMap.get(tmp).read(currentLine, i);
					}
					else {
						System.out.println("Error config load on line "+j+" for value \""+tmp+"\"");
					}
					break;
				}
				j++;
			}
			buffer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static int writeBoolean(boolean we) {
		return we ? 1 : 0;
	}
	
	static boolean readBoolean(char c) {
		return c == '1' ? true : false;
	}
}
