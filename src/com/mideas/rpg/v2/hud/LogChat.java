package com.mideas.rpg.v2.hud;

import java.sql.SQLException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.utils.Draw;

public class LogChat {
	
	private static String statusText = "";
	private static String statusText2 = "";
	private static String statusText3 = "";
	
	public static void draw() {
	}
	
	public static void drawStatus() { // player2
		int x = 40;
		int y = -190; 
		TTF2.font4.drawStringShadow(x, Display.getHeight()+y, statusText2, Color.white, Color.black, 1, 1, 1); 
	}
	
	public static void drawStatus2() { // player1
		int x = 40;
		int y = -230; 
		TTF2.font4.drawStringShadow(x, Display.getHeight()+y, statusText, Color.white, Color.black, 1, 1, 1);
	}
	
	public static void drawStatus3() {
		int x = 40;
		int y = -270; 
		TTF2.font4.drawStringShadow(x, Display.getHeight()+y, statusText3, Color.white, Color.black, 1, 1, 1);
	}
	
	public static void setStatusText(String text) throws SQLException {
		statusText = text;
		JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO chatlog (date, message) VALUES (?, ?)");
		statement.putLong(System.nanoTime());
		statement.putString(statusText);
		statement.execute();
	}
	
	public static void setStatusText2(String text) throws SQLException {
		statusText2 = text;
		JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO chatlog (date, message) VALUES (?, ?)");
		statement.putLong(System.nanoTime());
		statement.putString(statusText2);
		statement.execute();
	}
	
	public static void setStatusText3(String text) throws SQLException {
		statusText3 = text;
		JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO chatlog (date, message) VALUES (?, ?)");
		statement.putLong(System.nanoTime());
		statement.putString(statusText3);
		statement.execute();
	}
}
