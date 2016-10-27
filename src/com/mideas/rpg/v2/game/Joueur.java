package com.mideas.rpg.v2.game;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.ClassColor;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.bag.Bag;
import com.mideas.rpg.v2.game.item.bag.ContainerManager;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.game.profession.Profession;
import com.mideas.rpg.v2.game.profession.ProfessionManager;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.hud.PartyFrame;

public class Joueur extends Unit {

	public final static int MAXIMUM_AMOUNT_FRIENDS = 20; 
	private ArrayList<Friend> friendList;
	private Profession secondProfession;
	private Profession firstProfession;
	private WeaponType[] weaponType;
	private Spell[] spellUnlocked;
	private int numberYellowGem;
	private Bag bag = new Bag();
	private Shortcut[] shortcut;
	private Shortcut[] spells;
	private int numberBlueGem;
	private int defaultArmor;
	private int numberRedGem;
	private int goldGained;
	private Stuff[] stuff;
	private int expGained;
	private int critical;
	private int strength;
	private Unit target;
	private Party party;
	private int baseExp;
	private float armor;
	private Wear wear;
	private int gold;
	private int exp;
	private int id;
	public int x;
	public static int y;
	public static int z;

	private final static String warrior = "Guerrier";
	private final static String hunter = "Hunter";
	private final static String mage = "Mage";
	private final static String paladin = "Paladin";
	private final static String priest = "Priest";
	private final static String rogue = "Rogue";
	private final static String shaman = "Shaman";
	private final static String warlock = "Warlock";
	private final static String druid = "Druid";
	
	public Joueur(ClassType classType, int id, Wear wear, WeaponType[] weaponType, int stamina, int mana, int strength, int armor, int defaultArmor, int critical, int maxStamina, int maxMana, int expGained, int goldGained, Shortcut[] spells, Spell[] spellUnlocked, Stuff[] stuff) {
		super(id, stamina, maxStamina, mana, maxMana, 1, "", classType);
		this.strength = strength;
		this.armor = armor;
		this.defaultArmor = defaultArmor;
		this.critical = critical;
		this.expGained = expGained;
		this.goldGained = goldGained;
		this.spells = spells;
		this.spellUnlocked = spellUnlocked;
		this.weaponType = weaponType;
		this.stuff = stuff;
		this.wear = wear;
		this.firstProfession = ProfessionManager.getProfession(0);
		//this.classString = convClassTypeToString(this.classType);
		this.friendList = new ArrayList<Friend>();
		this.friendList.add(new Friend(3, "Jean-42", 70, Race.ORC, ClassType.GUERRIER));
		this.friendList.add(new Friend(2, "Jean-bas-level"));
	}
	
	public Joueur(Joueur joueur) {
		super(joueur.id, joueur.stamina, joueur.maxStamina, joueur.mana, joueur.maxMana, joueur.level, joueur.name, joueur.classType);
		this.spellUnlocked = joueur.spellUnlocked;
		this.defaultArmor = joueur.defaultArmor;
		this.classString = joueur.classString;
		this.weaponType = joueur.weaponType;
		this.goldGained = joueur.goldGained;
		this.friendList = joueur.friendList;
		this.expGained = joueur.expGained;
		this.strength = joueur.strength;
		this.critical = joueur.critical;
		this.spells = joueur.spells;
		this.stuff = joueur.stuff;
		this.armor = joueur.armor;
		this.mana = joueur.mana;
		this.wear = joueur.wear;
	}
	
	/*public void tick() throws SQLException {
		if(Mideas.getCurrentPlayer()) {
			attack(this.target);
			SpellBarFrame.setIsCastingSpell(false);
		}
	}
	
	public boolean cast(Spell spell) throws SQLException {
		if(spell.getType() == SpellType.DAMAGE) {
			SpellBarFrame.setIsCastingSpell(false);
			if(!spell.hasMana()) {
				attack(Mideas.target());
			}
			else {
				spell.cast(Mideas.target(), Mideas.joueur1(), spell);
				//LogChat.setStatusText("Le joueur 1 a enlevée "+spell.getDamage()+" hp au "+Mideas.target().getClasse()+", "+Mideas.target().getStamina()+" hp restant");
				return true;
			}
		}
		else if(spell.getType() == SpellType.HEAL) {
			SpellBarFrame.setIsCastingSpell(false);
			if(!spell.hasMana()) {
				attack(Mideas.target());
			}
			else {
				if(Mideas.joueur1().getStamina()+spell.getHeal() >= Mideas.joueur1().getMaxStamina()) {
					int diff = Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina();
					spell.healMax(Mideas.joueur1(), spell);
					LogChat.setStatusText("Vous vous êtes rendu "+diff+" hp, vous avez maintenant "+Mideas.joueur1().getStamina()+" hp");	
					return true;
				}
				else {
					spell.heal(Mideas.joueur1(), spell);
					LogChat.setStatusText("Vous vous êtes rendu "+spell.getHeal()+" hp, vous avez maintenant "+Mideas.joueur1().getStamina()+" hp");
					return true;
				}
			}
		}
		return false;
	}*/
	
	/*public void attack(Joueur joueur) throws SQLException {
		double damage = Mideas.joueur1().getStrength()*ThreadLocalRandom.current().nextDouble(.9, 1.1);
		SpellBarFrame.setIsCastingSpell(false);
		if(Math.random() < this.critical/100.) {
			damage*= 2;
		}
		joueur.setStamina(joueur.getStamina()-damage);
		//LogChat.setStatusText("Le joueur 1 a enlevé "+Math.round(damage)+" hp au "+joueur.getClasse()+", "+joueur.getStamina()+" hp restant"); //and "+Mideas.joueur2.getMana()+" mana left");
		if(Mideas.joueur1().getStamina() <= 0) {
			LogChat.setStatusText("Le joueur 2 a gagné !");
			LogChat.setStatusText2(""); 
			y = 1;
			return;
		}
		else if(Mideas.target().getStamina() <= 0) {
			LogChat.setStatusText("Le joueur 1 a gagné !");
			LogChat.setStatusText2("");
			z = 1;
			return;
		}
	}*/
	
	public void attackUI(Spell spell) {
		/*double damage = Mideas.joueur2().getStrength()*ThreadLocalRandom.current().nextDouble(.9, 1.1);
		float rand = (float)Math.random();
		if(rand < Mideas.joueur2().getCritical()/100.) {
			damage*= 2;
		}
		if(spell.getType() == SpellType.HEAL && spell.hasMana()) {
			if(Mideas.joueur2().getStamina() < Mideas.joueur2().getMaxStamina()) {
				if(Mideas.joueur2().getStamina()+spell.getHeal() >= Mideas.joueur2().getMaxStamina()) {
					int diff = Mideas.joueur2().getMaxStamina()-Mideas.joueur2().getStamina();
					spell.healMax(Mideas.joueur2(), spell);
					LogChat.setStatusText2("Le joueur2 s'est rendu "+diff+" hp, il a maintenant "+Mideas.joueur2().getStamina()+" hp");
				}
				else {
					spell.heal(Mideas.joueur2(), spell);
					LogChat.setStatusText2("Le joueur2 s'est rendu "+spell.getHeal()+" hp, il a maintenant "+Mideas.joueur2().getStamina()+" hp");	
				}
			}
		}
		else if(spell.getType() == SpellType.DAMAGE) {
			Spell cast = Spell.getRandomSpell();
			if(rand > .2 && rand <= .4 && cast.hasMana()) {	
				cast.cast(Mideas.joueur1(), Mideas.joueur2(), spell);
				LogChat.setStatusText2("Le joueur 2 a enlevée "+cast.getDamage()+" hp au "+Mideas.joueur1().getClasse()+", "+Mideas.joueur1().getStamina()+" hp restant");
			}
			else {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()-damage);
				LogChat.setStatusText2("Le joueur 2 a enlevée "+Math.round(damage)+" hp au "+Mideas.joueur1().getClasse()+", "+Mideas.joueur1().getStamina()+" hp restant");	
			}
		}*/
	}
	
	public void loadStuff() {
		int i = 0;
		Interface.setStuffFullyLoaded(true);
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) != null && !Mideas.joueur1().getStuff(i).getIsLoaded()) {
				if(StuffManager.exists(Mideas.joueur1().getStuff(i).getId())) {
					Mideas.joueur1().setStuff(i, StuffManager.getClone(Mideas.joueur1().getStuff(i).getId()));
					Mideas.joueur1().getStuff(i).setIsLoaded(true);
					ConnectionManager.getItemRequested().remove(Mideas.joueur1().getStuff(i).getId());
					i++;
					continue;
				}
				else if(WeaponManager.exists(Mideas.joueur1().getStuff(i).getId())) {
					Mideas.joueur1().setStuff(i, WeaponManager.getClone(Mideas.joueur1().getStuff(i).getId()));
					Mideas.joueur1().getStuff(i).setIsLoaded(true);
					ConnectionManager.getItemRequested().remove(Mideas.joueur1().getStuff(i).getId());
					i++;
					continue;
				}
				Interface.setStuffFullyLoaded(false);
			}
			i++;
		}
	}
	
	public void loadBag() {
		int i = 0;
		Interface.setBagFullyLoaded(true);
		while(i < this.bag.getBag().length) {
			if(this.bag.getBag(i) != null && !this.bag.getBag(i).getIsLoaded()) {
				if(StuffManager.exists(this.bag.getBag(i).getId())) {
					this.bag.setBag(i, StuffManager.getClone(this.bag.getBag(i).getId()));
					this.bag.getBag(i).setIsLoaded(true);
					ConnectionManager.getItemRequested().remove(this.bag.getBag(i).getId());
					i++;
					continue;
				}
				else if(WeaponManager.exists(this.bag.getBag(i).getId())) {
					this.bag.setBag(i, WeaponManager.getClone(this.bag.getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(this.bag.getBag(i).getId());
					this.bag.getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(GemManager.exists(this.bag.getBag(i).getId())) {
					this.bag.setBag(i, GemManager.getClone(this.bag.getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(this.bag.getBag(i).getId());
					this.bag.getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(ContainerManager.exists(this.bag.getBag(i).getId())) {
					this.bag.setBag(i, ContainerManager.getClone(this.bag.getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(this.bag.getBag(i).getId());
					this.bag.getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(PotionManager.exists(this.bag.getBag(i).getId())) {
					int number = this.bag.getBag(i).getAmount();
					this.bag.setBag(i, PotionManager.getClone(this.bag.getBag(i).getId()), number);
					ConnectionManager.getItemRequested().remove(this.bag.getBag(i).getId());
					this.bag.getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				Interface.setBagFullyLoaded(false);
			}
			i++;
		}
		if(Interface.getBagFullyLoaded()) {
			if(Mideas.joueur1().getFirstProfession() != null) {
				Mideas.joueur1().getFirstProfession().updateNumberPossibleCraft();
			}
			else if(Mideas.joueur1().getSecondProfession() != null) {
				Mideas.joueur1().getSecondProfession().updateNumberPossibleCraft();
			}
		}
	}
	
	public void loadSpellbar() {
		int i = 0;
		Interface.setSpellbarFullyLoaded(true);
		while(i < Mideas.joueur1().getSpells().length) {
			if(Mideas.joueur1().getSpells(i) != null && !Mideas.joueur1().getSpells(i).getIsLoaded()) {
				if(StuffManager.exists(Mideas.joueur1().getSpells(i).getId())) {
					Mideas.joueur1().setSpells(i, new StuffShortcut(StuffManager.getClone(Mideas.joueur1().getSpells(i).getId())));
					ConnectionManager.getItemRequested().remove(Mideas.joueur1().getSpells(i).getId());
					Mideas.joueur1().getSpells(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(WeaponManager.exists(Mideas.joueur1().getSpells(i).getId())) {
					Mideas.joueur1().setSpells(i, new StuffShortcut(WeaponManager.getClone(Mideas.joueur1().getSpells(i).getId())));
					ConnectionManager.getItemRequested().remove(Mideas.joueur1().getSpells(i).getId());
					Mideas.joueur1().getSpells(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(PotionManager.exists(Mideas.joueur1().getSpells(i).getId())) {
					Mideas.joueur1().setSpells(i, new PotionShortcut(PotionManager.getClone(Mideas.joueur1().getSpells(i).getId())));
					ConnectionManager.getItemRequested().remove(Mideas.joueur1().getSpells(i).getId());
					Mideas.joueur1().getSpells(i).setIsLoaded(true);
					i++;
					continue;
				}
				Interface.setSpellbarFullyLoaded(false);
			}
			i++;
		}
	}
	
	public boolean addItem(Item item, int amount) {
		if(amount == 1) {
			return addSingleItem(item, amount);
		}
		else if(amount > 1) {
			if(item.isStackable()) {
				return addSingleItem(item, amount);
			}
			else {
				return addMultipleUnstackableItem(item.getId(), amount);
			}
		}
		return false;
	}
	
	private boolean addSingleItem(Item item, int amount) {
		int i = 0;
		if(!item.isStackable()) {
			while(i < this.bag.getBag().length && amount > 0) {
				if(this.bag.getBag(i) == null) {
					this.bag.setBag(i, item);
					this.bag.setBagChange(true);
					amount --;
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
		else {
			while(i < this.bag.getBag().length) {
				if(this.bag.getBag(i) != null && this.bag.getBag(i).equals(item)) {
					this.bag.setBag(i, item, this.bag.getBag(i).getAmount()+amount);
					this.bag.setBagChange(true);
					CharacterStuff.setBagItems();
					return true;
				}
				i++;
			}
			i = 0;
			while(i < this.bag.getBag().length) {
				if(this.bag.getBag(i) == null) {
					this.bag.setBag(i, item, amount);
					this.bag.setBagChange(true);
					CharacterStuff.setBagItems();
					return true;
				}
				i++;
			}
		}
		LogChat.setStatusText3("Votre inventaire est pleins");
		return false;
	}
	
	private boolean addMultipleUnstackableItem(int id, int number) {
		int i = 0;
		boolean returns = false;
		ItemType type;
		if(WeaponManager.exists(id) || StuffManager.exists(id) || GemManager.exists(id) || ContainerManager.exists(id)) {
			if(WeaponManager.exists(id)) {
				type = ItemType.WEAPON;
			}
			else if(StuffManager.exists(id)) {
				type = ItemType.STUFF;
			}
			else if(GemManager.exists(id)) {
				type = ItemType.GEM;
			}
			else {
				type = ItemType.CONTAINER;
			}
			while(i < this.bag.getBag().length && number > 0) {
				if(this.bag.getBag(i) == null) {
					if(type == ItemType.WEAPON) {
						this.bag.setBag(i, WeaponManager.getClone(id));
					}
					else if(type == ItemType.STUFF) {
						this.bag.setBag(i, StuffManager.getClone(id));
					}
					else if(type == ItemType.GEM) {
						this.bag.setBag(i, GemManager.getClone(id));
					}
					else {
						this.bag.setBag(i, ContainerManager.getClone(id));
					}
					this.bag.setBagChange(true);
					number--;
					returns = true;
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
		return returns;
	}
	
	public void deleteItem(Item item, int amount) {
		int i = 0;
		if(!item.isStackable()) {
			while(i < this.bag.getBag().length && amount > 0) {
				if(this.bag.getBag(i) != null && this.bag.getBag(i).equals(item)) {
					this.bag.setBag(i, null);
					this.bag.setBagChange(true);
					amount--;
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
		else {
			while(i < this.bag.getBag().length && amount > 0) {
				if(this.bag.getBag(i) != null && this.bag.getBag(i).equals(item)) {
					int temp = amount;
					amount-= this.bag.getBag(i).getAmount();
					this.bag.setBag(i, this.bag.getBag(i), Math.max(0, this.bag.getBag(i).getAmount()-temp));
					this.bag.setBagChange(true);
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
	}
	
	public boolean canWear(Stuff stuff) {
		if(stuff != null) {
			if(this.wear == Wear.PLATE) {
				return true;
			}
			if(this.wear == Wear.MAIL) {
				if(stuff.getWear() == Wear.PLATE) {
					return false;
				}
				return true;
			}
			if(this.wear == Wear.LEATHER) {
				if(stuff.getWear() == Wear.PLATE || stuff.getWear() == Wear.MAIL) {
					return false;
				}
				return true;
			}
			if(this.wear == Wear.CLOTH) {
				if(stuff.getWear() == Wear.CLOTH || stuff.getWear() == Wear.NONE) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public boolean canEquipStuff(Stuff stuff) {
		if(Mideas.joueur1().getLevel() >= stuff.getLevel() && canWear(stuff) && stuff.canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString()))) {
			return true;
		}
		return false;
	}
	
	public void addFriend(Friend friend) {
		if(this.friendList.size() < MAXIMUM_AMOUNT_FRIENDS) {
			this.friendList.add(friend);
			sortFriendList();
		}
	}
	
	private void sortFriendList() {
		int i = 0;
		int j = 0;
		Friend temp;
		while(i < this.friendList.size()) {
			j = i;
			while(j < this.friendList.size()) {
				if(this.friendList.get(i).getName().compareTo(this.friendList.get(i).getName()) > 0) {
					temp = this.friendList.get(j);
					this.friendList.set(j, this.friendList.get(i));
					this.friendList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	public ArrayList<Friend> getFriendList() {
		return this.friendList;
	}
	
	public void removeFriend(Friend friend) {
		int i = 0;
		while(i < this.friendList.size()) {
			if(this.friendList.get(i) == friend) {
				this.friendList.remove(i);
				return;
			}
			i++;
		}
	}
	
	public void setParty(Party party) {
		if(party == null) {
			PartyFrame.setDisplayMember(-1);
		}
		this.party = party;
	}
	
	public Party getParty() {
		return this.party;
	}
	
	public Unit getTarget() {
		return this.target;
	}
	
	public void setTarget(Unit target) {
		this.target = target;
	}
	
	public void setFirstProfession(Profession profession) {
		this.firstProfession = profession;
	}
	
	public Profession getFirstProfession() {
		return this.firstProfession;
	}
	
	public Profession getSecondProfession() {
		return this.secondProfession;
	}
	
	public void setSecondProfession(Profession profession) {
		this.secondProfession = profession;
	}
	
	public void setStuffArmor(int armor) {
		this.armor+= armor;
	}

	public void setStuffStrength(int strengh) {
		this.strength+= strengh;
	}
	
	public void setStuffStamina(int stamina) {
		this.maxStamina+= stamina;
		this.stamina+= stamina;
		this.hasHpChanged = true;
	}
	
	public void setStuffCritical(int critical) {
		this.critical+= critical;
	}
	
	public void setStuffMana(int mana) {
		this.maxMana+= mana;
		this.mana+= mana;
		this.hasManaChanged = true;
	}
	
	public void setNumberRedGem(int nb) {
		this.numberRedGem = nb;
	}
	
	public int getNumberRedGem() {
		return this.numberRedGem;
	}
	
	public void setNumberBlueGem(int nb) {
		this.numberBlueGem = nb;
	}
	
	public int getNumberBlueGem() {
		return this.numberBlueGem;
	}
	
	public void setNumberYellowGem(int nb) {
		this.numberYellowGem = nb;
	}
	
	public int getNumberYellowGem() {
		return this.numberYellowGem;
	}
	
	public float getArmor() {
		return this.armor;
	}
	
	public Wear getWear() {
		return this.wear;
	}
	
	public int getCritical() {
		return this.critical;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getX() {
		return this.x;
	}

	public Shortcut[] getSpells() {
		return this.spells;
	}

	public Shortcut getSpells(int i) {
		return this.spells[i];
	}
	
	public void setSpells(int i, Shortcut spell) {
		this.spells[i] = spell;
	}
	
	public Spell[] getSpellUnlocked() {
		return this.spellUnlocked;
	}
	
	public Spell getSpellUnlocked(int i) {
		return this.spellUnlocked[i];
	}
	
	public Stuff[] getStuff() {
		return this.stuff;
	}
	
	public Stuff getStuff(int i) {
		return this.stuff[i];
	}
	
	public void setStuff(int i, Item tempItem) {
		if(tempItem == null) {
			this.stuff[i] = null;
		}
		else if(tempItem.getItemType() == ItemType.STUFF || tempItem.getItemType() == ItemType.WEAPON) {
			this.stuff[i] = (Stuff)tempItem;
		}
	}
	
	public Bag bag() {
		return this.bag;
	}
	
	public Shortcut getShortcut(int i) {
		return this.shortcut[i];
	}
	
	public Shortcut[] getShortcut() {
		return this.shortcut;
	}

	public void setSpellUnlocked(int i, Spell spell) {
		this.spellUnlocked[i] = spell;
	}
	
	public int getExp() {
		return this.exp;
	}
	
	public int getBaseExp() {
		return this.baseExp;
	}
	
	public int getExpGained() {
		return this.expGained;
	}
	
	public void setArmor(float number) {
		this.armor = (Math.round(100*(this.armor+number))/100.f);
	}
	
	public int getDefaultArmor() {
		return this.defaultArmor;
	}
	
	public int getGold() {
		return this.gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getGoldGained() {
		return this.goldGained;
	}
	
	public void setExp(int baseExp, int expGained ) {
		this.exp = baseExp+expGained;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
		this.level = Mideas.getLevel(this.exp);
	}
	
	public WeaponType[] getWeaponType() {
		return this.weaponType;
	}
	
	public WeaponType getweaponType(int i) {
		if(i < this.weaponType.length) {
			return this.weaponType[i];
		}
		return null;
	}
	
	public static String convClassTypeToString(ClassType type) {
		if(type == ClassType.DRUID) {
			return druid;
		}
		if(type == ClassType.GUERRIER) {
			return warrior;
		}
		if(type == ClassType.HUNTER) {
			return hunter;
		}
		if(type == ClassType.MAGE) {
			return mage;
		}
		if(type == ClassType.PALADIN) {
			return paladin;
		}
		if(type == ClassType.PRIEST) {
			return priest;
		}
		if(type == ClassType.ROGUE) {
			return rogue;
		}
		if(type == ClassType.SHAMAN) {
			return shaman;
		}
		if(type == ClassType.WARLOCK) {
			return warlock;
		}
		return "";
	}

	
	public static Color convClassTypeToColor(ClassType type) {
		if(type == ClassType.DRUID) {
			return ClassColor.DRUID_COLOR;
		}
		if(type == ClassType.GUERRIER) {
			return ClassColor.WARRIOR_COLOR;
		}
		if(type == ClassType.HUNTER) {
			return ClassColor.HUNTER_COLOR;
		}
		if(type == ClassType.MAGE) {
			return ClassColor.MAGE_COLOR;
		}
		if(type == ClassType.PALADIN) {
			return ClassColor.PALADIN_COLOR;
		}
		if(type == ClassType.PRIEST) {
			return ClassColor.PRIEST_COLOR;
		}
		if(type == ClassType.ROGUE) {
			return ClassColor.ROGUE_COLOR;
		}
		if(type == ClassType.SHAMAN) {
			return ClassColor.SHAMAN_COLOR;
		}
		if(type == ClassType.WARLOCK) {
			return ClassColor.WARLOCK_COLOR;
		}
		return null;
	}
	
	public static ClassType convStringToClassType(String classType) {
		if(classType.equals(warrior)) {
			return ClassType.GUERRIER;
		}
		if(classType.equals(druid)) {
			return ClassType.DRUID;
		}
		if(classType.equals(hunter)) {
			return ClassType.HUNTER;
		}
		if(classType.equals(mage)) {
			return ClassType.MAGE;
		}
		if(classType.equals(paladin)) {
			return ClassType.PALADIN;
		}
		if(classType.equals(priest)) {
			return ClassType.PRIEST;
		}
		if(classType.equals(rogue)) {
			return ClassType.ROGUE;
		}
		if(classType.equals(shaman)) {
			return ClassType.SHAMAN;
		}
		if(classType.equals(warlock)) {
			return ClassType.WARLOCK;
		}
		return null;
	}
} 

