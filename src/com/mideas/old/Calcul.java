package com.mideas.old;

public class Calcul {

	public static void isDad(int a) {
		if(a%2 == 0) {
			System.out.println("Le nombre est paire");
		}
		else {
			System.out.println("Le nombre n'est pas paire");
		}
	}

	public static void pow(int b) {
		for(int i=1;i<6;i++) {
			System.out.println("La puissance "+i+" du nombre "+b+" est "+(int)Math.pow(b, i));
			if(Math.pow(b, i)%2 ==0) {
				System.out.println("Le nombre "+b+" est paire");
			}
		}
		
	}
	
}
