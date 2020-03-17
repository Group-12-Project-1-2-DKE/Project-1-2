package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("green.jpeg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,800);
	}

	@Override
	public void render () {
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawWorld();
	}

	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img,0,0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
