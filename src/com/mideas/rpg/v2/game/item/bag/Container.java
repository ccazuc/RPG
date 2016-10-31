package com.mideas.rpg.v2.game.item.bag;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Container extends Item {
	
	private int id;
	private String sprite_id;
	private String name;
	private int size;
	
	public Container(Container bag) {
		super(bag.id, bag.sprite_id, bag.itemType, bag.name, bag.quality, bag.sellPrice, bag.maxStack, 1);
		this.sprite_id = bag.sprite_id;
		this.name = bag.name;
		this.size = bag.size;
		this.id = bag.id;
	}
	
	public Container(int id,  String name, String sprite_id, int quality, int size, int sellPrice) {
		super(id, sprite_id, ItemType.CONTAINER, name, quality, sellPrice, 1, 1);
		this.sprite_id = sprite_id;
		this.name = name;
		this.size = size;
		this.id = id;
	}
	
	public Container(int id) {
		super(id, "", ItemType.CONTAINER, "", 0, 0, 1, 1);
	}
	
	@Override
	public String getSpriteId()  {
		return this.sprite_id;
	}
	
	public int getSize() {
		return this.size;
	}
	
	@Override
	public int getId() {
		return this.id;
	}
}
