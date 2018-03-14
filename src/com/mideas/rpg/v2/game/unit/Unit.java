package com.mideas.rpg.v2.game.unit;

import java.util.ArrayList;

import com.mideas.rpg.v2.game.aura.AppliedAura;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;

public class Unit {

	protected boolean hasManaChanged = true;
	protected boolean hasHpChanged = true;
	protected int level;
	protected String levelString;
	protected String name;
	protected String classString = "";
	protected int maxStamina;
	protected int stamina;
	protected int maxMana;
	protected int mana;
	protected int id;
	protected String healthText = "";
	protected String manaText = "";
	protected ClassType classType;
	protected Texture portraitFrame;
	protected ArrayList<AppliedAura> buffList;
	protected ArrayList<AppliedAura> debuffList;
	
	public Unit(int id, int stamina, int maxStamina, int mana, int maxMana, int level, String name, ClassType type) {
		this.stamina = stamina;
		this.maxStamina = maxStamina;
		this.mana = mana;
		this.id = id;
		this.maxMana = maxMana;
		setLevel(level);
		this.name = name;
		this.classType = type;
		this.portraitFrame = getPortrait(type);
		this.classString = Joueur.convClassTypeToString(this.classType);
		this.buffList = new ArrayList<AppliedAura>();
		this.debuffList = new ArrayList<AppliedAura>();
	}
	
	public void applyAura(AppliedAura aura) {
		if(aura.getAura().isBuff()) {
			this.buffList.add(aura);
		}
		else {
			this.debuffList.add(aura);
		}
	}
	
	public AppliedAura getBuff(int auraID) {
		int i = this.buffList.size();
		while(--i >= 0) {
			if(this.buffList.get(i).getAura().getId() == auraID) {
				return this.buffList.get(i);
			}
		}
		return null;
	}
	
	public AppliedAura getDebuff(int auraID) {
		int i = this.debuffList.size();
		while(--i >= 0) {
			if(this.debuffList.get(i).getAura().getId() == auraID) {
				return this.debuffList.get(i);
			}
		}
		return null;
	}
	
	public void removeBuff(int auraID) {
		int i = this.buffList.size();
		while(--i >= 0) {
			if(this.buffList.get(i).getAura().getId() == auraID) {
				this.buffList.remove(i);
				return;
			}
		}
 	}
	
	public void removeDebuff(int auraID) {
		int i = this.debuffList.size();
		while(--i >= 0) {
			if(this.debuffList.get(i).getAura().getId() == auraID) {
				this.debuffList.remove(i);
				return;
			}
		}
 	}
	
	public Texture getPortrait() {
		return this.portraitFrame;
	}
	
	public ClassType getClassType() {
		return this.classType;
	}
	
	public String getHealthText() {
		return this.healthText;
	}
	
	public void setHealthText(String text) {
		this.healthText = text;
	}
	
	public String getManaText() {
		return this.manaText;
	}
	
	public String getClasseString() {
		return this.classString;
	}
	
	public void setManaText(String text) {
		this.manaText = text;
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
	
	public int getStamina() {
		return this.stamina;
	}
	
	public ArrayList<AppliedAura> getBuffList() {
		return this.buffList;
	}
	
	public ArrayList<AppliedAura> getDebuffList() {
		return this.debuffList;
	}
	
	public void setStamina(double d) {
		this.stamina = (int)Math.max(d, 0);
		this.hasHpChanged = true;
	}
	
	public int getMaxStamina() {
		return this.maxStamina;
	}
	
	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
		if(this.stamina > this.maxStamina) {
			this.stamina = this.maxStamina;
		}
		this.hasHpChanged = true;
	}
	
	public int getMana() {
		return this.mana;
	}
	
	public void setMana(int mana) {
		this.mana = Math.max(mana, 0);
		this.hasManaChanged = true;
	}
	
	public int getMaxMana() {
		return this.maxMana;
	}
	
	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.levelString = Integer.toString(level);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static Texture getPortrait(ClassType type) {
		if(type == ClassType.PRIEST) {
			return Sprites.priest;
		}
		else if(type == ClassType.MAGE) {
			return Sprites.mage;
		}
		else if(type == ClassType.GUERRIER) {
			return Sprites.war;
		}
		else if(type == ClassType.HUNTER) {
			return Sprites.hunter;
		}	
		else if(type == ClassType.PALADIN) {
			return Sprites.paladin;
		}
		else if(type == ClassType.ROGUE) {
			return Sprites.rogue;
		}
		else if(type == ClassType.SHAMAN) {
			return Sprites.shaman;
		}
		else if(type == ClassType.WARLOCK) {
			return Sprites.warlock;
		}
		else if(type == ClassType.NPC) {
			return Sprites.illidan;
		}
		return null;
	}
}