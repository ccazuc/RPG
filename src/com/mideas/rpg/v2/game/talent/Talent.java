package com.mideas.rpg.v2.game.talent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.hud.ClassSelectFrame;
import com.mideas.rpg.v2.utils.Draw;

public class Talent {
	private static int x = -480;
	private static int y = -400;

	public static void draw() {
		if(Mideas.joueur1().getClasse() == "Guerrier") {
			Draw.drawQuad(Sprites.warrior_talent_tree, Display.getWidth()/2+getX(), Display.getHeight()/2+y);
			GuerrierTalent.draw();
		}
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		if(Mideas.joueur1().getClasse() == "Guerrier") {
			GuerrierTalent.mouseEvent();
		}
		return false;
	}

	public static int getX() {
		return x;
	}
	
	public static int getY() {
		return y;
	}
	
	public static void getTalent() throws FileNotFoundException {
		BufferedReader br = null;
		int i = 0;
		int id;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(ClassSelectFrame.talentTxt()));
			while ((sCurrentLine = br.readLine()) != null) {
				id = Integer.parseInt(sCurrentLine);	
				if(i == 0) {
					GuerrierTalent.heroicStrikeTalent = id;
					GuerrierTalent.numberArmsTalent+= id;
				}
				else if( i == 1) {
					GuerrierTalent.deflectionTalent = id;
					GuerrierTalent.numberArmsTalent+= id;
				}
				else if( i == 2) {
					GuerrierTalent.improvedRend = id;
					GuerrierTalent.numberArmsTalent+= id;
				}
				else if( i == 3) {
					GuerrierTalent.improvedCharge = id;
					GuerrierTalent.numberArmsTalent+= id;
				}
				else if( i == 4) {
					GuerrierTalent.ironWill = id;
					GuerrierTalent.numberArmsTalent+= id;
				}
				else if( i == 5) {
					GuerrierTalent.improvedThunderClap = id;
					GuerrierTalent.numberArmsTalent+= id;
				}
				/*else if( i == 6) {
					Mideas.joueur1.setGloves(getGloves(id));
				}
				else if( i == 7) {
					Mideas.joueur1.setBelt(getBelt(id));
				}
				else if( i == 8) {
					Mideas.joueur1.setLeggings(getLeggings(id));
				}
				else if( i == 9) {
					Mideas.joueur1.setBoots(getBoots(id));
				}
				else if( i == 10) {
					Mideas.joueur1.setRing(getRing(id));
				}
				else if( i == 11) {
					Mideas.joueur1.setRing2(getRing2(id));
				}
				else if( i == 12) {
					Mideas.joueur1.setTrinket(getTrinket(id));
				}
				else if( i == 13) {
					Mideas.joueur1.setTrinket2(getTrinket2(id));
				}
				else if( i == 14) {
					Mideas.joueur1.setMainHand(getMainHand(id));
				}
				else if( i == 15) {
					Mideas.joueur1.setOffHand(getOffHand(id));
				}
				else if( i == 16) {
					Mideas.joueur1.setRanged(getRanged(id));
				}*/
				i++;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void setTalent() throws FileNotFoundException {
		try {
			String content = "";
			File file = new File(ClassSelectFrame.talentTxt());
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				if(GuerrierTalent.heroicStrikeTalent > 0) {
					content+= GuerrierTalent.heroicStrikeTalent+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(GuerrierTalent.deflectionTalent > 0) {
					content+= GuerrierTalent.deflectionTalent+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(GuerrierTalent.improvedRend> 0) {
					content+= GuerrierTalent.improvedRend+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				GuerrierTalent.numberFirstArms = GuerrierTalent.heroicStrikeTalent+GuerrierTalent.deflectionTalent+GuerrierTalent.improvedRend;
				if(GuerrierTalent.numberArmsTalent >= 5) { 
					if(GuerrierTalent.improvedCharge > 0) {
						content+= GuerrierTalent.improvedCharge+"\r\n";
					}
					else {
						content+= "0\r\n";
					}
					if(GuerrierTalent.ironWill > 0) {
						content+= GuerrierTalent.ironWill+"\r\n";
					}
					else {
						content+= "0\r\n";
					}
					if(GuerrierTalent.improvedThunderClap > 0) {
						content+= GuerrierTalent.improvedThunderClap+"\r\n";
					}
					else {
						content+= "0\r\n";
					}
				}
				/*if(Mideas.joueur1.getShoulders() != null) {
					content+= Mideas.joueur1().getShoulders().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getBack() != null) {
					content+= Mideas.joueur1().getBack().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getChest() != null) {
					content = content+Mideas.joueur1().getChest().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getWrists() != null) {
					content = content+Mideas.joueur1().getWrists().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getGloves() != null) {
					content = content+Mideas.joueur1().getGloves().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getBelt() != null) {
					content = content+Mideas.joueur1().getBelt().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getLeggings() != null) {
					content = content+Mideas.joueur1().getLeggings().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getBoots() != null) {
					content = content+Mideas.joueur1().getBoots().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getRing() != null) {
					content = content+Mideas.joueur1().getRing().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getRing2() != null) {
					content = content+Mideas.joueur1().getRing2().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getTrinket() != null) {
					content = content+Mideas.joueur1().getTrinket().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getTrinket2() != null) {
					content = content+Mideas.joueur1().getTrinket2().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getMainHand() != null) {
					content = content+Mideas.joueur1().getMainHand().getId()+"\r\n";
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getOffHand() != null) {
					content = content+Mideas.joueur1().getOffHand().getId()+"\r\n";;
				}
				else {
					content+= "0\r\n";
				}
				if(Mideas.joueur1.getRanged() != null) {
					content = content+Mideas.joueur1().getRanged().getId()+"\r\n";;
				}
				else {
					content+= "0\r\n";
				}*/
				bw.write(content);
				bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
