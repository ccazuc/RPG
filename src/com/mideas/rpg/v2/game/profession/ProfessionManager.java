package com.mideas.rpg.v2.game.profession;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.WeaponManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class ProfessionManager {

	private static ArrayList<CraftableItem> craftableList = new ArrayList<CraftableItem>();
	private static ArrayList<Category> categoryList = new ArrayList<Category>();
	private static ArrayList<Profession> professionList = new ArrayList<Profession>();

	public static void LoadAllCraft() throws SQLException {
		
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, ressource1, ressource1Amount, ressource2, ressource2Amount, ressource3, ressource3Amount, ressource4, ressource4Amount, ressource5, ressource5Amount, ressource6, ressource6Amount FROM craft_item");
		while(statement.fetch()) {
			int id = statement.getInt();
			Item item = getItem(id);
			Item ressource1 = getItem(statement.getInt());
			int ressource1Amount = statement.getInt();
			Item ressource2 = getItem(statement.getInt());
			int ressource2Amount = statement.getInt();
			Item ressource3= getItem(statement.getInt());
			int ressource3Amount = statement.getInt();
			Item ressource4 = getItem(statement.getInt());
			int ressource4Amount = statement.getInt();
			Item ressource5= getItem(statement.getInt());
			int ressource5Amount = statement.getInt();
			Item ressource6= getItem(statement.getInt());
			int ressource6Amount = statement.getInt();
			craftableList.add(new CraftableItem(id, item, ressource1, ressource1Amount, ressource2, ressource2Amount, ressource3, ressource3Amount, ressource4, ressource4Amount, ressource5, ressource5Amount, ressource6, ressource6Amount));
		}
		
		statement = Mideas.getJDO().prepare("SELECT id, name, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10 FROM craft_category");
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
		
		
		statement = Mideas.getJDO().prepare("SELECT profession_name, profession_id, category1, category2, category3, category4, category5, category6, category7, category8 FROM rpg");
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
		int i = 0;
		while(i < craftableList.size()) {
			if(craftableList.get(i).getId() == id) {
				return craftableList.get(i);
			}
			i++;
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
}
