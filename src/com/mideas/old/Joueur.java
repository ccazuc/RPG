package com.mideas.old;

public class Joueur {

	private int hp;
	private int atk;
	private int shield;
	private int crit;
	private int dodge;
	
	public Joueur(int hp, int atk, int shield, int crit, int dodge) {
		this.hp = hp;
		this.atk = atk;
		this.shield = shield;
		this.crit = crit;
		this.dodge = dodge;
	}

	public void attack(Joueur joueur) {
		if(Math.random() < dodge/100.) {
			System.out.println("Player dodge the attack");
		}
		else {
			int degats = Math.max(atk - joueur.getShield(), 1);
			if(Math.random() < crit/100.) {
				degats*= 2;
			}
			joueur.setHp(joueur.getHp()-degats);
			System.out.println("L'attaque a enlevé "+degats+" hp, il lui reste "+joueur.getHp()+" hp.");
		}
		
	}
	
	public int getShield() {
		return shield;
	}

	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = Math.max(hp, 0);
	}
	
}
