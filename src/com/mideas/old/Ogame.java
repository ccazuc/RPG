package com.mideas.old;

import java.util.Scanner;

public class Ogame {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Co�t en m�tal :");
		String input = sc.nextLine();
		int metal = 0;
		for(String str : input.split(" ")) {
			metal+= Integer.parseInt(str);
		}
		System.out.println("Co�t en cristal :");
		input = sc.nextLine();
		int cristal = 0;
		for(String str : input.split(" ")) {
			cristal+= Integer.parseInt(str);
		}
		System.out.println("�a co�te "+metal+" metal et "+cristal+" cristal");
		sc.close();
	}
}
