package com.mideas.rpg.v2.game.auction;

public enum AuctionHouseFilter {
	
	ERROR((byte)0, ""),
	NONE((byte)1, ""),
	WEAPON((byte)2, "Weapon"),
	WEAPON_ONE_HANDED_AXE((byte)3, "One-Handed axe"),
	WEAPON_TWO_HANDED_AXE((byte)4, "Two-Handed axe"),
	WEAPON_BOW((byte)5, "Bow"),
	WEAPON_GUN((byte)6, "Gun"),
	WEAPON_ONE_HANDED_MACE((byte)7, "One-Handed mace"),
	WEAPON_TWO_HANDED_MACE((byte)8, "Two-Handed mace"),
	WEAPON_POLEARM((byte)9, "Polearm"),
	WEAPON_ONE_HANDED_SWORD((byte)10, "One-Handed sword"),
	WEAPON_TWO_HANDED_SWORD((byte)11, "Two-Handed sword"),
	WEAPON_STAFF((byte)12, "Staff"),
	WEAPON_FIST_WEAPON((byte)13, "Fist-weapon"),
	WEAPON_MISCELLANEOUS((byte)14, "Miscellaneous"),
	WEAPON_DAGGERS((byte)15, "Daggers"),
	WEAPON_THROW_WEAPON((byte)16, "Throw weapon"),
	WEAPON_CROSSBOW((byte)17, "Crossbos"),
	WEAPON_WAND((byte)18, "Wand"),
	WEAPON_FISHING_POLES((byte)19," Fishing poles"),
	ARMOR((byte)20, "Armor"),
	ARMOR_MISCELLANEOUS((byte)21, "Miscellaneous"),
	ARMOR_CLOTH((byte)22, "Cloth"),
	ARMOR_LEATHER((byte)23, "Leather"),
	ARMOR_MAIL((byte)24, "Mail"),
	ARMOR_PLATE((byte)25, "Plate"),
	ARMOR_SHIELDS((byte)26, "Shields"),
	ARMOR_LIBRAMS((byte)27, "Librams"),
	ARMOR_IDOLS((byte)28, "Idols"),
	ARMOR_TOTEMS((byte)29, "Totems"),
	CONTAINER((byte)30, "Container"),
	CONTAINER_BAG((byte)31, "Bag"),
	CONTAINER_HERB_BAG((byte)32, "Herb Bag"),
	CONTAINER_ENCHANTING_BAG((byte)33, "Enchanting Bag"),
	CONTAINER_ENGINEERING_BAG((byte)34, "Engineering Bag"),
	CONTAINER_GEM_BAG((byte)35, "Gem Bag"),
	CONTAINER_MINING_BAG((byte)36, "Mining Bag"),
	CONTAINER_LEATHERWORKING_BAG((byte)37, "Leatherworking Bag"),
	CONTAINER_SOUL_BAG((byte)38, "Soul Bag"),
	CONSUMABLES((byte)39, "Consumable"),
	CONSUMABLES_FOOD_AND_DRINK((byte)40, "Food & Drink"),
	CONSUMABLES_POTION((byte)41, "Potion"),
	CONSUMABLES_ELIXIR((byte)42, "Elixir"),
	CONSUMABLES_FLASK((byte)43, "Flask"),
	CONSUMABLES_BANDAGE((byte)44, "Bandage"),
	CONSUMABLES_ITEMS_ENCHANCEMENTS((byte)45, "Item enhancement"),
	CONSUMABLES_PARCHEMIN((byte)46, "Inscription"),
	CONSUMABLES_MISCELLANEOUS((byte)47, "Miscellaneous"),
	TRADE_GOOD((byte)48, "Trade goods"),
	TRADE_GOOD_ELEMENTAL((byte)49, "Elemental"),
	TRADE_GOOD_CLOTH((byte)50, "Cloth"),
	TRADE_GOOD_LEATHER((byte)51, "Leather"),
	TRADE_GOOD_METAL_AND_STONE((byte)52,"Metal & Stone"),
	TRADE_GOOD_COOKING((byte)53, "Cooking"),
	TRADE_GOOD_HERB((byte)54, "Herb"),
	TRADE_GOOD_ENCHANTING((byte)55, "Enchanting"),
	TRADE_GOOD_JEWELCRAFTING((byte)56, "Jewelcrafting"),
	TRADE_GOOD_PARTS((byte)57, "Parts"),
	TRADE_GOOD_MATERIALS((byte)58, "Materials"),
	TRADE_GOOD_OTHER((byte)59, "Other"),
	PROJECTILE((byte)60, "Projectile"),
	PROJECTILE_ARROW((byte)61, "Arrow"),
	PROJECTILE_BULLET((byte)62, "Bullet"),
	QUIVER((byte)63, "Quiver"),
	QUIVER_QUIVER((byte)64, "Quiver"),
	QUIVER_GIBERNE((byte)65, "Giberne"),
	RECIPES((byte)66, "Recipe"),
	RECIPES_BOOK((byte)67, "Book"),
	RECIPES_LEATHERWORKING((byte)68, "Leatherworking"),
	RECIPES_TAILORING((byte)69, "Tailoring"),
	RECIPES_ENGINEERING((byte)70," Engineering"),
	RECIPES_BLACKSMITHING((byte)71, "Blacksmithing"),
	RECIPES_COOKING((byte)72, "Cooking"),
	RECIPES_ALCHEMY((byte)73, "Alchemy"),
	RECIPES_FIRST_AID((byte)74, "First Aid"),
	RECIPES_ENCHANTING((byte)75, "Enchanting"),
	RECIPES_FISHING((byte)76, "Fishing"),
	RECIPES_JEWELCRAFTING((byte)77, "Jewelcrafting"),
	GEM((byte)78, "Gem"),
	GEM_RED((byte)79, "Red"),
	GEM_BLUE((byte)80, "Blue"),
	GEM_YELLOW((byte)81, "Yellow"),
	GEM_PURPLE((byte)82, "Purple"),
	GEM_GREEN((byte)83, "Green"),
	GEM_ORANGE((byte)84, "Orange"),
	GEM_META((byte)85, "Meta"),
	GEM_SIMPLE((byte)86, "Simple"),
	GEM_PRISMATIC((byte)87, "Prismatic"),
	MISCELLANEOUS((byte)88, "Miscellaneous"),
	MISCELLANEOUS_JUNK((byte)89, "Junk"),
	MISCELLANEOUS_REAGENT((byte)90, "Reagent"),
	MISCELLANEOUS_COMPANION_PETS((byte)91, "Companion Pets"),
	MISCELLANEOUS_HOLIDAY((byte)92, "Holiday"),
	MISCELLANEOUS_MOUNT((byte)93, "Mount"),
	MISCELLANEOUS_OTHER((byte)94, "Other"),
	QUESTS((byte)95, "Quest Items"),
	
	;
	
	private final byte value;
	private final String name;
	
	private AuctionHouseFilter(byte value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public byte getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static AuctionHouseFilter getFilter(byte value) {
		if(value >= 0 && value < values().length) {
			return values()[value];
		}
		return ERROR;
	}
}
