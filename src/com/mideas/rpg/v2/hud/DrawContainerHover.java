package com.mideas.rpg.v2.hud;

public class DrawContainerHover {

	public static void draw() {
		if(ContainerFrame.getItemHover() >= 0) {
			ContainerFrame.drawHoverBag(ContainerFrame.getItemHover(), ContainerFrame.getXItemHover(), ContainerFrame.getYItemHover());
		}
	}
}
