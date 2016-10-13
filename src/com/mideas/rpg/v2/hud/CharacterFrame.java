package com.mideas.rpg.v2.hud;

import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;

public class CharacterFrame {

	private static Color bgColor = new Color(0, 0, 0, .6f); 
	private static Color borderColor = Color.decode("#494D4B");
	private static int hover = -1;
	private static int gemFrame;
	private static int xMouseShift;
	private static int yMouseShift;
	private static boolean hoverMove;
	private static boolean moving;
	private static int baseMouseX;
	private static int baseMouseY;
	private static int lastMouseX;
	private static int lastMouseY;
	private final static int X_CLOSE_FRAME_BUTTON = 69;
	private final static int Y_CLOSE_FRAME_BUTTON = -366;
	private static CrossButton closeFrameButton = new CrossButton(Display.getWidth()/2+(X_CLOSE_FRAME_BUTTON)*Mideas.getDisplayXFactor()+xMouseShift+gemFrame, Display.getHeight()/2+Y_CLOSE_FRAME_BUTTON*Mideas.getDisplayYFactor()+yMouseShift, Sprites.cross_button.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.cross_button.getImageHeight()*Mideas.getDisplayXFactor()) {
		@Override
		protected void eventButtonClick() {
			Interface.closeCharacterFrame();
		}
	};

	public static void draw() {
		Draw.drawQuad(Sprites.character_frame, Display.getWidth()/2-300*Mideas.getDisplayXFactor()+gemFrame+xMouseShift, Display.getHeight()/2-380*Mideas.getDisplayYFactor()+yMouseShift);
		closeFrameButton.draw();
		/*Draw.drawQuad(Sprites.character_frame_stats, Display.getWidth()/2-224+xMouseShift+gemFrame, Display.getHeight()/2-60+yMouseShift);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2-27+yMouseShift, "Strength:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getStrength())), Display.getHeight()/2-27+yMouseShift, String.valueOf(Mideas.joueur1().getStrength()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2-15+yMouseShift, "Stamina:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMaxStamina()/10)), Display.getHeight()/2-15+yMouseShift, String.valueOf(Mideas.joueur1().getMaxStamina()/10), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2-3+yMouseShift, "Armor:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf((int)Mideas.joueur1().getArmor())), Display.getHeight()/2-3+yMouseShift, String.valueOf((int)Mideas.joueur1().getArmor()), Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2+9+yMouseShift, "Critical:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getCritical())+"%"), Display.getHeight()/2+9+yMouseShift, String.valueOf(Mideas.joueur1().getCritical())+"%", Color.white, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-215+xMouseShift+gemFrame, Display.getHeight()/2+21+yMouseShift, "Mana:", Color.yellow, Color.black, 1, 1, 1);
		TTF2.characterFrameStats.drawStringShadow(Display.getWidth()/2-88+xMouseShift+gemFrame-TTF2.characterFrameStats.getWidth(String.valueOf(Mideas.joueur1().getMana())), Display.getHeight()/2+21+yMouseShift, String.valueOf(Mideas.joueur1().getMana()), Color.white, Color.black, 1, 1, 1);*/
		int xStuff = (int)(-280*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
		int i = 0;
		int j = 0;
		int z = 0;
		int yStuff = (int)(-302*Mideas.getDisplayYFactor()+yMouseShift);
		float yShift = 43.8f*Mideas.getDisplayXFactor();
		float xShift = 47.5f*Mideas.getDisplayXFactor();
		while(i < Mideas.joueur1().getStuff().length) {
			drawCharacterItems(i, xStuff+z*xShift, yStuff+j*yShift);
			i++;
			if(i <= 16) {
				j++;
			}
			else {
				z++;
			}
			if(i == 8) {
				xStuff = (int)(43*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
				j = 0;
			}
			else if(i == 16) {
				xStuff = (int)(-166*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
				yStuff = (int)(31*Mideas.getDisplayXFactor()+yMouseShift);
				j = 0;
			}
		}
		if(DragManager.isHoverCharacterFrame() && hover != -1) {
			i = 0;
			int x_hover = (int)(-236*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
			int y_hover = (int)(-300*Mideas.getDisplayYFactor()+yMouseShift);
			z = 0;
			j = 0;
			while(i < Mideas.joueur1().getStuff().length) {
				characterItemHover(i, x_hover+z*(int)yShift, y_hover+j*(int)yShift);
				i++;
				if(i < 16) {
					j++;
				}
				else {
					j = 0;
					x_hover = (int)(-205*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
					y_hover = (int)(80*Mideas.getDisplayXFactor()+yMouseShift);
					z++;
				}
				if(i == 8) {
					j = 0;
					x_hover = (int)(87*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
				}
				else if(i == 17) {
					yShift = 51*Mideas.getDisplayXFactor();
				}
			}
		}
	}

	public static boolean mouseEvent() throws SQLException {
		hover = -1;
		hoverMove = false;
		if(Mideas.mouseX()>= Display.getWidth()/2-300*Mideas.getDisplayXFactor()+xMouseShift+gemFrame && Mideas.mouseX() <= Display.getWidth()/2-300*Mideas.getDisplayXFactor()+xMouseShift+gemFrame+Sprites.character_frame.getImageWidth() && Mideas.mouseY() >= Display.getHeight()/2-375*Mideas.getDisplayYFactor()+yMouseShift && Mideas.mouseY() <= Display.getHeight()/2-375*Mideas.getDisplayYFactor()+yMouseShift+25) {
			hoverMove = true;
		}
		if(moving) {
			yMouseShift = Mideas.mouseY()-baseMouseY+lastMouseY;
			xMouseShift = Mideas.mouseX()-baseMouseX+lastMouseX;
			updateButton();
		}
		closeFrameButton.event();
		if(Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(hoverMove && !moving) {
					moving = true;
					baseMouseY = Mideas.mouseY();
					baseMouseX = Mideas.mouseX();
					return true;
				}
			}
		}
		if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(moving) {
					moving = false;
					lastMouseX = xMouseShift;
					lastMouseY = yMouseShift;
				}
			}
		}
		if(DragManager.isHoverCharacterFrame()) {
			int xStuff = (int)(-280*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
			int i = 0;
			int j = 0;
			int z = 0;
			int yStuff = (int)(-302*Mideas.getDisplayYFactor()+yMouseShift);
			float yShift = 43.8f*Mideas.getDisplayXFactor();
			float xShift = 47.5f*Mideas.getDisplayXFactor();
			while(i < Mideas.joueur1().getStuff().length) {
				isHover(i, xStuff+z*xShift, yStuff+j*yShift);
				i++;
				if(i <= 16) {
					j++;
				}
				else {
					z++;
				}
				if(i == 8) {
					xStuff = (int)(43*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
					j = 0;
				}
				else if(i == 16) {
					xStuff = (int)(-166*Mideas.getDisplayXFactor()+xMouseShift+gemFrame);
					yStuff = (int)(31*Mideas.getDisplayXFactor()+yMouseShift);
					j = 0;
				}
			}
			i = 0;
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 1 && Keyboard.isKeyDown(42)) {
					if(hover != -1 && Mideas.joueur1().getStuff(hover).getGemSlot1() != GemColor.NONE) {
						SocketingFrame.setStuff(Mideas.joueur1().getStuff(hover));
						Interface.setSocketingFrameStatus(true);
						gemFrame = 50+(int)(Sprites.socketing_frame.getImageWidth()*Mideas.getDisplayXFactor());
						updateButton();
						DragManager.resetInventoryClickedSlot();
						hover = -1;
					}
				}
			}
		}
		return false;
	}
	
	private static void characterItemHover(int i, int x, int z) {
		Color temp = null;
		int xShift = 237;
		int shift = 45;
		int yAnchor = 0;
		Stuff item = Mideas.joueur1().getStuff(i);
		if(item != null && hover == i) {
			xShift = Math.max(TTF2.itemName.getWidth(item.getStuffName()), TTF2.statsName.getWidth(item.getClassRequirements()))+15;
			int y = -75-TTF2.statsName.getLineHeight()*ContainerFrame.getNumberStats(item);
			if(Display.getHeight()/2+z-2+y < 0) {
				yAnchor = -(Display.getHeight()/2+z-2+y)+3;
			}
			Draw.drawColorQuad(Display.getWidth()/2+x-1, Display.getHeight()/2+z-2+yAnchor, 5+xShift, y, bgColor);
			Draw.drawColorQuadBorder(Display.getWidth()/2+x-1, Display.getHeight()/2+z-2+yAnchor, 6+xShift, y, borderColor);
			TTF2.itemName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+z+y+yAnchor, item.getStuffName(), ContainerFrame.getItemNameColor(item), Color.black, 1);
			TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+z+y+23+yAnchor, item.convStuffTypeToString(), Color.white, Color.black, 1);
			if(Mideas.joueur1().canWear(item)) {
				temp = Color.white;
			}
			else {
				temp = Color.red;
			}
			TTF2.statsName.drawStringShadow(Display.getWidth()/2+x-10+xShift-TTF2.font4.getWidth(item.convWearToString()), Display.getHeight()/2+y+23+z+yAnchor, item.convWearToString(), temp, Color.black, 1);
			if(item.getArmor() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getArmorString(), Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getStrength() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getStrengthString(), Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getMana() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getManaString(), Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getStamina() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getStaminaString(), Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getCritical() > 0) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getCriticalString(), Color.white, Color.black, 1);
				shift+= 20;
			}
			if(item.getGemSlot1() != GemColor.NONE) {
				if(item.getEquippedGem1() != null) {
					Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem1().getId()), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+19, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getEquippedGem1().getGemStatsString(), Color.white, Color.black, 1);
				}
				else {
					Draw.drawQuad(item.getFreeSlotGemSprite1(), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+26, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getGem1String(), Color.gray, Color.black, 1);
				}
				Draw.drawQuad(Sprites.cursor, -5000, -5000);
				shift+= 20;
			}
			if(item.getGemSlot2() != GemColor.NONE) {
				if(item.getEquippedGem2() != null) {
					Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem2().getId()), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+19, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getEquippedGem2().getGemStatsString(), Color.white, Color.black, 1);
				}
				else {
					Draw.drawQuad(item.getFreeSlotGemSprite2(), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+26, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getGem2String(), Color.gray, Color.black, 1);
				}
				Draw.drawQuad(Sprites.cursor, -5000, -5000);
				shift+= 20;
			}
			if(item.getGemSlot3() != GemColor.NONE) {
				if(item.getEquippedGem3() != null) {
					Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem3().getId()), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+19, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getEquippedGem3().getGemStatsString(), Color.white, Color.black, 1);
				}
				else {
					Draw.drawQuad(item.getFreeSlotGemSprite3(), Display.getWidth()/2+x+2, Display.getHeight()/2+y+shift+z+yAnchor);
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+26, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getGem3String(), Color.gray, Color.black, 1);
				}
				Draw.drawQuad(Sprites.cursor, -5000, -5000);
				shift+= 20;
			}
			if(item.getGemBonusType() != GemBonusType.NONE) {
				if(item.getGemBonusActivated()) {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor-3, item.getSocketBonusString(), Color.white, Color.black, 1);
				}
				else {
					TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z-3+yAnchor, item.getSocketBonusString(), Color.gray, Color.black, 1);
				}
				shift+= 20;
			}
			if(item.canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString()))) {
				temp = Color.white;
			}
			else {
				temp = Color.red;
			}
			if(item.getClassRequirements() != null) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getClassRequirements(), temp, Color.black, 1);
				shift+= 20;
			}
			if(Mideas.joueur1().getLevel() >= item.getLevel()) {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getLevelString(), Color.white, Color.black, 1);
			}
			else {
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x+5, Display.getHeight()/2+y+shift+z+yAnchor, item.getLevelString(), Color.red, Color.black, 1);
			}
		}
	}
	
	private static void drawCharacterItems(int i, float x, float y) {
		Stuff stuff = Mideas.joueur1().getStuff(i);
		if(stuff != null) {
			Draw.drawQuad(IconsManager.getSprite37((stuff.getSpriteId())), Display.getWidth()/2+x, Display.getHeight()/2+y);
			Draw.drawQuad(Sprites.spell_border, Display.getWidth()/2+x-2, Display.getHeight()/2+y-2);
			if(stuff == DragManager.getDraggedItem()) {
				Draw.drawColorQuad(Display.getWidth()/2+x, Display.getHeight()/2+y, 37, 35, bgColor);
			}
			if(DragManager.getDraggedItem() != null && hover != i && DragManager.getDraggedItem().isStuff() && ((Stuff)DragManager.getDraggedItem()).getType() == stuff.getType()) {
				//if(hover%2 == 0) {
					Draw.drawQuad(Sprites.spell_hover, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
				//}
				//else {
					Draw.drawQuad(Sprites.spell_hover2, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
				//}
			}
		}
		if(hover == i) {
			Draw.drawQuad(Sprites.spell_hover, Display.getWidth()/2+x-2, Display.getHeight()/2+y-2);
		}
		if(DragManager.getClickInventory(i) && DragManager.getDraggedItem() == null) {
			Draw.drawQuad(Sprites.inventory_click_hover, Display.getWidth()/2+x-3, Display.getHeight()/2+y-3);
		}
	}
	
	private static void isHover(int i, float x, float y) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+37 && Mideas.mouseY() >= Display.getHeight()/2+y && Mideas.mouseY() <= Display.getHeight()/2+y+38) {
			hover = i;
		}
	}
	
	public static void setMouseX(int x) throws SQLException {
		xMouseShift = x;
		lastMouseX = x;
		updateButton();
	}
	
	public static int getMouseX() {
		return xMouseShift;
	}
	
	public static void setMouseY(int y) throws SQLException {
		yMouseShift = y;
		lastMouseY = y;
		updateButton();
	}
	
	public static int getMouseY() {
		return yMouseShift;
	}
	
	public static void setGemFrame(int gemFrames) {
		gemFrame = gemFrames;
	}
	
	public static boolean getHoverCharacterFrame(int i) {
		return hover == i;
	}
	
	public static void setHoverFalse() {
		hover = -1;
	}
	
	public static int getGemFrameSize() {
		return gemFrame;
	}
	
	public static void updateButton() throws SQLException {
		closeFrameButton.update(Display.getWidth()/2+(X_CLOSE_FRAME_BUTTON)*Mideas.getDisplayXFactor()+xMouseShift+gemFrame, Display.getHeight()/2+Y_CLOSE_FRAME_BUTTON*Mideas.getDisplayYFactor()+yMouseShift, Sprites.cross_button.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.cross_button.getImageHeight()*Mideas.getDisplayXFactor());
		closeFrameButton.event();
		SocketingFrame.getCrossButton().update(Display.getWidth()/2+66*Mideas.getDisplayXFactor()+xMouseShift, Display.getHeight()/2-365*Mideas.getDisplayYFactor()+yMouseShift, Sprites.cross_button.getImageWidth()*Mideas.getDisplayXFactor(), (Sprites.cross_button.getImageHeight()+2)*Mideas.getDisplayXFactor());
		SocketingFrame.getButton().update(Display.getWidth()/2-100*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX(), 620*Mideas.getDisplayYFactor(), 184*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayXFactor());
	}
	
	public static void updateSize() {
		if(gemFrame != 0) {
			gemFrame = 50+(int)(Sprites.socketing_frame.getImageWidth()*Mideas.getDisplayXFactor());
		}
		closeFrameButton.update(Display.getWidth()/2+(X_CLOSE_FRAME_BUTTON)*Mideas.getDisplayXFactor()+xMouseShift+gemFrame, Display.getHeight()/2+Y_CLOSE_FRAME_BUTTON*Mideas.getDisplayYFactor()+yMouseShift, Sprites.cross_button.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.cross_button.getImageHeight()*Mideas.getDisplayXFactor());
		SocketingFrame.getCrossButton().update(Display.getWidth()/2+66*Mideas.getDisplayXFactor()+xMouseShift, Display.getHeight()/2-365*Mideas.getDisplayYFactor()+yMouseShift, Sprites.cross_button.getImageWidth()*Mideas.getDisplayXFactor(), (Sprites.cross_button.getImageHeight()+2)*Mideas.getDisplayXFactor());
		SocketingFrame.getButton().update(Display.getWidth()/2-100*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX(), 620*Mideas.getDisplayYFactor(), 184*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayXFactor());
	}
}
