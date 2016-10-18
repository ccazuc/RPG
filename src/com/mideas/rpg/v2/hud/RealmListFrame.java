package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.game.WorldServer;
import com.mideas.rpg.v2.utils.Button;

public class RealmListFrame {

	private static ArrayList<WorldServer> realmList = new ArrayList<WorldServer>();
	static WorldServer selectedRealm;
	private static Button acceptButton = new Button(Display.getWidth()/2+100, Display.getHeight()/2+250, 150, 20, "Ok", 15, 1) {
		@Override
		public void eventButtonClick() {
			CommandLogin.loginRealm(selectedRealm.getRealmId(), selectedRealm.getRealmName());
		}
	};
	private static Button cancelButton = new Button(Display.getWidth()/2+100, Display.getHeight()/2+200, 150, 20, "Cancel", 15, 1) {
		@Override
		public void eventButtonClick() {
			
		}
	};
	
	public static void draw() {
		int i = 0;
		while(i < realmList.size()) {
			i++;
		}
		acceptButton.draw();
		cancelButton.draw();
	}
	
	public static boolean mouseEvent() {
	
		acceptButton.event();
		cancelButton.event();
		return false;
	}
	
	public static void addRealm(WorldServer realm) {
		realmList.add(realm);
	}
	
	public static void sortRealmList() {
		int i = 0;
		int j = 0;
		WorldServer temp;
		while(i < realmList.size()) {
			j = i;
			while(j < realmList.size()) {
				if(realmList.get(i).getRealmName().compareTo(realmList.get(i).getRealmName()) > 0) {
					temp = realmList.get(j);
					realmList.set(j, realmList.get(i));
					realmList.set(i, temp);
				}
				j++;
			}
			i++;
		}
		selectedRealm = realmList.get(0);
	}
}
