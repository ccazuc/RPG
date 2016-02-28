package com.mideas.rpg.v2.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.game.stuff.back.Back;
import com.mideas.rpg.v2.game.stuff.back.SunwellBack;
import com.mideas.rpg.v2.game.stuff.belt.Belt;
import com.mideas.rpg.v2.game.stuff.belt.BeltOfTheTempest;
import com.mideas.rpg.v2.game.stuff.belt.GirdleOfHope;
import com.mideas.rpg.v2.game.stuff.belt.GronnstalkersBelt;
import com.mideas.rpg.v2.game.stuff.belt.OnslaughtWaistguard;
import com.mideas.rpg.v2.game.stuff.boots.Boots;
import com.mideas.rpg.v2.game.stuff.boots.BootsOfTheTempest;
import com.mideas.rpg.v2.game.stuff.boots.GronnstalkersBoots;
import com.mideas.rpg.v2.game.stuff.boots.OnslaughtBoots;
import com.mideas.rpg.v2.game.stuff.boots.PearlInlaidBoots;
import com.mideas.rpg.v2.game.stuff.chest.Chest;
import com.mideas.rpg.v2.game.stuff.chest.GronnstalkersChestguard;
import com.mideas.rpg.v2.game.stuff.chest.LightbringerChestguard;
import com.mideas.rpg.v2.game.stuff.chest.OnslaughtChestguard;
import com.mideas.rpg.v2.game.stuff.chest.RobesOfTheTempest;
import com.mideas.rpg.v2.game.stuff.gloves.Gloves;
import com.mideas.rpg.v2.game.stuff.gloves.GlovesOfTheTempest;
import com.mideas.rpg.v2.game.stuff.gloves.GronnstalkersGloves;
import com.mideas.rpg.v2.game.stuff.gloves.LightbringerHandguards;
import com.mideas.rpg.v2.game.stuff.gloves.OnslaughtHandguards;
import com.mideas.rpg.v2.game.stuff.head.CowlOfTheTempest;
import com.mideas.rpg.v2.game.stuff.head.GronnstalkersHelmet;
import com.mideas.rpg.v2.game.stuff.head.Head;
import com.mideas.rpg.v2.game.stuff.head.LightbringerFaceguard;
import com.mideas.rpg.v2.game.stuff.head.OnslaughtGreathelm;
import com.mideas.rpg.v2.game.stuff.item.Item;
import com.mideas.rpg.v2.game.stuff.item.craft.LinenCloth;
import com.mideas.rpg.v2.game.stuff.item.potion.healingPotion.SuperHealingPotion;
import com.mideas.rpg.v2.game.stuff.leggings.GronnstalkersLeggings;
import com.mideas.rpg.v2.game.stuff.leggings.Leggings;
import com.mideas.rpg.v2.game.stuff.leggings.LeggingsOfTheTempest;
import com.mideas.rpg.v2.game.stuff.leggings.LightbringerLegguards;
import com.mideas.rpg.v2.game.stuff.leggings.OnslaughtLegguards;
import com.mideas.rpg.v2.game.stuff.mainHand.MainHand;
import com.mideas.rpg.v2.game.stuff.mainHand.WarglaiveOfAzzinoth;
import com.mideas.rpg.v2.game.stuff.necklace.Necklace;
import com.mideas.rpg.v2.game.stuff.necklace.SunwellNecklace;
import com.mideas.rpg.v2.game.stuff.offHand.OffHand;
import com.mideas.rpg.v2.game.stuff.ranged.Ranged;
import com.mideas.rpg.v2.game.stuff.ranged.ThoridalTheStarsFury;
import com.mideas.rpg.v2.game.stuff.ring.Ring;
import com.mideas.rpg.v2.game.stuff.ring2.Ring2;
import com.mideas.rpg.v2.game.stuff.shoulders.GronnstalkersSpaulders;
import com.mideas.rpg.v2.game.stuff.shoulders.LightbringerShoulderguards;
import com.mideas.rpg.v2.game.stuff.shoulders.MantleOfTheTempest;
import com.mideas.rpg.v2.game.stuff.shoulders.OnslaughtShoulderguards;
import com.mideas.rpg.v2.game.stuff.shoulders.Shoulders;
import com.mideas.rpg.v2.game.stuff.trinket.Trinket;
import com.mideas.rpg.v2.game.stuff.trinket2.Trinket2;
import com.mideas.rpg.v2.game.stuff.wrists.BracersOfTheTempest;
import com.mideas.rpg.v2.game.stuff.wrists.GronnstalkersBracers;
import com.mideas.rpg.v2.game.stuff.wrists.OnslaughtWristguard;
import com.mideas.rpg.v2.game.stuff.wrists.TheSeekersWristguards;
import com.mideas.rpg.v2.game.stuff.wrists.Wrists;
import com.mideas.rpg.v2.hud.ClassSelectFrame;
import com.mideas.rpg.v2.hud.ShopFrame;

public class CharacterStuff {

	private static Stuff getStuff(int id) {
		if(id == 1) {
			return new SuperHealingPotion();
		}
		else if(id == 2) {
			return new LinenCloth();
		}
		else if(id == 101) {
			return new OnslaughtGreathelm();
		}
		else if(id == 121) {
			return new GronnstalkersHelmet();
		}
		else if(id == 131) {
			return new CowlOfTheTempest();
		}
		else if(id == 151) {
			return new LightbringerFaceguard();
		}
		else if(id == 201) {
			return new SunwellNecklace();
		}
		else if(id == 301) {
			return new OnslaughtShoulderguards();
		}
		else if(id == 321) {
			return new GronnstalkersSpaulders();
		}
		else if(id == 331) {
			return new MantleOfTheTempest();
		}
		else if(id == 351) {
			return new LightbringerShoulderguards();
		}
		else if(id == 401) {
			return new SunwellBack();
		}
		else if(id == 501) {
			return new OnslaughtChestguard();
		}
		else if(id == 521) {
			return new GronnstalkersChestguard();
		}
		else if(id == 531) {
			return new RobesOfTheTempest();
		}
		else if(id == 551) {
			return new LightbringerChestguard();
		}
		else if(id == 601) {
			return new OnslaughtWristguard();
		}
		else if(id == 621) {
			return new GronnstalkersBracers();
		}
		else if(id == 631) {
			return new BracersOfTheTempest();
		}
		else if(id == 651) {
			return new TheSeekersWristguards();
		}
		else if(id == 701) {
			return new OnslaughtHandguards();
		}
		else if(id == 721) {
			return new GronnstalkersGloves();
		}
		else if(id == 731) {
			return new GlovesOfTheTempest();
		}
		else if(id == 751) {
			return new LightbringerHandguards();
		}
		else if(id == 801) {
			return new OnslaughtWaistguard();
		}
		else if(id == 821) {
			return new GronnstalkersBelt();
		}
		else if(id == 831) {
			return new BeltOfTheTempest();
		}
		else if(id == 851) {
			return new GirdleOfHope();
		}
		else if( id == 901) {
			return new OnslaughtLegguards();
		}
		else if(id == 921) {
			return new GronnstalkersLeggings();
		}
		else if(id == 931) {
			return new LeggingsOfTheTempest();
		}
		else if(id == 951) {
			return new LightbringerLegguards();
		}
		else if(id == 1001) {
			return new OnslaughtBoots();
		}
		else if(id == 1021) {
			return new GronnstalkersBoots();
		}
		else if(id == 1031) {
			return new BootsOfTheTempest();
		}
		else if(id == 1051) {
			return new PearlInlaidBoots();
		}
		else if(id == 1501) {
			return new WarglaiveOfAzzinoth();
		}
		else if(id == 1701) {
			return new ThoridalTheStarsFury();
		}
		return null;
	}
	
	public static Texture getSprite(int id) {
		if(id == 1) {
			return Sprites.super_healing_potion;
		}
		else if(id == 2) {
			return Sprites.linen_cloth;
		}
		else if(id == 101) {
			return Sprites.warrior_t6_head;
		}
		else if(id == 121) {
			return Sprites.hunter_t6_head;
		}
		else if(id == 131) {
			return Sprites.mage_t6_head;
		}
		else if(id == 151) {
			return Sprites.paladin_t6_head;
		}
		else if(id == 201) {
			return Sprites.necklace_sunwell;
		}
		else if(id == 301) {
			return Sprites.warrior_t6_shoulders;
		}
		else if(id == 321) {
			return Sprites.hunter_t6_shoulders;
		}
		else if(id == 331) {
			return Sprites.mage_t6_shoulders;
		}
		else if(id == 351) {
			return Sprites.paladin_t6_shoulders;
		}
		else if(id == 401) {
			return Sprites.back_sunwell;
		}
		else if(id == 501) {
			return Sprites.warrior_t6_chest;
		}
		else if(id == 521) {
			return Sprites.hunter_t6_chest;
		}
		else if(id == 531) {
			return Sprites.mage_t6_chest;
		}
		else if(id == 551) {
			return Sprites.paladin_t6_chest;
		}
		else if(id == 601) {
			return Sprites.warrior_t6_wrists;
		}
		else if(id == 621) {
			return Sprites.hunter_t6_wrists;
		}
		else if(id == 631) {
			return Sprites.mage_t6_wrists;
		}
		else if(id == 651) {
			return Sprites.paladin_t6_wrists;
		}
		else if(id == 701) {
			return Sprites.warrior_t6_gloves;
		}
		else if(id == 721) {
			return Sprites.hunter_t6_gloves;
		}
		else if(id == 731) {
			return Sprites.mage_t6_gloves;
		}
		else if(id == 751) {
			return Sprites.paladin_t6_gloves;
		}
		else if(id == 801) {
			return Sprites.warrior_t6_belt;
		}
		else if(id == 821) {
			return Sprites.hunter_t6_belt;
		}
		else if(id == 831) {
			return Sprites.mage_t6_belt;
		}
		else if(id == 851) {
			return Sprites.paladin_t6_belt;
		}
		else if(id == 901) {
			return Sprites.warrior_t6_leggings;
		}
		else if(id == 921) {
			return Sprites.hunter_t6_leggings;
		}
		else if(id == 931) {
			return Sprites.mage_t6_leggings;
		}
		else if(id == 951) {
			return Sprites.paladin_t6_leggings;
		}
		else if(id == 1001) {
			return Sprites.warrior_t6_boots;
		}
		else if(id == 1021) {
			return Sprites.hunter_t6_boots;
		}
		else if(id == 1031) {
			return Sprites.mage_t6_boots;
		}
		else if(id == 1051) {
			return Sprites.paladin_t6_boots;
		}
		else if(id == 1501) {
			return Sprites.warglaive_of_azzinoth;
		}
		else if(id == 1701) {
			return Sprites.thoridal;
		}
		else {
			return null;
		}
	}
	
	public static Texture getBagSprite(int id) {
		if(id == 1) {
			return Sprites.bag_super_healing_potion;
		}
		else if(id == 2) {
			return Sprites.bag_linen_cloth;
		}
		else if(id == 101) {
			return Sprites.warrior_bag_t6_head;
		}
		else if(id == 121) {
			return Sprites.hunter_bag_t6_head;
		}
		else if(id == 131) {
			return Sprites.mage_bag_t6_head;
		}
		else if(id == 151) {
			return Sprites.paladin_bag_t6_head;
		}
		else if(id == 201) {
			return Sprites.necklace_bag_sunwell;
		}
		else if(id == 301) {
			return Sprites.warrior_bag_t6_shoulders;
		}
		else if(id == 321) {
			return Sprites.hunter_bag_t6_shoulders;
		}
		else if(id == 331) {
			return Sprites.mage_bag_t6_shoulders;
		}
		else if(id == 351) {
			return Sprites.paladin_bag_t6_shoulders;
		}
		else if(id == 401) {
			return Sprites.back_bag_sunwell;
		}
		else if(id == 501) {
			return Sprites.warrior_bag_t6_chest;
		}
		else if(id == 521) {
			return Sprites.hunter_bag_t6_chest;
		}
		else if(id == 531) {
			return Sprites.mage_bag_t6_chest;
		}
		else if(id == 551) {
			return Sprites.paladin_bag_t6_chest;
		}
		else if(id == 601) {
			return Sprites.warrior_bag_t6_wrists;
		}
		else if(id == 621) {
			return Sprites.hunter_bag_t6_wrists;
		}
		else if(id == 631) {
			return Sprites.mage_bag_t6_wrists;
		}
		else if(id == 651) {
			return Sprites.paladin_bag_t6_wrists;
		}
		else if(id == 701) {
			return Sprites.warrior_bag_t6_gloves;
		}
		else if(id == 721) {
			return Sprites.hunter_bag_t6_gloves;
		}
		else if(id == 731) {
			return Sprites.mage_bag_t6_gloves;
		}
		else if(id == 751) {
			return Sprites.paladin_bag_t6_gloves;
		}
		else if(id == 801) {
			return Sprites.warrior_bag_t6_belt;
		}
		else if(id == 821) {
			return Sprites.hunter_bag_t6_belt;
		}
		else if(id == 831) {
			return Sprites.mage_bag_t6_belt;
		}
		else if(id == 851) {
			return Sprites.paladin_bag_t6_belt;
		}
		else if(id == 901) {
			return Sprites.warrior_bag_t6_leggings;
		}
		else if(id == 921) {
			return Sprites.hunter_bag_t6_leggings;
		}
		else if(id == 931) {
			return Sprites.mage_bag_t6_leggings;
		}
		else if(id == 951) {
			return Sprites.paladin_bag_t6_leggings;
		}
		else if(id == 1001) {
			return Sprites.warrior_bag_t6_boots;
		}
		else if(id == 1021) {
			return Sprites.hunter_bag_t6_boots;
		}
		else if(id == 1031) {
			return Sprites.mage_bag_t6_boots;
		}
		else if(id == 1051) {
			return Sprites.paladin_bag_t6_boots;
		}
		else if(id == 1501) {
			return Sprites.bag_warglaive_of_azzinoth;
		}
		else if(id == 1701) {
			return Sprites.bag_thoridal;
		}
		else {
			return null;
		}
	}
	
	public static Texture getShopSprite(int id) {
		if(id == 1) {
			return Sprites.shop_super_healing_potion;
		}
		else if(id == 2) {
			return Sprites.linen_cloth;
		}
		else if(id == 101) {
			return Sprites.warrior_shop_t6_head;
		}
		else if(id == 121) {
			return Sprites.hunter_shop_t6_head;
		}
		else if(id == 131) {
			return Sprites.mage_shop_t6_head;
		}
		else if(id == 151) {
			return Sprites.paladin_shop_t6_head;
		}
		else if(id == 201) {
			return Sprites.necklace_shop_sunwell;
		}
		else if(id == 301) {
			return Sprites.warrior_shop_t6_shoulders;
		}
		else if(id == 321) {
			return Sprites.hunter_shop_t6_shoulders;
		}
		else if(id == 331) {
			return Sprites.mage_shop_t6_shoulders;
		}
		else if(id == 351) {
			return Sprites.paladin_shop_t6_shoulders;
		}
		else if(id == 401) {
			return Sprites.back_shop_sunwell;
		}
		else if(id == 501) {
			return Sprites.warrior_shop_t6_chest;
		}
		else if(id == 521) {
			return Sprites.hunter_shop_t6_chest;
		}
		else if(id == 531) {
			return Sprites.mage_shop_t6_chest;
		}
		else if(id == 551) {
			return Sprites.paladin_shop_t6_chest;
		}
		else if(id == 601) {
			return Sprites.warrior_shop_t6_wrists;
		}
		else if(id == 621) {
			return Sprites.hunter_shop_t6_wrists;
		}
		else if(id == 631) {
			return Sprites.mage_shop_t6_wrists;
		}
		else if(id == 651) {
			return Sprites.paladin_shop_t6_wrists;
		}
		else if(id == 701) {
			return Sprites.warrior_shop_t6_gloves;
		}
		else if(id == 721) {
			return Sprites.hunter_shop_t6_gloves;
		}
		else if(id == 731) {
			return Sprites.mage_shop_t6_gloves;
		}
		else if(id == 751) {
			return Sprites.paladin_shop_t6_gloves;
		}
		else if(id == 801) {
			return Sprites.warrior_shop_t6_belt;
		}
		else if(id == 821) {
			return Sprites.hunter_shop_t6_belt;
		}
		else if(id == 831) {
			return Sprites.mage_shop_t6_belt;
		}
		else if(id == 851) {
			return Sprites.paladin_shop_t6_belt;
		}
		else if(id == 901) {
			return Sprites.warrior_shop_t6_leggings;
		}
		else if(id == 921) {
			return Sprites.hunter_shop_t6_leggings;
		}
		else if(id == 931) {
			return Sprites.mage_shop_t6_leggings;
		}
		else if(id == 951) {
			return Sprites.paladin_shop_t6_leggings;
		}
		else if(id == 1001) {
			return Sprites.warrior_shop_t6_boots;
		}
		else if(id == 1021) {
			return Sprites.hunter_shop_t6_boots;
		}
		else if(id == 1031) {
			return Sprites.mage_shop_t6_boots;
		}
		else if(id == 1051) {
			return Sprites.paladin_shop_t6_boots;
		}
		else if(id == 1501) {
			return Sprites.shop_warglaive_of_azzinoth;
		}
		else if(id == 1701) {
			return Sprites.shop_thoridal;
		}
		else {
			return null;
		}
	}
	
	public static Head getHead(int id) {
		return (Head)getStuff(id);
	}
	
	public static Necklace getNecklace(int id) {
		return (Necklace)getStuff(id);
	}
	
	public static Shoulders getShoulders(int id) {
		return (Shoulders)getStuff(id);
	}

	public static Back getBack(int id) {
		return (Back)getStuff(id);
	}

	public static Chest getChest(int id) {
		return (Chest)getStuff(id);
	}
	
	public static Wrists getWrists(int id) {
		return (Wrists)getStuff(id);
	}
	
	public static Gloves getGloves(int id) {
		return (Gloves)getStuff(id);
	}

	public static Belt getBelt(int id) {
		return (Belt)getStuff(id);
	}
	
	public static Leggings getLeggings(int id) {
		return (Leggings)getStuff(id);
	}

	public static Boots getBoots(int id) {
		return (Boots)getStuff(id);
	}
	
	public static Ring getRing(int id) {
		return (Ring)getStuff(id);
	}
	
	public static Ring2 getRing2(int id) {
		return (Ring2)getStuff(id);
	}
	
	public static Trinket getTrinket(int id) {
		return (Trinket)getStuff(id);
	}
	
	public static Trinket2 getTrinket2(int id) {
		return (Trinket2)getStuff(id);
	}
	
	public static MainHand getMainHand(int id) {
		return (MainHand)getStuff(id);
	}
	
	public static OffHand getOffHand(int id) {
		return (OffHand)getStuff(id);
	}
	
	public static Ranged getRanged(int id) {
		return (Ranged)getStuff(id);
	}
	
	public static void getBagItems() throws FileNotFoundException {
		BufferedReader br = null;
		int i = 0;
		int id;
		try {
			String sCurrentLine;
			//String number[] = {"1","2","3"};
			String text[];
			br = new BufferedReader(new FileReader(ClassSelectFrame.bagTxt()));
			while ((sCurrentLine = br.readLine()) != null) {
				text = sCurrentLine.split("=");
				id = Integer.parseInt(text[0]);	
				if(i == 0) {
					getBag(0, id, sCurrentLine);
				}
				else if( i == 1) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 2) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 3) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 4) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 5) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 6) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 7) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 8) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 9) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 10) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 11) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 12) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 13) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 14) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 15) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 16) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 17) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 18) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 19) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 20) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 21) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 22) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 23) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 24) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 25) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 26) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 27) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 28) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 29) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 30) {
					getBag(i, id, sCurrentLine);
				}
				else if( i == 31) {
					getBag(i, id, sCurrentLine);
				}
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
	public static void setBagItems() throws FileNotFoundException {
		try {
			String content = "";
			File file = new File(ClassSelectFrame.bagTxt());
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(Mideas.bag().getBag(i) != null) {
						if(Mideas.bag().getBag(i) instanceof Item) {
							content+= Mideas.bag().getBag(i).getId()+"="+Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i))+System.lineSeparator();
						}
						else {
							content+= Mideas.bag().getBag(i).getId()+System.lineSeparator();
						}
					}
					else {
						content+= "0"+System.lineSeparator();
					}
					i++;
				}
				bw.write(content);
				bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getEquippedItems() throws FileNotFoundException {
		BufferedReader br = null;
		int i = 0;
		int id;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(ClassSelectFrame.classTxt()));
			while ((sCurrentLine = br.readLine()) != null) {
				id = Integer.parseInt(sCurrentLine);	
				if(i == 0) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isHead()) {
						Mideas.joueur1().setStuff(i, getHead(id));
					}
				}
				else if( i == 1) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isNecklace()) {
						Mideas.joueur1().setStuff(i, getNecklace(id));
					}
				}
				else if( i == 2) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isShoulders()) {
						Mideas.joueur1().setStuff(i, getShoulders(id));
					}
				}
				else if( i == 3) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isBack()) {
						Mideas.joueur1().setStuff(i, getBack(id));
					}
				}
				else if( i == 4) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isChest()) {
						Mideas.joueur1().setStuff(i, getChest(id));
					}
				}
				else if( i == 5) {
				}
				else if( i == 6) {
				}
				else if( i == 7) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isWrists()) {
						Mideas.joueur1().setStuff(i, getWrists(id));
					}
				}
				else if( i == 8) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isGloves()) {
						Mideas.joueur1().setStuff(i, getGloves(id));
					}
				}
				else if( i == 9) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isBelt()) {
						Mideas.joueur1().setStuff(i, getBelt(id));
					}
				}
				else if( i == 10) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isLeggings()) {
						Mideas.joueur1().setStuff(i, getLeggings(id));
					}
				}
				else if( i == 11) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isBoots()) {
						Mideas.joueur1().setStuff(i, getBoots(id));
					}
				}
				else if( i == 12) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isRing()) {
						Mideas.joueur1().setStuff(i, getRing(id));
					}
				}
				else if( i == 13) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isRing()) {
						Mideas.joueur1().setStuff(i, getRing2(id));
					}
				}
				else if( i == 14) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isTrinket()) {
						Mideas.joueur1().setStuff(i, getTrinket(id));
					}
				}
				else if( i == 15) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isTrinket()) {
						Mideas.joueur1().setStuff(i, getTrinket2(id));
					}
				}
				else if( i == 16) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isMainHand()) {
						Mideas.joueur1().setStuff(i, getMainHand(id));
					}
				}
				else if( i == 17) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isOffHand()) {
						Mideas.joueur1().setStuff(i, getOffHand(id));
					}
				}
				else if( i == 18) {
					if(CharacterStuff.getStuff(id) != null && CharacterStuff.getStuff(id).isRanged()) {
						Mideas.joueur1().setStuff(i, getRanged(id));
					}
				}
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
	
	public static void getShopItems() throws FileNotFoundException {
		BufferedReader br = null;
		int i = 0;
		int id;
		try {
			String sCurrentLine;
			if(ShopFrame.getShopPage().equals("shopPage1")) {
				br = new BufferedReader(new FileReader(ClassSelectFrame.shopPage1Txt()));
			}
			else if(ShopFrame.getShopPage().equals("shopPage2")) {
				br = new BufferedReader(new FileReader(ClassSelectFrame.shopPage2Txt()));
			}
			else if(ShopFrame.getShopPage().equals("shopPage3")) {
				br = new BufferedReader(new FileReader(ClassSelectFrame.shopPage3Txt()));
			}
			while ((sCurrentLine = br.readLine()) != null) {
				id = Integer.parseInt(sCurrentLine);	
				if(i == 0) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 1) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 2) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 3) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 4) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 5) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 6) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 7) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 8) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
				else if(i == 9) {
					Mideas.shop().setStuff(i, getStuff(id));
				}
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
	
	public static void setEquippedItems() throws FileNotFoundException {
		try {
			String content = "";
			File file = new File(ClassSelectFrame.classTxt());
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				int i = 0;
				while(i < Mideas.joueur1().getStuff().length) {
					if(Mideas.joueur1().getStuff(i) != null) {
						content+= Mideas.joueur1().getStuff(i).getId()+System.lineSeparator();
					}
					else {
						content+= "0"+System.lineSeparator();
					}
					i++;
				}
				bw.write(content);
				bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void calcStuffStats() {
		if(Mideas.joueur1() != null) {
			int i = 0;
			while(i < Mideas.joueur1().getStuff().length) {
				if(Mideas.joueur1().getStuff(i) != null) {
					Mideas.joueur1().setStuffStamina(Mideas.joueur1().getStuff(i).getStamina());
					Mideas.joueur1().setStuffCritical(Mideas.joueur1().getStuff(i).getCritical());
					Mideas.joueur1().setStuffArmor(Mideas.joueur1().getStuff(i).getArmor());
					Mideas.joueur1().setStuffMana(Mideas.joueur1().getStuff(i).getMana());
					Mideas.joueur1().setStuffStrength(Mideas.joueur1().getStuff(i).getStrength());
				}
				i++;
			}
		}
	}
	
	private static void getBag(int i, int id, String sCurrentLine) {
		Mideas.bag().setBag(i, (getStuff(id)));
		if(Mideas.bag().getBag(i) instanceof Item) {
			String number[] = sCurrentLine.split("=");
			Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), Integer.parseInt(number[1]));
		}
	}
}
