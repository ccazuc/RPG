package com.mideas.rpg.v2.game;

import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.bag.BagManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class CharacterStuff {

	private static int numberPieceLoaded;
	private static int numberBagPieceLoaded;
	private static String getBagRequest;
	private static String setBagRequest;
	
	public static void initSQLRequest() {
		int x = 1;
		int i = 1;
		StringBuilder builderSetBagItemsRequest = new StringBuilder();
		while(i < 97) {
			x = 1;
			builderSetBagItemsRequest.append("slot"+Integer.toString(i)+" = ?, numberstack"+Integer.toString(i)+" = ?, ");
			while(x <= 3) {
				if(i == 96 && x == 3) {
					builderSetBagItemsRequest.append("slot"+Integer.toString(i)+"_gem"+Integer.toString(x)+" = ? ");
				}
				else {
					builderSetBagItemsRequest.append("slot"+Integer.toString(i)+"_gem"+Integer.toString(x)+" = ?, ");
				}
				x++;
			}
			i++;
		}
		setBagRequest = builderSetBagItemsRequest.toString();
		x = 1;
		i = 1;
		StringBuilder builderGetBagItemsRequest = new StringBuilder();
		while(i < 97) {
			x = 1;
			builderGetBagItemsRequest.append("slot"+Integer.toString(i)+", numberstack"+Integer.toString(i)+", ");
			while(x <= 3) {
				if(i == 96 && x == 3) {
					builderGetBagItemsRequest.append("slot"+Integer.toString(i)+"_gem"+Integer.toString(x)+" ");
				}
				else {
					builderGetBagItemsRequest.append("slot"+Integer.toString(i)+"_gem"+Integer.toString(x)+", ");
				}
				x++;
			}
			i++;
		}
		getBagRequest = builderGetBagItemsRequest.toString();
	}
	
	public static void getBagItems() throws SQLException {
		int i = 1;
		int id;
		int number;
		int gem1Id;
		int gem2Id;
		int gem3Id;
		String request = "";
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append(getBagRequest);
		builder.append("FROM bag WHERE character_id = ?");
		request = builder.toString();
		//JDOStatement statement = Mideas.getJDO().prepare("SELECT slot1, numberstack1, slot1_gem1, slot1_gem2, slot1_gem3, slot2, numberstack2, slot2_gem1, slot2_gem2, slot2_gem3, slot3, numberstack3, slot3_gem1, slot3_gem2, slot3_gem3, slot4, numberstack4, slot4_gem1, slot4_gem2, slot4_gem3, slot5, numberstack5, slot5_gem1, slot5_gem2, slot5_gem3, slot6, numberstack6, slot6_gem1, slot6_gem2,slot6_gem3, slot7, numberstack7, slot7_gem1, slot7_gem2, slot7_gem3, slot8, numberstack8, slot8_gem1, slot8_gem2, slot8_gem3, slot9, numberstack9, slot9_gem1, slot9_gem2, slot9_gem3, slot10, numberstack10, slot10_gem1, slot10_gem2, slot10_gem3, slot11, numberstack11, slot11_gem1, slot11_gem2, slot11_gem3, slot12, numberstack12, slot12_gem1, slot12_gem2, slot12_gem3, slot13, numberstack13, slot13_gem1, slot13_gem2, slot13_gem3, slot14, numberstack14, slot14_gem1, slot14_gem2, slot14_gem3, slot15, numberstack15, slot15_gem1, slot15_gem2, slot15_gem3, slot16, numberstack16, slot16_gem1, slot16_gem2, slot16_gem3, slot17, numberstack17, slot17_gem1, slot17_gem2, slot17_gem3, slot18, numberstack18, slot18_gem1, slot18_gem2, slot18_gem3, slot19, numberstack19, slot19_gem1, slot19_gem2, slot19_gem3, slot20, numberstack20, slot20_gem1, slot20_gem2, slot20_gem3, slot21, numberstack21, slot21_gem1, slot21_gem2, slot21_gem3, slot22, numberstack22, slot22_gem1, slot22_gem2, slot22_gem3, slot23, numberstack23, slot23_gem1, slot23_gem2, slot23_gem3, slot24, numberstack24, slot24_gem1, slot24_gem2, slot24_gem3, slot25, numberstack25, slot25_gem1, slot25_gem2, slot25_gem3, slot26, numberstack26, slot26_gem1, slot26_gem2, slot26_gem3, slot27, numberstack27, slot27_gem1, slot27_gem2, slot27_gem3, slot28, numberstack28, slot28_gem1, slot28_gem2, slot28_gem3, slot29, numberstack29, slot29_gem1, slot29_gem2, slot29_gem3, slot30, numberstack30, slot30_gem1, slot30_gem2, slot30_gem3, slot31, numberstack31, slot31_gem1, slot31_gem2, slot31_gem3, slot32, numberstack32 , slot32_gem1, slot32_gem2, slot32_gem3, slot33, numberstack33, slot33_gem1, slot33_gem2, slot33_gem3, slot34, numberstack34, slot34_gem1, slot34_gem2, slot34_gem3, slot35, numberstack35, slot35_gem1, slot35_gem2, slot35_gem3, slot36, numberstack36, slot36_gem1, slot36_gem2, slot36_gem3, slot37, numberstack37, slot37_gem1, slot37_gem2, slot37_gem3, slot38, numberstack38, slot39, numberstack39, slot40, numberstack40, slot41, numberstack41, slot42, numberstack42, slot43, numberstack43, slot44, numberstack44, slot45, numberstack45, slot46, numberstack46, slot47, numberstack47, slot48, numberstack48, slot49, numberstack49, slot50, numberstack50, slot51, numberstack51, slot52, numberstack52, slot53, numberstack53, slot54, numberstack54, slot55, numberstack55, slot56, numberstack56, slot57, numberstack57, slot58, numberstack58, slot59, numberstack59, slot60, numberstack60, slot61, numberstack61, slot62, numberstack62, slot63, numberstack63, slot64, numberstack64, slot65, numberstack65, slot66, numberstack66, slot67, numberstack67, slot68, numberstack68, slot69, numberstack69, slot70, numberstack70, slot71, numberstack71, slot72, numberstack72, slot73, numberstack73, slot74, numberstack74, slot75, numberstack75, slot76, numberstack76, slot77, numberstack77, slot78, numberstack78, slot79, numberstack79, slot80, numberstack80, slot81, numberstack81, slot82, numberstack82, slot83, numberstack83, slot84, numberstack84, slot85, numberstack85, slot86, numberstack86, slot87, numberstack87, slot88, numberstack88, slot89, numberstack89, slot90, numberstack90, slot91, numberstack91, slot92, numberstack92, slot93, numberstack93, slot94, numberstack94, slot95, numberstack95, slot96, numberstack96 FROM bag WHERE class = ?");
		JDOStatement statement = Mideas.getJDO().prepare(request);
		i = 0;
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
		if(statement.fetch()) {
			while(i < 96) {
				id = statement.getInt();
				number = statement.getInt();
				gem1Id = statement.getInt();
				gem2Id = statement.getInt();
				gem3Id = statement.getInt();
				if(i < Mideas.bag().getBag().length) {
					if(StuffManager.exists(id)) {
						Mideas.bag().setBag(i, StuffManager.getClone(id));
						Stuff temp = (Stuff)Mideas.bag().getBag(i);
						if(GemManager.exists(gem1Id) && temp.getGemSlot1() != GemColor.NONE) {
							temp.setEquippedGem1(GemManager.getClone(gem1Id));
						}
						if(GemManager.exists(gem2Id) && temp.getGemSlot2() != GemColor.NONE) {
							temp.setEquippedGem2(GemManager.getClone(gem2Id));
						}
						if(GemManager.exists(gem3Id) && temp.getGemSlot3() != GemColor.NONE) {
							temp.setEquippedGem3(GemManager.getClone(gem3Id));
						}
						Mideas.bag().setBag(i, temp);
						((Stuff)Mideas.bag().getBag(i)).checkBonusTypeActivated();
						numberBagPieceLoaded++;
					}
					else if(PotionManager.exists(id)) {
						Mideas.bag().setBag(i, PotionManager.getClone(id));
						Mideas.bag().getNumberStack().put(Mideas.bag().getBag(i), number);
						numberBagPieceLoaded++;
					}
					else if(WeaponManager.exists(id)) {
						Mideas.bag().setBag(i, WeaponManager.getClone(id));
						Stuff temp = (Stuff)Mideas.bag().getBag(i);
						if(GemManager.exists(gem1Id)) {
							temp.setEquippedGem1(GemManager.getClone(gem1Id));
						}
						if(GemManager.exists(gem2Id)) {
							temp.setEquippedGem2(GemManager.getClone(gem2Id));
						}
						if(GemManager.exists(gem3Id)) {
							temp.setEquippedGem3(GemManager.getClone(gem3Id));
						}
						Mideas.bag().setBag(i, temp);
						numberBagPieceLoaded++;
					}
					else if(GemManager.exists(id)) {
						Mideas.bag().setBag(i, GemManager.getClone(id));
						numberBagPieceLoaded++;
					}
					else {
						Mideas.bag().setBag(i, null);
					}
				}
				i++;
			}
		}
	}
	
	public static void setBagItems() throws SQLException {
		String request = "";
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE bag SET ");
		int i = 1;
		i = 0;
		builder.append(setBagRequest);
		builder.append("WHERE character_id = ?");
		request = builder.toString();
		//JDOStatement statement = Mideas.getJDO().prepare("UPDATE bag SET slot1 = ?, numberStack1 = ?, slot2 = ?, numberStack2 = ?, slot3 = ?, numberStack3 = ?, slot4 = ?, numberStack4 = ?, slot5 = ?, numberStack5 = ?, slot6 = ?, numberStack6 = ?, slot7 = ?, numberStack7 = ?, slot8 = ?, numberStack8 = ?, slot9 = ?, numberStack9 = ?, slot10 = ?, numberStack10 = ?, slot11 = ?, numberStack11 = ?, slot12 = ?, numberStack12 = ?, slot13 = ?, numberStack13 = ?, slot14 = ?, numberStack14 = ?, slot15 = ?, numberStack15 = ?, slot16 = ?, numberStack16 = ?, slot17 = ?, numberStack17 = ?, slot18 = ?, numberStack18 = ?, slot19 = ?, numberStack19 = ?, slot20 = ?, numberStack20 = ?, slot21 = ?, numberStack21 = ?, slot22 = ?, numberStack22 = ?, slot23 = ?, numberStack23 = ?, slot24 = ?, numberStack24 = ?, slot25 = ?, numberStack25 = ?, slot26 = ?, numberStack26 = ?, slot27 = ?, numberStack27 = ?, slot28 = ?, numberStack28 = ?, slot29 = ?, numberStack29 = ?, slot30 = ?, numberStack30 = ?, slot31 = ?, numberStack31 = ?, slot32 = ?, numberStack32 = ?, slot33 = ?, numberstack33 = ?, slot34 = ?, numberstack34 = ?, slot35 = ?, numberstack35 = ?, slot36 = ?, numberstack36 = ?, slot37 = ?, numberstack37 = ?, slot38 = ?, numberstack38 = ?, slot39 = ?, numberstack39 = ?, slot40 = ?, numberstack40 = ?, slot41 = ?, numberstack41 = ?, slot42 = ?, numberstack42 = ?, slot43 = ?, numberstack43 = ?, slot44 = ?, numberstack44 = ?, slot45 = ?, numberstack45 = ?, slot46 = ?, numberstack46 = ?, slot47 = ?, numberstack47 = ?, slot48 = ?, numberstack48 = ?, slot49 = ?, numberstack49 = ?, slot50 = ?, numberstack50 = ?, slot51 = ?, numberstack51 = ?, slot52 = ?, numberstack52 = ?, slot53 = ?, numberstack53 = ?, slot54 = ?, numberstack54 = ?, slot55 = ?, numberstack55 = ?, slot56 = ?, numberstack56 = ?, slot57 = ?, numberstack57 = ?, slot58 = ?, numberstack58 = ?, slot59 = ?, numberstack59 = ?, slot60 = ?, numberstack60 = ?, slot61 = ?, numberstack61 = ?, slot62 = ?, numberstack62 = ?, slot63 = ?, numberstack63 = ?, slot64 = ?, numberstack64 = ?, slot65 = ?, numberstack65 = ?, slot66 = ?, numberstack66 = ?, slot67 = ?, numberstack67 = ?, slot68 = ?, numberstack68 = ?, slot69 = ?, numberstack69 = ?, slot70 = ?, numberstack70 = ?, slot71 = ?, numberstack71 = ?, slot72 = ?, numberstack72 = ?, slot73 = ?, numberstack73 = ?, slot74 = ?, numberstack74 = ?, slot75 = ?, numberstack75 = ?, slot76 = ?, numberstack76 = ?, slot77 = ?, numberstack77 = ?, slot78 = ?, numberstack78 = ?, slot79 = ?, numberstack79 = ?, slot80 = ?, numberstack80 = ?, slot81 = ?, numberstack81 = ?, slot82 = ?, numberstack82 = ?, slot83 = ?, numberstack83 = ?, slot84 = ?, numberstack84 = ?, slot85 = ?, numberstack85 = ?, slot86 = ?, numberstack86 = ?, slot87 = ?, numberstack87 = ?, slot88 = ?, numberstack88 = ?, slot89 = ?, numberstack89 = ?, slot90 = ?, numberstack90 = ?, slot91 = ?, numberstack91 = ?, slot92 = ?, numberstack92 = ?, slot93 = ?, numberstack93 = ?, slot94 = ?, numberstack94 = ?, slot95 = ?, numberstack95 = ?, slot96 = ?, numberstack96 = ? WHERE class = ?");
		JDOStatement statement = Mideas.getJDO().prepare(request);
		while(i < 96) {
			if(i < Mideas.bag().getBag().length) {
				Item tempBag = Mideas.bag().getBag(i);
				if(tempBag == null) {
					statement.putInt(0);
					statement.putInt(0);
					statement.putInt(0);
					statement.putInt(0);
					statement.putInt(0);
				}
				else if(tempBag.getItemType() == ItemType.STUFF || tempBag.getItemType() == ItemType.WEAPON) {
					statement.putInt(tempBag.getId());
					statement.putInt(0);
					if(((Stuff)tempBag).getEquippedGem1() == null) {
						statement.putInt(0);
					}
					else {
						statement.putInt((((Stuff)tempBag).getEquippedGem1().getId()));
					}
					if(((Stuff)tempBag).getEquippedGem2() == null) {
						statement.putInt(0);
					}
					else {
						statement.putInt((((Stuff)tempBag).getEquippedGem2().getId()));
					}
					if(((Stuff)tempBag).getEquippedGem3() == null) {
						statement.putInt(0);
					}
					else {
						statement.putInt((((Stuff)tempBag).getEquippedGem3().getId()));
					}
				}
				else if(tempBag.getItemType() == ItemType.BAG || tempBag.getItemType() == ItemType.GEM) {
					statement.putInt(tempBag.getId());
					statement.putInt(0);
					statement.putInt(0);
					statement.putInt(0);
					statement.putInt(0);
				}
				else if(tempBag.getItemType() == ItemType.ITEM || tempBag.getItemType() == ItemType.POTION) {
					if(Mideas.bag().getNumberStack().containsKey(tempBag)) {
						statement.putInt(tempBag.getId());
						statement.putInt(Mideas.bag().getNumberStack().get(tempBag));
						statement.putInt(0);
						statement.putInt(0);
						statement.putInt(0);
					}
					else {
						System.out.println("Error CharacterStuff/setBagItems");
						statement.putInt(0);
						statement.putInt(0);
						statement.putInt(0);
						statement.putInt(0);
						statement.putInt(0);
					}
				}
			}
			else {
				statement.putInt(0);
				statement.putInt(0);
				statement.putInt(0);
				statement.putInt(0);
				statement.putInt(0);
			}
			i++;
		}
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
	}
	
	public static void getEquippedBags() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT slot1, slot2, slot3, slot4 FROM character_containers WHERE character_id = ?");
		statement.putInt(Mideas.getCharacterId());
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
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE character_containers SET slot1 = ?, slot2 = ?, slot3 = ?, slot4 = ? WHERE character_id = ?");
		int i = 0;
		while(i < Mideas.bag().getEquippedBag().length) {
			if(Mideas.bag().getEquippedBag(i) == null) {
				statement.putInt(0);
			}
			else {
				statement.putInt(Mideas.bag().getEquippedBag(i).getId());
			}
			i++;
		}
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
	}
	
	public static void setEquippedItems() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE character_stuff SET head = ?, head_gem1 = ?, head_gem2 = ?, head_gem3 = ?, necklace = ?, necklace_gem1 = ?, necklace_gem2 = ?, necklace_gem3 = ?, shoulders = ?, shoulders_gem1 = ?, shoulders_gem2 = ?, shoulders_gem3 = ?, back = ?, back_gem1 = ?, back_gem2 = ?, back_gem3 = ?, chest = ?, chest_gem1 = ?, chest_gem2 = ?, chest_gem3 = ?, useless = ?, useless_gem1 = ?, useless_gem2 = ?, useless_gem3 = ?, useless2 = ?, useless2_gem1 = ?, useless2_gem2 = ?, useless2_gem3 = ?, wrists = ?, wrists_gem1 = ?, wrists_gem2 = ?, wrists_gem3 = ?, gloves = ?, gloves_gem1 = ?, gloves_gem2 = ?, gloves_gem3 = ?, belt = ?, belt_gem1 = ?, belt_gem2 = ?, belt_gem3 = ?, leggings = ?, leggings_gem1 = ?, leggings_gem2 = ?, leggings_gem3 = ?, boots = ?, boots_gem1 = ?, boots_gem2 = ?, boots_gem3 = ?, ring = ?, ring_gem1 = ?, ring_gem2 = ?, ring_gem3 = ?, ring2 = ?, ring2_gem1 = ?, ring2_gem2 = ?, ring2_gem3 = ?, trinket = ?, trinket_gem1 = ?, trinket_gem2 = ?, trinket_gem3 = ?, trinket2 = ?, trinket2_gem1 = ?, trinket2_gem2 = ?, trinket2_gem3 = ?, mainhand = ?, mainhand_gem1 = ?, mainhand_gem2 = ?, mainhand_gem3 = ?, offhand = ?, offhand_gem1 = ?, offhand_gem2 = ?, offhand_gem3 = ?, ranged = ?, ranged_gem1 = ?, ranged_gem2 = ?, ranged_gem3 = ? WHERE character_id = ?");
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length-1) {
			if(Mideas.joueur1().getStuff(i) == null) {
				statement.putInt(0);
				statement.putInt(0);
				statement.putInt(0);
				statement.putInt(0);
			}
			else {
				statement.putInt(Mideas.joueur1().getStuff(i).getId());
				if(Mideas.joueur1().getStuff(i).getEquippedGem1() != null) {
					statement.putInt(Mideas.joueur1().getStuff(i).getEquippedGem1().getId());
				}
				else {
					statement.putInt(0);
				}
				if(Mideas.joueur1().getStuff(i).getEquippedGem2() != null) {
					statement.putInt(Mideas.joueur1().getStuff(i).getEquippedGem2().getId());
				}
				else {
					statement.putInt(0);
				}	
				if(Mideas.joueur1().getStuff(i).getEquippedGem3() != null) {
					statement.putInt(Mideas.joueur1().getStuff(i).getEquippedGem3().getId());
				}
				else {
					statement.putInt(0);
				}
			}
			i++;
		}
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
	}
	
	/*public static void getEquippedItems() throws SQLException {
		int id;
		int gem1Id;
		int gem2Id;
		int gem3Id;
		Stuff temp;
		JDOStatement statement = Mideas.getJDO().prepare("SELECT head, head_gem1, head_gem2, head_gem3, necklace, necklace_gem1, necklace_gem2, necklace_gem3, shoulders, shoulders_gem1, shoulders_gem2, shoulders_gem3, back, back_gem1, back_gem2, back_gem3, chest, chest_gem1, chest_gem2, chest_gem3, wrists, wrists_gem1, wrists_gem2, wrists_gem3, gloves, gloves_gem1, gloves_gem2, gloves_gem3, belt, belt_gem1, belt_gem2, belt_gem3, leggings, leggings_gem1, leggings_gem2, leggings_gem3, boots, boots_gem1, boots_gem2, boots_gem3, ring, ring2, trinket, trinket2, mainhand, mainhand_gem1, mainhand_gem2, mainhand_gem3, offhand, offhand_gem1, offhand_gem2, offhand_gem3, ranged, ranged_gem1, ranged_gem2, ranged_gem3 FROM character_stuff WHERE character_id = ?");
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
		if(statement.fetch()) {
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isHead() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(0, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isNecklace() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(1, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isShoulders() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(2, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isBack() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(3, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isChest() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(4, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isWrists() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(7, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isGloves() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(8, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isBelt() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(9, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isLeggings() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(10, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isBoots() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				temp = StuffManager.getClone(id);
				Mideas.joueur1().setStuff(11, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isRing() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				Mideas.joueur1().setStuff(12, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isRing() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				Mideas.joueur1().setStuff(13, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isTrinket() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				Mideas.joueur1().setStuff(14, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			if(StuffManager.exists(id) && StuffManager.getStuff(id).isTrinket() && StuffManager.canEquipStuff(StuffManager.getStuff(id))) {
				Mideas.joueur1().setStuff(15, StuffManager.getClone(id));
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(WeaponManager.exists(id) && WeaponManager.canEquipWeapon(WeaponManager.getWeapon(id))) {
				temp = WeaponManager.getClone(id);
				Mideas.joueur1().setStuff(16, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(WeaponManager.exists(id) && WeaponManager.canEquipWeapon(WeaponManager.getWeapon(id))) {
				temp = WeaponManager.getClone(id);
				Mideas.joueur1().setStuff(17, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
			id = statement.getInt();
			gem1Id = statement.getInt();
			gem2Id = statement.getInt();
			gem3Id = statement.getInt();
			if(WeaponManager.exists(id) && WeaponManager.getWeapon(id).isRanged() && WeaponManager.canEquipWeapon(WeaponManager.getWeapon(id))) {
				temp = WeaponManager.getClone(id);
				Mideas.joueur1().setStuff(18, temp);
				setGems(temp, gem1Id, gem2Id, gem3Id);
				numberPieceLoaded++;
			}
		}
		else {
			System.out.println("statement error (stuff load)");
		}
	}*/
	
	private static void setGems(Stuff stuff, int gem1Id, int gem2Id, int gem3Id) {
		if(stuff.getGemSlot1() != GemColor.NONE && GemManager.exists(gem1Id)) {
			stuff.setEquippedGem1(GemManager.getClone(gem1Id));
		}
		if(stuff.getGemSlot2() != GemColor.NONE && GemManager.exists(gem2Id)) {
			stuff.setEquippedGem1(GemManager.getClone(gem2Id));
		}
		if(stuff.getGemSlot3() != GemColor.NONE && GemManager.exists(gem3Id)) {
			stuff.setEquippedGem1(GemManager.getClone(gem3Id));
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
	
	public static int getNumberBagPieceLoaded() {
		return numberBagPieceLoaded;
	}
	
	public static int getNumberPieceLoaded() {
		return numberPieceLoaded;
	}
}
