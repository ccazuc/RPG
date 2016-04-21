package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DrawContainerHover {

	public static void draw() throws FileNotFoundException, SQLException {
		if(ContainerFrame.getItemHover() >= 0) {
			ContainerFrame.drawHoverBag(ContainerFrame.getItemHover(), ContainerFrame.getXItemHover(), ContainerFrame.getYItemHover());
		}
	}
}
