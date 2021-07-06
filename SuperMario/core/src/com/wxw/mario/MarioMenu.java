package com.wxw.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wxw.mario.texture.ResourceName;

public class MarioMenu implements Screen {

    final Mario game;
    final OrthographicCamera camera;
    final AssetManager manager = new AssetManager();

    public MarioMenu(final Mario gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 320, 160);

        manager.load(ResourceName.ENEMY, Texture.class);
        manager.load(ResourceName.PLAYER,Texture.class);
        manager.load(ResourceName.SCAN,Texture.class);

        manager.load(ResourceName.Stage01_BGM, Music.class);
        manager.load(ResourceName.Jump, Music.class);
        manager.load(ResourceName.Coin, Sound.class);
        manager.load(ResourceName.Break_Bricks, Sound.class);
        manager.load(ResourceName.Crashed_Bricks, Sound.class);



        manager.finishLoading();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to SuperMario!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MarioScreen(game,manager));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}