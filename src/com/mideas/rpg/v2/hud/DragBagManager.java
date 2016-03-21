package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;

public class DragBagManager {

	private static boolean[] hoverBag = new boolean[4];
	
	public static void openBag() {
		Arrays.fill(hoverBag, false);
		int i = 0;
		while(i < hoverBag.length) {
			bagHover(i, -272-46*i);
			i++;
		}
		if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
			if(!Mouse.getEventButtonState()) {
				i = 0;
				if(Mideas.mouseX() >= Display.getWidth()-272+46 && Mideas.mouseX() <= Display.getWidth()-272+46+46 && Mideas.mouseY() >= Display.getHeight()-40 && Mideas.mouseY() <= Display.getHeight()-3) {
					ContainerFrame.setBagOpen(0, !ContainerFrame.getBagOpen(0));
				}
				while(i < hoverBag.length) {
					if(hoverBag[i]) {
						ContainerFrame.setBagOpen(i+1, !ContainerFrame.getBagOpen(i+1));
						break;
					}
					i++;
				}
			}
		}
	}
	
	private static void bagHover(int i, int x) {
		if(Mideas.mouseX() >= Display.getWidth()+x && Mideas.mouseX() <= Display.getWidth()+x+37 && Mideas.mouseY() >= Display.getHeight()-40 && Mideas.mouseY() <= Display.getHeight()-3) {
			hoverBag[i] = true;
		}
	}
}
