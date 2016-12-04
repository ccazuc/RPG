package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.command.CommandTrade;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;

public class TradeFrame {

	private static float X_FRAME = Display.getWidth()/2-650*Mideas.getDisplayXFactor();
	private static float Y_FRAME = Display.getHeight()/2-300*Mideas.getDisplayYFactor();
	private static float Y_SHIFT = 47*Mideas.getDisplayYFactor();
	private static float Y_HOVER_TOP = 105*Mideas.getDisplayYFactor();
	private static float Y_HOVER_SIZE = 45*Mideas.getDisplayXFactor();
	private static float x_hover;
	private static float y_hover;
	private static int leftClickDown = -1;
	private static int rightClickDown = -1;
	private static float x_click_down;
	private static float y_click_down;
	private static float x_click_down_draw;
	private static float y_click_down_draw;
	static boolean tradeAcceptedSelf;
	static boolean tradeAcceptedOther;
	private static String name = "";
	static Item[] itemList = new Item[14];
	private static int hoveredSlot = -1;
	static boolean requestPending;
	private static long requestReceivedTimer;
	private static long requestMaximumTimer = 20000;
	private final static Color YELLOW = Color.decode("#FFC700");
	private static Button acceptRequest = new Button(Display.getWidth()/2-200*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Accept", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandTrade.writeConfirm();
			requestPending = false;
			Interface.setTradeFrameStatus(true);
		}
	};
	private static Button declineRequest = new Button(Display.getWidth()/2+20*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor(), "Decline", 15, 2) {
		@Override
		public void eventButtonClick() {
			CommandTrade.writeCloseTrade();
			requestPending = false;
		}
	};
	private static CrossButton closeFrame = new CrossButton(Display.getWidth()/2-272*Mideas.getDisplayXFactor(), Display.getHeight()/2-285*Mideas.getDisplayYFactor()) {
		@Override
		public void eventButtonClick() {
			Interface.setTradeFrameStatus(false);
			CommandTrade.writeCloseTrade();
			setAllItemSelectable();
			tradeAcceptedSelf = false;
			tradeAcceptedOther = false;
		}
	};
	private static Button acceptTrade = new Button(Display.getWidth()/2-438*Mideas.getDisplayXFactor(), Display.getHeight()/2+167*Mideas.getDisplayYFactor(), 93*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Trade", 15, 1) {
		@Override
		public void eventButtonClick() {
			CommandTrade.writeAccept();
			tradeAcceptedSelf = true;
		}
		
		@Override
		public boolean activateCondition() {
			return !tradeAcceptedSelf;
		}
	};
	private static Button cancelTrade = new Button(Display.getWidth()/2-340*Mideas.getDisplayXFactor(), Display.getHeight()/2+167*Mideas.getDisplayYFactor(), 87*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Cancel", 15, 1) {
		@Override
		public void eventButtonClick() {
			Interface.setTradeFrameStatus(false);
			CommandTrade.writeCloseTrade();
			setAllItemSelectable();
			tradeAcceptedSelf = false;
			tradeAcceptedOther = false;
		}
	};
	
	public static void draw() {
		Draw.drawQuad(Sprites.trade_frame, X_FRAME, Y_FRAME);
		FontManager.get("FRIZQT", 13).drawStringShadow(X_FRAME+150*Mideas.getDisplayXFactor(), Y_FRAME+15*Mideas.getDisplayYFactor(), Mideas.joueur1().getName(), YELLOW, Color.black, 1, 0, 0);
		float x = X_FRAME+25*Mideas.getDisplayXFactor();
		float y = 0;
		int i = 0;
		int j = 0;
		if(tradeAcceptedSelf) {
			Draw.drawQuad(Sprites.trade_accepted, X_FRAME+20*Mideas.getDisplayXFactor(), Y_FRAME+108*Mideas.getDisplayXFactor(), 180*Mideas.getDisplayXFactor(), 355*Mideas.getDisplayYFactor());
			Draw.drawQuad(Sprites.cursor, -100, -100);
		}
		if(tradeAcceptedOther) {
			Draw.drawQuad(Sprites.trade_accepted, X_FRAME+215*Mideas.getDisplayXFactor(), Y_FRAME+108*Mideas.getDisplayXFactor(), 180*Mideas.getDisplayXFactor(), 355*Mideas.getDisplayYFactor());
		}
		while(i < itemList.length) {
			if(itemList[i] != null) {
				Draw.drawQuad(IconsManager.getSprite37(itemList[i].getSpriteId()), x+3, Y_FRAME+Y_HOVER_TOP+j*Y_SHIFT+y+3, 40*Mideas.getDisplayXFactor(), 37*Mideas.getDisplayYFactor());
				if(itemList[i].getAmount() > 1) {
					FontManager.get("FRIZQT", 13).drawStringShadow(x+20*Mideas.getDisplayXFactor(), Y_FRAME+Y_HOVER_TOP+j*Y_SHIFT+y+25*Mideas.getDisplayYFactor(), itemList[i].getAmountString(), Color.white, Color.black, 1, 1, 1);
				}
			}
			i++;
			j++;
			if(i == 6 || i == 13) {
				y = 25*Mideas.getDisplayXFactor();
			}
			else if(i == 7) {
				x = X_FRAME+218*Mideas.getDisplayXFactor();
				y = 0;
				j = 0;
			}
		}
		if(hoveredSlot != -1) {
			Draw.drawQuad(Sprites.bag_hover, x_hover, y_hover, 40*Mideas.getDisplayXFactor(), 40*Mideas.getDisplayXFactor());
		}
		if(leftClickDown != -1 || rightClickDown != -1) {
			Draw.drawQuad(Sprites.click_down, x_click_down_draw, y_click_down_draw, 42*Mideas.getDisplayXFactor(), 42*Mideas.getDisplayXFactor());
		}
		cancelTrade.draw();
		closeFrame.draw();
		acceptTrade.draw();
	}
	
	public static boolean mouseEvent() {
		hoveredSlot = -1;
		float x = X_FRAME+25*Mideas.getDisplayXFactor();
		float y = 0;
		cancelTrade.event();
		closeFrame.event();
		acceptTrade.event();
		int i = 0;
		int j = 0;
		while(i < itemList.length) {
			isHover(j, x, y);
			i++;
			j++;
			if(i == 6 || i == 13) {
				y = 25*Mideas.getDisplayXFactor();
			}
			else if(i == 7) {
				x = X_FRAME+218*Mideas.getDisplayXFactor();
				j = 0;
				y = 0;
			}
		}
		if(hoveredSlot != -1 && Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				leftClickDown = hoveredSlot;
				x_click_down = Mideas.mouseX();
				x_click_down_draw = x_hover;
				y_click_down = Mideas.mouseY();
				y_click_down_draw = y_hover;
				return true;
			}
			else if(Mouse.getEventButton() == 1) {
				rightClickDown = hoveredSlot;
				x_click_down = Mideas.mouseX();
				x_click_down_draw = x_hover;
				y_click_down = Mideas.mouseY();
				y_click_down_draw = y_hover;
				return true;
			}
		}
		else {
			if(Mouse.getEventButton() == 0) {
				if(hoveredSlot >= 0 && hoveredSlot <= 6) {
					clickEvent();
				}
				leftClickDown = -1;
			}
			else if(Mouse.getEventButton() == 1) {
				rightClickDown = -1;
			}
		}
		if(Math.abs(Math.abs(Mideas.mouseX())-Math.abs(x_click_down)) >= 15 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(y_click_down)) >= 15) {
			if(leftClickDown != -1) {
				if(itemList[leftClickDown] != null) {
					setDraggedItem(leftClickDown);
				}
				leftClickDown = -1;
			}
			else if(rightClickDown != -1) {
			}
		}
		return false;
	}
	
	public static void event() { //TODO: use Popup classe
		if(requestPending) {
			if(System.currentTimeMillis()-requestReceivedTimer <= requestMaximumTimer) {
				Draw.drawQuad(Sprites.alert, Display.getWidth()/2-250*Mideas.getDisplayXFactor(), Display.getHeight()/2-250*Mideas.getDisplayYFactor(), 500*Mideas.getDisplayXFactor(), 110*Mideas.getDisplayYFactor());
				FontManager.get("FRIZQT", 16).drawStringShadow(Display.getWidth()/2-(FontManager.get("FRIZQT", 16).getWidth("Do you want to accept "+name+"'s trade ? "+(int)(20-(System.currentTimeMillis()-requestReceivedTimer)/1000)+" seconds left")*Mideas.getDisplayXFactor())/2, Display.getHeight()/2-230*Mideas.getDisplayYFactor(), "Do you want to accept "+name+"'s trade ? "+(int)(20-(System.currentTimeMillis()-requestReceivedTimer)/1000)+" seconds left", Color.white, Color.black, 1, Mideas.getDisplayXFactor(), Mideas.getDisplayXFactor());
				acceptRequest.event();
				declineRequest.event();
				acceptRequest.draw();
				declineRequest.draw();
			}
			else {
				requestPending = false;
				CommandTrade.writeCloseTrade();
				requestReceivedTimer = 0;
			}
		}
	}
	
	private static void clickEvent() {
		if(hoveredSlot >= 0 && hoveredSlot <= 6 && DragManager.getDraggedItem() != null) {
			if(DragManager.checkBagItems(DragManager.getDraggedItem())) {
				if(itemList[hoveredSlot] == null) {
					itemList[hoveredSlot] = DragManager.getDraggedItem();
					itemList[hoveredSlot].setIsSelectable(false);
					DragManager.setDraggedItem(null);
					tradeAcceptedSelf = false;
					tradeAcceptedOther = false;
					CommandTrade.writeAddItem(itemList[hoveredSlot], hoveredSlot, itemList[hoveredSlot].getAmount());
				}
				else {
					
				}
			}
			else if(DragManager.checkCharacterItems(DragManager.getDraggedItem())) {
				if(itemList[hoveredSlot] == null) {
					
				}
				else {
					
				}
			}
		}
		else {
			setDraggedItem(hoveredSlot);
		}
	}
	
	private static void setDraggedItem(int i) {
		DragManager.setDraggedItem(itemList[i]);
		itemList[i] = null;
		CommandTrade.writeRemovedItem(i);
		tradeAcceptedSelf = false;
		tradeAcceptedOther = false;
	}
	
	private static void isHover(int i, float x, float y) {
		if(Mideas.mouseX() >= x && Mideas.mouseX() <= x+50*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Y_FRAME+Y_HOVER_TOP+i*Y_SHIFT+y && Mideas.mouseY() <= Y_FRAME+Y_HOVER_SIZE+Y_HOVER_TOP+i*Y_SHIFT+y) {
			x_hover = x+2;
			y_hover = Y_FRAME+Y_HOVER_TOP+i*Y_SHIFT+2+y;
			hoveredSlot = i;
		}
	}
	
	public static void setName(String names) {
		name = names;
	}
	
	public static void addItem(int id, int slot) {
		if(slot < itemList.length) {
			itemList[slot] = Item.getItem(id);
		}
	}
	
	public static void addItem(Item item, int slot) {
		if(slot < itemList.length) {
			itemList[slot] = item;
		}
	}
	
	public static Item getItem(int slot) {
		if(slot < itemList.length) {
			return itemList[slot];
		}
		return null;
	}
	
	public static void setAllItemSelectable() {
		int i = 0;
		while(i < itemList.length) {
			if(itemList[i] != null) {
				itemList[i].setIsSelectable(true);
			}
			i++;
		}
	}
	
	public static float getXFrame() {
		return X_FRAME;
	}
	
	public static  float getYFrame() {
		return Y_FRAME;
	}
	
	public static void setTraceAcceptedOther(boolean we) {
		tradeAcceptedOther = we;
	}
	
	public static void setTraceAcceptedSelf(boolean we) {
		tradeAcceptedSelf = we;
	}
	
	public static void requestPending(boolean we) {
		requestPending = we;
		requestReceivedTimer = System.currentTimeMillis();
	}
	
	public static void removedTradedItems() {
		int i = 0;
		while(i < itemList.length-1) {
			if(itemList[i] != null) {
				Mideas.joueur1().bag().setBag(Mideas.joueur1().bag().getBagSlot(itemList[i]), null);
			}
			i++;
		}
	}
	
	public static void reset() {
		tradeAcceptedSelf = false;
		tradeAcceptedOther = false;
		name = "";
		Arrays.fill(itemList, null);
		hoveredSlot = -1;
		leftClickDown = -1;
		rightClickDown = -1;
	}
	
	public static void updateSize() {
		closeFrame.update(Display.getWidth()/2-272*Mideas.getDisplayXFactor(), Display.getHeight()/2-285*Mideas.getDisplayXFactor(), CrossButton.DEFAULT_WIDTH*Mideas.getDisplayXFactor(), CrossButton.DEFAULT_HEIGHT*Mideas.getDisplayXFactor());
		X_FRAME = Display.getWidth()/2-650*Mideas.getDisplayXFactor();
		Y_FRAME = Display.getHeight()/2-300*Mideas.getDisplayYFactor();
		Y_HOVER_TOP = 110*Mideas.getDisplayXFactor();
		Y_HOVER_SIZE = 45*Mideas.getDisplayYFactor();
		Y_SHIFT = 47*Mideas.getDisplayYFactor();
		cancelTrade.update(Display.getWidth()/2-340*Mideas.getDisplayXFactor(), Display.getHeight()/2+167*Mideas.getDisplayYFactor(), 87*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		acceptTrade.update(Display.getWidth()/2-438*Mideas.getDisplayXFactor(), Display.getHeight()/2+167*Mideas.getDisplayYFactor(), 93*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor());
		declineRequest.update(Display.getWidth()/2+20*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor());
		acceptRequest.update(Display.getWidth()/2-200*Mideas.getDisplayXFactor(), Display.getHeight()/2-190*Mideas.getDisplayYFactor(), 180*Mideas.getDisplayXFactor(), 25*Mideas.getDisplayXFactor());
	}
}
