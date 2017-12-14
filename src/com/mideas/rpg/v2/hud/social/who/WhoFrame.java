package com.mideas.rpg.v2.hud.social.who;

import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandIgnore;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandWho;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.social.WhoUnit;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.ButtonMenuSort;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.DropDownMenu;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.ScrollBar;
import com.mideas.rpg.v2.utils.TextMenu;
import com.mideas.rpg.v2.utils.TooltipMenu;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class WhoFrame {

	private static boolean shouldUpdateSize;
	private final static int MAXIMUM_UNIT_DISPLAYED = 17;
	private static int hoveredUnit = -1;
	static int selectedUnit = -1;
	private static int unitDown = -1;
	private static String numberPeopleFound = "0 player found";
	private static int peopleFoundWidth = FontManager.get("FRIZQT", 12).getWidth(numberPeopleFound);
	private static boolean updateString = true;
	static WhoSort sorted = WhoSort.NAME_ASCENDING;
	static WhoDropDownDisplay dropDownDisplay = WhoDropDownDisplay.AREA;
	final static ArrayList<WhoUnit> whoList = new ArrayList<WhoUnit>();
	final static TooltipMenu tooltipMenu = new TooltipMenu(0, 0, 0) {
		
		@Override
		public void onShow() {
			dropDownMenu.setActive(false);
		}
	};
	final static Input input = new Input(FontManager.get("FRIZQT", 13), 255, false, false) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE) {
				CommandWho.write(input.getText());
				return true;
			}
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.setIsActive(false);
				return true;
			}
			return false;
		}
	};
	private final static TextMenu whisperMenu = new TextMenu(0, 0, 0, "Whisper", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			ChatFrame.setWhisper(whoList.get(selectedUnit).getName());
			ChatFrame.setChatActive(true);
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu inviteMenu = new TextMenu(0, 0, 0, "Invite", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			CommandParty.invitePlayer(whoList.get(selectedUnit).getName());
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu targetMenu = new TextMenu(0, 0, 0, "Target", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu ignoreMenu = new TextMenu(0, 0, 0, "Ignore", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			CommandIgnore.addIgnore(whoList.get(selectedUnit).getName());
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu cancelMenu = new TextMenu(0, 0, 0, "Cancel", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			tooltipMenu.setActive(false);
		}
	};
	final static DropDownMenu dropDownMenu = new DropDownMenu(X_SOCIAL_FRAME+114*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 115*Mideas.getDisplayXFactor(), X_SOCIAL_FRAME+105*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+98*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 13, .6f, 15, true) {
		
		@Override
		public void menuEventButtonClick() {
			if(this.selectedMenuValue == 0) {
				dropDownDisplay = WhoDropDownDisplay.AREA;
			}
			else if(this.selectedMenuValue == 1) {
				dropDownDisplay = WhoDropDownDisplay.GUILD;
			}
			else if(this.selectedMenuValue == 2) {
				dropDownDisplay = WhoDropDownDisplay.RACE;
			}
		}
		
		@Override
		public void barEventButtonClick() {
			if(dropDownDisplay == WhoDropDownDisplay.AREA) {
				if(sorted == WhoSort.AREA_ASCENDING) {
					
					sorted = WhoSort.AREA_DESCENDING;
				}
				else {
					sorted = WhoSort.AREA_ASCENDING;
				}
			}
			else if(dropDownDisplay == WhoDropDownDisplay.GUILD) {
				if(sorted == WhoSort.GUILD_ASCENDING) {
					sortByGuildDescending();
					sorted = WhoSort.GUILD_DESCENDING;
				}
				else {
					sortByGuildAscending();
					sorted = WhoSort.GUILD_ASCENDING;
				}
			}
			else if(dropDownDisplay == WhoDropDownDisplay.RACE) {
				if(sorted == WhoSort.RACE_ASCENDING) {
					sortByRaceDescending();
					sorted = WhoSort.RACE_DESCENDING;
				}
				else {
					sortByRaceAscending();
					sorted = WhoSort.RACE_ASCENDING;
				}
			}
		}
	};
	private final static TextMenu sortByGuild = new TextMenu(0, 0, 0, "Guild", 12, 1);
	private final static TextMenu sortByArea = new TextMenu(0, 0, 0, "Area", 12, 1);
	private final static TextMenu sortByRace = new TextMenu(0, 0, 0, "Race", 12, 1);
	private final static Button updateButton = new Button(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor(), "Actualise", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandWho.write(input.getText());
		}
	};
	private final static Button addFriendButton = new Button(X_SOCIAL_FRAME+119*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 132*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor(), "Add Friend", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			if(selectedUnit != -1) {
				CommandFriend.addFriend(whoList.get(selectedUnit).getName());
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedUnit != -1;
		}
	};
	private final static Button inviteButton = new Button(X_SOCIAL_FRAME+253*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 132*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor(), "Inv. Party", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			if(selectedUnit != -1) {
				CommandParty.invitePlayer(whoList.get(selectedUnit).getName());
			}
		}
		
		@Override
		public boolean activateCondition() {
			return selectedUnit != -1 && Mideas.joueur1().canInvitePlayerInParty(whoList.get(selectedUnit).getId());
		}
	};
	private final static ButtonMenuSort sortByName = new ButtonMenuSort(X_SOCIAL_FRAME+19*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 95*Mideas.getDisplayXFactor(), "Name", 12) {
	
		@Override
		public void eventButtonClick() {
			if(sorted == WhoSort.NAME_ASCENDING) {
				sortByNameDescending();
				sorted = WhoSort.NAME_DESCENDING;
			}
			else {
				sortByNameAscending();
				sorted = WhoSort.NAME_ASCENDING;
			}
		}
	};
	private final static ButtonMenuSort dropDownBackground = new ButtonMenuSort(X_SOCIAL_FRAME+112*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 125*Mideas.getDisplayXFactor(), "", 12);
	private final static ButtonMenuSort sortByLevel = new ButtonMenuSort(X_SOCIAL_FRAME+234*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 40*Mideas.getDisplayXFactor(), "Lvl.", 12) {
	
		@Override
		public void eventButtonClick() {
			if(sorted == WhoSort.LEVEL_ASCENDING) {
				sortByLevelDescending();
				sorted = WhoSort.LEVEL_DESCENDING;
			}
			else {
				sortByLevelAscending();
				sorted = WhoSort.LEVEL_ASCENDING;
			}
		}
	};
	private final static ButtonMenuSort sortByClasse = new ButtonMenuSort(X_SOCIAL_FRAME+272*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 80*Mideas.getDisplayXFactor(), "Classe", 12) {
		
		@Override
		public void eventButtonClick() {
			if(sorted == WhoSort.CLASSE_ASCENDING) {
				sortByClasseDescending();
				sorted = WhoSort.CLASSE_DESCENDING;
			}
			else {
				sortByClasseAscending();
				sorted = WhoSort.CLASSE_ASCENDING;
			}
		}
	};
	private final static ScrollBar scrollBar = new ScrollBar(X_SOCIAL_FRAME+357*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+100*Mideas.getDisplayYFactor(), 290*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 290*Mideas.getDisplayYFactor(), false, 20*Mideas.getDisplayYFactor());
	private static boolean initDropDownMenu;
	
	public static void draw() {
		updateSize();
		if(!initDropDownMenu) {
			dropDownMenu.addMenu(sortByArea);
			dropDownMenu.addMenu(sortByGuild);
			dropDownMenu.addMenu(sortByRace);
			initDropDownMenu = true;
		}
		if(updateString) {
			if(whoList.size() > 1)
				numberPeopleFound = whoList.size()+" players found";
			else
				numberPeopleFound = whoList.size()+" player found";
			peopleFoundWidth = FontManager.get("FRIZQT", 12).getWidth(numberPeopleFound);
			updateString = false;
		}
		Draw.drawQuad(Sprites.who_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		updateButton.draw();
		addFriendButton.draw();
		inviteButton.draw();
		sortByName.draw();
		sortByLevel.draw();
		sortByClasse.draw();
		FontManager.get("FRIZQT", 12).drawStringShadow(X_SOCIAL_FRAME+200*Mideas.getDisplayXFactor()-peopleFoundWidth/2, Y_SOCIAL_FRAME+392*Mideas.getDisplayYFactor(), numberPeopleFound, Color.YELLOW, Color.BLACK, 1, 0, 0);
		if(whoList.size() == 0) {
			dropDownBackground.draw();
			dropDownMenu.draw();
			return;
		}
		int i = 0;
		int iOffset = 0;
		if(whoList.size() > MAXIMUM_UNIT_DISPLAYED) {
			scrollBar.draw();
			i = iOffset = (int)((whoList.size()-MAXIMUM_UNIT_DISPLAYED)*scrollBar.getScrollPercentage());
		}
		TTF font = FontManager.get("FRIZQT", 12);
		font.drawBegin();
		float y = Y_SOCIAL_FRAME+103*Mideas.getDisplayYFactor();
		float yShift = 17*Mideas.getDisplayYFactor();
		float yShiftHeight = 0;
		while(i < whoList.size()) {
			WhoUnit unit = whoList.get(i);
			font.drawStringShadowPart(X_SOCIAL_FRAME+30*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getName(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			if(dropDownDisplay == WhoDropDownDisplay.AREA)
				font.drawStringShadowPart(X_SOCIAL_FRAME+120*Mideas.getDisplayXFactor(), y+yShiftHeight, "Area", Color.WHITE, Color.BLACK, 1, 0, 0);
			else if(dropDownDisplay == WhoDropDownDisplay.GUILD)
				font.drawStringShadowPart(X_SOCIAL_FRAME+120*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getGuildName(), Color.WHITE, Color.BLACK, 1, 0, 0);
			else if(dropDownDisplay == WhoDropDownDisplay.RACE)
				font.drawStringShadowPart(X_SOCIAL_FRAME+120*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getRace(), Color.WHITE, Color.BLACK, 1, 0, 0);
			font.drawStringShadowPart(X_SOCIAL_FRAME+240*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getLevelString(), Color.WHITE, Color.BLACK, 1, 0, 0);
			font.drawStringShadowPart(X_SOCIAL_FRAME+280*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getClasse(), unit.getColor(), Color.BLACK, 1, 0, 0);
			yShiftHeight+= yShift;
			if(yShiftHeight >= 280*Mideas.getDisplayYFactor())
				break;
			i++;
		}
		font.drawEnd();
		i = iOffset;
		float width;
		if(whoList.size() > MAXIMUM_UNIT_DISPLAYED)
			width = 325*Mideas.getDisplayXFactor();
		else
			width = 345*Mideas.getDisplayXFactor();
		if(hoveredUnit != -1 && hoveredUnit >= iOffset && hoveredUnit < iOffset+MAXIMUM_UNIT_DISPLAYED)
			Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), y+(hoveredUnit-iOffset)*yShift, width, 17*Mideas.getDisplayYFactor());
		if(selectedUnit != -1 && selectedUnit != hoveredUnit && selectedUnit >= iOffset && selectedUnit < iOffset+MAXIMUM_UNIT_DISPLAYED)
			Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), y+(selectedUnit-iOffset)*yShift, width, 17*Mideas.getDisplayYFactor());
		tooltipMenu.draw();
		dropDownBackground.draw();
		dropDownMenu.draw();	
	}
	
	public static boolean mouseEvent() {
		if(updateButton.event()) return true;
		if(addFriendButton.event()) return true;
		if(inviteButton.event()) return true;
		if(sortByName.event()) return true;
		if(sortByLevel.event()) return true;
		if(dropDownMenu.event()) return true;
		if(sortByClasse.event()) return true;
		if(tooltipMenu.event()) return true;
		int i = 0;
		hoveredUnit = -1;
		if(whoList.size() > MAXIMUM_UNIT_DISPLAYED) {
			if(scrollBar.event()) 
				return true;
			i  = (int)((whoList.size()-MAXIMUM_UNIT_DISPLAYED)*scrollBar.getScrollPercentage());
		}
		float y = Y_SOCIAL_FRAME+103*Mideas.getDisplayYFactor();
		float yShift = 17*Mideas.getDisplayYFactor();
		float yShiftHeight = 0;
		while(i < whoList.size()) {
			if(Mideas.getHover() && Mideas.mouseX() >= X_SOCIAL_FRAME+30*Mideas.getDisplayXFactor() && Mideas.mouseX() <= X_SOCIAL_FRAME+350*Mideas.getDisplayXFactor() && Mideas.mouseY() >= y+yShiftHeight && Mideas.mouseY() <= y+yShiftHeight+yShift) {
				hoveredUnit = i;
				Mideas.setHover(false);
				break;
			}
			i++;
			yShiftHeight+= yShift;
		}
		if(hoveredUnit != -1) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)
					unitDown = hoveredUnit;
			}
			else if(hoveredUnit == unitDown) {
				if(Mouse.getEventButton() == 0) {
					selectedUnit = hoveredUnit;
					unitDown = -1;
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					selectedUnit = hoveredUnit;
					buildTooltipMenu();
					unitDown = -1;
					return true;
				}
			}
		}
		return false;
	}
	
	private static void buildTooltipMenu() {
		tooltipMenu.clearMenu();
		tooltipMenu.setName(whoList.get(selectedUnit).getName());
		tooltipMenu.addMenu(whisperMenu);
		tooltipMenu.addMenu(inviteMenu);
		tooltipMenu.addMenu(targetMenu);
		tooltipMenu.addMenu(ignoreMenu);
		tooltipMenu.addMenu(cancelMenu);
		tooltipMenu.setActive(true);
		tooltipMenu.updateSize(Mideas.mouseX(), Mideas.mouseY(), true);
	}
	
	public static void sortByNameAscending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getName().compareTo(whoList.get(j).getName()) > 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByNameDescending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getName().compareTo(whoList.get(j).getName()) < 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByArea() {
		
	}
	
	public static void sortByGuildAscending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getGuildName().compareTo(whoList.get(j).getGuildName()) > 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByGuildDescending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getGuildName().compareTo(whoList.get(j).getGuildName()) < 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByLevelAscending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getLevel() >= whoList.get(j).getLevel()) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByLevelDescending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getLevel() <= whoList.get(j).getLevel()) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByRaceAscending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getRace().compareTo(whoList.get(j).getRace()) >= 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByRaceDescending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getRace().compareTo(whoList.get(j).getRace()) <= 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByClasseAscending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getClasse().compareTo(whoList.get(j).getClasse()) >= 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void sortByClasseDescending() {
		int i = -1;
		int j = -1;
		WhoUnit tmp;
		while(++i < whoList.size()) {
			j = i;
			while(++j < whoList.size()) {
				if(whoList.get(i).getClasse().compareTo(whoList.get(j).getClasse()) <= 0) {
					tmp = whoList.get(j);
					whoList.set(j, whoList.get(i));
					whoList.set(i, tmp);
				}
			}
		}
	}
	
	public static void clearList() {
		selectedUnit = -1;
		whoList.clear();
		updateString = true;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize)
			return;
		dropDownMenu.update(X_SOCIAL_FRAME+114*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 115*Mideas.getDisplayXFactor(), X_SOCIAL_FRAME+105*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+98*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor());
		dropDownBackground.update(X_SOCIAL_FRAME+112*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 125*Mideas.getDisplayXFactor());
		sortByClasse.update(X_SOCIAL_FRAME+272*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 80*Mideas.getDisplayXFactor());
		sortByLevel.update(X_SOCIAL_FRAME+234*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 40*Mideas.getDisplayXFactor());
		sortByName.update(X_SOCIAL_FRAME+19*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+74*Mideas.getDisplayYFactor(), 95*Mideas.getDisplayXFactor());
		scrollBar.update(X_SOCIAL_FRAME+357*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+100*Mideas.getDisplayYFactor(), 290*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 290*Mideas.getDisplayYFactor(), 20*Mideas.getDisplayYFactor());
		updateButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor());
		addFriendButton.update(X_SOCIAL_FRAME+119*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 132*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor());
		inviteButton.update(X_SOCIAL_FRAME+253*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 132*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor());
		tooltipMenu.updateSize();
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	public static void addToList(WhoUnit unit) {
		whoList.add(unit);
		int i = 0;
		while(i < 70) {
			whoList.add(new WhoUnit(3, "Test", "GuildTest", (int)(70*Math.random()), ClassType.DRUID, Race.DRAENEI));
			i++;
		}
		whoList.add(new WhoUnit(3, "END", "EENNDD", 70, ClassType.DRUID, Race.DRAENEI));
	}
} 
