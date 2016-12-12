package com.mideas.rpg.v2.hud.social.who;

import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandWho;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.race.Race;
import com.mideas.rpg.v2.game.social.WhoUnit;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.DropDownMenu;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.ScrollBar;

public class WhoFrame {

	private final static int MAXIMUM_UNIT_DISPLAYED = 17;
	private static int hoveredUnit = -1;
	static int selectedUnit = -1;
	private static int unitDown = -1;
	private static String numberPeopleFound = "0 player found";
	private static int peopleFoundWidth = FontManager.get("FRIZQT", 12).getWidth(numberPeopleFound);
	private static boolean updateString = true;
	private static WhoSort sorted = WhoSort.NAME;
	private static int dropDownMenuValue;
	final static ArrayList<WhoUnit> whoList = new ArrayList<WhoUnit>();
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
	private final static DropDownMenu dropDownMenu = new DropDownMenu(X_SOCIAL_FRAME+200*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME, +70*Mideas.getDisplayXFactor(), X_SOCIAL_FRAME+70*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+50*Mideas.getDisplayYFactor(), 80*Mideas.getDisplayXFactor(), 13, .6f) {
		
		@Override
		public void eventButtonClick() {
			if(this.selectedMenuValue == 0) {
				sortByArea();
			}
			else if(this.selectedMenuValue == 1) {
				sortByGuild();
			}
			else if(this.selectedMenuValue == 2) {
				sortByRace();
			}
		}
	};
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
	private final static ScrollBar scrollBar = new ScrollBar(X_SOCIAL_FRAME+357*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+100*Mideas.getDisplayYFactor(), 290*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 290*Mideas.getDisplayYFactor(), false, 20*Mideas.getDisplayYFactor());
	
	public static void draw() {
		if(updateString) {
			if(whoList.size() > 1) {
				numberPeopleFound = whoList.size()+" players found";
			}
			else {
				numberPeopleFound = whoList.size()+" player found";
				
			}
			peopleFoundWidth = FontManager.get("FRIZQT", 12).getWidth(numberPeopleFound);
			updateString = false;
		}
		Draw.drawQuad(Sprites.who_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		updateButton.draw();
		addFriendButton.draw();
		inviteButton.draw();
		dropDownMenu.draw();
		FontManager.get("FRIZQT", 12).drawStringShadow(X_SOCIAL_FRAME+200*Mideas.getDisplayXFactor()-peopleFoundWidth/2, Y_SOCIAL_FRAME+392*Mideas.getDisplayYFactor(), numberPeopleFound, Color.YELLOW, Color.BLACK, 1, 0, 0);
		if(!(whoList.size() > 0)) {
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
			if(dropDownMenuValue == 0) {
				font.drawStringShadowPart(X_SOCIAL_FRAME+140*Mideas.getDisplayXFactor(), y+yShiftHeight, "Area", Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			else if(dropDownMenuValue == 1) {
				font.drawStringShadowPart(X_SOCIAL_FRAME+140*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getGuildName(), Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			else if(dropDownMenuValue == 2) {
				font.drawStringShadowPart(X_SOCIAL_FRAME+140*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getRace(), Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			font.drawStringShadowPart(X_SOCIAL_FRAME+230*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getLevel(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			font.drawStringShadowPart(X_SOCIAL_FRAME+310*Mideas.getDisplayXFactor(), y+yShiftHeight, unit.getClasse(), unit.getColor(), Color.BLACK, 1, 0, 0);
			yShiftHeight+= yShift;
			if(yShiftHeight >= 280*Mideas.getDisplayYFactor()) {
				break;
			}
			i++;
		}
		font.drawEnd();
		i = iOffset;
		float width;
		if(whoList.size() > MAXIMUM_UNIT_DISPLAYED) {
			width = 325*Mideas.getDisplayXFactor();
		}
		else {
			width = 345*Mideas.getDisplayXFactor();
		}
		if(hoveredUnit != -1 && hoveredUnit >= iOffset && hoveredUnit < iOffset+MAXIMUM_UNIT_DISPLAYED) {
			Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), y+(hoveredUnit-iOffset)*yShift, width, 17*Mideas.getDisplayYFactor());
		}
		if(selectedUnit != -1 && selectedUnit != hoveredUnit && selectedUnit >= iOffset && selectedUnit < iOffset+MAXIMUM_UNIT_DISPLAYED) {
			Draw.drawQuadBlend(Sprites.friend_border, X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), y+(selectedUnit-iOffset)*yShift, width, 17*Mideas.getDisplayYFactor());
		}	
	}
	
	public static boolean mouseEvent() {
		if(updateButton.event()) return true;
		if(addFriendButton.event()) return true;
		if(inviteButton.event()) return true;
		int i = 0;
		hoveredUnit = -1;
		if(whoList.size() > MAXIMUM_UNIT_DISPLAYED) {
			if(scrollBar.event()) { 
				return true;
			}
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
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					unitDown = hoveredUnit;
				}
			}
			else if(hoveredUnit == unitDown) {
				if(Mouse.getEventButton() == 0) {
					selectedUnit = hoveredUnit;
					unitDown = -1;
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					//draw tooltip
					unitDown = -1;
					return true;
				}
			}
		}
		return false;
	}
	
	public static void sortByArea() {
		
	}
	
	public  static void sortByGuild() {
		
	}
	
	public static void sortByRace() {
		
	}
	
	public static void clearList() {
		selectedUnit = -1;
		whoList.clear();
		updateString = true;
	}
	
	public static void updateSize() {
		scrollBar.update(X_SOCIAL_FRAME+357*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+100*Mideas.getDisplayYFactor(), 290*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 290*Mideas.getDisplayYFactor(), 20*Mideas.getDisplayYFactor());
		updateButton.update(X_SOCIAL_FRAME+17*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor());
		addFriendButton.update(X_SOCIAL_FRAME+119*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 132*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor());
		inviteButton.update(X_SOCIAL_FRAME+253*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+437*Mideas.getDisplayYFactor(), 132*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayYFactor());
	}
	
	public static void addToList(WhoUnit unit) {
		whoList.add(unit);
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "Test", "GuildTest", 70, ClassType.DRUID, Race.DRAENEI));
		whoList.add(new WhoUnit(3, "END", "EENNDD", 70, ClassType.DRUID, Race.DRAENEI));
	}
} 
