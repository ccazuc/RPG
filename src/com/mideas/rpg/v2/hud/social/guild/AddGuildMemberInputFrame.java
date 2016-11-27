package com.mideas.rpg.v2.hud.social.guild;

public class AddGuildMemberInputFrame { //UNUSED
	
/*	static Input input = new Input(TTF2.addingFriendInput, 12, false, false) {

		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ENTER_CHAR_VALUE) {
				CommandGuild.addMember(input.getText());
				closeFrame();
				return true;
			}
			else if(c == Input.ESCAPE_CHAR_VALUE) {
				this.setIsActive(false);
				return true;
			}
			return false;
		}
	};
	private static InputBar inputBar = new InputBar(Display.getWidth()/2-88*Mideas.getDisplayXFactor(), Display.getHeight()/2-325*Mideas.getDisplayYFactor(), 170*Mideas.getDisplayXFactor());
	private static Button acceptButton = new Button(Display.getWidth()/2-143*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Accept", 13, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.addMember(input.getText());
			closeFrame();
			this.reset();
		}
	};
	private static Button cancelButton = new Button(Display.getWidth()/2+10*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor(), "Cancel", 13, 1) {
		
		@Override
		public void eventButtonClick() {
			closeFrame();
			this.reset();
		}
	};
	private static AlertBackground background = new AlertBackground(Display.getWidth()/2-180*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 360*Mideas.getDisplayXFactor(), 120*Mideas.getDisplayYFactor(), 0.6f);
	
	public static void draw() {
		background.draw();
		TTF2.addingFriendTitle.drawStringShadow(Display.getWidth()/2-TTF2.addingFriendTitle.getWidth("Add a member to the guild :")/2, Display.getHeight()/2-350*Mideas.getDisplayYFactor(), "Add a member to the guild :", Color.white, Color.black, 1, 0, 0);
		inputBar.draw();
		TTF2.addingFriendInput.drawStringShadow(Display.getWidth()/2-78*Mideas.getDisplayXFactor(), Display.getHeight()/2-318*Mideas.getDisplayYFactor(), input.getText(), Color.white, Color.black, 1, 0, 0);
		if(input.isActive() && System.currentTimeMillis()%1000 < 500) {
			Draw.drawColorQuad(Display.getWidth()/2-78*Mideas.getDisplayXFactor()+input.getCursorShift(), Display.getHeight()/2-317*Mideas.getDisplayYFactor(), 5*Mideas.getDisplayXFactor(), 15*Mideas.getDisplayYFactor(), Color.white);
		}
		acceptButton.draw();
		cancelButton.draw();
	}
	
	public static boolean mouseEvent() {
		if(inputBar.isHover()) {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					input.setIsActive(true);
					return true;
				}
			}
		}
		return cancelButton.event() || acceptButton.event();
	}
	
	public static boolean event() {
		if(!input.isActive() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
			Interface.setAddGuildMemberStatus(false);
			input.setIsActive(false);
			return true;
		}
		return input.event();
	}
	
	public static void updateSize() {
		background.update(Display.getWidth()/2-180*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 360*Mideas.getDisplayXFactor(), 120*Mideas.getDisplayYFactor());
		acceptButton.update(Display.getWidth()/2-143*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor());
		cancelButton.update(Display.getWidth()/2+10*Mideas.getDisplayXFactor(), Display.getHeight()/2-283*Mideas.getDisplayYFactor(), 135*Mideas.getDisplayXFactor(), 20*Mideas.getDisplayYFactor());
		inputBar.update(Display.getWidth()/2-88*Mideas.getDisplayXFactor(), Display.getHeight()/2-325*Mideas.getDisplayYFactor(), 170*Mideas.getDisplayXFactor());
	}
	
	public static Input getInput() {
		return input;
	}
	
	public static void closeFrame() {
		Interface.setAddGuildMemberStatus(false);
		input.setIsActive(false);
		input.resetText();
	}*/
}
