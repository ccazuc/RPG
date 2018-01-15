package com.mideas.rpg.v2.utils;

public class StringUtils {
	
	public final static String[] value = new String[100];
	public final static long MS_IN_A_YEAR = 31536000000l;
	public final static long MS_IN_A_MONTH = 1036800000l;
	public final static long MS_IN_A_WEEK = 604800000l;
	public final static long MS_IN_A_DAY = 86400000l;
	public final static long MS_IN_AN_HOUR = 3600000l;
	public final static long MS_IN_A_MINUTE = 60000l;
	
	public static void initValues() {
		int i = value.length;
		while(--i >= 0)
			value[i] = Integer.toString(i);
	}

	public static String formatPlayerName(String str) {
		if(str.length() == 0)
			return str;
		final char[] table = new char[str.length()];
		int i = table.length;
		table[0] = toUpperCase(str.charAt(0));
		char tmp;
		while(--i >= 1) {
			tmp = str.charAt(i);
			if(tmp >= 'A' && tmp <= 'Z')
				tmp+= 32;
			table[i] = tmp;
		}
		return new String(table);
	}
	
	public static char toUpperCase(char c) {
		return c >= 'a' && c <= 'z' ? (char)(c-32) : c;
	}
	
	public static int charToInt(char c) {
		return c - 48;
	}
	
	public static String convertTimeToStringSimple(long delta) {
		String result = null;
		if(delta >= MS_IN_A_YEAR) {
			result = (delta/MS_IN_A_YEAR)+" year";
			if(delta/MS_IN_A_YEAR > 1)
				result += "s";
		}
		else if(delta >= MS_IN_A_MONTH) {
			result = (delta/MS_IN_A_MONTH)+" month";
			if(delta/MS_IN_A_MONTH > 1)
				result += "s";
		}
		else if(delta >= MS_IN_A_WEEK) {
			result = (delta/MS_IN_A_WEEK)+" week";
			if(delta/MS_IN_A_WEEK > 1)
				result += "s";
		}
		else if(delta >= MS_IN_A_DAY) {
			result = (delta/MS_IN_A_DAY)+" day";
			if(delta/MS_IN_A_DAY > 1)
				result += "s";
		}
		else if(delta >= MS_IN_AN_HOUR) {
			result = (delta/MS_IN_AN_HOUR)+" hour";
			if(delta/MS_IN_AN_HOUR > 1)
				result += "s";
		}
		else if(delta >= MS_IN_A_MINUTE) {
			result = (delta/MS_IN_A_MINUTE)+" minute";
			if(delta/MS_IN_A_MINUTE > 1)
				result += "s";
		}
		else
			result = "Less than a minute ago";
		return result;
	}
	
	public static String convertTimeToString(long delta) {
		StringBuilder result = new StringBuilder();
		boolean written = false;
		if (delta >= MS_IN_A_YEAR)
		{
			result.append(delta / MS_IN_A_YEAR).append(" year");
			if(delta / MS_IN_A_YEAR > 1)
				result.append('s');
			delta %= MS_IN_A_YEAR;
			written = true;
		}
		if (delta >= MS_IN_A_MONTH)
		{
			if (written)
				result.append(", ");
			result.append(delta / MS_IN_A_MONTH).append(" month");
			if(delta / MS_IN_A_MONTH > 1)
				result.append('s');
			delta %= MS_IN_A_MONTH;
			written = true;
		}
		if (delta >= MS_IN_A_WEEK)
		{
			if (written)
				result.append(", ");
			result.append(delta / MS_IN_A_WEEK).append(" week");
			if(delta / MS_IN_A_WEEK > 1)
				result.append('s');
			delta %= MS_IN_A_WEEK;
			written = true;
		}
		if (delta >= MS_IN_A_DAY)
		{
			if (written)
				result.append(", ");
			result.append(delta / MS_IN_A_DAY).append(" day");
			if(delta / MS_IN_A_DAY > 1)
				result.append('s');
			delta %= MS_IN_A_DAY;
			written = true;
		}
		if (delta >= MS_IN_AN_HOUR)
		{
			if (written)
				result.append(", ");
			result.append(delta / MS_IN_AN_HOUR).append(" hour");
			if(delta / MS_IN_AN_HOUR > 1)
				result.append('s');
			delta %= MS_IN_AN_HOUR;
			written = true;
		}
		if (delta >= MS_IN_A_MINUTE)
		{
			if (written)
				result.append(", ");
			result.append(delta / MS_IN_A_MINUTE).append(" minute");
			if(delta / MS_IN_A_MINUTE > 1)
				result.append('s');
			delta %= MS_IN_A_MINUTE;
			written = true;
		}
		if (delta >= 1000)
		{
			if (written)
				result.append(", ");
			result.append(delta / 1000).append(" second");
			if (delta / 1000> 1)
				result.append('s');
		}
		return (result.toString());
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
	
	public static String toUpperCase(String str) {
		if(str.length() == 0) {
			return str;
		}
		final char[] table = new char[str.length()];
		int i = table.length;
		char c;
		while(--i >= 0) {
			c = str.charAt(i);
			if(c >= 'a' && c <= 'z') {
				c-= 32;
			}
			table[i] = c;
		}
		return new String(table);
	}
	
	public static String toLowerCase(String str) {
		if(str.length() == 0) {
			return str;
		}
		final char[] table = new char[str.length()];
		int i = table.length;
		char c;
		while(--i >= 0) {
			c = str.charAt(i);
			if(c >= 'A' && c <= 'Z') {
				c+= 32;
			}
			table[i] = c;
		}
		return new String(table);
	}
	
	public static boolean isInteger(String str) {
		if(str.length() == 0) {
			return false;
		}
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
