package com.mideas.rpg.v2;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class Hud { 
	
	////////////
	//        //
	// UNUSED //
	//		  //
	////////////
	
	private static String statusText = "";
	private static String statusText2 = "";
	static float x;
	static float y;
	static float a;
	static float b;
	static float e;
	static boolean sprite = true;
	static int selectedSpell;
	static Spell hoveredSpell;
	
	public static void render() {  
		if(sprite) {
			Sprites.sprite();
			sprite = false;
		}
		Color bgColor = new Color(0, 0, 0,.35f);     
		//Color healthBar = Color.decode("#07D705");
		//Color manaBar = Color.decode("#0101D5");
		Draw.drawQuad(Sprites.bg, Display.getWidth()/2-Sprites.bg.getWidth()/2, Display.getHeight()/2-Sprites.bg.getHeight()/2);  //bg
		expBar();
		Draw.drawQuad(Sprites.spellbar, Window.getWidth()/2-300, Window.getHeight()-120);                                                 //spellBar
		Draw.drawQuad(Sprites.spell_attack, Window.getWidth()/2-139, Window.getHeight()-61);                                              //attack
		Draw.drawQuad(Sprites.border, Window.getWidth()/2-143, Window.getHeight()-66);
		FontManager.font5.drawStringShadow(Window.getWidth()/2-105, Window.getHeight()-61, "1", Color.WHITE, Color.BLACK, 1, 1, 1);
		
		/*Draw.drawColorQuad(90, 58, 120, 13, bgColor);                           //left backgroundHealthBar
		Draw.drawColorQuad(90, 58, 120*x, 12, healthBar);                       //left healthBar		
		Draw.drawColorQuad(90, 68, 119, 13,  bgColor);                          //left backgroundmanaBar
		Draw.drawColorQuad(90, 38, 119, 18, bgColor);                           //left backgroundName
		Draw.drawColorQuad(90, 68, 119*a, 12, manaBar);                         //left manaBar
		Draw.drawColorQuad(Window.getWidth()-154, 58, 120, 13,  bgColor);       //right backgroundHealhBar
		Draw.drawColorQuad(Window.getWidth()-154, 58, 120*y, 12, healthBar);    //right healthBar			
		Draw.drawColorQuad(Window.getWidth()-154, 38, 119, 18,  bgColor);       //right backgroundName	
		Draw.drawColorQuad(Window.getWidth()-154, 68, 119, 13,  bgColor);       //right backgroundManaBar
		Draw.drawColorQuad(Window.getWidth()-154, 68, 119*b-3, 12, manaBar);    //right manaBar	*/	                                                   
		FontManager.font.drawStringShadow(40, Window.getHeight()-230, statusText, Color.BLACK, Color.WHITE, 1, 1, 1);
		FontManager.font.drawStringShadow(40, Window.getHeight()-190, statusText2, Color.BLACK, Color.WHITE, 1, 1, 1);                                                                     
		 
		FontManager.get("FRIZQT", 13).drawString(155-FontManager.font.getWidth(Mideas.joueur1().getClasseString())/2, 38, Mideas.joueur1().getClasseString(), Color.ORANGE);                                              //leftPlayerName
		//TTF2.playerName.drawString(Window.getWidth()-91-TTF2.font.getWidth(Mideas.joueur1().getClasseString())/2, 38,  Mideas.target().getClasseString(), Colors.orange);                          //fps
		
		/*if(Mideas.x >= Window.getWidth()/2-139 && Mideas.y >= Window.getHeight()-61 && Mideas.x <= Window.getWidth()/2-83 && Mideas.y <= Window.getHeight()-5) {
			Draw.drawQuad(Sprites.hover, Window.getWidth()/2-142, Window.getHeight()-63);	
			Draw.drawQuad(Sprites.tooltip, Window.getWidth()/2-141, Window.getHeight()-220);
			TTF2.spellName.drawString(Window.getWidth()/2-133, Window.getHeight()-210, "Attack", Colors.white);
			TTF2.font4.drawString(Window.getWidth()/2-133, Window.getHeight()-188, "No mana cost", Colors.white);
		}*/
		/*if(Mideas.joueur1().getStamina() <= 0) {
			Draw.drawColorQuad(90, 58, 120, 10, Colors.red);
		}
		else if(Mideas.target().getStamina() <= 0) {
			Draw.drawColorQuad(Window.getWidth()-154, 58, 120, 10,  Colors.red);
		}
		Draw.drawColorQuad(30, Window.getHeight()-240, 490, 100, bgColor);                                  
		Draw.drawQuad(Sprites.playerUI, 20, 23);
		Draw.drawQuad(Sprites.playerUI2, Window.getWidth()-224, 23); 
		TTF2.hpAndMana.drawStringShadow(Window.getWidth()-134-TTF2.font.getWidth(Mideas.target().getStamina()+" / "+Mideas.target().getMaxStamina())/2+48, 56, Mideas.target().getStamina()+" / "+Mideas.target().getMaxStamina(), Colors.white, Colors.black, 1, 1, 1);   
		TTF2.hpAndMana.drawStringShadow(Window.getWidth()-134-TTF2.font.getWidth(Mideas.target().getMana()+" / "+Mideas.target().getMaxMana())/2+48, 67, Mideas.target().getMana()+" / "+Mideas.target().getMaxMana(), Colors.white, Colors.black, 1, 1, 1); 
		TTF2.hpAndMana.drawStringShadow(150-TTF2.font.getWidth(Mideas.joueur1().getMana()+"")/2-14, 67, Mideas.joueur1().getMana()+" / "+Mideas.joueur1().getMaxMana(), Colors.white, Colors.black, 1, 1, 1);   
		TTF2.hpAndMana.drawStringShadow(150-TTF2.font.getWidth(Mideas.joueur1().getStamina()+"")/2-14, 56, Mideas.joueur1().getStamina()+" / "+Mideas.joueur1().getMaxStamina(), Colors.white, Colors.black, 1, 1, 1);  
		TTF2.font2.drawString(Window.getWidth()-100, 300,"level "+Mideas.joueur1().getLevel(), Colors.black);
		hoveredSpell = null;*/
		/*int i = 0;
		if(Mideas.currentPlayer) {
			for(Spell spell : Mideas.joueur1().getSpells()) {
				if(i == 0) {
					Draw.drawQuad(spell.getSprite(), Window.getWidth()/2+62, Window.getHeight()-61);
					Draw.drawQuad(Sprites.border, Window.getWidth()/2+58, Window.getHeight()-66);
					if(Mideas.x >= Window.getWidth()/2+62 && Mideas.y >= Window.getHeight()-61 && Mideas.x <= Window.getWidth()/2+118 && Mideas.y <= Window.getHeight()-5) {
						Draw.drawQuad(Sprites.hover, Window.getWidth()/2+59, Window.getHeight()-63);	
						Draw.drawQuad(Sprites.tooltip, Window.getWidth()/2+60, Window.getHeight()-220);
						TTF2.spellName.drawString(Window.getWidth()/2+68, Window.getHeight()-210, spell.getName()+"", Colors.white);
						TTF2.font4.drawString(Window.getWidth()/2+68, Window.getHeight()-188, spell.getManaCost()+" Mana", Colors.white);
						if(spell.getName() == "HealingSurge" || spell.getName() == "FlashHeal" || spell.getName() == "LayOnHands") {
							TTF2.font4.drawString(Window.getWidth()/2+68, Window.getHeight()-158, "Heals you for "+spell.getHeal(), Colors.yellow);
						}
						else {
							TTF2.font4.drawString(Window.getWidth()/2+68, Window.getHeight()-158, "Deals "+spell.getBaseDamage()+" damage to the enemy", Colors.yellow);
						}
						hoveredSpell = spell;
					}
					TTF2.font5.drawString(Window.getWidth()/2+99, Window.getHeight()-61, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+99, Window.getHeight()-60, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+99, Window.getHeight()-62, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+100, Window.getHeight()-60, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+100, Window.getHeight()-62, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+101, Window.getHeight()-60, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+101, Window.getHeight()-61, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+101, Window.getHeight()-62, "4", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+100, Window.getHeight()-61, "4", Colors.white);
				}
				else if(i == 1) {
					Draw.drawQuad(spell.getSprite(), Window.getWidth()/2-5, Window.getHeight()-61);
					Draw.drawQuad(Sprites.border, Window.getWidth()/2-9, Window.getHeight()-66);
					if(Mideas.x >= Window.getWidth()/2-5 && Mideas.y >= Window.getHeight()-61 && Mideas.x <= Window.getWidth()/2+51 && Mideas.y <= Window.getHeight()-5) {
						Draw.drawQuad(Sprites.hover, Window.getWidth()/2-8, Window.getHeight()-63);	
						Draw.drawQuad(Sprites.tooltip, Window.getWidth()/2-7, Window.getHeight()-220);
						TTF2.spellName.drawString(Window.getWidth()/2+1, Window.getHeight()-210, spell.getName()+"", Colors.white);
						TTF2.font4.drawString(Window.getWidth()/2+1, Window.getHeight()-188, spell.getManaCost()+" Mana", Colors.white);
						TTF2.font4.drawString(Window.getWidth()/2+1, Window.getHeight()-158, "Deals "+spell.getBaseDamage()+" damage to the enemy", Colors.yellow);
						hoveredSpell = spell;
						
					}
					TTF2.font5.drawString(Window.getWidth()/2+33, Window.getHeight()-61, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+33, Window.getHeight()-60, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+33, Window.getHeight()-62, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+34, Window.getHeight()-60, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+34, Window.getHeight()-62, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+35, Window.getHeight()-60, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+35, Window.getHeight()-61, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+35, Window.getHeight()-62, "3", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2+34, Window.getHeight()-61, "3", Colors.white);
				}
				else if(i == 2) {
					Draw.drawQuad(spell.getSprite(), Window.getWidth()/2-72, Window.getHeight()-61);
					Draw.drawQuad(Sprites.border, Window.getWidth()/2-76, Window.getHeight()-66);
					if(Mideas.x >= Window.getWidth()/2-72 && Mideas.y >= Window.getHeight()-61 && Mideas.x <= Window.getWidth()/2-16 && Mideas.y <= Window.getHeight()-5) {
						Draw.drawQuad(Sprites.hover, Window.getWidth()/2-75, Window.getHeight()-63);	
						Draw.drawQuad(Sprites.tooltip, Window.getWidth()/2-74, Window.getHeight()-220);
						TTF2.font4.drawString(Window.getWidth()/2-66, Window.getHeight()-210, spell.getName()+"", Colors.white);
						TTF2.font4.drawString(Window.getWidth()/2-66, Window.getHeight()-188, spell.getManaCost()+" Mana", Colors.white);
						TTF2.font4.drawString(Window.getWidth()/2-66, Window.getHeight()-158, "Deals "+spell.getBaseDamage()+" damage to the enemy", Colors.yellow);
						hoveredSpell = spell;
					}
					TTF2.font5.drawString(Window.getWidth()/2-34, Window.getHeight()-61, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-34, Window.getHeight()-60, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-34, Window.getHeight()-62, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-35, Window.getHeight()-60, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-35, Window.getHeight()-62, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-36, Window.getHeight()-60, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-36, Window.getHeight()-61, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-36, Window.getHeight()-62, "2", Colors.black);
					TTF2.font5.drawString(Window.getWidth()/2-35, Window.getHeight()-61, "2", Colors.white);
				}
				i++;
			}
		}*/
		if(Mideas.joueur1().getClasseString() == "Priest") {
			Draw.drawQuad(Sprites.priest, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "Mage") {
			Draw.drawQuad(Sprites.mage, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "DeathKnight") {
			Draw.drawQuad(Sprites.deathknight, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "Guerrier") {
			Draw.drawQuad(Sprites.war, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "Hunter") {
			Draw.drawQuad(Sprites.hunter, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "Monk") {
			Draw.drawQuad(Sprites.monk, 31, 31);
		}	
		else if(Mideas.joueur1().getClasseString() == "Paladin") {
			Draw.drawQuad(Sprites.paladin, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "Rogue") {
			Draw.drawQuad(Sprites.rogue, 31, 31);
		}
		else if(Mideas.joueur1().getClasseString() == "Shaman") {
			Draw.drawQuad(Sprites.shaman, 31, 31);
		}
		else {
			Draw.drawQuad(Sprites.warlock, 31, 31);
		}
		
		
		/*if(Mideas.target().getClasseString() == "Priest") {
			Draw.drawQuad(Sprites.priest2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "Mage") {
			Draw.drawQuad(Sprites.mage2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "DeathKnight") {
			Draw.drawQuad(Sprites.deathknight2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "Guerrier") {
			Draw.drawQuad(Sprites.war2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "Hunter") {
			Draw.drawQuad(Sprites.hunter2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "Monk") {
			Draw.drawQuad(Sprites.monk2, Window.getWidth()-213, 31);
		}	
		else if(Mideas.target().getClasseString() == "Paladin") {
			Draw.drawQuad(Sprites.paladin2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "Rogue") {
			Draw.drawQuad(Sprites.rogue2, Window.getWidth()-213, 31);
		}
		else if(Mideas.target().getClasseString() == "Shaman") {
			Draw.drawQuad(Sprites.shaman2, Window.getWidth()-213, 31);
		}
		else {
			Draw.drawQuad(Sprites.warlock2, Window.getWidth()-213, 31);
		}*/
	}
	
	/*public static void setStatusText(String text) {
		statusText = text;
	}
	
	public static void setStatusText2(String text2) {
		statusText2 = text2;
	}*/
	
	public static void lifeBars() {
		/*if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			x = Mideas.joueur1().getStamina()/(float)Guerrier.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Mage")) {
			x = Mideas.joueur1().getStamina()/(float)Mage.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Priest")) {
			x = Mideas.joueur1().getStamina()/(float)Priest.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
			x = Mideas.joueur1().getStamina()/(float)DeathKnight.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Hunter")) {
			x = Mideas.joueur1().getStamina()/(float)Hunter.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Monk")) {
			x = Mideas.joueur1().getStamina()/(float)Monk.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Paladin")) {
			x = Mideas.joueur1().getStamina()/(float)Paladin.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Rogue")) {
			x = Mideas.joueur1().getStamina()/(float)Rogue.MAX_HEALTH;
		}
		else if(Mideas.joueur1().getClasse().equals("Shaman")) {
			x = Mideas.joueur1().getStamina()/(float)Shaman.MAX_HEALTH;
		}
		else {
			x = Mideas.joueur1().getStamina()/(float)Warlock.MAX_HEALTH;
		}
		

		if(Mideas.target().getClasse().equals("Guerrier")) {
			y = Mideas.target().getStamina()/(float)Guerrier.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Mage")) {
			y = Mideas.target().getStamina()/(float)Mage.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Priest")) {
			y = Mideas.target().getStamina()/(float)Priest.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("DeathKnight")) {
			y = Mideas.target().getStamina()/(float)DeathKnight.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Hunter")) {
			y = Mideas.target().getStamina()/(float)Hunter.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Monk")) {
			y = Mideas.target().getStamina()/(float)Monk.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Paladin")) {
			y = Mideas.target().getStamina()/(float)Paladin.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Rogue")) {
			y = Mideas.target().getStamina()/(float)Rogue.MAX_HEALTH;
		}
		else if(Mideas.target().getClasse().equals("Shaman")) {
			y = Mideas.target().getStamina()/(float)Shaman.MAX_HEALTH;
		}
		else {
			y = Mideas.target().getStamina()/(float)Warlock.MAX_HEALTH;
		}*/
	}
	
	public static void manaBars() {
		/*if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			a = Mideas.joueur1().getMana()/(float)Guerrier.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("Mage")) {
			a = Mideas.joueur1().getMana()/(float)Mage.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("Priest")) {
			a = Mideas.joueur1().getMana()/(float)Priest.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
			a = Mideas.joueur1().getMana()/(float)DeathKnight.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("Hunter")) {
			a = Mideas.joueur1().getMana()/(float)Hunter.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("Monk")) {
			a = Mideas.joueur1().getMana()/(float)Monk.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("Paladin")) {
			a = Mideas.joueur1().getMana()/(float)Paladin.MAX_MANA+0.01f;
		}
		else if(Mideas.joueur1().getClasse().equals("Rogue")) {
			a = Mideas.joueur1().getMana()/(float)Rogue.MAX_MANA+0.02f;
		}
		else if(Mideas.joueur1().getClasse().equals("Shaman")) {
			a = Mideas.joueur1().getMana()/(float)Shaman.MAX_MANA+0.01f;
		}
		else {
			a = Mideas.joueur1().getMana()/(float)Warlock.MAX_MANA+0.01f; //warlock
		}
		

		if(Mideas.target().getClasse().equals("Guerrier")) {
			b = Mideas.target().getMana()/(float)Guerrier.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("Mage")) {
			b = Mideas.target().getMana()/(float)Mage.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("Priest")) {
			b = Mideas.target().getMana()/(float)Priest.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("DeathKnight")) {
			b = Mideas.target().getMana()/(float)DeathKnight.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("Hunter")) {
			b = Mideas.target().getMana()/(float)Hunter.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("Monk")) {
			b = Mideas.target().getMana()/(float)Monk.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("Paladin")) {
			b = Mideas.target().getMana()/(float)Paladin.MAX_MANA+0.015f;
		}
		else if(Mideas.target().getClasse().equals("Rogue")) {
			b = Mideas.target().getMana()/(float)Rogue.MAX_MANA+0.025f;
		}
		else if(Mideas.target().getClasse().equals("Shaman")) {
			b = Mideas.target().getMana()/(float)Shaman.MAX_MANA+0.015f;
		}
		else {
			b = Mideas.target().getMana()/(float)Warlock.MAX_MANA+0.015f;
		}*/
	}
	
	public static boolean redBars() {
		if(Mideas.joueur1().getStamina() <= 0) {
			Draw.drawColorQuad(90, 58, 120, 10, Color.RED);
			return true;
		}
		/*else if(Mideas.target().getStamina() <= 0) {
			Draw.drawColorQuad(Window.getWidth()-154, 58, 120, 10,  Colors.red);
			return true;
		}*/
		return false;
	}
	
	public static void expBar() {
		e = ((float)Mideas.joueur1().getExp()-(float)Mideas.getExpNeeded(Mideas.joueur1().getLevel()-1))/((float)Mideas.getExpNeeded(Mideas.joueur1().getLevel())-Mideas.getExpNeeded(Mideas.joueur1().getLevel()-1));
		//Draw.drawColorQuad(Window.getWidth()/2-140, Window.getHeight()-80, 270*e, 11,  Color.decode("#680764"));
	}
}
