package com.mideas.rpg.v2.hud;

import java.sql.SQLException;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.utils.Colors;

public class LogChat {
	
	private static String statusText = "";
	private static String statusText2 = "";
	private static String statusText3 = "";
	
	public static void draw() {
	}
	
	public static void drawStatus() { // player2
		int x = 40;
		int y = -190; 
		FontManager.get("FRIZQT", 16).drawStringShadow(x, Display.getHeight()+y, statusText2, Colors.WHITE, Colors.BLACK, 1, 1, 1); 
	}
	
	public static void drawStatus2() { // player1
		int x = 40;
		int y = -230; 
		FontManager.get("FRIZQT", 16).drawStringShadow(x, Display.getHeight()+y, statusText, Colors.WHITE, Colors.BLACK, 1, 1, 1);
	}
	
	public static void drawStatus3() {
		int x = 40;
		int y = -270; 
		FontManager.get("FRIZQT", 16).drawStringShadow(x, Display.getHeight()+y, statusText3, Colors.WHITE, Colors.BLACK, 1, 1, 1);
	}
	
	public static void setStatusText(String text) {
		try {
			statusText = text;
			JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO chatlog (date, message) VALUES (?, ?)");
			statement.putLong(System.nanoTime());
			statement.putString(statusText);
			statement.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setStatusText2(String text) {
		try {
			statusText2 = text;
			JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO chatlog (date, message) VALUES (?, ?)");
			statement.putLong(System.nanoTime());
			statement.putString(statusText2);
			statement.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setStatusText3(String text) {
		try {
			statusText3 = text;
			JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO chatlog (date, message) VALUES (?, ?)");
			statement.putLong(System.nanoTime());
			statement.putString(statusText3);
			statement.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
