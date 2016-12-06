package com.mideas.rpg.v2.hud.social.who;

import static com.mideas.rpg.v2.hud.social.SocialFrame.X_SOCIAL_FRAME;
import static com.mideas.rpg.v2.hud.social.SocialFrame.Y_SOCIAL_FRAME;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.command.CommandFriend;
import com.mideas.rpg.v2.command.CommandParty;
import com.mideas.rpg.v2.command.CommandWho;
import com.mideas.rpg.v2.game.social.WhoUnit;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.DropDownMenu;
import com.mideas.rpg.v2.utils.ScrollBar;

public class WhoFrame {

	private final static int MAXIMUM_UNIT_DISPLAYED = 17;
	private static int hoveredUnit = -1;
	static int selectedUnit = -1;
	private static WhoSort sorted = WhoSort.NAME;
	private static int dropDownMenuValue;
	final static ArrayList<WhoUnit> whoList = new ArrayList<WhoUnit>();
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
	private final static Button updateButton = new Button(X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Actualise", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			CommandWho.write();
			selectedUnit = -1;
		}
	};
	private final static Button addFriendButton = new Button(X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Actualise", 12, 1) {
	
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
	private final static Button inviteButton = new Button(X_SOCIAL_FRAME+25*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+350*Mideas.getDisplayYFactor(), 100*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Actualise", 12, 1) {
	
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
	private final static ScrollBar scrollBar = new ScrollBar(X_SOCIAL_FRAME+350*Mideas.getDisplayXFactor(), Y_SOCIAL_FRAME+50*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayYFactor(), 400*Mideas.getDisplayXFactor(), 400*Mideas.getDisplayYFactor(), false, 20*Mideas.getDisplayYFactor());
	
	public void draw() {
		Draw.drawQuad(Sprites.who_frame, X_SOCIAL_FRAME, Y_SOCIAL_FRAME);
		updateButton.draw();
		addFriendButton.draw();
		inviteButton.draw();
		dropDownMenu.draw();
		if(!(whoList.size() > 0)) {
			return;
		}
		int i = 0;
		int iOffset = 0;
		if(whoList.size() > MAXIMUM_UNIT_DISPLAYED) {
			scrollBar.draw();
			i = iOffset = (int)((whoList.size()-MAXIMUM_UNIT_DISPLAYED)*scrollBar.getScrollPercentage());
		}
		TTF font = FontManager.get("FRIZQT", 13);
		font.drawBegin();
		float y = Y_SOCIAL_FRAME+80*Mideas.getDisplayYFactor();
		float yShift = 20*Mideas.getDisplayYFactor();
		while(i < whoList.size()) {
			WhoUnit unit = whoList.get(i);
			font.drawStringShadowPart(X_SOCIAL_FRAME+30*Mideas.getDisplayXFactor(), y+i*yShift, unit.getName(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			if(dropDownMenuValue == 0) {
				font.drawStringShadowPart(X_SOCIAL_FRAME+60*Mideas.getDisplayXFactor(), y+i*yShift, "Area", Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			else if(dropDownMenuValue == 1) {
				font.drawStringShadowPart(X_SOCIAL_FRAME+60*Mideas.getDisplayXFactor(), y+i*yShift, unit.getGuildName(), Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			else if(dropDownMenuValue == 2) {
				font.drawStringShadowPart(X_SOCIAL_FRAME+60*Mideas.getDisplayXFactor(), y+i*yShift, unit.getRace(), Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			font.drawStringShadowPart(X_SOCIAL_FRAME+90*Mideas.getDisplayXFactor(), y+i*yShift, unit.getLevel(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			font.drawStringShadowPart(X_SOCIAL_FRAME+120*Mideas.getDisplayXFactor(), y+i*yShift, unit.getClasse(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			i++;
		}
		font.drawEnd();
		i = iOffset;
		if(hoveredUnit != -1) {
			
		}
		if(selectedUnit != -1 && selectedUnit != hoveredUnit) {
			
		}	
	}
	
	public static void sortByArea() {
		
	}
	
	public  static void sortByGuild() {
		
	}
	
	public static void sortByRace() {
		
	}
	
	public static void clearList() {
		whoList.clear();
	}
	
	public static void addToList(WhoUnit unit) {
		whoList.add(unit);
	}
} 
