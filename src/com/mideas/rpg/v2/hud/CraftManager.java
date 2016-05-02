package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Draw;

public class CraftManager {
	
	private static int y_elevator;
	private static int y_elevator_base;
	private static int y_elevator_last;
	private static boolean elevator_selected;
	//private static boolean[] expand = new boolean[30];

	public static void draw() {
		Draw.drawQuad(Sprites.craft_frame, Display.getWidth()/2-480, Display.getHeight()/2-350);
		Draw.drawQuad(Sprites.elevator_button, Display.getWidth()/2-90, Display.getHeight()/2-200+y_elevator);
	}
	
	public static boolean mouseEvent() {
		buttonMove(-5, -90, -222);
		buttonMove(5, -90, -83);
		elevatorEvent();
		mouseScroll();
		return false;
	}
	
	private static void elevatorEvent() {
		if(Mideas.mouseX() >= Display.getWidth()/2-90 && Mideas.mouseX() <= Display.getWidth()/2-67 && Mideas.mouseY() >= Display.getHeight()/2-200+y_elevator && Mideas.mouseY() <= Display.getHeight()/2-177+y_elevator) {
			if(Mouse.getEventButton() == 0) {
				if(Mouse.getEventButtonState()) {
					elevator_selected = true;
					y_elevator_base = Mideas.mouseY();
				}
			}
		}
		if(elevator_selected) {
			if(Mideas.mouseY() >= Display.getHeight()/2-190 && Mideas.mouseY() <= Display.getHeight()/2-100) {
				y_elevator = Mideas.mouseY()-y_elevator_base+y_elevator_last;
			}
			if(!Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
				elevator_selected = false;
				y_elevator_last = y_elevator;
			}
		}
	}
	
	private static void buttonMove(int button_shift, int mouse_x, int mouse_y) {
		if(Mouse.getEventButton() == 0 && !Mouse.getEventButtonState()) {
			if(Mideas.mouseX() >= Display.getWidth()/2+mouse_x && Mideas.mouseX() <= Display.getWidth()/2+mouse_x+23 && Mideas.mouseY() >= Display.getHeight()/2+mouse_y && Mideas.mouseY() <= Display.getHeight()/2+mouse_y+22) {
				if(y_elevator+button_shift >= 0 && y_elevator+button_shift <= 90) {
					y_elevator+= button_shift;
					y_elevator_last = y_elevator;
				}
				else if(y_elevator+button_shift < 0) {
					y_elevator = 0;
					y_elevator_last = y_elevator;
				}
				else if(y_elevator+button_shift > 90) {
					y_elevator = 90;
					y_elevator_last = y_elevator;
				}
			}
		}
	}
	
	private static void mouseScroll() {
		if(Mideas.mouseX() >= Display.getWidth()/2-462 && Mideas.mouseX() <= Display.getWidth()/2-60 && Mideas.mouseY() >= Display.getHeight()/2-226 && Mideas.mouseY() <= Display.getHeight()/2-61) {
			int mouseWheel = Mouse.getDWheel();
			if(mouseWheel != 0 && y_elevator-mouseWheel/12 >= 0 && y_elevator-mouseWheel/12 <= 90) {
				y_elevator-= mouseWheel/12;
				y_elevator_last = y_elevator;
			}
		}
	}
	
	/*private static boolean checkNumberItemss(int number, Stuff item, int itemNumber) {
		if(itemNumber > number) {
			int i = 0;
			while(i < Mideas.bag().getBag().length) {
				if(Mideas.bag().getBag(i).equals(item)) {
					Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i), ContainerFrame.getSlotItem(item)))-itemNumber);
					return true;
				}
				i++;
			}
		}
		return false;
	}*/
}
