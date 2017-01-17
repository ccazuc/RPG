package com.mideas.rpg.v2.utils;

public class StringUtils {
	
	public final static String[] value = new String[100];
	
	public static void initValues() {
		int i = 0;
		while(i < value.length) {
			value[i] = Integer.toString(i);
			i++;
		}
	}
	
	public static String removeSpaces(String str) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		while(i < str.length()) {
			if(i < str.length()-1 && str.charAt(i) == ' ' && str.charAt(i+1) == ' ') {
				i++;
				continue;
			}
			if(i == str.length() && str.charAt(i) == ' ') {
				break;
			}
			builder.append(str.charAt(i));
			i++;
		}
		return builder.toString();
	}
	
	public static boolean isInteger(String str) {
		int i = -1;
		while(++i < str.length()) {
			char c = str.charAt(i);
			if(c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isInteger(char c) {
		return c >= '0' && c <= '9';
	}
}
