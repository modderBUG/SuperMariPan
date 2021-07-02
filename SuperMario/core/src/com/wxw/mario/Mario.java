package com.wxw.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mario extends Game {

	SpriteBatch batch;
	BitmapFont font;

	public void create() {
//		Gdx.graphics.setContinuousRendering(false);
//		Gdx.graphics.requestRendering();

		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MarioMenu(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
