package com.mideas.rpg.v2.game.item.container;

import java.sql.SQLException;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;

public class ContainerManager {
	
	private static HashMap<Integer, Container> containerList = new HashMap<Integer, Container>();
	private static HashMap<Integer, Texture> bagsSprites = new HashMap<Integer, Texture>();
	
	/*public static void loadBags() {
		try {
			JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, quality, size, sellprice FROM item_container");
			statement.execute();
			while(statement.fetch()) {
				int id = statement.getInt();
				String sprite_id = statement.getString();
				String name = statement.getString();
				byte quality = statement.getByte();
				byte size = statement.getByte();
				int sellPrice = statement.getInt();
				Container newPiece = new Container(id, name, sprite_id, quality, size, sellPrice);
				containerList.put(id, newPiece);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	public static void storeNewPiece(Container container) {
		if(container != null) {
			Container tmp = containerList.get(container.getId());
			if(tmp != null && !tmp.getIsLoaded()) {
				tmp.updateContainer(container);
			}
			else {
				containerList.put(container.getId(), container);
			}
		}
	}
	
	public static void loadBagsSprites() {
		bagsSprites.put(100001, Sprites.back_bag);
		bagsSprites.put(100003, Sprites.bag20);
		bagsSprites.put(100004, Sprites.bag12);
	}
	
	public static HashMap<Integer, Texture> getBagsSprites() {
		return bagsSprites;
	}
	
	public static Container getContainer(int id) {
		if(containerList.containsKey(id)) {
			return containerList.get(id);
		}
		return null;
	}
	
	public static boolean exists(int id) {
		return containerList.containsKey(id);
	}
	
	public static Container getClone(int id) {
		Container tempContainer = getContainer(id);
		if(tempContainer != null) {
			return new Container(tempContainer);
		}
		return null;
	}
	
	public static HashMap<Integer, Container> getContainerMap() {
		return containerList;
	}
}
