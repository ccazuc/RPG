package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellHeal;
import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.game.stuff.item.craft.LinenCloth;
import com.mideas.rpg.v2.game.stuff.item.potion.healingPotion.SuperHealingPotion;
import com.mideas.rpg.v2.hud.LogChat;

public class Joueur {
	
	/*private Leggings leggings;
	private Wrists wrists;
	private Shoulders shoulders;
	private Necklace necklace;
	private Back back;
	private Boots boots;
	private Chest chest;
	private Head head;
	private Gloves gloves;
	private Belt belt;
	private Ring ring;
	private Ring2 ring2;
	private Trinket trinket;
	private Trinket2 trinket2;
	private MainHand mainHand;
	private OffHand offHand;
	private Ranged ranged;*/
	private String classe;
	private Spell[] spells;
	private Spell[] spellUnlocked;
	private Stuff[] stuff;
	private int numberSuperHealingPotion;
	private int numberLinenCloth;
	private int maxStamina;
	private int expGained;
	private int isHealer;
	private int critical;
	public int stamina;
	private int maxMana;
	private int baseExp;
	private int strength;
	private float armor;
	private int mana;
	private int stun;
	private int exp;
	private int gold;
	private int goldGained;
	private int defaultArmor;
	private int defaultStuffArmor;
	private int tailorExp;
	public int x;
	public static int y;
	public static int z;
	
	public Joueur(int stamina, int strength, float armor, int defaultArmor, int defaultStuffArmor, int critical, int mana, Spell[] spells, Spell[] spellUnlocked, Stuff[] stuff, String classe, int id, int maxStamina, int maxMana, int isHealer, int expGained, int goldGained, int tailorExp) {
		this.maxStamina = maxStamina;
		this.expGained = expGained;
		this.goldGained = goldGained;
		this.isHealer = isHealer;
		this.critical = critical;
		this.tailorExp = tailorExp;
		this.stamina = stamina;
		this.maxMana = maxMana;
		this.strength = strength;
		this.armor = armor;
		this.mana = mana;
		this.defaultArmor = defaultArmor;
		this.defaultStuffArmor = defaultStuffArmor;
		//this.stun = stun;
		this.spells = spells;
		this.spellUnlocked = spellUnlocked;
		this.stuff = stuff;
		this.classe = classe;
		/*for(Spell spell : this.spells) {
			spell.setJoueur(this);
		}*/
	}
	
	public void tick() {
		if(stun > 0) {
			System.out.println("Le joueur "+(Mideas.joueur1().equals(this)?1:2)+" est stun");
			stun--;
		}
		else {
			if(Mideas.getCurrentPlayer()) {
				attack(Mideas.joueur2());
			}
		}
		
	}
	
	public boolean cast(Spell spell) {
		if(spell instanceof Spell) {
			if(!spell.hasMana()) {
				attack(Mideas.joueur2());
			}
			else {
				spell.cast(Mideas.joueur2(), Mideas.joueur1(), spell);
				LogChat.setStatusText("Le joueur 1 a enlevée "+spell.getDamage()+" hp au "+Mideas.joueur2().getClasse()+", "+Mideas.joueur2().getStamina()+" hp restant");
				return true;
			}
		}
		else {
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
	
	public void attack(Joueur joueur) {
		double damage = Mideas.joueur1().getStrength()+Math.random()*100;
		if(Math.random() < critical/100.) {
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
		double damage = Mideas.joueur2().getStrength()+Math.random()*100;
		float rand = (float) Math.random();
		if(rand < Mideas.joueur2().getCritical()/100.) {
			damage*= 2;
		}
		if(spell instanceof SpellHeal && spell.hasMana()) {
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
		else {
			Spell cast = Spell.getRandomSpell();
			if(rand > .2 && rand <= .4 && cast.hasMana()) {	
				cast.cast(Mideas.joueur1(), Mideas.joueur2(), spell);
				LogChat.setStatusText2("Le joueur 2 a enlevée "+cast.getDamage()+" hp au "+Mideas.joueur1().getClasse()+", "+Mideas.joueur1().getStamina()+" hp restant");
			}
			else {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()-damage);
				LogChat.setStatusText2("Le joueur 2 a enlevée "+Math.round(damage)+" hp au "+Mideas.joueur1().getClasse()+", "+Mideas.joueur1().getStamina()+" hp restant");	
			}
		}
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
	}
	
	public void setStuffCritical(int critical) {
		this.critical+= critical;
	}
	
	public void setStuffMana(int mana) {
		this.maxMana+= mana;
		this.mana+= mana;
	}
	
	public int getDefaultStuffArmor() {
		return defaultStuffArmor;
	}
	
	/*public void setHead(Head head) {
		this.stuff[0] = head;
	}
	
	public Head getHead() {
		return (Head)stuff[0];
	}

	public void setNecklace(Necklace necklace) {
		this.stuff[1] = necklace;
	}
	
	public Necklace getNecklace() {
		return (Necklace)stuff[1];
	}
	
	public void setShoulders(Shoulders shoulders) {
		this.stuff[2] = shoulders;
	}
	
	public Shoulders getShoulders() {
		return (Shoulders)stuff[2];
	}

	public void setBack(Back back) {
		this.stuff[3] = back;
	}
	
	public Back getBack() {
		return (Back)stuff[3];
	}
	
	public void setChest(Chest chest) {
		this.stuff[4] = chest;
	}

	public Chest getChest() {
		return (Chest)stuff[4];
	}
	
	public void setWrists(Wrists wrists) {
		this.stuff[7] = wrists;
	}
	
	public Wrists getWrists() {
		return (Wrists)stuff[7];
	}
	
	public void setGloves(Gloves gloves) {
		this.stuff[8] = gloves;
	}
	
	public Gloves getGloves() {
		return (Gloves)stuff[8];
	}
	
	public void setBelt(Belt belt) {
		this.stuff[9] = belt;
	}
	
	public Belt getBelt() {
		return (Belt)stuff[9];
	}
	
	public void setLeggings(Leggings leggings) {
		this.stuff[10] = leggings;
	}
	
	public Leggings getLeggings() {
		return (Leggings)stuff[10];
	}
	
	public void setBoots(Boots boots) {
		this.stuff[11] = boots;
	}
	
	public Boots getBoots() {
		return (Boots)stuff[11];
	}
	
	public void setRing(Ring ring) {
		this.stuff[12] = ring;
	}
	
	public Ring getRing() {
		return (Ring)stuff[12];
	}
	
	public void setRing2(Ring2 ring2) {
		this.stuff[13] = ring2;
	}
	
	public Ring2 getRing2() {
		return (Ring2)stuff[13];
	}
	
	public void setTrinket(Trinket trinket) {
		this.stuff[14] = trinket;
	}
	
	public Trinket getTrinket() {
		return (Trinket)stuff[14];
	}
	
	public void setTrinket2(Trinket2 trinket) {
		this.stuff[15] = trinket;
	}
	
	public Trinket2 getTrinket2() {
		return (Trinket2)stuff[15];
	}
	
	public void setMainHand(MainHand mainHand) {
		this.stuff[16] = mainHand;
	}
	
	public MainHand getMainHand() {
		return (MainHand)stuff[16];
	}
	
	public void setOffHand(OffHand offHand) {
		this.stuff[17] = offHand;
	}
	
	public OffHand getOffHand() {
		return (OffHand)stuff[17];
	}
	
	public void setRanged(Ranged ranged) {
		this.stuff[18] = ranged;
	}
	
	public Ranged getRanged() {
		return (Ranged)stuff[18];
	}*/

	/*public void setHead(Head head) {
		this.head = head;
	}
	
	public Head getHead() {
		return head;
	}

	public void setNecklace(Necklace necklace) {
		this.necklace = necklace;
	}
	
	public Necklace getNecklace() {
		return necklace;
	}
	
	public void setShoulders(Shoulders shoulders) {
		this.shoulders = shoulders;
	}
	
	public Shoulders getShoulders() {
		return shoulders;
	}

	public void setBack(Back back) {
		this.back = back;
	}
	
	public Back getBack() {
		return back;
	}
	
	public void setChest(Chest chest) {
		this.chest = chest;
	}

	public Chest getChest() {
		return chest;
	}
	
	public void setWrists(Wrists wrists) {
		this.wrists = wrists;
	}
	
	public Wrists getWrists() {
		return wrists;
	}
	
	public void setGloves(Gloves gloves) {
		this.gloves = gloves;
	}
	
	public Gloves getGloves() {
		return gloves;
	}
	
	public void setBelt(Belt belt) {
		this.belt= belt;
	}
	
	public Belt getBelt() {
		return belt;
	}
	
	public void setLeggings(Leggings leggings) {
		this.leggings = leggings;
	}
	
	public Leggings getLeggings() {
		return leggings;
	}
	
	public void setBoots(Boots boots) {
		this.boots = boots;
	}
	
	public Boots getBoots() {
		return boots;
	}
	
	public void setRing(Ring ring) {
		this.ring = ring;
	}
	
	public Ring getRing() {
		return ring;
	}
	
	public void setRing2(Ring2 ring2) {
		this.ring2 = ring2;
	}
	
	public Ring2 getRing2() {
		return ring2;
	}
	
	public void setTrinket(Trinket trinket) {
		this.trinket = trinket;
	}
	
	public Trinket getTrinket() {
		return trinket;
	}
	
	public void setTrinket2(Trinket2 trinket) {
		this.trinket2 = trinket;
	}
	
	public Trinket2 getTrinket2() {
		return trinket2;
	}
	
	public void setMainHand(MainHand mainHand) {
		this.mainHand = mainHand;
	}
	
	public MainHand getMainHand() {
		return mainHand;
	}
	
	public void setOffHand(OffHand offHand) {
		this.offHand = offHand;
	}
	
	public OffHand getOffHand() {
		return offHand;
	}
	
	public void setRanged(Ranged ranged) {
		this.ranged = ranged;
	}
	
	public Ranged getRanged() {
		return ranged;
	}*/
	
	public String getClasse() {
		return classe;
	}
	
	public float getArmor() {
		return armor;
	}
	
	public int getIsHealer() {
		return isHealer;
	}
	
	public void setStamina(double d) {
		this.stamina = (int) Math.max(d, 0);
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public int getCritical() {
		return critical;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public void setMana(int mana) {
		this.mana = Math.max(mana, 0);
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	public void setStun(int stun) {
		this.stun = stun;
	}
	
	public int getX() {
		return x;
	}

	public Spell[] getSpells() {
		return spells;
	}

	public Spell getSpells(int i) {
		return spells[i];
	}
	
	public void setSpells(int i, Spell spell) {
		this.spells[i] = spell;
	}
	
	public Spell[] getSpellUnlocked() {
		return spellUnlocked;
	}
	
	public Spell getSpellUnlocked(int i) {
		return spellUnlocked[i];
	}
	
	public Stuff[] getStuff() {
		return stuff;
	}
	
	public Stuff getStuff(int i) {
		return stuff[i];
	}
	
	public void setStuff(int i, Stuff stuff) {
		this.stuff[i] = stuff;
	}

	public void setSpellUnlocked(int i, Spell spell) {
		this.spellUnlocked[i] = spell;
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getBaseExp() {
		return baseExp;
	}
	
	public int getExpGained() {
		return expGained;
	}
	
	public void setArmor(float number) {
		this.armor = (Math.round(100*(armor+number))/100.f);
	}
	
	public int getDefaultArmor() {
		return defaultArmor;
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getGoldGained() {
		return goldGained;
	}
	
	public void setExp(int baseExp, int expGained ) {
		exp = baseExp+expGained;
	}
	
	public int getNumberItem(Stuff potion) {
		if(potion instanceof SuperHealingPotion) {
			return numberSuperHealingPotion;
		}
		if(potion instanceof LinenCloth) {
			return numberLinenCloth;
		}
		
		return 0;
	}
	
	public void setNumberItem(Stuff potion, int number) {
		if(potion instanceof SuperHealingPotion) {
			numberSuperHealingPotion = number;
		}
		if(potion instanceof LinenCloth) {
			numberLinenCloth = number;
		}
	}
} 
