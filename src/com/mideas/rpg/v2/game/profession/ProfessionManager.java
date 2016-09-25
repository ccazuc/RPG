package com.mideas.rpg.v2.game.profession;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class ProfessionManager {

	private static ArrayList<CraftableItem> craftableList = new ArrayList<CraftableItem>();
	private static ArrayList<Category> categoryList = new ArrayList<Category>();
	private static ArrayList<Profession> professionList = new ArrayList<Profession>();
	private static ArrayList<Integer> unlockedCraftList = new ArrayList<Integer>();
	
	private static void loadUnlockedCraft() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT craft_id FROM craft_unlocked WHERE character_id = ?");
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			unlockedCraftList.add(id);
		}
	}
	
	public static void addUnlockedCraft(int id) throws SQLException {
		if(!unlockedCraftList.contains(id)) {
			unlockedCraftList.add(id);
			JDOStatement statement = Mideas.getJDO().prepare("INSERT INTO craft_unlocked (character_id, craft_id) VALUES (?, ?)");
			statement.putInt(Mideas.getCharacterId());
			statement.putInt(id);
			statement.execute();
		}
	}

	public static void LoadAllCraft() throws SQLException {	
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, level, craft_time, item1, item2, item3, item4, item5, item6 FROM craft_item");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			int level = statement.getInt();
			int craftTime = statement.getInt();
			Item item = getItem(id);
			String tempItem = statement.getString();
			Item ressource1 = getItem(Integer.valueOf(tempItem.split(":")[0]));
			int ressource1Amount = Integer.valueOf(tempItem.split(":")[1]);
			tempItem = statement.getString();
			Item ressource2 = getItem(Integer.valueOf(tempItem.split(":")[0]));
			int ressource2Amount = Integer.valueOf(tempItem.split(":")[1]);
			tempItem = statement.getString();
			Item ressource3 = getItem(Integer.valueOf(tempItem.split(":")[0]));
			int ressource3Amount = Integer.valueOf(tempItem.split(":")[1]);
			tempItem = statement.getString();
			Item ressource4 = getItem(Integer.valueOf(tempItem.split(":")[0]));
			int ressource4Amount = Integer.valueOf(tempItem.split(":")[1]);
			tempItem = statement.getString();
			Item ressource5 = getItem(Integer.valueOf(tempItem.split(":")[0]));
			int ressource5Amount = Integer.valueOf(tempItem.split(":")[1]);
			tempItem = statement.getString();
			Item ressource6 = getItem(Integer.valueOf(tempItem.split(":")[0]));
			int ressource6Amount = Integer.valueOf(tempItem.split(":")[1]);
			ArrayList<Item> itemList = new ArrayList<Item>();
			if(ressource1 != null) {
				itemList.add(ressource1);
			}
			if(ressource2 != null) {
				itemList.add(ressource2);
			}
			if(ressource3 != null) {
				itemList.add(ressource3);
			}
			if(ressource4 != null) {
				itemList.add(ressource4);
			}
			if(ressource5 != null) {
				itemList.add(ressource5);
			}
			if(ressource6 != null) {
				itemList.add(ressource6);
			}
			ArrayList<Integer> numberList = new ArrayList<Integer>();
			if(ressource1 != null) {
				numberList.add(ressource1Amount);
			}
			if(ressource2 != null) {
				numberList.add(ressource2Amount);
			}
			if(ressource3 != null) {
				numberList.add(ressource3Amount);
			}
			if(ressource4 != null) {
				numberList.add(ressource4Amount);
			}
			if(ressource5 != null) {
				numberList.add(ressource5Amount);
			}
			if(ressource6 != null) {
				numberList.add(ressource6Amount);
			}
			craftableList.add(new CraftableItem(id, level, craftTime, item, itemList, numberList));
			//craftableList.add(new CraftableItem(id, level, item, ressource1, ressource1Amount, ressource2, ressource2Amount, ressource3, ressource3Amount, ressource4, ressource4Amount, ressource5, ressource5Amount, ressource6, ressource6Amount));
		}
		loadUnlockedCraft();
		statement = Mideas.getJDO().prepare("SELECT id, name, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10 FROM craft_category");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String name = statement.getString();
			CraftableItem item1 = getCraftableItem(statement.getInt());
			CraftableItem item2 = getCraftableItem(statement.getInt());
			CraftableItem item3 = getCraftableItem(statement.getInt());
			CraftableItem item4 = getCraftableItem(statement.getInt());
			CraftableItem item5 = getCraftableItem(statement.getInt());
			CraftableItem item6 = getCraftableItem(statement.getInt());
			CraftableItem item7 = getCraftableItem(statement.getInt());
			CraftableItem item8 = getCraftableItem(statement.getInt());
			CraftableItem item9 = getCraftableItem(statement.getInt());
			CraftableItem item10 = getCraftableItem(statement.getInt());
			categoryList.add(new Category(id, name, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10));
		}
		statement = Mideas.getJDO().prepare("SELECT name, id, category1, category2, category3, category4, category5, category6, category7, category8 FROM craft_profession");
		statement.execute();
		while(statement.fetch()) {
			String professionName = statement.getString();
			int professionId = statement.getInt();
			Category category1 = getCategory(statement.getInt());
			Category category2 = getCategory(statement.getInt());
			Category category3 = getCategory(statement.getInt());
			Category category4 = getCategory(statement.getInt());
			Category category5 = getCategory(statement.getInt());
			Category category6 = getCategory(statement.getInt());
			Category category7 = getCategory(statement.getInt());
			Category category8 = getCategory(statement.getInt());
			professionList.add(new Profession(professionId, professionName, category1, category2, category3, category4, category5, category6, category7, category8));
		}
		/*int i = 0;
		int j = 0;
		int k = 0;
		while(i < professionList.size()) {
			j = 0;
			while(j < professionList.get(i).getCategoryList().size()) {
				k = 0;
				while(k < professionList.get(i).getCategoryList().get(j).getCraftList().size()) {
					System.out.println(professionList.get(i).getCategoryList().get(j).getCraftList().get(k).getItem().getStuffName()+" "+professionList.get(i).getCategoryList().get(j).getCraftList().get(k).getId());
					k++;
				}
				System.out.println(professionList.get(i).getCategoryList().get(j).getName());
				j++;
			}
			i++;
		}*/
	}
	
	private static Item getItem(int id) {
		if(WeaponManager.getClone(id) != null) {
			return WeaponManager.getClone(id);
		}
		else if(GemManager.getClone(id) != null) {
			return GemManager.getClone(id);
		}
		else if(StuffManager.getClone(id) != null) {
			return StuffManager.getClone(id);
		}
		else if(PotionManager.getClone(id) != null) {
			return PotionManager.getClone(id);
		}
		return null;
	}
	
	private static CraftableItem getCraftableItem(int id) {
		if(craftExists(id)) {
			int i = 0;
			while(i < craftableList.size()) {
				if(craftableList.get(i).getId() == id) {
					return new CraftableItem(craftableList.get(i));
				}
				i++;
			}
		}
		return null;
	}
	
	private static Category getCategory(int id) {
		int i = 0;
		while(i < categoryList.size()) {
			if(categoryList.get(i).getId() == id) {
				return categoryList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public static Profession getProfession(int id) {
		int i = 0;
		while(i < professionList.size()) {
			if(professionList.get(i).getId() == id) {
				return professionList.get(i);
			}
			i++;
		}
		return null;
	}
	
	private static boolean craftExists(int id) {
		int i = 0;
		while(i < unlockedCraftList.size()) {
			if(unlockedCraftList.get(i) == id) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static ArrayList<Profession> getProfessionList() {
		return professionList;
	}
}
