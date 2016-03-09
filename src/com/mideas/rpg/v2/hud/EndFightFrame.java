package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.utils.Draw;

public class EndFightFrame {

	private static boolean expGiven;
	private static boolean drop;
	private static boolean gold;
	private static boolean isGold;

	public static void draw() throws FileNotFoundException, SQLException {
		Arrays.fill(AdminPanelFrame.getHover(), false);
		Arrays.fill(CharacterFrame.getHoverCharacterFrame(), false);
		Arrays.fill(ContainerFrame.getContainerFrameSlotHover(), false);
		Arrays.fill(ShopFrame.getShopHover(), false);
		Arrays.fill(SpellBookFrame.getHoverBook(), false);
		Draw.drawQuad(Sprites.alert, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-105, Display.getHeight()/2-80);
		if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-3 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
			Draw.drawQuad(Sprites.button_hover, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
		}
		else {
			Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
			Draw.drawQuad(Sprites.button_hover2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
		}
		else {
			Draw.drawQuad(Sprites.button2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
		}
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Retry")/2-69, Display.getHeight()/2-41, "Retry", Color.white, Color.black, 1, 1, 1);
		TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Quit")/2+69, Display.getHeight()/2-41, "Quit", Color.white, Color.black, 1, 1, 1);
		if(Mideas.joueur1().getStamina() <= 0) {
			TTF2.font4.drawStringShadow(Display.getWidth()/2-50, Display.getHeight()/2-66, "Player 2 WON", Color.white, Color.black, 1, 1, 1);
		}
		else if(Mideas.joueur2().getStamina() <= 0) {
			TTF2.font4.drawStringShadow(Display.getWidth()/2-50, Display.getHeight()/2-66, "Player 1 WON", Color.white, Color.black, 1, 1, 1);
			isGold = true;
			if(!expGiven) {
				Mideas.setExp();
				Mideas.getLevel();
				expGiven = true;
			}
			if(!gold) {
				Mideas.setGold(Mideas.joueur2().getGoldGained());
				gold = true;
			}
			lootManager();
		}
	}
	
	public static void mouseEvent() throws SQLException {
		if(Mouse.getEventButton() == 0) {
			if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
				System.exit(1);
			}
			else if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-3 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
				Mideas.setJoueur2(Mideas.getRandomClass(2));
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
				Mideas.joueur1().setMana(Mideas.joueur1().getMaxMana());
				LogChat.setStatusText("");
				LogChat.setStatusText2("");
				expGiven = false;
				drop = false;
				if(isGold) {
					gold = false;
				}
				isGold = false;
			}
		}
	}
	
	private static boolean dropRate(Item item) throws FileNotFoundException, SQLException {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				Mideas.bag().setBag(i, item);
				CharacterStuff.setBagItems();
				return true;
			}
			i++;
		}
		LogChat.setStatusText3("Votre inventaire est pleins");
		return false;
	}
	
	public static boolean dropItem(Item potion, int number) throws FileNotFoundException, SQLException {
		if(potion.getItemType() == ItemType.ITEM || potion.getItemType() == ItemType.POTION) {
			if(checkBagItems(potion)) {
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(Mideas.bag().getBag(i) == null) {
						Mideas.bag().setBag(i, potion);
						//number = Mideas.joueur1().getNumberItem(potion, i)+1;
						Mideas.joueur1().setNumberItem(potion, 1);
						CharacterStuff.setBagItems();
						return true;
					}
					i++;
				}
			}
			else {
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).equals(potion)) {
						Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i))+1);
						CharacterStuff.setBagItems();
						return true;
					}
					i++;
				}
			}
		}
		return false;
	}
	
	public static boolean checkBagItems(Item potion) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && potion.getId() ==  Mideas.bag().getBag(i).getId()) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	public static boolean bagItems(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && item.equals(Mideas.bag().getBag(i))) {
				return false;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) != null && item.equals(Mideas.joueur1().getStuff(i))) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	private static void lootGuerrier() throws FileNotFoundException, SQLException {
		float x = 0.005f;
		drop(x, StuffManager.getClone(1001));
		drop(x, StuffManager.getClone(2001));
		drop(x, StuffManager.getClone(3001));
		drop(x, StuffManager.getClone(4001));
		drop(x, StuffManager.getClone(5001));
		drop(x, StuffManager.getClone(6001));
		drop(x, StuffManager.getClone(7001));
		drop(x, StuffManager.getClone(8001));
		drop(x, StuffManager.getClone(9001));
		drop(x, StuffManager.getClone(10001));
		drop(x, StuffManager.getClone(10001));
		drop(.95f, PotionManager.getClone(1));
		//drop(.01f*x, new ThoridalTheStarsFury());
		drop(.01f*x, StuffManager.getClone(15001));
		drop(.01f*x, StuffManager.getClone(16001));
		//drop(100*x, new SuperHealingPotion());
		//drop(200*x, new LinenCloth());
	}

	private static void lootHunter() throws FileNotFoundException, SQLException {
		float x = 0.005f;
		drop(x, StuffManager.getClone(1201));
		drop(x, StuffManager.getClone(2001));
		drop(x, StuffManager.getClone(3201));
		drop(x, StuffManager.getClone(4001));
		drop(x, StuffManager.getClone(5201));
		drop(x, StuffManager.getClone(6201));
		drop(x, StuffManager.getClone(7201));
		drop(x, StuffManager.getClone(8201));
		drop(x, StuffManager.getClone(9201));
		drop(x, StuffManager.getClone(10201));
		//drop(0.01f*x, new ThoridalTheStarsFury());
		//drop(100*x, new SuperHealingPotion());
		//drop(200*x, new LinenCloth());
		/*if(Math.random() < 100*x && !drop) {
			drop = true;
			dropPotion(new SuperHealingPotion(), 1);
		}*/
	}
	/*private static void lootPaladin() throws FileNotFoundException, SQLException {
		float x = 0.005f;
		drop(x, new LightbringerShoulderguards());
		drop(x, new SunwellBack());
		drop(x, new GirdleOfHope());
		drop(x, new PearlInlaidBoots());
		drop(x, new LightbringerChestguard());
		drop(x, new LightbringerHandguards());
		drop(x, new LightbringerFaceguard());
		drop(x, new LightbringerLegguards());
		drop(x, new SunwellNecklace());
		drop(x, new TheSeekersWristguards());
		drop(100*x, new SuperHealingPotion());
		drop(200*x, new LinenCloth());
	}

	private static void lootMage() throws FileNotFoundException, SQLException {
		float x = 0.005f;
		drop(x, new MantleOfTheTempest());
		drop(x, new SunwellBack());
		drop(x, new BeltOfTheTempest());
		drop(x, new BootsOfTheTempest());
		drop(x, new RobesOfTheTempest());
		drop(x, new GlovesOfTheTempest());
		drop(x, new CowlOfTheTempest());
		drop(x, new LeggingsOfTheTempest());
		drop(x, new SunwellNecklace());
		drop(x, new BracersOfTheTempest());
		drop(100*x, new SuperHealingPotion());
		drop(200*x, new LinenCloth());
	}*/
	
	private static void lootIllidan() throws FileNotFoundException, SQLException {
		//drop(.03f, new WarglaiveOfAzzinoth());
	}
	
	public static boolean lootManager() throws FileNotFoundException, SQLException {
		if(Mideas.joueur2().getClasse().equals("DeathKnight")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Guerrier")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Hunter")) {
			lootHunter();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Mage")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Monk")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Paladin")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Priest")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Rogue")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Shaman")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Warlock")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Illidan")) {
			lootIllidan();
			return true;
		}		
		return false;	
	}
	
	private static void drop(float x, Item item) throws FileNotFoundException, SQLException {
		if(Math.random() <= x && !drop && item != null) {
			if(item.getItemType() == ItemType.POTION || item.getItemType() == ItemType.ITEM) {
				dropItem(item, 1);
			}
			else {
				dropRate(item);
			}
			LogChat.setStatusText3("Vous avez obtenus "+item.getStuffName());
			drop = true;
		}
	}
	
	public static boolean getExpGiven() {
		return expGiven;
	}
	
	public static void setDrop(boolean we) {
		drop = we;
	}
}

