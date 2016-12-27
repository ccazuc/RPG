package com.mideas.rpg.v2;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public final class Window {
	
	private static boolean windowedFullscreen;
	private static boolean oldDecorated;
	private static boolean oldResizable;
	private static boolean decorated;
	private static boolean resizable;
	private static int oldHeight;
	private static int oldWidth;
	
	public static int MAX_FPS;
	
	public final static void init(final int width, final int height, final String title) throws LWJGLException, IOException {
		createWindow(title, width, height);
		updateGLContext();
	}
	
	private final static void createWindow(final String title, final int width, final int height) throws LWJGLException, IOException {
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.setResizable(true);
		Display.setTitle(title);
		Display.create();
	}
	
	public final static void updateGLContext() {
		 GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glShadeModel(GL11.GL_SMOOTH);        
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glDisable(GL11.GL_LIGHTING);                    
	  
	        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
	        GL11.glClearDepth(1);                                       
	  
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	  
	        GL11.glViewport(0,0,getWidth(),getHeight());
	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	  
	        GL11.glMatrixMode(GL11.GL_PROJECTION);
	        GL11.glLoadIdentity();
	        GL11.glOrtho(0, getWidth(), getHeight(), 0, 1, -1);
	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        GL11.glLoadIdentity();
	}
	
	private final static void wasResized() {
		updateGLContext();
	}
	
	public final static void setMaxFPS() throws LWJGLException {
		final DisplayMode[] modes = Display.getAvailableDisplayModes();
		int i = modes.length;
		while(--i > -1) {
			if(modes[i].getFrequency() > MAX_FPS) {
				MAX_FPS = modes[i].getFrequency();
			}
		}
	}
	
	public final static void setResizable(final boolean resizable) {
		Display.setResizable(resizable);
		Window.resizable = resizable;
	}
	
	public final static void setSize(final int width, final int height) throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(width, height));
		wasResized();
	}
	
	public final static void setWidth(final int width) throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(width, getHeight()));
		wasResized();
	}
	
	public final static void setHeight(final int height) throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(getWidth(), height));
		wasResized();
	}
	
	public final static void setWindowedFullscreen(final boolean fullscreen) throws LWJGLException {
		if(windowedFullscreen) {
			setDecorated(oldDecorated);
			setResizable(oldResizable);
			setSize(oldWidth, oldHeight);
			wasResized();
			windowedFullscreen = false;
		}
		else {
			oldWidth = getWidth();
			oldHeight = getHeight();
			oldDecorated = decorated;
			oldResizable = resizable;
			int bestWidth = 0;
			int bestHeight = 0;
			final DisplayMode[] modes = Display.getAvailableDisplayModes();
			int i = modes.length;
			while(--i > -1) {
				DisplayMode mode = modes[i];
				if(mode.getHeight() >= bestHeight && mode.getWidth() >= bestWidth) {
					bestHeight = mode.getHeight();
					bestWidth = mode.getWidth();
				}
			}
			setDecorated(false);
			setResizable(false);
			setSize(bestWidth, bestHeight);
			wasResized();
			windowedFullscreen = true;
		}
	}
	
	public final static void setFullscreen(final boolean fullscreen) throws LWJGLException {
		if(fullscreen && !Display.isFullscreen()) {
			oldWidth = getWidth();
			oldHeight = getHeight();
			int bestWidth = 0;
			int bestHeight = 0;
			int bestFrequency = 0;
			int bestBpp = 0;
			DisplayMode displayMode = null;
			final DisplayMode[] modes = Display.getAvailableDisplayModes();
			int i = modes.length;
			while(--i > -1) {
				DisplayMode mode = modes[i];
				if(mode.getHeight() >= bestHeight || mode.getWidth() >= bestWidth || mode.getFrequency() >= bestFrequency || mode.getBitsPerPixel() >= bestBpp) {
					bestHeight = mode.getHeight();
					bestWidth = mode.getWidth();
					bestFrequency = mode.getFrequency();
					bestBpp = mode.getBitsPerPixel();
					displayMode = mode;
				}
			}
			Display.setDisplayModeAndFullscreen(displayMode);
			wasResized();
		}
		else if(!fullscreen && Display.isFullscreen()) {
			setSize(oldWidth, oldHeight);
		}
	}
	
	public final static void checkResize() {
		if(Display.wasResized()) {
			wasResized();
		}
	}
	
	public final static void setDecorated(final boolean decorated) {
		if(decorated) {
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		}
		else {
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		}
		Window.decorated = decorated;
	}
	
	public final static void enableVSync() {
		Display.setVSyncEnabled(true);
	}
	
	public final static void disableVSync() {
		Display.setVSyncEnabled(false);
	}
	
	public final static void updateDisplay() {
		Display.update();
	}
	
	public final static void clearRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public final static void sync() {
		Display.sync(MAX_FPS);
	}
	
	public final static boolean isWindowedFullscreen() {
		return windowedFullscreen;
	}
	
	public final static boolean isFullscreen() {
		return Display.isFullscreen();
	}
	
	public final static int getWidth() {
        return Display.getWidth();
	}
	
	public final static int getHeight() {
        return Display.getHeight();
	}
	
}
