package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.game.stuff.back.SunwellBack;
import com.mideas.rpg.v2.game.stuff.belt.BeltOfTheTempest;
import com.mideas.rpg.v2.game.stuff.belt.GirdleOfHope;
import com.mideas.rpg.v2.game.stuff.belt.GronnstalkersBelt;
import com.mideas.rpg.v2.game.stuff.belt.OnslaughtWaistguard;
import com.mideas.rpg.v2.game.stuff.boots.BootsOfTheTempest;
import com.mideas.rpg.v2.game.stuff.boots.GronnstalkersBoots;
import com.mideas.rpg.v2.game.stuff.boots.OnslaughtBoots;
import com.mideas.rpg.v2.game.stuff.boots.PearlInlaidBoots;
import com.mideas.rpg.v2.game.stuff.chest.GronnstalkersChestguard;
import com.mideas.rpg.v2.game.stuff.chest.LightbringerChestguard;
import com.mideas.rpg.v2.game.stuff.chest.OnslaughtChestguard;
import com.mideas.rpg.v2.game.stuff.chest.RobesOfTheTempest;
import com.mideas.rpg.v2.game.stuff.gloves.GlovesOfTheTempest;
import com.mideas.rpg.v2.game.stuff.gloves.GronnstalkersGloves;
import com.mideas.rpg.v2.game.stuff.gloves.LightbringerHandguards;
import com.mideas.rpg.v2.game.stuff.gloves.OnslaughtHandguards;
import com.mideas.rpg.v2.game.stuff.head.CowlOfTheTempest;
import com.mideas.rpg.v2.game.stuff.head.GronnstalkersHelmet;
import com.mideas.rpg.v2.game.stuff.head.LightbringerFaceguard;
import com.mideas.rpg.v2.game.stuff.head.OnslaughtGreathelm;
import com.mideas.rpg.v2.game.stuff.item.Item;
import com.mideas.rpg.v2.game.stuff.item.craft.LinenCloth;
import com.mideas.rpg.v2.game.stuff.item.potion.healingPotion.SuperHealingPotion;
import com.mideas.rpg.v2.game.stuff.leggings.GronnstalkersLeggings;
import com.mideas.rpg.v2.game.stuff.leggings.LeggingsOfTheTempest;
import com.mideas.rpg.v2.game.stuff.leggings.LightbringerLegguards;
import com.mideas.rpg.v2.game.stuff.leggings.OnslaughtLegguards;
import com.mideas.rpg.v2.game.stuff.mainHand.WarglaiveOfAzzinoth;
import com.mideas.rpg.v2.game.stuff.necklace.SunwellNecklace;
import com.mideas.rpg.v2.game.stuff.ranged.ThoridalTheStarsFury;
import com.mideas.rpg.v2.game.stuff.shoulders.GronnstalkersSpaulders;
import com.mideas.rpg.v2.game.stuff.shoulders.LightbringerShoulderguards;
import com.mideas.rpg.v2.game.stuff.shoulders.MantleOfTheTempest;
import com.mideas.rpg.v2.game.stuff.shoulders.OnslaughtShoulderguards;
import com.mideas.rpg.v2.game.stuff.wrists.BracersOfTheTempest;
import com.mideas.rpg.v2.game.stuff.wrists.GronnstalkersBracers;
import com.mideas.rpg.v2.game.stuff.wrists.OnslaughtWristguard;
import com.mideas.rpg.v2.game.stuff.wrists.TheSeekersWristguards;
import com.mideas.rpg.v2.utils.Draw;

public class EndFightFrame {

	private static boolean expGiven;
	private static boolean drop;
	private static boolean gold;
	private static boolean isGold;

	public static void draw() throws FileNotFoundException {
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
	
	public static void mouseEvent() {
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
	
	private static boolean dropRate(Stuff item) throws FileNotFoundException {
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
	
	public static boolean dropItem(Stuff potion, int number) throws FileNotFoundException {
		if(potion instanceof Item) {
			if(checkBagItems(potion)) {
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(Mideas.bag().getBag(i) == null) {
						Mideas.bag().setBag(i, potion);
						number = Mideas.joueur1().getNumberItem(potion)+1;
						Mideas.joueur1().setNumberItem(potion, number);
						CharacterStuff.setBagItems();
						return true;
					}
					i++;
				}
			}
			else {
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					lootItems(Mideas.bag().getBag(i), potion, number);
					i++;
				}
			}
		}
		return false;
	}
	
	public static boolean checkBagItems(Stuff item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && item.equals(Mideas.bag().getBag(i))) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	public static boolean bagItems(Stuff item) {
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
		}
		return true;
	}
	
	private static void lootItems(Stuff bag, Stuff potion, int number) throws FileNotFoundException {
		if(bag != null && bag.equals(potion)) {
			bag = potion;
			number = Mideas.joueur1().getNumberItem(potion)+1;
			Mideas.joueur1().setNumberItem(potion, number);
			CharacterStuff.setBagItems();
		}
	}
	
	private static void lootGuerrier() throws FileNotFoundException {
		float x = 0.005f;
		drop(x, new OnslaughtShoulderguards());
		drop(x, new SunwellBack());
		drop(x, new OnslaughtWaistguard());
		drop(x, new OnslaughtBoots());
		drop(x, new OnslaughtChestguard());
		drop(x, new OnslaughtHandguards());
		drop(x, new OnslaughtGreathelm());
		drop(x, new OnslaughtLegguards());
		drop(x, new SunwellNecklace());
		drop(x, new OnslaughtWristguard());
		drop(.01f*x, new ThoridalTheStarsFury());
		drop(.01f*x, new WarglaiveOfAzzinoth());
		drop(100*x, new SuperHealingPotion());
		drop(200*x, new LinenCloth());
	}

	private static void lootHunter() throws FileNotFoundException {
		float x = 0.005f;
		drop(x, new GronnstalkersSpaulders());
		drop(x, new SunwellBack());
		drop(x, new GronnstalkersBelt());
		drop(x, new GronnstalkersBoots());
		drop(x, new GronnstalkersChestguard());
		drop(x, new GronnstalkersGloves());
		drop(x, new GronnstalkersHelmet());
		drop(x, new GronnstalkersLeggings());
		drop(x, new SunwellNecklace());
		drop(x, new GronnstalkersBracers());
		drop(0.01f*x, new ThoridalTheStarsFury());
		drop(100*x, new SuperHealingPotion());
		drop(200*x, new LinenCloth());
		/*if(Math.random() < 100*x && !drop) {
			drop = true;
			dropPotion(new SuperHealingPotion(), 1);
		}*/
	}
	private static void lootPaladin() throws FileNotFoundException {
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

	private static void lootMage() throws FileNotFoundException {
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
	}
	
	private static void lootIllidan() throws FileNotFoundException {
		drop(.03f, new WarglaiveOfAzzinoth());
	}
	
	public static boolean lootManager() throws FileNotFoundException {
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
			lootMage();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Monk")) {
			lootGuerrier();
			return true;
		}
		else if(Mideas.joueur2().getClasse().equals("Paladin")) {
			lootPaladin();
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
	
	private static void drop(float x, Stuff item) throws FileNotFoundException {
		if(Math.random() <= x && !drop) {
			if(item instanceof SuperHealingPotion) {
				dropItem(new SuperHealingPotion(), 1);
			}
			else if(item instanceof LinenCloth) {
				dropItem(new LinenCloth(), 1);
			}
			else {
				dropRate(item);
			}
			LogChat.setStatusText3("Vous avez obtenus "+item.getStuffName());
			drop = true;
		}
	}
	
	public static void setDrop(boolean we) {
		drop = we;
	}
}

