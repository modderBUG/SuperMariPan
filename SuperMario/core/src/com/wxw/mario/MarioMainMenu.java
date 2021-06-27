package com.wxw.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wxw.mario.Mario;
import com.wxw.mario.entity.Bricks;
import com.wxw.mario.entity.Player;
import com.wxw.mario.other.Drop;

public class MarioMainMenu implements Screen {

    final Mario game;
    OrthographicCamera camera;

    Texture playerTexture = new Texture(Gdx.files.internal("mario/PlayerAtlas.png"));
    Texture armyTexture = new Texture(Gdx.files.internal("mario/ArmyAtlas.png"));
    Texture sceneryTexture = new Texture(Gdx.files.internal("mario/ScanAtlas.png"));

    TextureRegion playerTextureRegion = new TextureRegion(playerTexture,79,1,356,49);
    Player playerA = new Player(playerTextureRegion);

    TextureRegion sceneryTextureRegion = new TextureRegion(sceneryTexture,96,24,16,16);


    Stage BricksStage = new Stage();

    Stage BricksStage2 = new Stage();



//    TextureRegion playerTextureRegion = new TextureRegion("ArmyAtlas.png");

    public MarioMainMenu(final Mario gam) {

        game = gam;
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 300, 200);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 400, 500);


        for (int i = 0; i < 1000; i++) {
            Bricks temp = new Bricks(sceneryTextureRegion);
            temp.setPosition(i*16+1,40);

            Bricks temp2 = new Bricks(sceneryTextureRegion);
            temp2.setPosition(i*16+1,20);
            BricksStage.addActor(temp);
            BricksStage.addActor(temp2);
        }

        Bricks temp = new Bricks(sceneryTextureRegion,300,60);
        Bricks temp1 = new Bricks(sceneryTextureRegion,340,120);
        Bricks temp2 = new Bricks(sceneryTextureRegion,360,180);

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

        playerA.drop();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                playerA.runRight();
            else
            playerA.moveRight();



        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                playerA.runLeft();
            else
            playerA.moveLeft();


        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                playerA.runJump();
            else
                playerA.jump();
        }

        Array<Actor> actors = BricksStage2.getActors();
        for (int i = 0; i < actors.size; i++) {
            playerA.crash(actors.get(i).getX(),actors.get(i).getY(),actors.get(i).getWidth(),actors.get(i).getHeight());
        }

        Array<Actor> actors2 = BricksStage.getActors();
        for (int i = 0; i < actors2.size; i++) {
            playerA.crash(actors2.get(i).getX(),actors2.get(i).getY(),actors2.get(i).getWidth(),actors2.get(i).getHeight());
        }


        playerA.draw(game.batch, 100);
        BricksStage.draw();
        BricksStage2.draw();


        game.batch.end();

    }



    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height/width;
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
