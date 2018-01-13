package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Window;
import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.connection.AuthServerConnectionRunnable;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.WorldServer;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class RealmListFrame {

	private static boolean shouldUpdateSize;
	private final static Color BG_COLOR = new Color(0, 0, 0, .78f);
	private final static Color GREEN = Color.decode("#158816");
	private final static ArrayList<WorldServer> realmList = new ArrayList<WorldServer>();
	static WorldServer selectedRealm;
	private static Button acceptButton = new Button(Display.getWidth()/2+88*Mideas.getDisplayXFactor(), Display.getHeight()/2+282*Mideas.getDisplayYFactor(), 154*Mideas.getDisplayXFactor(), 31*Mideas.getDisplayXFactor(), "OK", 20, 2) {
		
		@Override
		public void eventButtonClick() {
			if(selectedRealm != null) {
				connectToWorldServer();
				this.reset();
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedRealm != null;
		}
	};
	private static Button cancelButton = new Button(Display.getWidth()/2+253*Mideas.getDisplayXFactor(), Display.getHeight()/2+282*Mideas.getDisplayYFactor(), 152*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayXFactor(), "Cancel", 18, 2) {
		
		@Override
		public void eventButtonClick() {
			SelectScreen.setRealmScreenActive(false);
			this.reset();
		}
	};
	
	public static void draw() {
		updateSize();
		float yShift = 29*Mideas.getDisplayYFactor();
		Draw.drawColorQuad(0, 0, Display.getWidth(), Display.getHeight(), BG_COLOR);
		Draw.drawQuad(Sprites.realm_list_box, Display.getWidth()/2+5*Mideas.getDisplayXFactor()-Sprites.realm_list_box.getImageWidth()/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-350*Mideas.getDisplayYFactor());
		int i = 0;
		while(i < realmList.size()) {
			if(selectedRealm == realmList.get(i)) {
				Draw.drawQuad(Sprites.selected_realm, Display.getWidth()/2+25*Mideas.getDisplayXFactor()-Sprites.realm_list_box.getImageWidth()/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-264*Mideas.getDisplayYFactor()+i*yShift);
				FontManager.get("FRIZQT", 18).drawStringShadow(Display.getWidth()/2+40*Mideas.getDisplayXFactor()-Sprites.realm_list_box.getImageWidth()/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-264*Mideas.getDisplayYFactor()+i*yShift, realmList.get(i).getRealmName(), Color.WHITE, Color.BLACK, 2, 1, 1);
			}
			else {
				FontManager.get("FRIZQT", 18).drawStringShadow(Display.getWidth()/2+40*Mideas.getDisplayXFactor()-Sprites.realm_list_box.getImageWidth()/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-264*Mideas.getDisplayYFactor()+i*yShift, realmList.get(i).getRealmName(), GREEN, Color.BLACK, 2, 1, 1);
			}
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
	
	public static boolean event() {
		if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
			if(selectedRealm != null) {
				connectToWorldServer();
			}
			return true;
		}
		return false;
	}
	
	public static void addRealm(WorldServer realm) {
		realmList.add(realm);
	}
	
	public static void clearRealmList() {
		realmList.clear();
		selectedRealm = null;
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
		if(realmList.size() > 0) {
			selectedRealm = realmList.get(0);
		}
	}
	
	public static WorldServer getSelectedRealm() {
		return selectedRealm;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		acceptButton.update(Display.getWidth()/2+88*Mideas.getDisplayXFactor(), Display.getHeight()/2+282*Mideas.getDisplayYFactor(), 154*Mideas.getDisplayXFactor(), 31*Mideas.getDisplayXFactor());
		cancelButton.update(Display.getWidth()/2+253*Mideas.getDisplayXFactor(), Window.getHeight()/2+282*Mideas.getDisplayYFactor(), 152*Mideas.getDisplayXFactor(), 30*Mideas.getDisplayXFactor());
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	static void connectToWorldServer() {
		if (ConnectionManager.getWorldServerConnection() != null)
		{
			CommandLogout.logoutWorldServer();
			ConnectionManager.close();
		}
		SelectScreen.setRealmScreenActive(false);
		SelectScreen.setAlert("Connecting to server...");
		AuthServerConnectionRunnable.connectToWorldServer(selectedRealm.getRealmId());
		SelectScreen.resetCharacterList();
	}
}
