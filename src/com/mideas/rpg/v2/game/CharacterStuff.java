package com.mideas.rpg.v2.game;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class CharacterStuff {

	private static int numberPieceLoaded;
	private static int numberBagPieceLoaded;
	/*private static Stuff getStuff(int id) {
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
	}*/
	
	/*public static Texture getSprite(int id) {
		if(id == 1) {
			return Sprites.super_healing_potion;
		}
		else if(id == 2) {
			return Sprites.linen_cloth;
		}
		else if(id == 1001) {
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
		else if(id == 2001) {
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
	}*/
	
	/*public static Texture getBagSprite(int id) {
		if(id == 1) {
			return Sprites.bag_super_healing_potion;
		}
		else if(id == 2) {
			return Sprites.bag_linen_cloth;
		}
		else if(id == 1001) {
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
		else if(id == 2001) {
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
	}*/
	
	/*public static Texture getShopSprite(int id) {
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
	}*/
	
	/*public static Head getHead(int id) {
		return (Head)StuffManager.getClone(id);
	}
	
	public static Necklace getNecklace(int id) {
		return (Necklace)StuffManager.getClone(id);
	}
	
	public static Shoulders getShoulders(int id) {
		return (Shoulders)StuffManager.getClone(id);
	}

	public static Back getBack(int id) {
		return (Back)StuffManager.getClone(id);
	}

	public static Chest getChest(int id) {
		return (Chest)StuffManager.getClone(id);
	}
	
	public static Wrists getWrists(int id) {
		return (Wrists)StuffManager.getClone(id);
	}
	
	public static Gloves getGloves(int id) {
		return (Gloves)StuffManager.getClone(id);
	}

	public static Belt getBelt(int id) {
		return (Belt)StuffManager.getClone(id);
	}
	
	public static Leggings getLeggings(int id) {
		return (Leggings)StuffManager.getClone(id);
	}

	public static Boots getBoots(int id) {
		return (Boots)StuffManager.getClone(id);
	}
	
	public static Ring getRing(int id) {
		return (Ring)StuffManager.getClone(id);
	}
	
	public static Ring2 getRing2(int id) {
		return (Ring2)StuffManager.getClone(id);
	}
	
	public static Trinket getTrinket(int id) {
		return (Trinket)StuffManager.getClone(id);
	}
	
	public static Trinket2 getTrinket2(int id) {
		return (Trinket2)StuffManager.getClone(id);
	}
	
	public static MainHand getMainHand(int id) {
		return (MainHand)StuffManager.getClone(id);
	}
	
	public static OffHand getOffHand(int id) {
		return (OffHand)StuffManager.getClone(id);
	}
	
	public static Ranged getRanged(int id) {
		return (Ranged)StuffManager.getClone(id);
	}*/
	
	public static void getBagItems() throws FileNotFoundException, SQLException {
	int i = 0;
	int id;
	int number;
	JDOStatement statement = Mideas.getJDO().prepare("SELECT slot1, numberstack1, slot2, numberstack2, slot3, numberstack3, slot4, numberstack4, slot5, numberstack5, slot6, numberstack6, slot7, numberstack7, slot8, numberstack8, slot9, numberstack9, slot10, numberstack10, slot11, numberstack11, slot12, numberstack12, slot13, numberstack13, slot14, numberstack14, slot15, numberstack15, slot16, numberstack16, slot17, numberstack17, slot18, numberstack18, slot19, numberstack19, slot20, numberstack20, slot21, numberstack21, slot22, numberstack22, slot23, numberstack23, slot24, numberstack24, slot25, numberstack25, slot26, numberstack26, slot27, numberstack27, slot28, numberstack28, slot29, numberstack29, slot30, numberstack30, slot31, numberstack31, slot32, numberstack32 FROM bag WHERE class = ?");
	statement.putString(Mideas.joueur1().getClasse());
	statement.execute();
	if(statement.fetch()) {
		while(i < 32) {
			id = statement.getInt();
			number = statement.getInt();
			if(StuffManager.exists(id)) {
				Mideas.bag().setBag(i, StuffManager.getClone(id));
			}
			if(PotionManager.exists(id)) {
				if(PotionManager.getPotion(id).getItemType() == ItemType.POTION) {
					Mideas.bag().setBag(i, PotionManager.getClone(id));
					Mideas.bag().getNumberStack().put(Mideas.bag().getBag(i), number);
				}
			}
			else if(id == 0) {
				Mideas.bag().setBag(i, null);
			}
			if(id != 0) {
				numberBagPieceLoaded++;
			}
			i++;
		}
	}
		/*BufferedReader br = null;
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
		}*/
	}
	public static void setBagItems() throws FileNotFoundException, SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE bag SET slot1 = ?, numberStack1 = ?, slot2 = ?, numberStack2 = ?, slot3 = ?, numberStack3 = ?, slot4 = ?, numberStack4 = ?, slot5 = ?, numberStack5 = ?, slot6 = ?, numberStack6 = ?, slot7 = ?, numberStack7 = ?, slot8 = ?, numberStack8 = ?, slot9 = ?, numberStack9 = ?, slot10 = ?, numberStack10 = ?, slot11 = ?, numberStack11 = ?, slot12 = ?, numberStack12 = ?, slot13 = ?, numberStack13 = ?, slot14 = ?, numberStack14 = ?, slot15 = ?, numberStack15 = ?, slot16 = ?, numberStack16 = ?, slot17 = ?, numberStack17 = ?, slot18 = ?, numberStack18 = ?, slot19 = ?, numberStack19 = ?, slot20 = ?, numberStack20 = ?, slot21 = ?, numberStack21 = ?, slot22 = ?, numberStack22 = ?, slot23 = ?, numberStack23 = ?, slot24 = ?, numberStack24 = ?, slot25 = ?, numberStack25 = ?, slot26 = ?, numberStack26 = ?, slot27 = ?, numberStack27 = ?, slot28 = ?, numberStack28 = ?, slot29 = ?, numberStack29 = ?, slot30 = ?, numberStack30 = ?, slot31 = ?, numberStack31 = ?, slot32 = ?, numberStack32 = ? WHERE class = ?");
		int i = 0;
		while(i < 32) {
			Item tempBag = Mideas.bag().getBag(i);
			if(tempBag == null) {
				statement.putInt(0);
				statement.putInt(0);
			}
			else if(tempBag.getItemType() == ItemType.STUFF) {
				statement.putInt(tempBag.getId());
				statement.putInt(0);
			}
			else if(tempBag.getItemType() == ItemType.ITEM || tempBag.getItemType() == ItemType.POTION) {
				statement.putInt(tempBag.getId());
				statement.putInt(Mideas.bag().getNumberStack().get(tempBag));
			}
			i++;
		}
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
		/*try {	
			String content = "";
			File file = new File(ClassSelectFrame.bagTxt());
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				i = 0;
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
				//bbw.write(content);
				bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	public static void setEquippedItems() throws FileNotFoundException, SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE character_stuff SET head = ?, necklace = ?, shoulders = ?, back = ?, chest = ?, useless =?, useless2 =?, wrists = ?, gloves = ?, belt = ?, leggings = ?, boots = ?, ring = ?, ring2 = ?, trinket = ?, trinket2 = ?, mainhand = ?, offhand = ?, ranged = ? WHERE class = ?");
		int i = 0;
		while(i < 19) {
			Stuff tempStuff = Mideas.joueur1().getStuff(i);
			if(tempStuff == null) {
				statement.putInt(0);
			}
			else {
				statement.putInt(tempStuff.getId());
			}
			i++;
		}
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
		/*try {
			String content = "";
			File file = new File(ClassSelectFrame.classTxt());
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				i = 0;
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
		}*/
	}
	
	public static void getEquippedItems() throws FileNotFoundException, SQLException {
		//BufferedReader br = null;
		//int i = 0;
		int id;
		JDOStatement statement = Mideas.getJDO().prepare("SELECT head, necklace, shoulders, back, chest, wrists, gloves, belt, leggings, boots, ring, ring2, trinket, trinket2, mainhand, offhand, ranged FROM character_stuff WHERE class = ?");
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
		if(statement.fetch()) {
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isHead()) {
				Mideas.joueur1().setStuff(0, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isNecklace()) {
				Mideas.joueur1().setStuff(1, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isShoulders()) {
				Mideas.joueur1().setStuff(2, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isBack()) {
				Mideas.joueur1().setStuff(3, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isChest()) {
				Mideas.joueur1().setStuff(4, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isWrists()) {
				Mideas.joueur1().setStuff(7, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isGloves()) {
				Mideas.joueur1().setStuff(8, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isBelt()) {
				Mideas.joueur1().setStuff(9, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isLeggings()) {
				Mideas.joueur1().setStuff(10, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isBoots()) {
				Mideas.joueur1().setStuff(11, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isRing()) {
				Mideas.joueur1().setStuff(12, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isRing()) {
				Mideas.joueur1().setStuff(13, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isTrinket()) {
				Mideas.joueur1().setStuff(14, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isTrinket()) {
				Mideas.joueur1().setStuff(15, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isMainHand()) {
				Mideas.joueur1().setStuff(16, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isOffHand()) {
				Mideas.joueur1().setStuff(17, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isRanged()) {
				Mideas.joueur1().setStuff(18, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
		}
		else {
			System.out.println("statement error (stuff load)");
		}
		/*try {
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
		}*/
	}
	
	/*public static void getShopItems() throws FileNotFoundException {
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
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 1) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 2) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 3) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 4) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 5) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 6) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 7) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 8) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
				}
				else if(i == 9) {
					Mideas.shop().setStuff(i, StuffManager.getClone(id));
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
	}*/

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
	
	public static int getNumberBagPieceLoaded() {
		return numberBagPieceLoaded;
	}
	
	public static int getNumberPieceLoaded() {
		return numberPieceLoaded;
	}
	
	/*private static void getBag(int i, int id, String sCurrentLine) {
		Mideas.bag().setBag(i, (StuffManager.getClone(id)));
		if(Mideas.bag().getBag(i) instanceof Item) {
			String number[] = sCurrentLine.split("=");
			Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), ContainerFrame.getSlotItem(Mideas.bag().getBag(i)), Integer.parseInt(number[1]));
		}
	}*/
}
