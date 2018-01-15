package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.command.CommandCreateCharacter;
import com.mideas.rpg.v2.command.CommandDeleteCharacter;
import com.mideas.rpg.v2.command.CommandLoadCharacter;
import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.command.CommandSendRealmList;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.classes.SelectScreenPlayer;
import com.mideas.rpg.v2.game.race.Classe;
import com.mideas.rpg.v2.game.race.NewCharacterRace;
import com.mideas.rpg.v2.utils.Alert;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.DebugUtils;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.StringUtils;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class SelectScreen {

	private static boolean shouldUpdateSize;
	static boolean creatingCharacter;
	static boolean deletingCharacter;
	static boolean characterLoaded;
	static SelectScreenPlayer[] characterList = new SelectScreenPlayer[10];
	private static boolean[] selectedCharacter = new boolean[10];
	static int selectedCharacterIndex = 0;
	private static int hoveredCharacter = -1;
	private static int totalCharacter;
	private static float x_hover_race;
	private static float y_hover_race;
	private static float x_hover_classe;
	private static float y_hover_classe;
	private static NewCharacterRace[] race = new NewCharacterRace[]{NewCharacterRace.HUMAN, NewCharacterRace.DWARF, NewCharacterRace.NIGHTELF, NewCharacterRace.GNOME, NewCharacterRace.DRAENEI, NewCharacterRace.ORC, NewCharacterRace.UNDEAD, NewCharacterRace.TAUREN, NewCharacterRace.TROLL, NewCharacterRace.BLOODELF};
	private static NewCharacterRace hoveredRace;
	private static Classe hoveredClasse;
	private static NewCharacterRace selectedRace = NewCharacterRace.UNDEAD;
	private static Classe selectedClasse = NewCharacterRace.HUMAN.getClasse()[0];
	private static float x_selected_race = -742;
	private static float y_selected_race = -340+66;
	private static float x_selected_classe = -868;
	private static float y_selected_classe = 99;
	private static int loginQueuePosition;
	private static String loginQueuePositionString;
	private static int loginQueueSize;
	private static String loginQueueTimeInQueueString;
	private static String loginQueueSizeString;
	private static long loginQueueJoinTimer;
	private static long loginQueueLastTimerUpdate;
	private static Color bgColors = new Color(0, 0, 0, .35f);
	final static Input character = new Input(FontManager.get("FRIZQT", 21), 12, false, false);
	final static Input deleteCharacter = new Input(FontManager.get("FRIZQT", 21), 8, false, false);
	static boolean realmScreenActive = true;
	final static Alert alert = new Alert("", -355*Mideas.getDisplayXFactor(), -60*Mideas.getDisplayYFactor(), 700*Mideas.getDisplayXFactor(), 20, "Ok");
	static Alert currentAlert = alert;
	final static Alert loginQueueAlert = new Alert("", -355 * Mideas.getDisplayXFactor(), -80 * Mideas.getDisplayYFactor(), 700 * Mideas.getDisplayXFactor(), 20, "Change realm")
	{
		
		@Override
		public void onClose()
		{
			if (ConnectionManager.getWorldServer() == null)
				setRealmScreenActive(true);
		}
	}; 
	private final static Button newCharacterButton = new Button(Display.getWidth()/2+630*Mideas.getDisplayXFactor(), Display.getHeight()/2+293*Mideas.getDisplayYFactor(), 278*Mideas.getDisplayXFactor(), 36*Mideas.getDisplayYFactor(), "Create new character", 16, 2) {
		@Override
		public void eventButtonClick() {
			creatingCharacter = true;
			character.setIsActive(true);
			this.reset();
		}
		
		@Override
		public boolean activateCondition() {
			return (ConnectionManager.isLoggedOnWorldServer());
		}
	};	
	static final Button acceptCharacterButton = new Button(Display.getWidth()/2+705*Mideas.getDisplayXFactor(), Display.getHeight()/2+393*Mideas.getDisplayYFactor(), 195*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor(), "Accept", 16, 2) {
		@Override
		public void eventButtonClick() {
			SelectScreen.createCharacter();
			returnCharacterButton.reset();
			this.reset();
		}
	};	
	static final Button returnCharacterButton = new Button(Display.getWidth()/2+730*Mideas.getDisplayXFactor(), Display.getHeight()/2+442*Mideas.getDisplayYFactor(), 150*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor(), "Return", 16, 2) {
		@Override
		public void eventButtonClick() {
			creatingCharacter = false;
			character.setIsActive(false);
			acceptCharacterButton.reset();
			SelectScreen.mouseEvent();
		}
	};
	private final static Button returnButton = new Button(Display.getWidth()/2+785*Mideas.getDisplayXFactor(), Display.getHeight()/2+438*Mideas.getDisplayYFactor(), 122*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor(), "Return", 16, 2) {
		@Override
		public void eventButtonClick() {
			CommandLogout.write();
			ConnectionManager.close();
			ConnectionManager.closeAuth();
			Mideas.setJoueur1Null();
			Mideas.setAccountId(0);
			LoginScreen.resetMenuState();
			characterLoaded = false;
			realmScreenActive = false;
			currentAlert.setInactive();
			selectedCharacterIndex = 0;
			LoginScreen.mouseEvent();
			Arrays.fill(characterList, null);
			this.reset();
		}
	};
	private final static Button enterGameButton = new Button(Display.getWidth()/2-125*Mideas.getDisplayXFactor(), Display.getHeight()/2+403*Mideas.getDisplayYFactor(), 250*Mideas.getDisplayXFactor(), 50*Mideas.getDisplayYFactor(), "Enter game", 19, 2) {
		@Override
		public void eventButtonClick() {
			loadCharacterInfo();
			/*Arrays.fill(characterList, null);
			characterLoaded = false;
			Interface.setCharacterLoaded(false);*/
			this.reset();
		}
		
		@Override
		public boolean activateCondition() {
			return characterList[selectedCharacterIndex] != null;
		}
		
	};
	private final static Button changeRealmButton = new Button(Display.getWidth()/2+682*Mideas.getDisplayXFactor(), 57*Mideas.getDisplayYFactor(), 175*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor(), "Change Realm", 16, 2) {
		@Override
		public void eventButtonClick() {
			if (currentAlert == loginQueueAlert)
			{
				currentAlert = alert;
				loginQueueAlert.setInactive();
			}
			currentAlert.setActive();
			currentAlert.setText("Loading realm...");
			CommandSendRealmList.requestRealm();
			this.reset();
			ConnectionManager.setIsLoggedOnWorldServer(false);
			ConnectionManager.setWorldServer(null);
			characterLoaded = false;
		}
		
	};
	private final static Button deleteCharacterButton = new Button(Display.getWidth()/2+558*Mideas.getDisplayXFactor(), Display.getHeight()/2+438*Mideas.getDisplayYFactor(), 202*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor(), "Delete character", 16, 2) {
		@Override
		public void eventButtonClick() {
			deletingCharacter = true;
			deleteCharacter.setIsActive(true);
			this.reset();
		}
		
		@Override
		public boolean activateCondition() {
			return characterList[selectedCharacterIndex] != null;
		}
	};
	private final static Button confirmDeleteCharacterButton = new Button(Display.getWidth()/2-275*Mideas.getDisplayXFactor(), Display.getHeight()/2+58*Mideas.getDisplayYFactor(), 240, 32*Mideas.getDisplayYFactor(), "OK", 20, 2) {
		@Override
		public void eventButtonClick() {
			deleteCharacter();
			deleteCharacter.setIsActive(false);
		}
		
		@Override
		public boolean activateCondition() {
			if(deleteCharacter.getText().trim().toLowerCase().equals("effacer")) {
				return true;
			}
			return false;
		}
	};
	private final static Button cancelDeleteCharacterButton = new Button(Display.getWidth()/2+23*Mideas.getDisplayXFactor(), Display.getHeight()/2+58*Mideas.getDisplayYFactor(), 240, 32*Mideas.getDisplayYFactor(), "Annuler", 20, 2) {
		@Override
		public void eventButtonClick() {
			deletingCharacter = false;
			deleteCharacter.setIsActive(false);
		}
	};

	public static void draw() {
		updateSize();
		if(ConnectionManager.isLoggedOnWorldServer() && !characterLoaded) {
			selectedCharacter[0] = true;
			characterLoaded = true;
		}
		if(!creatingCharacter) {
			Draw.drawQuadBG(Sprites.select_screen_background);
			if(deletingCharacter) { //TODO: use PopupInput classe
				Draw.drawQuad(Sprites.big_alert, Display.getWidth()/2-350*Mideas.getDisplayXFactor(), Display.getHeight()/2-120*Mideas.getDisplayYFactor(), Sprites.big_alert.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.big_alert.getImageHeight()*Mideas.getDisplayYFactor());
				FontManager.get("FRIZQT", 21).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 21).getWidth("Voulez-vous effacer")/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-105*Mideas.getDisplayYFactor(), "Voulez-vous effacer", Color.YELLOW, Color.BLACK, 3, 2, 2);
				FontManager.get("FRIZQT", 21).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 21).getWidth(characterList[selectedCharacterIndex].getName()+" "+characterList[selectedCharacterIndex].getClasse()+" level "+characterList[selectedCharacterIndex].getLevel())/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-80*Mideas.getDisplayYFactor(), characterList[selectedCharacterIndex].getName()+" "+characterList[selectedCharacterIndex].getClasse()+" level "+characterList[selectedCharacterIndex].getLevel(), Color.WHITE, Color.BLACK, 2, 4, 2);
				FontManager.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 15).getWidth("Tapez \"EFFACER\" dans le champ pour confirmer.")*Mideas.getDisplayXFactor()/2, Display.getHeight()/2-30*Mideas.getDisplayYFactor(), "Tapez \"EFFACER\" dans le champ pour confirmer." , Color.YELLOW, Color.BLACK, 2, 1, 1);
				Draw.drawQuad(Sprites.input_box, Display.getWidth()/2-Sprites.input_box.getImageWidth()/2*Mideas.getDisplayXFactor(), Display.getHeight()/2-2*Mideas.getDisplayYFactor(), Sprites.input_box.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.input_box.getImageHeight()*Mideas.getDisplayYFactor());
				FontManager.get("FRIZQT", 21).drawStringShadow(Display.getWidth()/2-92*Mideas.getDisplayXFactor(), Display.getHeight()/2+5*Mideas.getDisplayYFactor(), deleteCharacter.getText(), Color.WHITE, Color.BLACK, 1, 1, 1);
				if(System.currentTimeMillis()%1000 < 500) {
					FontManager.get("FRIZQT", 22).drawString(Display.getWidth()/2-96*Mideas.getDisplayXFactor()+deleteCharacter.getCursorShift(), Display.getHeight()/2+0*Mideas.getDisplayYFactor(), "|", Color.WHITE);
				}
				confirmDeleteCharacterButton.draw();
				cancelDeleteCharacterButton.draw();
				return;
			}
			int i = 0;
			float y = 110*Mideas.getDisplayYFactor();
			while(i < totalCharacter) {
				drawCharacter(i, y);
				i++;
				y+= 75*Mideas.getDisplayYFactor();
			}
			if(characterList[selectedCharacterIndex] != null) {
				FontManager.get("FRIZQT", 30).drawStringShadow(Display.getWidth()/2-FontManager.get("FRIZQT", 30).getWidth(characterList[selectedCharacterIndex].getName())/2, Display.getHeight()-170*Mideas.getDisplayYFactor(), characterList[selectedCharacterIndex].getName(), Color.YELLOW, Color.BLACK, 2, 2, 2);
			}
			updateLoginQueueTimer();
			currentAlert.draw();
			newCharacterButton.draw();
			returnButton.draw();
			enterGameButton.draw();
			deleteCharacterButton.draw();
			changeRealmButton.draw();
		}
		else {
			Draw.drawQuadBG(Sprites.create_character_background);
			currentAlert.draw();
			FontManager.get("FRIZQT", 21).drawStringShadow(Display.getWidth()/2-77*Mideas.getDisplayXFactor(), Display.getHeight()/2+405*Mideas.getDisplayYFactor(), character.getText(), Color.WHITE, Color.BLACK, 1, 1, 2);
			acceptCharacterButton.draw();
			returnCharacterButton.draw();
			int i = 0;
			float x = Display.getWidth()/2-868*Mideas.getDisplayXFactor();
			float y = Display.getHeight()/2+99*Mideas.getDisplayYFactor();
			while(i < selectedRace.getClasse().length) {
				Draw.drawQuad(selectedRace.getClasse()[i].getTexture(),x , y, selectedRace.getClasse()[i].getTexture().getImageWidth()*Mideas.getDisplayXFactor(), selectedRace.getClasse()[i].getTexture().getImageHeight()*Mideas.getDisplayYFactor());
				i++;
				x+= 61;
				if(i == 3) {
					x = Display.getWidth()/2-868*Mideas.getDisplayXFactor();
					y+= 56;
				}
			}
			if(System.currentTimeMillis()%1000 < 500) {
				FontManager.loginScreenTick.drawString(Display.getWidth()/2-84*Mideas.getDisplayXFactor()+character.getCursorShift(), Display.getHeight()/2+393*Mideas.getDisplayYFactor(), "|", Color.WHITE);
			}
			if(hoveredRace != null && hoveredRace != selectedRace) {
				Draw.drawQuad(Sprites.select_screen_hover, Display.getWidth()/2+x_hover_race*Mideas.getDisplayXFactor()+3, Display.getHeight()/2+y_hover_race*Mideas.getDisplayYFactor()+2, Sprites.select_screen_hover.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.select_screen_hover.getImageHeight()*Mideas.getDisplayXFactor());
				Draw.drawColorQuad(Display.getWidth()/2+(x_hover_race+63)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_race-35)*Mideas.getDisplayYFactor(), FontManager.get("FRIZQT", 15).getWidth(hoveredRace.getName())+50*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayYFactor(), bgColors);
				Draw.drawColorQuadBorder(Display.getWidth()/2+(x_hover_race+63)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_race-35)*Mideas.getDisplayYFactor(), FontManager.get("FRIZQT", 15).getWidth(hoveredRace.getName())+50*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayYFactor(), Color.GREY);
				FontManager.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2+(x_hover_race+73)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_race-29)*Mideas.getDisplayYFactor(), hoveredRace.getName(), Color.WHITE, Color.BLACK, 1, 1, 1);
			}
			if(selectedRace != null) {
				Draw.drawQuad(Sprites.select_screen_hover, Display.getWidth()/2+(x_selected_race+3)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_selected_race+2)*Mideas.getDisplayYFactor(), Sprites.select_screen_hover.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.select_screen_hover.getImageHeight()*Mideas.getDisplayXFactor());
				FontManager.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2+(x_selected_race+30)*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 15).getWidth(selectedRace.getName())/2, Display.getHeight()/2+(y_selected_race+40)*Mideas.getDisplayYFactor(), selectedRace.getName(), Color.YELLOW, Color.BLACK, 1, 1, 1);
			}
			if(hoveredClasse != null && hoveredClasse != selectedClasse) {
				Draw.drawQuad(Sprites.select_screen_hover, Display.getWidth()/2+(x_hover_classe-1)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_classe-1)*Mideas.getDisplayYFactor(), Sprites.select_screen_hover.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.select_screen_hover.getImageHeight()*Mideas.getDisplayXFactor());
				Draw.drawColorQuad(Display.getWidth()/2+(x_hover_classe+63)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_classe-35)*Mideas.getDisplayYFactor(), FontManager.get("FRIZQT", 15).getWidth(hoveredClasse.getName())+50*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayYFactor(), bgColors);
				Draw.drawColorQuadBorder(Display.getWidth()/2+(x_hover_classe+63)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_classe-35)*Mideas.getDisplayYFactor(), FontManager.get("FRIZQT", 15).getWidth(hoveredClasse.getName())+50*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayYFactor(), Color.GREY);
				FontManager.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2+(x_hover_classe+73)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_hover_classe-29)*Mideas.getDisplayYFactor(), hoveredClasse.getName(), Color.WHITE, Color.BLACK, 1, 1, 1);
			}
			if(selectedClasse != null) {
				Draw.drawQuad(Sprites.select_screen_hover, Display.getWidth()/2+(x_selected_classe-1)*Mideas.getDisplayXFactor(), Display.getHeight()/2+(y_selected_classe-1)*Mideas.getDisplayYFactor(), Sprites.select_screen_hover.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.select_screen_hover.getImageHeight()*Mideas.getDisplayXFactor());
				FontManager.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2+(x_selected_classe+30)*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 15).getWidth(selectedClasse.getName())/2, Display.getHeight()/2+(y_selected_classe+40)*Mideas.getDisplayYFactor(), selectedClasse.getName(), Color.YELLOW, Color.BLACK, 2, 1, 1);
			}
		}
		if(realmScreenActive) {
			RealmListFrame.draw();
		}
	}
 
	public static boolean mouseEvent() {
		if(realmScreenActive) {
			if(RealmListFrame.mouseEvent()) return true;
		}
		else {
			if(!creatingCharacter) {
				if(deletingCharacter) {
					if(confirmDeleteCharacterButton.event()) return true;
					if(cancelDeleteCharacterButton.event()) return true;
				}
				else {
					if(newCharacterButton.event()) return true;
					if(returnButton.event()) return true;
					if(enterGameButton.event()) return true;
					if(deleteCharacterButton.event()) return true;
					if(changeRealmButton.event()) return true;
					selectCharacter();
					if(!Mouse.getEventButtonState()) {
						if(Mouse.getEventButton() == 0 && hoveredCharacter != -1) {
							selectedCharacter[selectedCharacterIndex] = false;
							selectedCharacterIndex = hoveredCharacter;
							selectedCharacter[selectedCharacterIndex] = true;
						}
					}
				}
				if(currentAlert.event()) return true;
			}
			else {
				currentAlert.event();
				acceptCharacterButton.event();
				returnCharacterButton.event();
				hoveredRace = null;
				hoveredClasse = null;
				selectRace();
				selectClasse();
				if(!Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0) {
						if(hoveredRace != null) {
							selectedRace = hoveredRace;
							x_selected_race = x_hover_race;
							y_selected_race = y_hover_race;
							selectedClasse = selectedRace.getClasse()[0];
							x_selected_classe = -868;
							y_selected_classe = 99;
						}
						else if(hoveredClasse != null) {
							selectedClasse = hoveredClasse;
							x_selected_classe = x_hover_classe;
							y_selected_classe = y_hover_classe;
						}
					}
				}
			}
		}
		return true;
	}
	
	public static boolean event() { //TODO: return correct value if event is triggered
		if(realmScreenActive) {
			if(RealmListFrame.event()) return true;
		}
		else {
			if(creatingCharacter) {
				character.event();
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
					createCharacter();
				}
			}
			else {
				if(deletingCharacter) {
					if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
						deleteCharacter();
					}
					else {
						deleteCharacter.event();
					}
				}
				else {
					if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						if(selectedCharacterIndex < totalCharacter-1) {
							selectedCharacter[selectedCharacterIndex] = false;
							selectedCharacterIndex++;
							selectedCharacter[selectedCharacterIndex] = true;
						}
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
						if(selectedCharacterIndex > 0) {
							selectedCharacter[selectedCharacterIndex] = false;
							selectedCharacterIndex--;
							selectedCharacter[selectedCharacterIndex] = true;
						}
					}
					else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == 156) {
						if (currentAlert.isActive())
							currentAlert.setInactive();
						else
							loadCharacterInfo();
					}
				}
			}
		}
		return false;
	}
	
	static void loadCharacterInfo() {
		if(characterList[selectedCharacterIndex] != null) {
			//setPlayer();
			//Mideas.joueur1().setId(characterList[selectedCharacterIndex].getId());
			//Mideas.joueur1().setName(characterList[selectedCharacterIndex].getName());
			CommandLoadCharacter.write(characterList[selectedCharacterIndex].getId());
		}
	}
	
	static void deleteCharacter() {
		if(deleteCharacter.getText().trim().toLowerCase().equals("effacer")) {
			CommandDeleteCharacter.write(characterList[selectedCharacterIndex].getId());
		}
	}
	
	public static void characterDeleted() {
		deletingCharacter = false;
		Arrays.fill(characterList, null);
		selectedCharacterIndex = 0;
		selectedCharacter[0] = true;
		deleteCharacter.resetText();
	}
	
	private static void selectRace() {
		int i = 0;
		float x = -875;
		float y = -338;
		while(i < 10) {
			if(Mideas.mouseX() >= Display.getWidth()/2+x*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+(x+63)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+y*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+(y+60)*Mideas.getDisplayYFactor()) {
				hoveredRace = race[i];
				x_hover_race = x;
				y_hover_race = y;
				break;
			}
			i++;
			y+= 66;
			if(i == 5) {
				y = -338;
				x = -742;
			}
		}
	}
	
	private static void selectClasse() {
		int i = 0;
		float x = -868;
		float y = 99;
		while(i < selectedRace.getClasse().length) {
			if(Mideas.mouseX() >= Display.getWidth()/2+x*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+(x+58)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2+y*Mideas.getDisplayYFactor() && Mideas.mouseY() <= Display.getHeight()/2+(y+57)*Mideas.getDisplayYFactor()) {
				hoveredClasse = selectedRace.getClasse()[i];
				x_hover_classe = x;
				y_hover_classe = y;
				break;
			}
			i++;
			x+= 61;
			if(i == 3) {
				x = -868;
				y+= 60;
			}
		}	
	}
	
	private static void selectCharacter() {
		int i = 0;
		float y = 110*Mideas.getDisplayYFactor();
		hoveredCharacter = -1;
		while(i < totalCharacter) {
			if(Mideas.mouseX() >= Display.getWidth()/2+605*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+605*Mideas.getDisplayXFactor()+Sprites.selected_character.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y && Mideas.mouseY() <= y+Sprites.selected_character.getImageHeight()*Mideas.getDisplayXFactor()) {
				hoveredCharacter = i;
			}
			y+= 72*Mideas.getDisplayYFactor();
			i++;
		}
	}
	
	static void createCharacter() {
		CommandCreateCharacter.write(character.getText());
	}
	
	public static String getSelectedRace() {
		return selectedRace.toString();
	}
	
	public static String getSelectedClasse() {
		return selectedClasse.toString();
	}
	
	public static void setCreatingCharacter(boolean we) {
		creatingCharacter = we;
	}
	
	public static void setSelectedCharacter(int i, boolean we) {
		selectedCharacter[i] = we;
	}
	
	public static int getSelectedCharacterIndex() {
		return selectedCharacterIndex;
	}
	
	public static void setSelectedCharacterIndex(int we) {
		selectedCharacterIndex = we;
	}
	
	public static int getTotalCharacter() {
		return totalCharacter;
	}
	
	public static void setCharacterList(SelectScreenPlayer player, int i) {
		if(i < characterList.length) {
			characterList[i] = player;
		}
	}
	
	private static void drawCharacter(int i, float y) {
		if(characterList[i] != null) {
			if(selectedCharacter[i] || hoveredCharacter == i) {
				Draw.drawQuadBlend(Sprites.selected_character, Display.getWidth()/2+592*Mideas.getDisplayXFactor(), y-15*Mideas.getDisplayYFactor(), 362*Mideas.getDisplayXFactor(), 98*Mideas.getDisplayYFactor());
			}
			FontManager.get("FRIZQT", 22).drawStringShadow(Display.getWidth()/2+625*Mideas.getDisplayXFactor(), y, characterList[i].getName(), Color.YELLOW, Color.BLACK, 2, 1, 1);
			FontManager.get("FRIZQT", 16).drawStringShadow(Display.getWidth()/2+625*Mideas.getDisplayXFactor(), y+27, convClasseToString(characterList[i].getClasse())+" level "+characterList[i].getLevel(), Color.WHITE, Color.BLACK, 2, 0, 0);
		}
	}
	
	private static String convClasseToString(String race) {
		if(race.equals("DEATHKNIGHT")) {
			return "DeathKnight";
		}
		if(race.equals("WARRIOR")) {
			return "Warrior";
		}
		if(race.equals("MAGE")) {
			return "Mage";
		}
		if(race.equals("HUNTER")) {
			return "Hunter";
		}
		if(race.equals("PALADIN")) {
			return "Paladin";
		}
		if(race.equals("WARLOCK")) {
			return "Warlock";
		}
		if(race.equals("MONK")) {
			return "Monk";
		}
		if(race.equals("PRIEST")) {
			return "Priest";
		}
		if(race.equals("ROGUE")) {
			return "Rogue";
		}
		if(race.equals("SHAMAN")) {
			return "Shaman";
		}
		return null;
	}
	
	public static void setTotalCharacter(int i) {
		totalCharacter = i;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		newCharacterButton.update(Display.getWidth()/2+630*Mideas.getDisplayXFactor(), Display.getHeight()/2+293*Mideas.getDisplayYFactor(), 278*Mideas.getDisplayXFactor(), 36*Mideas.getDisplayYFactor());
		acceptCharacterButton.update(Display.getWidth()/2+705*Mideas.getDisplayXFactor(), Display.getHeight()/2+393*Mideas.getDisplayYFactor(), 195*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
		returnCharacterButton.update(Display.getWidth()/2+730*Mideas.getDisplayXFactor(), Display.getHeight()/2+442*Mideas.getDisplayYFactor(), 150*Mideas.getDisplayXFactor(), 34*Mideas.getDisplayYFactor());
		returnButton.update(Display.getWidth()/2+785*Mideas.getDisplayXFactor(), Display.getHeight()/2+438*Mideas.getDisplayYFactor(), 122*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		enterGameButton.update(Display.getWidth()/2-125*Mideas.getDisplayXFactor(), Display.getHeight()/2+403*Mideas.getDisplayYFactor(), 250*Mideas.getDisplayXFactor(), 50*Mideas.getDisplayYFactor());
		deleteCharacterButton.update(Display.getWidth()/2+558*Mideas.getDisplayXFactor(), Display.getHeight()/2+438*Mideas.getDisplayYFactor(), 202*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		alert.update(Display.getWidth()/2-720*Mideas.getDisplayXFactor()/2, Display.getHeight()/2-60*Mideas.getDisplayYFactor(), 720*Mideas.getDisplayXFactor());
		confirmDeleteCharacterButton.update(Display.getWidth()/2-275*Mideas.getDisplayXFactor(), Display.getHeight()/2+58*Mideas.getDisplayYFactor(), 240, 32*Mideas.getDisplayYFactor());
		cancelDeleteCharacterButton.update(Display.getWidth()/2+23*Mideas.getDisplayXFactor(), Display.getHeight()/2+58*Mideas.getDisplayYFactor(), 240, 32*Mideas.getDisplayYFactor());
		changeRealmButton.update(Display.getWidth()/2+682*Mideas.getDisplayXFactor(), 57*Mideas.getDisplayYFactor(), 175*Mideas.getDisplayXFactor(), 28*Mideas.getDisplayYFactor());
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	public static void resetScreen() {
		Arrays.fill(characterList, null);
		creatingCharacter = false;
		deletingCharacter = false;
		selectedCharacterIndex = 0;
		hoveredCharacter = -1;
		characterLoaded = false;
		character.setIsActive(false);
		currentAlert.setInactive();
	}
	
	public static Alert getAlert() {
		return alert;
	}
	
	public static void setAlertLoadingCharacter()
	{
		if (currentAlert == loginQueueAlert)
			loginQueueAlert.setInactive();
		currentAlert = alert;
		currentAlert.setText("Loading characters...");
		currentAlert.setActive();
	}
	
	public static void setAlert(String text) {
		alert.setActive();
		alert.setText(text);
	}
	
	public static Input getCharacterInput() {
		return character;
	}
	
	public static void setRealmScreenActive(boolean we) {
		realmScreenActive = we;
	}
	
	public static void resetCharacterList() {
		Arrays.fill(characterList, null);
	}
	
	public static void joinedLoginQueue(int size)
	{
		loginQueuePosition = size;
		loginQueueSize = size;
		loginQueueJoinTimer = Mideas.getLoopTickTimer() - 1000;
		loginQueuePositionString = Integer.toString(loginQueuePosition);
		loginQueueSizeString = Integer.toString(loginQueueSize);
		updateLoginQueueTimer();
		updateLoginQueueAlertText();
		loginQueueAlert.setActive();
		if (currentAlert == alert)
			alert.setInactive();
		currentAlert = loginQueueAlert;
	}
	
	public static void updateLoginQueueTimer()
	{
		if (loginQueueAlert.isActive() && loginQueueLastTimerUpdate + 1000 <= Mideas.getLoopTickTimer())
		{
			loginQueueTimeInQueueString = StringUtils.convertTimeToStringSimple(Mideas.getLoopTickTimer() - loginQueueJoinTimer);
			loginQueueLastTimerUpdate = Mideas.getLoopTickTimer();
			updateLoginQueueAlertText();
		}
	}
	
	public static void updateLoginQueueAlertText()
	{
		loginQueueAlert.setText("Server is Full\nPosition in queue: " + loginQueuePositionString + "/" + loginQueueSizeString + "\nTime in queue: " + loginQueueTimeInQueueString);
	}
	
	public static void updateLoginQueuePosition(int position, int size)
	{
		loginQueuePosition = position;
		loginQueueSize = size;
		loginQueuePositionString = Integer.toString(loginQueuePosition);
		loginQueueSizeString = Integer.toString(loginQueueSize);
		updateLoginQueueAlertText();		
	}
}
