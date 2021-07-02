package com.wxw.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wxw.mario.Mario;
import com.wxw.mario.entity.Bricks;
import com.wxw.mario.entity.Player;
import com.wxw.mario.other.Drop;

public class MarioScreen implements Screen {

    final Mario game;
    final OrthographicCamera camera;
    final AssetManager manager;

    Texture playerTexture;     //= new Texture(Gdx.files.internal("mario/PlayerAtlas.png"));
    Texture armyTexture;       //= new Texture(Gdx.files.internal("mario/ArmyAtlas.png"));
    Texture sceneryTexture;    //= new Texture(Gdx.files.internal("mario/ScanAtlas.png"));

    TextureRegion playerTextureRegion;  // = new TextureRegion(playerTexture,79,1,356,49);
    TextureRegion sceneryTextureRegion;
    TextureRegion iceTextureRegion;

    Group playerGroup;
    Player playerA;
    Stage BricksStage = new Stage();
    Stage BricksStage2 = new Stage();


    public MarioScreen(final Mario gam, final AssetManager manager,final OrthographicCamera camera0) {
        this.manager = manager;
        game = gam;

        playerTexture   = manager.get(ResourceName.PLAYER);
        armyTexture     = manager.get(ResourceName.ENEMY);
        sceneryTexture  = manager.get(ResourceName.SCAN);

        playerTextureRegion = new TextureRegion(playerTexture, 79, 1, 356, 49);
        sceneryTextureRegion = new TextureRegion(sceneryTexture, 96, 24, 16, 16);

        //cloudTextureRegion = new TextureRegion(sceneryTexture, 48, 144, 16, 16);
        iceTextureRegion = new TextureRegion(sceneryTexture, 64, 152, 16, 16);

        playerA = new Player(playerTextureRegion);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 40, 50);

        this.camera = camera0;
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();



        for (int i = 0; i < 20; i++) {
            Bricks temp = new Bricks(sceneryTextureRegion);
            temp.setPosition(i * 16 + 1, 0);
            Bricks temp2 = new Bricks(sceneryTextureRegion);
            temp2.setPosition(i * 16 + 1, 17);
            BricksStage.addActor(temp);
            BricksStage.addActor(temp2);
        }
        for (int i = 20; i < 40; i++) {
            Bricks temp = new Bricks(iceTextureRegion);
            temp.setFriction(0.01f);
            temp.setPosition(i * 16 + 1, 0);
            Bricks temp2 = new Bricks(iceTextureRegion);
            temp2.setFriction(0.01f);
            temp2.setPosition(i * 16 + 1, 17);
            BricksStage.addActor(temp);
            BricksStage.addActor(temp2);
        }



        Bricks temp = new Bricks(sceneryTextureRegion, 300, 80);
        Bricks temp1 = new Bricks(sceneryTextureRegion, 340, 120);
        Bricks temp2 = new Bricks(sceneryTextureRegion, 360, 180);

        BricksStage2.addActor(temp);
        BricksStage2.addActor(temp1);
        BricksStage2.addActor(temp2);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        game.batch.begin();

        playerA.handle();
        playerA.crash(BricksStage);
        playerA.crash(BricksStage2);


        playerA.draw(game.batch, 50);
        BricksStage.draw();
        BricksStage2.draw();

        game.batch.end();

    }


    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height / width;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
