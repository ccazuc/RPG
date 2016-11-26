package com.mideas.rpg.v2.hud.social.guild;

public class GuildInviteNotification { //UNUSED
	
	/*static boolean requestPending;
	private static Button acceptRequest = new Button(Display.getWidth()/2-200*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Accept", 12, 1) {
		
		@Override
		public void eventButtonClick() {
			CommandGuild.acceptRequest();
			requestPending = false;
			this.reset();
		}
		
		@Override
		public void popupClosed() {
			CommandGuild.declineRequest();
			requestPending = false;
			this.reset();
		}
	};
	private static Popup popup = new Popup(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor(), "Ceci est un test");
	
	public static void draw() {
		if(requestPending) {
			popup.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(requestPending) {
			if(popup.event()) return true;
		}
		return false;
	}
	
	public static void setRequest(String player_name, String guild_name) {
		requestPending = true;
		popup.setPopup(acceptRequest, player_name+" wants to invite you in the guild: "+guild_name);
	}
	
	public static void updateSize() {
		//popup.update(Display.getWidth()/2-240*Mideas.getDisplayXFactor(), Display.getHeight()/2-365*Mideas.getDisplayYFactor(), 480*Mideas.getDisplayXFactor(), 75*Mideas.getDisplayYFactor());
	}*/
}
