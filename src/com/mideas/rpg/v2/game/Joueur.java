package com.mideas.rpg.v2.game;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.bag.BagManager;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.game.profession.Profession;
import com.mideas.rpg.v2.game.profession.ProfessionManager;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class Joueur {
	
	private Shortcut[] spells;
	private Spell[] spellUnlocked;
	private Stuff[] stuff;
	private WeaponType[] weaponType;
	private Shortcut[] shortcut;
	private int maxStamina;
	private int expGained;
	private int critical;
	public int stamina;
	private int maxMana;
	private int baseExp;
	private int strength;
	private float armor;
	private Wear wear;
	private int mana;
	private int exp;
	private int gold;
	private int goldGained;
	private int defaultArmor;
	private int numberRedGem;
	private int numberBlueGem;
	private int numberYellowGem;
	private Profession firstProfession;
	private Profession secondProfession;
	private boolean hasHpChanged = true;
	private boolean hasManaChanged = true;
	//private int tailorExp;
	private String id;
	private int tempId;
	public int x;
	public static int y;
	public static int z;
	
	public Joueur(String id, int tempId, Wear wear, WeaponType[] weaponType, int stamina, int mana, int strength, int armor, int defaultArmor, int critical, int maxStamina, int maxMana, int expGained, int goldGained, Shortcut[] spells, Spell[] spellUnlocked, Stuff[] stuff) {
		this.id = id;
		this.tempId = tempId;
		this.stamina = stamina;
		this.mana = mana;
		this.strength = strength;
		this.armor = armor;
		this.defaultArmor = defaultArmor;
		this.critical = critical;
		this.maxStamina = maxStamina;
		this.maxMana = maxMana;
		this.expGained = expGained;
		this.goldGained = goldGained;
		this.spells = spells;
		this.spellUnlocked = spellUnlocked;
		this.weaponType = weaponType;
		this.stuff = stuff;
		this.wear = wear;
		this.firstProfession = ProfessionManager.getProfession(0);
	}
	
	public Joueur(Joueur joueur) {
		this.id = joueur.id;
		this.weaponType = joueur.weaponType;
		this.stamina = joueur.stamina;
		this.mana = joueur.mana;
		this.strength = joueur.strength;
		this.armor = joueur.armor;
		this.defaultArmor = joueur.defaultArmor;
		this.critical = joueur.critical;
		this.maxStamina = joueur.maxStamina;
		this.maxMana = joueur.maxMana;
		this.expGained = joueur.expGained;
		this.goldGained = joueur.goldGained;
		this.spells = joueur.spells;
		this.spellUnlocked = joueur.spellUnlocked;
		this.stuff = joueur.stuff;
		this.wear = joueur.wear;
	}
	
	public void tick() throws SQLException {
		if(Mideas.getCurrentPlayer()) {
			attack(Mideas.joueur2());
			SpellBarFrame.setIsCastingSpell(false);
		}
	}
	
	public boolean cast(Spell spell) throws SQLException {
		if(spell.getType() == SpellType.DAMAGE) {
			SpellBarFrame.setIsCastingSpell(false);
			if(!spell.hasMana()) {
				attack(Mideas.joueur2());
			}
			else {
				spell.cast(Mideas.joueur2(), Mideas.joueur1(), spell);
				LogChat.setStatusText("Le joueur 1 a enlevée "+spell.getDamage()+" hp au "+Mideas.joueur2().getClasse()+", "+Mideas.joueur2().getStamina()+" hp restant");
				return true;
			}
		}
		else if(spell.getType() == SpellType.HEAL) {
			SpellBarFrame.setIsCastingSpell(false);
			if(!spell.hasMana()) {
				attack(Mideas.joueur2());
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
	}
	
	public void attack(Joueur joueur) throws SQLException {
		double damage = Mideas.joueur1().getStrength()*ThreadLocalRandom.current().nextDouble(.9, 1.1);
		SpellBarFrame.setIsCastingSpell(false);
		if(Math.random() < this.critical/100.) {
			damage*= 2;
		}
		joueur.setStamina(joueur.getStamina()-damage);
		LogChat.setStatusText("Le joueur 1 a enlevé "+Math.round(damage)+" hp au "+joueur.getClasse()+", "+joueur.getStamina()+" hp restant"); //and "+Mideas.joueur2.getMana()+" mana left");
		if(Mideas.joueur1().getStamina() <= 0) {
			LogChat.setStatusText("Le joueur 2 a gagné !");
			LogChat.setStatusText2(""); 
			y = 1;
			return;
		}
		else if(Mideas.joueur2().getStamina() <= 0) {
			LogChat.setStatusText("Le joueur 1 a gagné !");
			LogChat.setStatusText2("");
			z = 1;
			return;
		}
	}
	
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
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && !Mideas.bag().getBag(i).getIsLoaded()) {
				if(StuffManager.exists(Mideas.bag().getBag(i).getId())) {
					Mideas.bag().setBag(i, StuffManager.getClone(Mideas.bag().getBag(i).getId()));
					Mideas.bag().getBag(i).setIsLoaded(true);
					ConnectionManager.getItemRequested().remove(Mideas.bag().getBag(i).getId());
					i++;
					continue;
				}
				else if(WeaponManager.exists(Mideas.bag().getBag(i).getId())) {
					Mideas.bag().setBag(i, WeaponManager.getClone(Mideas.bag().getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(Mideas.bag().getBag(i).getId());
					Mideas.bag().getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(GemManager.exists(Mideas.bag().getBag(i).getId())) {
					Mideas.bag().setBag(i, GemManager.getClone(Mideas.bag().getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(Mideas.bag().getBag(i).getId());
					Mideas.bag().getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(BagManager.exists(Mideas.bag().getBag(i).getId())) {
					Mideas.bag().setBag(i, BagManager.getClone(Mideas.bag().getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(Mideas.bag().getBag(i).getId());
					Mideas.bag().getBag(i).setIsLoaded(true);
					i++;
					continue;
				}
				else if(PotionManager.exists(Mideas.bag().getBag(i).getId())) {
					int number = Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i));
					Mideas.bag().getNumberStack().remove(Mideas.bag().getBag(i));
					Mideas.bag().setBag(i, PotionManager.getClone(Mideas.bag().getBag(i).getId()));
					ConnectionManager.getItemRequested().remove(Mideas.bag().getBag(i).getId());
					Mideas.bag().getBag(i).setIsLoaded(true);
					Mideas.bag().getNumberStack().put(Mideas.bag().getBag(i), number);
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
		}
	}
	
	public boolean addItem(Item item, int amount) throws SQLException {
		int i = 0;
		if(!item.isStackable()) {
			while(i < Mideas.bag().getBag().length && amount > 0) {
				if(Mideas.bag().getBag(i) == null) {
					Mideas.bag().setBag(i, item);
					Mideas.bag().setBagChange(true);
					amount --;
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
		else {
			while(i < Mideas.bag().getBag().length) {
				if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).equals(item)) {
					Mideas.bag().setBag(i, item, Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i))+amount);
					Mideas.bag().setBagChange(true);
					CharacterStuff.setBagItems();
					return true;
				}
				i++;
			}
			i = 0;
			while(i < Mideas.bag().getBag().length) {
				if(Mideas.bag().getBag(i) == null) {
					Mideas.bag().setBag(i, item, amount);
					Mideas.bag().setBagChange(true);
					CharacterStuff.setBagItems();
					return true;
				}
				i++;
			}
		}
		LogChat.setStatusText3("Votre inventaire est pleins");
		return false;
	}
	
	public void addMultipleUnstackableItem(int id, int number) throws SQLException {
		int i = 0;
		ItemType type;
		if(WeaponManager.exists(id) || StuffManager.exists(id) || GemManager.exists(id) || BagManager.exists(id)) {
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
				type = ItemType.BAG;
			}
			while(i < Mideas.bag().getBag().length && number > 0) {
				if(Mideas.bag().getBag(i) == null) {
					if(type == ItemType.WEAPON) {
						Mideas.bag().setBag(i, WeaponManager.getClone(id));
					}
					else if(type == ItemType.STUFF) {
						Mideas.bag().setBag(i, StuffManager.getClone(id));
					}
					else if(type == ItemType.GEM) {
						Mideas.bag().setBag(i, GemManager.getClone(id));
					}
					else {
						Mideas.bag().setBag(i, BagManager.getClone(id));
					}
					Mideas.bag().setBagChange(true);
					number--;
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
	}
	
	public void deleteItem(Item item, int amount) throws SQLException {
		int i = 0;
		if(!item.isStackable()) {
			while(i < Mideas.bag().getBag().length && amount > 0) {
				if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).equals(item)) {
					Mideas.bag().setBag(i, null);
					Mideas.bag().setBagChange(true);
					amount--;
				}
				i++;
			}
			CharacterStuff.setBagItems();
		}
		else {
			while(i < Mideas.bag().getBag().length && amount > 0) {
				if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).equals(item)) {
					int temp = amount;
					amount-= Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i));
					Mideas.bag().setBag(i, Mideas.bag().getBag(i), Math.max(0, Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i))-temp));
					Mideas.bag().setBagChange(true);
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
		if(Mideas.getLevel() >= stuff.getLevel() && canWear(stuff) && stuff.canEquipTo(DragManager.convClassType())) {
			return true;
		}
		return false;
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
	
	public boolean getHasHpChanged() {
		return this.hasHpChanged;
	}
	
	public void setHasHpChanged(boolean we) {
		this.hasHpChanged = we;
	}
	
	public boolean getHasManaChanged() {
		return this.hasManaChanged;
	}
	
	public void setHasManaChanged(boolean we) {
		this.hasManaChanged = we;
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
	
	public String getClasse() {
		return this.id;
	}
	
	public int getId() {
		return this.tempId;
	}
	
	public float getArmor() {
		return this.armor;
	}
	
	public void setStamina(double d) {
		this.stamina = (int)Math.max(d, 0);
		this.hasHpChanged = true;
	}
	
	public int getMaxStamina() {
		return this.maxStamina;
	}
	
	public int getStamina() {
		return this.stamina;
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
	
	public void setMana(int mana) {
		this.mana = Math.max(mana, 0);
		this.hasManaChanged = true;
	}
	
	public int getMana() {
		return this.mana;
	}
	
	public int getMaxMana() {
		return this.maxMana;
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
	
	public int getGoldGained() {
		return this.goldGained;
	}
	
	public void setExp(int baseExp, int expGained ) {
		this.exp = baseExp+expGained;
	}
	
	public void setMaxStamina(int stamina) {
		this.maxStamina = stamina;
	}
	
	public void setMaxMana(int mana) {
		this.maxMana = mana;
	}
	
	public int getNumberItem(Item item) {
		if(item.isStackable() && Mideas.bag().getNumberStack().containsKey(item)) {
			return Mideas.bag().getNumberStack().get(item);
		}
		return 0;
	}
	
	public void setNumberItem(Item item, int number) {
		if(item.isStackable()) {
			Mideas.bag().getNumberStack().put(item, number);
		}
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
} 

