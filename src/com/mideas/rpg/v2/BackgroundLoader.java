package com.mideas.rpg.v2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GLSync;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

abstract class BackgroundLoader {

	// CPU synchronization
	private final ReentrantLock lock = new ReentrantLock();
	// GPU synchronization
	protected GLSync fence;

	private Drawable drawable;

	private boolean running;

	protected ByteBuffer texture;
	protected int texID;
	
	protected int i;

	abstract Drawable getDrawable() throws LWJGLException;
	
	public BackgroundLoader(int i) {
		this.i = i;
	}

	void start() throws LWJGLException {
		// The shared context must be created on the main thread.
		this.drawable = getDrawable();
	}

	public void run() {
		try {
			// Make the shared context current in the worker thread
			BackgroundLoader.this.drawable.makeCurrent();
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}
		BackgroundLoader.this.lock.lock();
		BackgroundLoader.this.texID = glGenTextures();
		if(this.i == 0) {
			Sprites.sprite();
			Sprites.initBG();
		}
		else if(this.i == 1) {
			Sprites.sprite2();
		}	
		else if(this.i == 2) {
			Sprites.sprite8();
		}
		else if(this.i == 3) {
			Sprites.sprite9();
		}
		else if(this.i == 4) {
			Sprites.sprite10();
		}
		// OpenGL commands from different contexts may be executed in any order. So we need a way to synchronize
		final boolean useFences = GLContext.getCapabilities().OpenGL32;
		if ( useFences )
			BackgroundLoader.this.fence = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
		else
			glFlush(); // Best we can do without fences. This will force rendering on the main thread to happen after we upload the texture.
		BackgroundLoader.this.lock.unlock();
		while ( BackgroundLoader.this.running ) {
			// Create the "true" texture
			BackgroundLoader.this.lock.lock();
			if ( useFences )
				BackgroundLoader.this.fence = glFenceSync(GL_SYNC_GPU_COMMANDS_COMPLETE, 0);
			else
				glFlush();
			BackgroundLoader.this.lock.unlock();
		}
		BackgroundLoader.this.drawable.destroy();
	}
}