package com.mideas.rpg.v2.game;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Bag;
import com.mideas.rpg.v2.game.item.stuff.BagManager;
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
		JDOStatement statement = Mideas.getJDO().prepare("SELECT slot1, numberstack1, slot2, numberstack2, slot3, numberstack3, slot4, numberstack4, slot5, numberstack5, slot6, numberstack6, slot7, numberstack7, slot8, numberstack8, slot9, numberstack9, slot10, numberstack10, slot11, numberstack11, slot12, numberstack12, slot13, numberstack13, slot14, numberstack14, slot15, numberstack15, slot16, numberstack16, slot17, numberstack17, slot18, numberstack18, slot19, numberstack19, slot20, numberstack20, slot21, numberstack21, slot22, numberstack22, slot23, numberstack23, slot24, numberstack24, slot25, numberstack25, slot26, numberstack26, slot27, numberstack27, slot28, numberstack28, slot29, numberstack29, slot30, numberstack30, slot31, numberstack31, slot32, numberstack32 , slot33, numberstack33, slot34, numberstack34, slot35, numberstack35, slot36, numberstack36, slot37, numberstack37, slot38, numberstack38, slot39, numberstack39, slot40, numberstack40, slot41, numberstack41, slot42, numberstack42, slot43, numberstack43, slot44, numberstack44, slot45, numberstack45, slot46, numberstack46, slot47, numberstack47, slot48, numberstack48, slot49, numberstack49, slot50, numberstack50, slot51, numberstack51, slot52, numberstack52, slot53, numberstack53, slot54, numberstack54, slot55, numberstack55, slot56, numberstack56, slot57, numberstack57, slot58, numberstack58, slot59, numberstack59, slot60, numberstack60, slot61, numberstack61, slot62, numberstack62, slot63, numberstack63, slot64, numberstack64, slot65, numberstack65, slot66, numberstack66, slot67, numberstack67, slot68, numberstack68, slot69, numberstack69, slot70, numberstack70, slot71, numberstack71, slot72, numberstack72, slot73, numberstack73, slot74, numberstack74, slot75, numberstack75, slot76, numberstack76, slot77, numberstack77, slot78, numberstack78, slot79, numberstack79, slot80, numberstack80, slot81, numberstack81, slot82, numberstack82, slot83, numberstack83, slot84, numberstack84, slot85, numberstack85, slot86, numberstack86, slot87, numberstack87, slot88, numberstack88, slot89, numberstack89, slot90, numberstack90, slot91, numberstack91, slot92, numberstack92, slot93, numberstack93, slot94, numberstack94, slot95, numberstack95, slot96, numberstack96 FROM bag WHERE class = ?");
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
		if(statement.fetch()) {
			while(i < 96) {
				id = statement.getInt();
				number = statement.getInt();
				if(i < Mideas.bag().getBag().length+16) {
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
				}
				i++;
			}
		}
	}
	
	public static void setBagItems() throws FileNotFoundException, SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE bag SET slot1 = ?, numberStack1 = ?, slot2 = ?, numberStack2 = ?, slot3 = ?, numberStack3 = ?, slot4 = ?, numberStack4 = ?, slot5 = ?, numberStack5 = ?, slot6 = ?, numberStack6 = ?, slot7 = ?, numberStack7 = ?, slot8 = ?, numberStack8 = ?, slot9 = ?, numberStack9 = ?, slot10 = ?, numberStack10 = ?, slot11 = ?, numberStack11 = ?, slot12 = ?, numberStack12 = ?, slot13 = ?, numberStack13 = ?, slot14 = ?, numberStack14 = ?, slot15 = ?, numberStack15 = ?, slot16 = ?, numberStack16 = ?, slot17 = ?, numberStack17 = ?, slot18 = ?, numberStack18 = ?, slot19 = ?, numberStack19 = ?, slot20 = ?, numberStack20 = ?, slot21 = ?, numberStack21 = ?, slot22 = ?, numberStack22 = ?, slot23 = ?, numberStack23 = ?, slot24 = ?, numberStack24 = ?, slot25 = ?, numberStack25 = ?, slot26 = ?, numberStack26 = ?, slot27 = ?, numberStack27 = ?, slot28 = ?, numberStack28 = ?, slot29 = ?, numberStack29 = ?, slot30 = ?, numberStack30 = ?, slot31 = ?, numberStack31 = ?, slot32 = ?, numberStack32 = ?, slot33 = ?, numberstack33 = ?, slot34 = ?, numberstack34 = ?, slot35 = ?, numberstack35 = ?, slot36 = ?, numberstack36 = ?, slot37 = ?, numberstack37 = ?, slot38 = ?, numberstack38 = ?, slot39 = ?, numberstack39 = ?, slot40 = ?, numberstack40 = ?, slot41 = ?, numberstack41 = ?, slot42 = ?, numberstack42 = ?, slot43 = ?, numberstack43 = ?, slot44 = ?, numberstack44 = ?, slot45 = ?, numberstack45 = ?, slot46 = ?, numberstack46 = ?, slot47 = ?, numberstack47 = ?, slot48 = ?, numberstack48 = ?, slot49 = ?, numberstack49 = ?, slot50 = ?, numberstack50 = ?, slot51 = ?, numberstack51 = ?, slot52 = ?, numberstack52 = ?, slot53 = ?, numberstack53 = ?, slot54 = ?, numberstack54 = ?, slot55 = ?, numberstack55 = ?, slot56 = ?, numberstack56 = ?, slot57 = ?, numberstack57 = ?, slot58 = ?, numberstack58 = ?, slot59 = ?, numberstack59 = ?, slot60 = ?, numberstack60 = ?, slot61 = ?, numberstack61 = ?, slot62 = ?, numberstack62 = ?, slot63 = ?, numberstack63 = ?, slot64 = ?, numberstack64 = ?, slot65 = ?, numberstack65 = ?, slot66 = ?, numberstack66 = ?, slot67 = ?, numberstack67 = ?, slot68 = ?, numberstack68 = ?, slot69 = ?, numberstack69 = ?, slot70 = ?, numberstack70 = ?, slot71 = ?, numberstack71 = ?, slot72 = ?, numberstack72 = ?, slot73 = ?, numberstack73 = ?, slot74 = ?, numberstack74 = ?, slot75 = ?, numberstack75 = ?, slot76 = ?, numberstack76 = ?, slot77 = ?, numberstack77 = ?, slot78 = ?, numberstack78 = ?, slot79 = ?, numberstack79 = ?, slot80 = ?, numberstack80 = ?, slot81 = ?, numberstack81 = ?, slot82 = ?, numberstack82 = ?, slot83 = ?, numberstack83 = ?, slot84 = ?, numberstack84 = ?, slot85 = ?, numberstack85 = ?, slot86 = ?, numberstack86 = ?, slot87 = ?, numberstack87 = ?, slot88 = ?, numberstack88 = ?, slot89 = ?, numberstack89 = ?, slot90 = ?, numberstack90 = ?, slot91 = ?, numberstack91 = ?, slot92 = ?, numberstack92 = ?, slot93 = ?, numberstack93 = ?, slot94 = ?, numberstack94 = ?, slot95 = ?, numberstack95 = ?, slot96 = ?, numberstack96 = ? WHERE class = ?");
		int i = 0;
		while(i < 96) {
			if(i < Mideas.bag().getBag().length)      {
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
			}
			else {
				statement.putInt(0);
				statement.putInt(0);
			}
			i++;
		}
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
	}
	
	public static void getEquippedBags() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT slot1, slot2, slot3, slot4 FROM character_containers WHERE class = ?");
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
		int i = 0;
		int id;
		if(statement.fetch()) {
			while(i < Mideas.bag().getEquippedBag().length) {
				id = statement.getInt();
				if(BagManager.exists(id)) {
					Mideas.bag().setEquippedBag(i, BagManager.getClone(id));
				}
				else {
					Mideas.bag().setEquippedBag(i, null);
				}
				i++;
			}
		}
	}
	
	public static void setEquippedBags() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE character_containers SET slot1 = ?, slot2 = ?, slot3 = ?, slot4 = ? WHERE class = ?");
		int i = 0;
		while(i < Mideas.bag().getEquippedBag().length) {
			Bag tempBag = Mideas.bag().getEquippedBag(i);
			if(tempBag == null) {
				statement.putInt(0);
			}
			else {
				statement.putInt(tempBag.getId());
			}
			i++;
		}
		statement.putString(Mideas.joueur1().getClasse());
		statement.execute();
	}
	
	public static void setEquippedItems() throws FileNotFoundException, SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE character_stuff SET head = ?, necklace = ?, shoulders = ?, back = ?, chest = ?, useless = ?, useless2 = ?, wrists = ?, gloves = ?, belt = ?, leggings = ?, boots = ?, ring = ?, ring2 = ?, trinket = ?, trinket2 = ?, mainhand = ?, offhand = ?, ranged = ? WHERE class = ?");
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length-2) {
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
