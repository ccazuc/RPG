package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.bag.ContainerManager;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.CrossButton;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.IntegerInput;
import com.mideas.rpg.v2.utils.Tooltip;

public class ContainerFrame {
	
	private static int hoveredSlot = -1;
	private final static Color bgColor = new Color(0, 0, 0, .6f); 
	private final static Color BLUE = Color.decode("#0268CC");
	private final static Color PURPLE = Color.decode("#822CB7");
	private final static Color LEGENDARY = Color.decode("#FF800D");
	private final static Color YELLOW = Color.decode("#FFC700");
	private static int x;
	private static int xShift;
	private static int y;
	private static int yShift;
	static boolean[] isBagOpen = new boolean[5];
	private static int[] bagSize = new int[5];
	private static boolean bagChange = true;
	private static int xItemNumber;
	private static int yItemNumber;
	static int iItemNumber = -1;
	private static boolean itemNumberOkButton;
	private static boolean itemNumberCancelButton;
	private static boolean itemNumberLeftArrow;
	private static boolean itemNumberRightArrow;
	private static int iHover;
	private static int x_item;
	private static int y_item;
	private static int numberItem = 1;
	private static int lastSplit;
	private static Tooltip itemHoverTooltip = new Tooltip(0, 0, 0, 0, 0.7f);
	private static CrossButton backpackButton = new CrossButton(0, 0) {
		@Override
		protected void eventButtonClick() {
			isBagOpen[0] = false;
			this.reset();
		}
	};

	private static CrossButton firstBagButton = new CrossButton(0, 0) {
		@Override
		protected void eventButtonClick() {
			isBagOpen[1] = false;
			this.reset();
		}
	};

	private static CrossButton secondBagButton = new CrossButton(0, 0) {
		@Override
		protected void eventButtonClick() {
			isBagOpen[2] = false;
			this.reset();
		}
	};

	private static CrossButton thirdBagButton = new CrossButton(0, 0) {
		@Override
		protected void eventButtonClick() {
			isBagOpen[3] = false;
			this.reset();
		}
	};

	private static CrossButton fourthBagButton = new CrossButton(0, 0) {
		@Override
		protected void eventButtonClick() {
			isBagOpen[4] = false;
			this.reset();
		}
	};
	
	private static IntegerInput itemNumber = new IntegerInput(TTF2.itemNumber, 3) {
		@Override
		public int maximumValue() {
			if(iItemNumber != -1) {
				return Mideas.joueur1().bag().getBag(iItemNumber).getAmount();
			}
			return 1;
		}
	};
 	
	private static void drawBags(int i, int x_items, int y_items, int x_textt, int y_textt) {
		drawBag(i, x_items, y_items);
		if(hoveredSlot == i) {
			iHover = i;
			x_item = x_items;
			y_item = y_items;
		}
		if(iItemNumber == i) {
			iItemNumber = i;
			xItemNumber = x_items;
			yItemNumber = y_items;
		}
	}
	public static void draw() {
		xItemNumber = 0;
		yItemNumber = 0;
		iHover = -1;
		if(bagChange) {
			updateBagFrameSize();
		}
		int xBagShift = 0;
		int bagShift = 0;
		float yBagShift = 0;
		int xBagIcon = (int)(-313*Mideas.getDisplayXFactor());
		int yBagIcon = -4;
		float xCloseButton = -155*Mideas.getDisplayXFactor();
		float yCloseButton = -2;
		if(isBagOpen[0]) {
			yBagShift = (int)(-bagSize[0]-150*Mideas.getDisplayYFactor());
			backpackButton.draw(Display.getWidth()+xCloseButton, Display.getHeight()+bagShift+yBagShift+yCloseButton+9.5f);
			Draw.drawQuad(Sprites.back_bag, Display.getWidth()-320*Mideas.getDisplayXFactor(), Display.getHeight()+yBagShift);
			bagShift+= yBagShift;
		}
		if(isBagOpen[1]) {
			if(Mideas.joueur1().bag().getEquippedBag(0) != null) {
				if(isBagOpen[0]) {
					yBagShift = -bagSize[1];
				}
				else {
					yBagShift = -bagSize[1]-142*Mideas.getDisplayYFactor();
				}
				firstBagButton.draw(Display.getWidth()+xCloseButton, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.joueur1().bag().getSpriteId(0)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(0).getId()), Display.getWidth()-320*Mideas.getDisplayXFactor(), Display.getHeight()+bagShift+yBagShift-10*Mideas.getDisplayYFactor());
				bagShift+= yBagShift;
				yBagShift = 0;
			}
		}
		if(isBagOpen[2]) {
			if(Mideas.joueur1().bag().getEquippedBag(1) != null) {
				if(isBagOpen[0] || isBagOpen[1]) {
					yBagShift = -bagSize[2];
				}
				else {
					yBagShift = -bagSize[2]-142*Mideas.getDisplayYFactor();
				}
				secondBagButton.draw(Display.getWidth()+xCloseButton, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.joueur1().bag().getSpriteId(1)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(1).getId()), Display.getWidth()-(320+xBagShift)*Mideas.getDisplayXFactor(), Display.getHeight()+bagShift+yBagShift-10);
				bagShift+= yBagShift;
				yBagShift = 0;
			}
		}
		if(isBagOpen[3]) {
			if(Mideas.joueur1().bag().getEquippedBag(2) != null) {
				if(isBagOpen[0] || isBagOpen[1] || isBagOpen[2]) {
					yBagShift = -bagSize[3];
				}
				else {
					yBagShift = -bagSize[3]-142;
				}
				if(bagShift <= -700) {
					bagShift = 0;
					xBagShift = (int)(-200*Mideas.getDisplayXFactor());
					yBagShift = -bagSize[3]-142;
				}
				thirdBagButton.draw(Display.getWidth()+xCloseButton+xBagShift, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.joueur1().bag().getSpriteId(2)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(2).getId()), Display.getWidth()-320*Mideas.getDisplayXFactor()+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
				bagShift+= yBagShift;
				yBagShift = 0;
			}
		}
		if(isBagOpen[4]) {
			if(Mideas.joueur1().bag().getEquippedBag(3) != null) {
				if(isBagOpen[0] || isBagOpen[1] || isBagOpen[2] || isBagOpen[3]) {
					yBagShift = -bagSize[4];
				}
				else {
					yBagShift = -bagSize[4]-142;
				}
				if(bagShift <= -700) {
					bagShift = 0;
					xBagShift = (int)(-200*Mideas.getDisplayXFactor());
					yBagShift = -bagSize[4]-142;
				}
				fourthBagButton.draw(Display.getWidth()+xCloseButton+xBagShift, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.joueur1().bag().getSpriteId(3)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(3).getId()), Display.getWidth()-320*Mideas.getDisplayXFactor()+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
			}
		}
		x = (int)(-303*Mideas.getDisplayXFactor());
		xShift = (int)(42*Mideas.getDisplayXFactor());
		y = (int)(-50*Mideas.getDisplayYFactor());
		yShift = (int)(41*Mideas.getDisplayYFactor());
		boolean backPack = true;
		boolean first = true;
		boolean second = true;
		boolean third = true;
		boolean fourth = true;
		int size = 16;
		int i = 0;
		int j = 0;
		int k = 0;
		int z = 0;
		boolean resize = false;
		xBagShift = 0;
		yBagShift = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(backPack) {
				if(isBagOpen[0]) {
					z+= -bagSize[0]-50*Mideas.getDisplayYFactor();
				}
				backPack = false;
			}
			if(i < 16) {
				if(isBagOpen[0]) {
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z);
				}
			}
			else if(i >= 16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+16) {
				if(isBagOpen[1]) {
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z);
				}
			}
			else if(i >= Mideas.joueur1().bag().getEquippedBagSize(0)+16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+16) {
				if(isBagOpen[2]) { 
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z);
				}
			}
			else if(i >= Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+Mideas.joueur1().bag().getEquippedBagSize(2)+16) {
				if(isBagOpen[3]) {
					drawBags(i, x+j*xShift+xBagShift, k*yShift+z, x+xBagShift, z);
				}
			}
			else if(i >= Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+Mideas.joueur1().bag().getEquippedBagSize(2)+16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+Mideas.joueur1().bag().getEquippedBagSize(2)+Mideas.joueur1().bag().getEquippedBagSize(3)+16) {
				if(isBagOpen[4]) { 
					drawBags(i, x+j*xShift+xBagShift, k*yShift+z, x+xBagShift, z);
				}
			}
			i++;
			j++;
			if(j == 4) {
				j = 0;
			}
			if(i != 0 && i%4 == 0) {
				k++;
			}
			if(i == size && first) {
				if(Mideas.joueur1().bag().getEquippedBag(0) != null) {
					if(isBagOpen[1]) {
						if(isBagOpen[0]) {
							z+= -bagSize[1]-10*Mideas.getDisplayYFactor();
						}
						else {
							z+= -bagSize[1]-52*Mideas.getDisplayYFactor();
						}
					}
					yBagShift = 0;
					size+= Mideas.joueur1().bag().getEquippedBagSize(0);
				}
				first = false;
				j = 0;
				k = 0;
			}
			if(i == size && second) {
				if(Mideas.joueur1().bag().getEquippedBag(1) != null) {
					if(isBagOpen[2]) {
						if(isBagOpen[0] && !isBagOpen[1]) {
							z+= -bagSize[2]-10;
						}
						else if(!isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else if(isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else {
							z+= -bagSize[2]-52;
						}
						yBagShift = 0;
					}
					size+= Mideas.joueur1().bag().getEquippedBagSize(1);
				}
				second = false;
				j = 0;
				k = 0;
			}
			if(i == size && third) {
				if(Mideas.joueur1().bag().getEquippedBag(2) != null) {
					if(isBagOpen[3]) {
						if(z <= -650) {
							z = 0;
							xBagShift = (int)(-201*Mideas.getDisplayXFactor());
							yBagShift = -bagSize[3]+22;
							resize = true;
						}
						if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
							z+= -bagSize[3]-60;
						}
						else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[3]-52;
						}
						else if(!isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize) {
							z+= -bagSize[3]-50;
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) {
							z+= -bagSize[3]-52;
						}
						else if(!resize) {
							z+= -bagSize[3]-52;
						}
						else if(resize) {
							z+= -bagSize[3]-101;
						}
					}
					size+= Mideas.joueur1().bag().getEquippedBagSize(2);
				}
				third = false;
				j = 0;
				k = 0;
			}
			if(i == size && fourth) {
				if(Mideas.joueur1().bag().getEquippedBag(3) != null) {
					if(isBagOpen[4]) {
						if(z <= -650) {  
							z = 0;
							xBagShift = (int)(-200*Mideas.getDisplayXFactor());
							yBagShift = -bagSize[4]+22;
							resize = true;
						}
						if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize)  {
							z+= -bagSize[3]-52*Mideas.getDisplayYFactor();
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
							z+= -bagSize[4]-102;
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
							z+= -bagSize[4]-62;
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3]) {
							z+= -bagSize[4]-62;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
							z+= -bagSize[4]-62;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
							z+= -bagSize[4]-62;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && !resize) {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && resize) {
							z+= -bagSize[4]-102;
						}
						else if(isBagOpen[1] && isBagOpen[2] && !resize) {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !resize) {
							z+= -bagSize[4];
						}
						else if((isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
							z+= -bagSize[4]-60;
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[3] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[3] && isBagOpen[1] && isBagOpen[2] && !resize) {
							z+= -bagSize[4]-28;
						}
						else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if(!resize) {
							z+= -bagSize[4]-28;
						}
						else if(resize) {
							z+= -bagSize[4];
						}
					}
					size+= Mideas.joueur1().bag().getEquippedBagSize(3);
				}
				fourth = false;
				j = 0;
				k = 0;
			}
		}
		backPack = true;
		first = true;
		second = true;
		third = true;
		fourth = true;
		size = 16;
		i = 0;
		j = 0;
		k = 0;
		z = 0;
		resize = false;
		xBagShift = 0;
		if(xItemNumber != 0) { //draw itemNumber frame
			itemNumber.event();
			drawItemNumber(xItemNumber, yItemNumber);
		}
		if(itemNumberOkButton) {
			Draw.drawQuad(Sprites.itemnumber_hover_ok, Display.getWidth()+xItemNumber+11, Display.getHeight()+yItemNumber+51.7f);
		}
		else if(itemNumberCancelButton) {
			Draw.drawQuad(Sprites.itemnumber_hover_cancel, Display.getWidth()+xItemNumber+87, Display.getHeight()+yItemNumber+52.3f);
		}
	}
	
	public static boolean mouseEvent() {
		hoveredSlot = -1;
		itemNumberOkButton = false;
		itemNumberCancelButton = false;
		itemNumberLeftArrow = false;
		itemNumberRightArrow = false;
		checkItemNumberMouseEvent();
		if(!DragManager.isHoverCharacterFrame()) {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					if(itemNumberOkButton) {
						splitItem(iItemNumber);
						return true;
					}
					else if(itemNumberCancelButton) {
						iItemNumber = -1;
						numberItem = 1;
						return true;
					}
					else if(itemNumberLeftArrow) {
						if(numberItem > 1) {
							itemNumber.setText(numberItem-1);
							return true;
						}
					}
					else if(itemNumberRightArrow) {
						if(numberItem < Mideas.joueur1().bag().getBag(iItemNumber).getAmount()) {
							itemNumber.setText(numberItem+1);
							return true;
						}
					}
				}
			}
			int x = (int)(-303*Mideas.getDisplayXFactor());
			int xShift = (int)(42*Mideas.getDisplayXFactor());
			int yShift = (int)(41*Mideas.getDisplayYFactor());
			int y = (int)(-50*Mideas.getDisplayYFactor());
			int i = 0;
			int j = 0;
			int k = 0;
			int z = 0;
			int xBagShift = 0;
			boolean backPack = true;
			boolean first = true;
			boolean second = true;
			boolean third = true;
			boolean fourth = true;
			boolean resize = false;
			int size = 16;
			while(i < Mideas.joueur1().bag().getBag().length) {
				if(backPack) {
					if(isBagOpen[0]) {
						z+= -bagSize[0]-50;
					}
					backPack = false;
				}
				if(i < 16) {
					if(isBagOpen[0]) {
						slotHover(x+j*xShift+xBagShift, y+k*yShift+z, i);
						backpackButton.event();
					}
				}
				else if(i >= 16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+16) {
					if(isBagOpen[1]) { 
						slotHover(x+j*xShift+xBagShift, y+k*yShift+z, i);
						firstBagButton.event();
					}
				}
				else if(i >= Mideas.joueur1().bag().getEquippedBagSize(0)+16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+16) {
					if(isBagOpen[2]) { 
						slotHover(x+j*xShift+xBagShift, y+k*yShift+z, i);
						secondBagButton.event();
					}
				}
				else if(i >= Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+Mideas.joueur1().bag().getEquippedBagSize(2)+16) {
					if(isBagOpen[3]) {
						slotHover(x+j*xShift+xBagShift, k*yShift+z, i);
						thirdBagButton.event();
					}
				}
				else if(i >= Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+Mideas.joueur1().bag().getEquippedBagSize(2)+16 && i < Mideas.joueur1().bag().getEquippedBagSize(0)+Mideas.joueur1().bag().getEquippedBagSize(1)+Mideas.joueur1().bag().getEquippedBagSize(2)+Mideas.joueur1().bag().getEquippedBagSize(3)+16) {
					if(isBagOpen[4]) { 
						slotHover(x+j*xShift+xBagShift, k*yShift+z, i);
						fourthBagButton.event();
					}
				}
				i++;
				j++;
				if(j == 4) {
					j = 0;
				}
				if(i != 0 && i%4 == 0) {
					k++;
				}
				if(i == size && first) {
					if(Mideas.joueur1().bag().getEquippedBag(0) != null) {
						if(isBagOpen[1]) {
							if(isBagOpen[0]) {
								z+= -bagSize[1]-10;
							}
							else {
								z+= -bagSize[1]-52;
							}
						}
						size+= Mideas.joueur1().bag().getEquippedBagSize(0);
					}
					first = false;
					j = 0;
					k = 0;
				}
				if(i == size && second) {
					if(Mideas.joueur1().bag().getEquippedBag(1) != null) {
						if(isBagOpen[2]) {
							if(isBagOpen[0] && !isBagOpen[1]) {
								z+= -bagSize[2]-10;
							}
							else if(!isBagOpen[0] && isBagOpen[1]) {
								z+= -bagSize[2];
							}
							else if(isBagOpen[0] && isBagOpen[1]) {
								z+= -bagSize[2];
							}
							else {
								z+= -bagSize[2]-52;
							}
						}
						size+= Mideas.joueur1().bag().getEquippedBagSize(1);
					}
					second = false;
					j = 0;
					k = 0;
				}
				if(i == size && third) {
					if(Mideas.joueur1().bag().getEquippedBag(2) != null) {
						if(isBagOpen[3]) {
							if(z <= -650) {
								z = 0;
								xBagShift = (int)(-200*Mideas.getDisplayXFactor());
								resize = true;
							}
							if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
								z+= -bagSize[3]-60;
							}
							else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
								z+= -bagSize[3]-52;
							}
							else if(!isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize) {
								z+= -bagSize[3]-50;
							}
							else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) {
								z+= -bagSize[3]-102;
							}
							else if(!resize) {
								z+= -bagSize[3]-52;
							}
							else if(resize) {
								z+= -bagSize[3]-101;
							}
						}
						size+= Mideas.joueur1().bag().getEquippedBagSize(2);
					}
					third = false;
					j = 0;
					k = 0;
				}
				if(i == size && fourth) {
					if(Mideas.joueur1().bag().getEquippedBag(3) != null) {
						if(isBagOpen[4]) {
							if(z <= -650) { 
								z = 0;
								xBagShift = (int)(-200*Mideas.getDisplayXFactor());
								resize = true;
							}
							if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize)  {
								z+= -bagSize[3]-52;
							}
							else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
								z+= -bagSize[4]-102;
							}
							else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
								z+= -bagSize[4]-62;
							}
							else if(!isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3]) {
								z+= -bagSize[4]-62;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
								z+= -bagSize[4]-62;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
								z+= -bagSize[4]-62;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && !resize) {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && resize) {
								z+= -bagSize[4]-102;
							}
							else if(isBagOpen[1] && isBagOpen[2] && !resize) {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && resize) {
								z+= -bagSize[4];
							}
							else if(!isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !resize) {
								z+= -bagSize[4];
							}
							else if((isBagOpen[1] || isBagOpen[2]) && !resize)  {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
								z+= -bagSize[4]-60;
							}
							else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[3] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[3] && isBagOpen[1] && isBagOpen[2] && !resize) {
								z+= -bagSize[4]-28;
							}
							else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
								z+= -bagSize[4]-52;
							}
							else if(!resize) {
								z+= -bagSize[4]-28;
							}
							else if(resize) {
								z+= -bagSize[4];
							}
						}
						size+= Mideas.joueur1().bag().getEquippedBagSize(3);
					}
					fourth = false;
					j = 0;
					k = 0;
				}
				
			}
		}
		return false;
	}
	
	private static void slotHover(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()+x-3 && Mideas.mouseX() <= Display.getWidth()+x+37 && Mideas.mouseY() >= Display.getHeight()+y-3 && Mideas.mouseY() <= Display.getHeight()+y+38) {
			hoveredSlot = i;
		}
	}
	
	private static void drawBag(int i, int x, int y) {
		if(Mideas.joueur1().bag().getBag(i) != null) {
			Draw.drawQuad(IconsManager.getSprite37((Mideas.joueur1().bag().getBag(i).getSpriteId())), Display.getWidth()+x, Display.getHeight()+y);
			if((Mideas.joueur1().bag().getBag(i).isStackable())) {
				TTF2.itemNumber.drawStringShadow(Display.getWidth()+x+35*Mideas.getDisplayXFactor()-TTF2.font4.getWidth(Integer.toString(Mideas.joueur1().bag().getBag(i).getAmount())), Display.getHeight()+y+20*Mideas.getDisplayYFactor(), Integer.toString(Mideas.joueur1().bag().getBag(i).getAmount()), Color.white, Color.black, 1, 1, 1);
				if(Mideas.joueur1().bag().getBag(i).getAmount() <= 0) {
					Mideas.joueur1().bag().setBag(i, null);
				}
			}
			if(hoveredSlot == i) {
				Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x, Display.getHeight()+y);
			}
			if(Mideas.joueur1().bag().getBag(i) == DragManager.getDraggedItem() || !Mideas.joueur1().bag().getBag(i).isSelectable()) {
				Draw.drawColorQuad(Display.getWidth()+x, Display.getHeight()+y, 37, 35, bgColor);
			}
		}
		Draw.drawQuad(Sprites.bag_border1, Display.getWidth()+x-3, Display.getHeight()+y-2);
		if(DragManager.getClickBag(i)) {
			Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()+x-1, Display.getHeight()+y-1);
		}
	}
	
	private static void splitItem(int i) {
		if(Mideas.joueur1().bag().getBag(i) != null) {
			if(Mideas.joueur1().bag().getBag(i).isPotion()) {
				Potion temp = PotionManager.getClone(Mideas.joueur1().bag().getBag(i).getId());
				temp.setAmount(numberItem);
				DragManager.setDraggedItem(temp);
				Mideas.joueur1().bag().getBag(i).setAmount(Mideas.joueur1().bag().getBag(i).getAmount()-numberItem);
			}
			else if(Mideas.joueur1().bag().getBag(i).isItem()) {
				
			}
			iItemNumber = -1;
			numberItem = 1;
			lastSplit = i;
			DragManager.setDraggedItemSplit(true);
		}
	}
	
	private static void checkItemNumberMouseEvent() {
		if(Mideas.mouseX() >= Display.getWidth()+xItemNumber+10 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+10+Sprites.itemnumber_hover_ok.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+yItemNumber+55 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+55+Sprites.itemnumber_hover_ok.getImageHeight()) {
			itemNumberOkButton = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()+xItemNumber+88 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+88+Sprites.itemnumber_hover_cancel.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+yItemNumber+55 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+55+Sprites.itemnumber_hover_cancel.getImageHeight()) {
			itemNumberCancelButton = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()+xItemNumber+6 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+18 && Mideas.mouseY() >= Display.getHeight()+yItemNumber+20 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+35) {
			itemNumberLeftArrow = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()+xItemNumber+145 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+157 && Mideas.mouseY() >= Display.getHeight()+yItemNumber+20 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+35) {
			itemNumberRightArrow = true;
		}
	}
	
	private static void drawItemNumber(int x, int y) {
		if(Mideas.isInteger(itemNumber.getText())) {
			numberItem = Integer.valueOf(itemNumber.getText());
		}
		else {
			numberItem = 1;
		}
		Draw.drawQuad(Sprites.itemnumber_frame, Display.getWidth()+x+30-Sprites.itemnumber_frame.getImageWidth(), Display.getHeight()+y-5-Sprites.itemnumber_frame.getImageHeight());
		TTF2.itemNumber.drawStringShadow(Display.getWidth()+x+153-Sprites.itemnumber_frame.getImageWidth()-TTF2.itemNumber.getWidth(Integer.toString(numberItem)), Display.getHeight()+y+15-Sprites.itemnumber_frame.getImageHeight(), Integer.toString(numberItem), Color.white, Color.black, 1, 1, 1);
		xItemNumber = x+30-Sprites.itemnumber_frame.getImageWidth();
		yItemNumber = y-5-Sprites.itemnumber_frame.getImageHeight();
		if(numberItem > 1) {
			Draw.drawQuad(Sprites.itemnumber_leftyellow_arrow, Display.getWidth()+xItemNumber+9, Display.getHeight()+yItemNumber+19);
		}
		if(numberItem == Mideas.joueur1().bag().getBag(iItemNumber).getAmount()) {
			Draw.drawQuad(Sprites.itemnumber_rightgray_arrow, Display.getWidth()+xItemNumber+149, Display.getHeight()+yItemNumber+19);
		}
	}
	
	/*private static void drawHoverBag(int i, int x, int z, int x_hover, int y_hover) throws FileNotFoundException {
		if(slot_hover[i]) {
			int shift = 60;
			if(Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).getItemType() == ItemType.STUFF) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.joueur1().bag().getBag(i).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.joueur1().bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				if(((Stuff)Mideas.joueur1().bag().getBag(i)).getArmor() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.joueur1().bag().getBag(i)).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.joueur1().bag().getBag(i)).getStamina() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.joueur1().bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.joueur1().bag().getBag(i)).getMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.joueur1().bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.joueur1().bag().getBag(i)).getStrength() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.joueur1().bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.joueur1().bag().getBag(i)).getCritical() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.joueur1().bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				}
				ContainerFrame.calcCoinContainer(Mideas.joueur1().bag().getBag(i).getSellPrice(), x-70, y+100);
			}
			else if(Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).getItemType() == ItemType.POTION) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.joueur1().bag().getBag(i).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.joueur1().bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				if(((Potion)Mideas.joueur1().bag().getBag(i)).getPotionHeal() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "Restores "+((Potion)Mideas.joueur1().bag().getBag(i)).getPotionHeal()+" Hp", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Potion)Mideas.joueur1().bag().getBag(i)).getPotionMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "Restores "+((Potion)Mideas.joueur1().bag().getBag(i)).getPotionMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				}
			}
			if(Mideas.joueur1().bag().getBag(i) != null && DragManager.getClickBag(i) && DragManager.getDraggedItem() == null) {
				Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()+x+x_hover, Display.getHeight()/2+y+y_hover);
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+x_hover, Display.getHeight()/2+y+y_hover);
		}
	}*/
	
	public static void drawHoverBag(int i, int x, int y) {
		if(hoveredSlot == i && !isHoverItemNumberFrame()) {
			if(Mideas.joueur1().bag().getBag(i) != null) {
				if(Mideas.joueur1().bag().getBag(i).isStuff()) {
					drawStuff(i, x, y);
				}
				else if(Mideas.joueur1().bag().getBag(i).isWeapon()) {
					drawWeapon(i, x, y);
				}
				else if(Mideas.joueur1().bag().getBag(i).isPotion()) {
					drawPotion(i, x, y);
				}
				else if(Mideas.joueur1().bag().getBag(i).isPotion()) {
					drawGem(i, x, y);
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x, Display.getHeight()+y-1);
		}
	}
	
	private static void drawGem(int i, int x, int z) {
		Gem gem = (Gem)Mideas.joueur1().bag().getBag(i);
		int y = -75;
		int shift = 0;
		int xShift = TTF2.itemName.getWidth(gem.getStuffName());
		//Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, 10+y, bgColor);
		//Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, 11+y, borderColor);
		if(itemHoverTooltip.getX() != Display.getWidth()+x-15-xShift || itemHoverTooltip.getY() != Display.getHeight()+z-10+y) {
			itemHoverTooltip.update(Display.getWidth()+x-15-xShift, Display.getHeight()+z+y, 25+Math.abs(xShift), Math.abs(y)+15);
		}
		itemHoverTooltip.draw();
		y =  -65;
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, gem.getStuffName(), getItemNameColor(gem), Color.black, 1, 1, 1);
		shift+= 25;
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+shift, gem.getGemSlotString(), YELLOW, Color.black, 1, 1, 1);
		calcCoinContainer(Mideas.joueur1().bag().getBag(i).getSellPrice(), x-55, z+y+shift-5);
	}
	
	private static void drawPotion(int i, int x, int z) {
		Potion potion = (Potion)Mideas.joueur1().bag().getBag(i);
		int y = -75;
		int shift = 0;
		int xShift = TTF2.itemName.getWidth(potion.getStuffName());
		//Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, 10+y, bgColor);
		//Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, 11+y, borderColor);
		if(itemHoverTooltip.getX() != Display.getWidth()+x-15-xShift || itemHoverTooltip.getY() != Display.getHeight()+z-10+y) {
			itemHoverTooltip.update(Display.getWidth()+x-15-xShift, Display.getHeight()+z+y, 25+Math.abs(xShift), Math.abs(y)+15);
		}
		itemHoverTooltip.draw();
		y =  -65;
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, potion.getStuffName(), getItemNameColor(potion), Color.black, 1, 1, 1);
		shift+= 25;
		if(potion.getPotionHeal() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+shift, potion.getDoHealString(), Color.green, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(potion.getPotionMana() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+shift, potion.getDoManaString(), Color.green, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(Mideas.joueur1().getLevel() >= potion.getLevel()) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, potion.getLevelString(), Color.white, Color.black, 1, 1, 1);
		}
		else {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, potion.getLevelString(), Color.red, Color.black, 1, 1, 1);
		}
		calcCoinContainer(Mideas.joueur1().bag().getBag(i).getSellPrice(), x-55, z+y+shift-5);
	}
	
	private static void drawStuff(int i, int x, int z) {
		Color temp = null;
		Stuff item = (Stuff)Mideas.joueur1().bag().getBag(i);
		int xShift = 237;
		int shift = 45;
		xShift = Math.max(TTF2.itemName.getWidth(item.getStuffName()), TTF2.statsName.getWidth(item.getClassRequirements())+15);
		int y = -80-TTF2.statsName.getLineHeight()*getNumberStats(item);
		//Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, y, bgColor);
		//Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, y, borderColor);
		if(itemHoverTooltip.getX() != Display.getWidth()+x-15-xShift || itemHoverTooltip.getY() != Display.getHeight()+z-10+y) {
			itemHoverTooltip.update(Display.getWidth()+x-15-xShift, Display.getHeight()+z-10+y, 28+Math.abs(xShift), Math.abs(y)+15);
		}
		itemHoverTooltip.draw();
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, item.getStuffName(), getItemNameColor(item), Color.black, 1);
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+23, item.convStuffTypeToString(), Color.white, Color.black, 1);
		if(Mideas.joueur1().canWear(item)) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-20-TTF2.font4.getWidth(item.convWearToString()), Display.getHeight()+y+23+z, item.convWearToString(), temp, Color.black, 1);
		if(item.getArmor() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getArmorString(), Color.white, Color.black, 1);
			shift+= 20;
		}
		if(item.getStrength() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getStrengthString(), Color.white, Color.black, 1);
			shift+= 20;
		}
		if(item.getMana() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getManaString(), Color.white, Color.black, 1);
			shift+= 20;
		}
		if(item.getStamina() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getStaminaString(), Color.white, Color.black, 1);
			shift+= 20;
		}
		if(item.getCritical() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getCriticalString(), Color.white, Color.black, 1);
			shift+= 20;
		}
		
		//TODO: draw gem with a function in a loop
		
		if(item.getGemColor(0) != GemColor.NONE) {
			if(item.getEquippedGem(0) != null) {
				Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem(0).getId()), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, item.getEquippedGem(0).getGemStatsString(), Color.white, Color.black, 1);
			}
			else {
				Draw.drawQuad(item.getFreeSlotGemSprite1(), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, item.getGem1String(), Color.gray, Color.black, 1);
			}
			Draw.drawQuad(Sprites.cursor, -5000, -5000);
			shift+= 20;
		}
		if(item.getGemColor(1) != GemColor.NONE) {
			if(item.getEquippedGem(1) != null) {
				Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem(1).getId()), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, item.getEquippedGem(1).getGemStatsString(), Color.white, Color.black, 1);
			}
			else {
				Draw.drawQuad(item.getFreeSlotGemSprite2(), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, item.getGem2String(), Color.gray, Color.black, 1);
			}
			Draw.drawQuad(Sprites.cursor, -5000, -5000);
			shift+= 20;
		}
		if(item.getGemColor(2) != GemColor.NONE) {
			if(item.getEquippedGem(2) != null) {
				Draw.drawQuad(GemManager.getGemSprite(item.getEquippedGem(2).getId()), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, item.getEquippedGem(2).getGemStatsString(), Color.white, Color.black, 1);
			}
			else {
				Draw.drawQuad(item.getFreeSlotGemSprite3(), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, item.getGem3String(), Color.gray, Color.black, 1);
			}
			Draw.drawQuad(Sprites.cursor, -5000, -5000);
			shift+= 20;
		}
		if(item.getGemBonusType() != GemBonusType.NONE) {
			if(item.getGemBonusActivated()) {
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z-3, item.getSocketBonusString(), Color.green, Color.black, 1);
			}
			else {
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z-3, item.getSocketBonusString(), Color.gray, Color.black, 1);
			}
			shift+= 20;
		}
		if(item.canEquipTo(Mideas.joueur1().getClassType())) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		if(item.getClassRequirements() != null) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getClassRequirements(), temp, Color.black, 1);
			shift+= 20;
		}
		if(Mideas.joueur1().getLevel() >= item.getLevel()) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getLevelString(), Color.white, Color.black, 1);
		}
		else {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getLevelString(), Color.red, Color.black, 1);
		}
		calcCoinContainer(item.getSellPrice(), x-55, z+y+shift-5);
	}
	
	private static void drawWeapon(int i, int x, int z) {
		Color temp = null;
		Stuff item = (Stuff)Mideas.joueur1().bag().getBag(i);
		int xShift = 0;
		int shift = 45;
		int y = -75-TTF2.statsName.getLineHeight()*getNumberStats(item);
		xShift = Math.max(TTF2.font4.getWidth(item.convTypeToString()+item.convSlotToString())+10, Math.max(TTF2.itemName.getWidth(item.getStuffName()), TTF2.statsName.getWidth(item.getClassRequirements())+15));
		if(itemHoverTooltip.getX() != Display.getWidth()+x-15-xShift || itemHoverTooltip.getY() != Display.getHeight()+z-10+y) {
			itemHoverTooltip.update(Display.getWidth()+x-15-xShift, Display.getHeight()+z-10+y, 20+Math.abs(xShift), Math.abs(y)+15);
		}
		itemHoverTooltip.draw();
		//Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, y, bgColor);
		//Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, y, borderColor);
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, Mideas.joueur1().bag().getBag(i).getStuffName(), getItemNameColor(item), Color.black, 1, 1, 1);
		if(Mideas.joueur1().canWear(item)) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-TTF2.font4.getWidth(item.convTypeToString()), Display.getHeight()+y+25+z, item.convTypeToString(), temp, Color.black, 1, 1, 1);
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-xShift, Display.getHeight()+y+25+z, item.convSlotToString(), Color.white, Color.black, 1, 1, 1);
		if(item.getArmor() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getArmorString(), Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(item.getStrength() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getStrengthString(), Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(item.getMana() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getManaString(), Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(item.getStamina() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getStaminaString(), Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(item.getCritical() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getCriticalString(), Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(item.canEquipTo(Mideas.joueur1().getClassType())) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		if(item.getClassRequirements() != null) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getClassRequirements(), temp, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(Mideas.joueur1().getLevel() >= item.getLevel()) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, item.getLevelString(), Color.white, Color.black, 1, 1, 1);
		}
		else {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z,item.getLevelString(), Color.red, Color.black, 1, 1, 1);
		}
		calcCoinContainer(Mideas.joueur1().bag().getBag(i).getSellPrice(), x-55, z+y+shift-2);
	}
	
	public static int getNumberStats(Stuff stuff) {
		int i = 0;
		if(stuff.getArmor() > 0) {
			i++;
		}
		if(stuff.getCritical() > 0) {
			i++;
		}
		if(stuff.getMana() > 0) {
			i++;
		}
		if(stuff.getStamina() > 0) {
			i++;
		}
		if(stuff.getStrength() > 0) {
			i++;
		}
		if(stuff.getClassType().length < 10) {
			i++;
		}
		if(stuff.getGemColor(0) != GemColor.NONE)  {
			i+= 2; //bonus type + gem
		}
		if(stuff.getGemColor(1) != GemColor.NONE) {
			i++;
		}
		if(stuff.getGemColor(2) != GemColor.NONE) {
			i++;
		}
		return i;
	}
	
	public static Color getItemNameColor(Item item) {
		if(item.getQuality() == 0) {
			return Color.gray;
		}
		if(item.getQuality() == 1) {
			return Color.white;
		}
		if(item.getQuality() == 2) {
			return Color.green;
		}
		if(item.getQuality() == 3) {
			return BLUE;
		}
		if(item.getQuality() == 4) {
			return PURPLE;
		}
		if(item.getQuality() == 5) {
			return LEGENDARY;
		}
		return Color.white;
	}
	
	public static int getSlotItem(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(item != null && Mideas.joueur1().bag().getBag(i) != null && item.getId() == Mideas.joueur1().bag().getBag(i).getId()) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static void setBagOpen(int i, boolean we) {
		if(i < isBagOpen.length) {
			isBagOpen[i] = we;
		}
	}
	
	public static boolean getBagOpen(int i) {
		if(i < isBagOpen.length) {
			return isBagOpen[i];
		}
		return false;
	}
	
	public static void setBagchange(boolean we) {
		bagChange = we;
	}
	
	public static void setItemNumberOpen(int i) {
		iItemNumber = i;
	}
	
	
	public static boolean isHoverItemNumberFrame() {
		if(Mideas.mouseX() >= Display.getWidth()+xItemNumber && Mideas.mouseX() <= Display.getWidth()+xItemNumber+Sprites.itemnumber_frame.getImageWidth()+8 && Mideas.mouseY() >= Display.getHeight()+yItemNumber && Mideas.mouseY() <= Display.getHeight()+yItemNumber+Sprites.itemnumber_frame.getImageHeight()+4) {
			return true;
		}
		return false;
	}

	
	public static boolean calcCoinContainer(int cost, int x, int y) {
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		return true;
	}
	
	public static void updateBagFrameSize() {
		bagSize[0] = (int)(Sprites.back_bag.getImageHeight()*Mideas.getDisplayXFactor());
		if(Mideas.joueur1().bag().getEquippedBag(0) != null) {
			bagSize[1] = (int)(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(0).getId()).getImageHeight()*Mideas.getDisplayXFactor());
		}
		else {
			bagSize[1] = 0;
			isBagOpen[1] = false;
		}
		if(Mideas.joueur1().bag().getEquippedBag(1) != null) {
			bagSize[2] = (int)(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(1).getId()).getImageHeight()*Mideas.getDisplayXFactor());
		}
		else {
			bagSize[2] = 0;
			isBagOpen[2] = false;
		}
		if(Mideas.joueur1().bag().getEquippedBag(2) != null) {
			bagSize[3] = (int)(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(2).getId()).getImageHeight()*Mideas.getDisplayXFactor());
		}
		else {
			bagSize[3] = 0;
			isBagOpen[3] = false;
		}
		if(Mideas.joueur1().bag().getEquippedBag(3) != null) {
			bagSize[4] = (int)(ContainerManager.getBagsSprites().get(Mideas.joueur1().bag().getEquippedBag(3).getId()).getImageHeight()*Mideas.getDisplayXFactor());
		}
		else {
			bagSize[4] = 0;
			isBagOpen[4] = false;
		}
		bagChange = false;
	}
	
	public static boolean getContainerFrameSlotHover(int i) {
		return hoveredSlot == i;
	}
	
	public static void setHoverFalse() {
		hoveredSlot = -1;
	}
	
	public static int getLastSplit() {
		return lastSplit;
	}
	
	public static int getItemHover() {
		return iHover;
	}
	
	public static int getXItemHover() {
		return x_item;
	}
	
	public static int getYItemHover() {
		return y_item;
	}
}