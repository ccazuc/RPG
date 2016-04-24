package com.mideas.rpg.v2.game.talent;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.utils.Draw;

public class GuerrierTalent {

	public static boolean hoverLeft1;
	public static boolean hoverLeft2;
	public static boolean hoverLeft3;
	public static boolean hoverLeft4;
	public static boolean hoverLeft5;
	public static boolean hoverLeft6;
	public static boolean hoverLeft7;
	public static boolean hoverLeft8;
	public static boolean hoverLeft9;
	public static boolean hoverLeft10;
	public static int numberArmsTalent;
	private static int numberFuryTalent;
	private static int numberProtectionTalent;
	private static int numberTalent;
	public static int heroicStrikeTalent;
	public static int deflectionTalent;
	public static int improvedRend;
	public static int improvedCharge;
	public static int ironWill;
	public static int improvedThunderClap;
	public static int numberFirstArms;
	private static Color bgColor = new Color(0, 0, 0,.8f); 
	
	public static void draw() {
		if(heroicStrikeTalent == 1) {
			Draw.drawQuad(Sprites.un_green_talent, Display.getWidth()/2+Talent.getX()+24+32, Display.getHeight()/2+Talent.getY()+53);
		}
		else if(heroicStrikeTalent == 2) {
			Draw.drawQuad(Sprites.deux_green_talent, Display.getWidth()/2+Talent.getX()+24+32, Display.getHeight()/2+Talent.getY()+54);
		}
		else if(heroicStrikeTalent == 3) {
			Draw.drawQuad(Sprites.trois_yellow_square_talent, Display.getWidth()/2+Talent.getX()+20, Display.getHeight()/2+Talent.getY()+5);
		}
		//drawTalent(heroicStrikeTalent, 3, Talent.getX()+24+32, Talent.getY()+53, Talent.getX()+20, Talent.getY()+5);
		Draw.drawQuad(Sprites.cursor, -100, -100);
		if(deflectionTalent == 1) {
			Draw.drawQuad(Sprites.un_green_talent, Display.getWidth()/2+Talent.getX()+24+97, Display.getHeight()/2+Talent.getY()+53);
		}
		else if(deflectionTalent == 2) {
			Draw.drawQuad(Sprites.deux_green_talent, Display.getWidth()/2+Talent.getX()+24+97, Display.getHeight()/2+Talent.getY()+54);
		}
		else if(deflectionTalent == 3) {
			Draw.drawQuad(Sprites.trois_green_talent, Display.getWidth()/2+Talent.getX()+24+97, Display.getHeight()/2+Talent.getY()+54);
		}
		else if(deflectionTalent == 4) {
			Draw.drawQuad(Sprites.quatre_green_talent, Display.getWidth()/2+Talent.getX()+24+97, Display.getHeight()/2+Talent.getY()+53);
		}
		else if(deflectionTalent == 5) {
			Draw.drawQuad(Sprites.cinq_yellow_square_talent, Display.getWidth()/2+Talent.getX()+85, Display.getHeight()/2+Talent.getY()+7);
		}
		//drawTalent(deflectionTalent, 5, Talent.getX()+24+97, Talent.getY()+53, Talent.getX()+85, Talent.getY()+7);
		Draw.drawQuad(Sprites.cursor, -100, -100);
		if(improvedRend == 1) {
			Draw.drawQuad(Sprites.un_green_talent, Display.getWidth()/2+Talent.getX()+24+162, Display.getHeight()/2+Talent.getY()+53);
		}
		else if(improvedRend == 2) {
			Draw.drawQuad(Sprites.deux_green_talent, Display.getWidth()/2+Talent.getX()+24+162, Display.getHeight()/2+Talent.getY()+54);
		}
		else if(improvedRend == 3) {
			Draw.drawQuad(Sprites.trois_yellow_square_talent, Display.getWidth()/2+Talent.getX()+150, Display.getHeight()/2+Talent.getY()+5);
		}
		if(numberFirstArms >= 5) {
			Draw.drawQuad(Sprites.left_second_line_talent, Display.getWidth()/2+Talent.getX()+17, Display.getHeight()/2+Talent.getY()+67);
		}
		if(improvedCharge == 1) {
			Draw.drawQuad(Sprites.un_green_talent, Display.getWidth()/2+Talent.getX()+24+32, Display.getHeight()/2+Talent.getY()+53+65);
		}
		else if(improvedCharge == 2) {
			Draw.drawQuad(Sprites.deux_yellow_square_talent, Display.getWidth()/2+Talent.getX()+17, Display.getHeight()/2+Talent.getY()+53+18);
		}
		if(hoverLeft1) {
			Draw.drawColorQuad(Display.getWidth()/2+Talent.getX()+24+74, Display.getHeight()/2+Talent.getY()-80, 350, 100, bgColor);
			TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()-78, "Frappe h�ro�que am�lior�e", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()-65, "Rang "+heroicStrikeTalent+"/3", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()-43, "R�duit le coup en rage de votre technique", Color.yellow, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()-33, "frappe h�ro�que de 1 points.", Color.yellow, Color.black, 1, 1, 1);
			requiredPoint(numberArmsTalent, 0, heroicStrikeTalent, 5, 78, -50, 0, "");
		}
		else if(hoverLeft2) {
			Draw.drawColorQuad(Display.getWidth()/2+Talent.getX()+24+139, Display.getHeight()/2+Talent.getY()-80, 350, 100, bgColor);
			TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY()-78, "D�viation", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY()-65, "Rang "+deflectionTalent+"/5", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY()-43, "Augmente votre armure de 5", Color.yellow, Color.black, 1, 1, 1);
			requiredPoint(numberArmsTalent, 0, deflectionTalent, 5, 143, -50, 0, "");
		}
		else if(hoverLeft3) {
			Draw.drawColorQuad(Display.getWidth()/2+Talent.getX()+24+204, Display.getHeight()/2+Talent.getY()-80, 350, 100, bgColor);
			TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+208, Display.getHeight()/2+Talent.getY()-78, "Pourfendre am�lior�e", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+208, Display.getHeight()/2+Talent.getY()-65, "Rang "+improvedRend+"/3", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+208, Display.getHeight()/2+Talent.getY()-35, "Augmente de 25% les points de d�g�ts", Color.yellow, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+208, Display.getHeight()/2+Talent.getY()-25, "inflig�s par la technique Pourfendre.", Color.yellow, Color.black, 1, 1, 1);
			requiredPoint(numberArmsTalent, 0, improvedRend, 3, 208, -50, 0, "");
		}
		else if(hoverLeft4) {
			Draw.drawColorQuad(Display.getWidth()/2+Talent.getX()+24+74, Display.getHeight()/2+Talent.getY()-18, 350, 100, bgColor);
			TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()-15, "Charge am�lior�e", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY(), "Rang "+improvedCharge+"/2", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()+37, "Augmente la quantit� de Rage g�n�r�e par", Color.yellow, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+78, Display.getHeight()/2+Talent.getY()+49, "votre technique Charge de 3.", Color.yellow, Color.black, 1, 1, 1);
			requiredPoint(numberArmsTalent, 5, improvedCharge, 2, 78, 15, 65, "Requiert 5 points en Armes");
		}
		else if(hoverLeft5) {
			Draw.drawColorQuad(Display.getWidth()/2+Talent.getX()+24+139, Display.getHeight()/2+Talent.getY()-18, 350, 100, bgColor);
			TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY()-15, "Volont� de fer", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY(), "Rang "+ironWill+"/5", Color.white, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY()+37, "Augmente la quantit� de Rage g�n�r�e par", Color.yellow, Color.black, 1, 1, 1);
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+143, Display.getHeight()/2+Talent.getY()+49, "votre technique Charge de 3.", Color.yellow, Color.black, 1, 1, 1);
			requiredPoint(numberArmsTalent, 5, ironWill, 5, 143, 15, 65, "Requiert 5 points en Armes");
		}
		TTF2.itemName.drawStringShadow(Display.getWidth()/2+Talent.getX()+250, Display.getHeight()/2+160, String.valueOf(numberArmsTalent), Color.white, Color.black, 1, 1, 1);
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		hoverLeft1 = false;
		hoverLeft2 = false;
		hoverLeft3 = false;
		hoverLeft4 = false;
		hoverLeft5 = false;
		hoverLeft6 = false;
		hoverLeft7 = false;
		hoverLeft8 = false;
		hoverLeft9 = false;
		hoverLeft10 = false;
		if(Mideas.mouseX() >= Display.getWidth()/2+Talent.getX()+24 && Mideas.mouseX() <= Display.getWidth()/2+Talent.getX()+24+47  && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12 && Mideas.mouseY() <= Display.getHeight()/2+Talent.getY()+12+48) {
			hoverLeft1 = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+Talent.getX()+24+66 && Mideas.mouseX() <= Display.getWidth()/2+Talent.getX()+24+47+66 && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12 && Mideas.mouseY() <= Display.getHeight()/2+Talent.getY()+12+48) {
			hoverLeft2 = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+Talent.getX()+24+132 && Mideas.mouseX() <= Display.getWidth()/2+Talent.getX()+24+47+132 && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12 && Mideas.mouseY() <= Display.getHeight()/2+Talent.getY()+12+48) {
			hoverLeft3 = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+Talent.getX()+24 && Mideas.mouseX() <= Display.getWidth()/2+Talent.getX()+24+47 && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12+65 && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12+65 && Mideas.mouseY() <= Display.getHeight()/2+Talent.getY()+12+65+48) {
			hoverLeft4 = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+Talent.getX()+24+66 && Mideas.mouseX() <= Display.getWidth()/2+Talent.getX()+24+47+66 && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12+65 && Mideas.mouseY() <= Display.getHeight()/2+Talent.getY()+12+65+48) {
			hoverLeft5 = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+Talent.getX()+24+132 && Mideas.mouseX() <= Display.getWidth()/2+Talent.getX()+24+47+132 && Mideas.mouseY() >= Display.getHeight()/2+Talent.getY()+12+65 && Mideas.mouseY() <= Display.getHeight()/2+Talent.getY()+12+65+48) {
			hoverLeft6 = true;
		}
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(numberTalent < Mideas.getLevel()+1) {
					if(hoverLeft1 && heroicStrikeTalent < 3) {
						heroicStrikeTalent++;
						numberArmsTalent++;
						numberFirstArms++;
						numberTalent++;
						((SpellShortcut)Mideas.joueur1().getSpells(0)).getSpell().setDamage(((SpellShortcut)Mideas.joueur1().getSpells(0)).getSpell().getDefaultDamage()*0.05f);
						Talent.setTalent();
					}
					else if(hoverLeft2 && deflectionTalent < 5) {
						deflectionTalent++;
						numberArmsTalent++;
						numberFirstArms++;
						numberTalent++;
						Mideas.joueur1().setStuffArmor(5);
						Talent.setTalent();
					}
					else if(hoverLeft3 && improvedRend < 3) {
						improvedRend++;
						numberArmsTalent++;
						numberFirstArms++;
						numberTalent++;
						Talent.setTalent();
					}
					else if(hoverLeft4 && improvedCharge < 2 && numberFirstArms >= 5) {
						improvedCharge++;
						numberArmsTalent++;
						numberTalent++;
						Talent.setTalent();
					}
					else if(hoverLeft5 && ironWill < 5 && numberFirstArms >= 5) {
						ironWill++;
						numberArmsTalent++;
						numberTalent++;
						Talent.setTalent();
					}
					else if(hoverLeft6 && improvedThunderClap < 3 && numberFirstArms >= 5) {
						improvedThunderClap++;
						numberArmsTalent++;
						numberTalent++;
						Talent.setTalent();
					}
				}
			}
			if(Mouse.getEventButton() == 1) {
				if(hoverLeft1 && heroicStrikeTalent > 0 && checkLine(numberFirstArms, improvedCharge, ironWill, improvedThunderClap)) {
					heroicStrikeTalent--;
					numberArmsTalent--;
					numberFirstArms--;
					numberTalent--;
					((SpellShortcut)Mideas.joueur1().getSpells(0)).getSpell().setDamage(-((SpellShortcut)Mideas.joueur1().getSpells(0)).getSpell().getDefaultDamage()*0.05f);
					Talent.setTalent();
				}
				else if(hoverLeft2 && deflectionTalent > 0 && checkLine(numberFirstArms, improvedCharge, ironWill, improvedThunderClap)) {
					deflectionTalent--;
					numberArmsTalent--;
					numberFirstArms--;
					numberTalent--;
					Mideas.joueur1().setStuffArmor(-5);
					Talent.setTalent();
				}
				else if(hoverLeft3 && improvedRend > 0 && checkLine(numberFirstArms, improvedCharge, ironWill, improvedThunderClap)) {
					improvedRend--;
					numberArmsTalent--;
					numberFirstArms--;
					numberTalent--;
					Talent.setTalent();
				}
				else if(hoverLeft4 && improvedCharge > 0) {
					improvedCharge--;
					numberArmsTalent--;
					numberFirstArms--;
					numberTalent--;
					Talent.setTalent();
				}
				else if(hoverLeft5 && ironWill > 0) {
					ironWill--;
					numberArmsTalent--;
					numberFirstArms--;
					numberTalent--;
					Talent.setTalent();
				}
				else if(hoverLeft6 && improvedThunderClap > 0) {
					improvedThunderClap--;
					numberArmsTalent--;
					numberFirstArms--;
					numberTalent--;
					Talent.setTalent();
				}
			}
		}
		return false;
	}
	
	public static void clickToLearn(int x, int y) {
		TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, "Cliquer pour apprendre", Color.green, Color.black, 1, 1, 1);
	}
	public static void clickToUnlearn(int x, int y) {
		TTF2.itemNumber.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, "Clic droit pour d�sapprendre", Color.red, Color.black, 1, 1, 1);
	}
	 
	private static void requiredPoint(int numberTalentSpec, int number, int talent, int numberTalent, int x_string, int y_string, int y_stringClickToLearn, String string) {
		if(numberTalentSpec >= number) {
			if(talent != numberTalent) {
				clickToLearn(Talent.getX()+24+x_string, Talent.getY()+y_stringClickToLearn);
			}
			else {
				clickToUnlearn(Talent.getX()+24+x_string, Talent.getY()+y_stringClickToLearn);
			}
		}
		if(number != 0 && numberTalentSpec >= 5) {
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+x_string, Display.getHeight()/2+Talent.getY()+y_string, string, Color.green, Color.black, 1, 1, 1);
		}
		else if(numberTalentSpec <= number && number != 0) {
			TTF2.talent.drawStringShadow(Display.getWidth()/2+Talent.getX()+24+x_string, Display.getHeight()/2+Talent.getY()+y_string, string, Color.red, Color.black, 1, 1, 1);
		}
	}
	
	private static boolean checkLine(int number, int firstTalent, int secondTalent, int thirdTalent) {
		if(firstTalent != 0 || secondTalent != 0 || thirdTalent != 0) {
			if(number == 5) {
				return false;
			}
		}
		return true;
	}
	
	private static void drawTalent(int talent, int maxLevel, int x, int y, int xLevelMax, int yLevelMax) {
		Texture temp = null;
		if(maxLevel == 5) {
			if(talent == 1) {
				temp = Sprites.un_green_talent;
			}
			else if(talent == 2) {
				temp = Sprites.deux_green_talent;
			}
			else if(talent == 3) {
				temp = Sprites.trois_green_talent;
			}
			else if(talent == 4) {
				temp = Sprites.quatre_green_talent;
			}
			else if(talent == 5) {
				temp = Sprites.cinq_yellow_square_talent;
				x = xLevelMax;
				y = yLevelMax;
			}
		}
		else if(maxLevel == 3) {
			if(talent == 1) {
				temp = Sprites.un_green_talent;
			}
			else if(talent == 2) {
				temp = Sprites.deux_green_talent;
			}
			else if(talent == 3) {
				temp = Sprites.trois_yellow_square_talent;
				x = xLevelMax;
				y = yLevelMax;
			}
		}
		else if(maxLevel == 2) {
			if(talent == 1) {
				temp = Sprites.un_green_talent;
			}
			else if(talent == 2) {
				temp = Sprites.deux_yellow_square_talent;
				x = xLevelMax;
				y = yLevelMax;
			}
		}
		else if(maxLevel == 1) {
			if(talent == 1) {
				//one yellow  talent
			}
		}
		Draw.drawQuad(temp, Display.getWidth()/2+x, Display.getHeight()/2+y);
	}
}
