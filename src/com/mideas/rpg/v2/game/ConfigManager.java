package com.mideas.rpg.v2.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.mideas.rpg.v2.hud.LoginScreen;

public class ConfigManager {
	
	private final static String FILE_NAME = "config.wtf";
	private final static String REMEMBER_ACCOUNT_NAME = "remember_account_name ";
	private final static String ACCOUNT_NAME = "account_name ";
	private final static int NUMBER_LINE = 2;
	
	public static void saveConfig() {
		//BufferedReader br = null;
		try {
			StringBuilder content = new StringBuilder();
			//String sCurrentLine;
			int i = 0;
			File file = new File(FILE_NAME);
			if (!file.exists()) {
				file.createNewFile();
			}
			//br = new BufferedReader(new FileReader(FILE_NAME));
			while(i < NUMBER_LINE) {
				if(i == 0) {
					content.append(writeRememberAccountName());
				}
				else if(i == 1) {
					content.append(writeAccountName());
				}
				//sCurrentLine = br.readLine();
				i++;
			}
			//FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(content.toString());
			bw.close();
			//br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadConfig() {
		BufferedReader br = null;
		try {
			int i = 0;
			String currentLine;
			br = new BufferedReader(new FileReader(FILE_NAME));
			while ((currentLine = br.readLine()) != null && i < NUMBER_LINE) {
				if(i == 0) {
					readRememberAccountName(currentLine);
				}
				else if(i == 1) {
					readAccountName(currentLine);
				}
				i++;
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String writeRememberAccountName() {
		return REMEMBER_ACCOUNT_NAME+'"'+writeBoolean(LoginScreen.getRememberAccountName())+'"'+System.lineSeparator();
	}
	
	private static void readRememberAccountName(String config) {
		int i = 0;
		while(i < config.length()) {
			if(config.charAt(i) == '"') {
				LoginScreen.setRememberAccountName(readBoolean(config.charAt(i+1)));
				return;
			}
			i++;
		}
	}
	
	private static String writeAccountName() {
		return LoginScreen.getRememberAccountName() ? ACCOUNT_NAME+'"'+LoginScreen.getAccountName()+'"'+System.lineSeparator() : ACCOUNT_NAME+"\"\""+System.lineSeparator();
	}
	
	private static void readAccountName(String config) {
		int i = 0;
		while(i < config.length()) {
			if(config.charAt(i) == '"') {
				i++;
				int j = i;
				while(j < config.length()) {
					if(config.charAt(j) == '"') {
						LoginScreen.setAccountName(config.substring(i, j));
						return;
					}
					j++;
				}
			}
			i++;
		}
	}
	
	private static int writeBoolean(boolean we) {
		return we ? 1 : 0;
	}
	
	private static boolean readBoolean(char c) {
		return c == '1' ? true : false;
	}
}
