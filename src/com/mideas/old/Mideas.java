package com.mideas.old;

public class Mideas {
	
	public static void main(String[] args) {
		Joueur joueur1 = new Guerrier();
		Joueur joueur2 = new Mage();
		
		while(joueur1.getHp() > 0 && joueur2.getHp() > 0) {
			joueur1.attack(joueur2);
			joueur2.attack(joueur1);
		}
		if(joueur1.getHp() <= 0) {
			System.out.println("Le joueur 2 a gagné !");
		}
		else {
			System.out.println("Le joueur 1 a gagné !");
		}
	}	
}
