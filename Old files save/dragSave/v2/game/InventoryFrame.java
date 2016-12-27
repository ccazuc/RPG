package com.mideas.rpg.v2.game;

public class InventoryFrame {

	/*public static void frame() {
		Draw.drawQuad(Sprites.character_frame, Display.getWidth()/2-300, Display.getHeight()/2-380);
		float x = Mouse.getX();
		float y = Display.getHeight()-Mouse.getY();
		if(x >= Display.getWidth()/2+112 && x <= Display.getWidth()/2+132 && y >= Display.getHeight()/2-360 && y <= Display.getHeight()/2-342) {
			Draw.drawQuad(Sprites.close_hover, Display.getWidth()/2+112, Display.getHeight()/2-360);
			while(Mouse.next()) {
				if(Mouse.getEventButtonState()) {
					if(x >= Display.getWidth()/2+112 && x <= Display.getWidth()/2+132 && y >= Display.getHeight()/2-360 && y <= Display.getHeight()/2-342) {
						Mideas.key = true;
					}
				}
			}
		}
		if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2-290 && y <= Display.getHeight()/2-242) { //left
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2-290);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2-242 && y <= Display.getHeight()/2-194) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2-242);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2-194 && y <= Display.getHeight()/2-146) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2-194);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2-144 && y <= Display.getHeight()/2-98) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2-144);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2-93 && y <= Display.getHeight()/2-50) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2-93);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2-45 && y <= Display.getHeight()/2-2) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2-45);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2+5 && y <= Display.getHeight()/2+46) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2+5);
		}
		else if(x >= Display.getWidth()/2-283 && x <= Display.getWidth()/2-235 && y >= Display.getHeight()/2+56 && y <= Display.getHeight()/2+94) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-283, Display.getHeight()/2+56);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2-290 && y <= Display.getHeight()/2-242) { //right
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2-290);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2-242 && y <= Display.getHeight()/2-194) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2-242);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2-194 && y <= Display.getHeight()/2-146) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2-194);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2-144 && y <= Display.getHeight()/2-98) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2-144);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2-93 && y <= Display.getHeight()/2-50) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2-93);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2-45 && y <= Display.getHeight()/2-2) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2-45);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2+5 && y <= Display.getHeight()/2+46) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2+5);
		}
		else if(x >= Display.getWidth()/2+78 && x <= Display.getWidth()/2+126 && y >= Display.getHeight()/2+56&& y <= Display.getHeight()/2+94) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2+78, Display.getHeight()/2+56);
		}
		else if(x >= Display.getWidth()/2-157 && x <= Display.getWidth()/2-109 && y >= Display.getHeight()/2+82 && y <= Display.getHeight()/2+130) { //weapons
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-157, Display.getHeight()/2+82);
		}
		else if(x >= Display.getWidth()/2-103 && x <= Display.getWidth()/2-50 && y >= Display.getHeight()/2+82 && y <= Display.getHeight()/2+130) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-103, Display.getHeight()/2+82);
		}
		else if(x >= Display.getWidth()/2-50 && x <= Display.getWidth()/2-2 && y >= Display.getHeight()/2+82 && y <= Display.getHeight()/2+130) {
			Draw.drawQuad(Sprites.stuff_hover, Display.getWidth()/2-50 , Display.getHeight()/2+82);
		}
	}
		drawBorder(Mideas.bag.bag17, Sprites.bag_border17, x+, y+ySecondBag);
		drawBorder(Mideas.bag.bag18, Sprites.bag_border18, x+xShift, y+ySecondBag);
		drawBorder(Mideas.bag.bag19, Sprites.bag_border19, x+2*xShift, y+ySecondBag);
		drawBorder(Mideas.bag.bag20, Sprites.bag_border20, x+3*xShift, y+ySecondBag);
		drawBorder(Mideas.bag.bag21, Sprites.bag_border21, x, y+yShift+ySecondBag);
		drawBorder(Mideas.bag.bag22, Sprites.bag_border22, x+xShift, y+yShift+ySecondBag);
		drawBorder(Mideas.bag.bag23, Sprites.bag_border23, x+2*xShift, y+yShift+ySecondBag);
		drawBorder(Mideas.bag.bag24, Sprites.bag_border24, x+3*xShift, y+yShift+ySecondBag);
		drawBorder(Mideas.bag.bag25, Sprites.bag_border25, x, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag26, Sprites.bag_border26, x+xShift, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag27, Sprites.bag_border27, x+2*xShift, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag28, Sprites.bag_border28, x+3*xShift, y+2*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag29, Sprites.bag_border29, x, y+3*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag30, Sprites.bag_border30, x+xShift, y+3*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag31, Sprites.bag_border31, x+2*xShift, y+3*yShift+ySecondBag);
		drawBorder(Mideas.bag.bag32, Sprites.bag_border32, x+3*xShift, y+3*yShift+ySecondBag);
	
	public static void bags() {
		Draw.drawQuad(Sprites.back_bag, Display.getWidth()/2+380, Display.getHeight()-250);
		Draw.drawQuad(Sprites.back_bag2, Display.getWidth()/2+380, Display.getHeight()-500);
	}*/
}
