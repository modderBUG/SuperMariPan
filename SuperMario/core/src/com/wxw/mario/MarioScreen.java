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
import com.wxw.mario.entity.BricksType;
import com.wxw.mario.entity.Player;
import com.wxw.mario.other.Drop;
import com.wxw.mario.texture.TexturePositions;

public class MarioScreen implements Screen {

    final Mario game;
    final OrthographicCamera camera;
    final AssetManager manager;
    final TexturePositions texturePositions ;

    Texture playerTexture;     //= new Texture(Gdx.files.internal("mario/PlayerAtlas.png"));
    Texture armyTexture;       //= new Texture(Gdx.files.internal("mario/ArmyAtlas.png"));
    Texture sceneryTexture;    //= new Texture(Gdx.files.internal("mario/ScanAtlas.png"));

    TextureRegion playerTextureRegion;  // = new TextureRegion(playerTexture,79,1,356,49);

    TextureRegion[] bricksTextureRegion = new TextureRegion[5];


    TextureRegion giftTextureRegion;
    TextureRegion rewardTextureRegion;
    TextureRegion reward25TextureRegion;

    TextureRegion[] mountainTextureRegion = new TextureRegion[2];


    TextureRegion cloudTextureRegion ;
    TextureRegion[] cloudBrickTextureRegion = new TextureRegion[3];
    TextureRegion[] coinBrickTextureRegion = new TextureRegion[4];

    TextureRegion[] glassBrickTextureRegion= new TextureRegion[2];


    Group playerGroup;

    Player playerA;

    Group bricksGroup = new Group();

    Stage bricksStage = new Stage();

    Stage sceneryStage = new Stage();



    public MarioScreen(final Mario gam, final AssetManager manager,final OrthographicCamera camera0) {
        this.manager = manager;
        texturePositions = new TexturePositions(manager);

        game = gam;

        playerTexture   = manager.get(ResourceName.PLAYER);
        armyTexture     = manager.get(ResourceName.ENEMY);
        sceneryTexture  = manager.get(ResourceName.SCAN);

        playerTextureRegion = new TextureRegion(playerTexture, 79, 0, 358, 49);


        bricksTextureRegion[0] = new TextureRegion(sceneryTexture, 96, 24, 16, 16);
        bricksTextureRegion[1] = new TextureRegion(sceneryTexture, 64, 152, 16, 16);
        bricksTextureRegion[2] = new TextureRegion(sceneryTexture, 0, 56, 16, 16);

        reward25TextureRegion = new TextureRegion(sceneryTexture, 64, 24, 16, 16);
        giftTextureRegion = new TextureRegion(sceneryTexture, 80, 24, 16, 16);
        rewardTextureRegion = new TextureRegion(sceneryTexture, 80, 40, 16, 16);


        mountainTextureRegion[0] = new TextureRegion(sceneryTexture, 128, 118, 48, 18);
        mountainTextureRegion[1] = new TextureRegion(sceneryTexture, 128, 6, 48, 18);

        cloudTextureRegion = new TextureRegion(sceneryTexture, 212, 118, 32, 24);

        cloudBrickTextureRegion[0] = new TextureRegion(sceneryTexture, 64, 56, 16, 16);
        cloudBrickTextureRegion[1] = new TextureRegion(sceneryTexture, 48, 32, 16, 16);
        cloudBrickTextureRegion[2] = new TextureRegion(sceneryTexture, 48, 48, 16, 16);

        coinBrickTextureRegion[0] = new TextureRegion(sceneryTexture, 48, 16, 16, 16);
        coinBrickTextureRegion[1] = new TextureRegion(sceneryTexture, 48, 64, 16, 16);
        coinBrickTextureRegion[2] = new TextureRegion(sceneryTexture, 48, 128, 16, 16);
        coinBrickTextureRegion[3] = new TextureRegion(sceneryTexture, 48, 176, 16, 16);

        glassBrickTextureRegion[0] = new TextureRegion(sceneryTexture, 112, 192, 24, 16);
        glassBrickTextureRegion[1] = new TextureRegion(sceneryTexture, 112, 80, 24, 16);


        playerA = new Player(playerTextureRegion,texturePositions);

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
            Bricks temp = new Bricks(bricksTextureRegion[2]);
            temp.setPosition(i * 16 , 0);
            Bricks temp2 = new Bricks(bricksTextureRegion[2]);
            temp2.setPosition(i * 16 , 16);
            bricksGroup.addActor(temp);
            bricksGroup.addActor(temp2);

        }
        for (int i = 20; i < 40; i++) {
            Bricks temp = new Bricks(bricksTextureRegion[1]);
            temp.setFriction(0.01f);
            temp.setPosition(i * 16  , 0);
            Bricks temp2 = new Bricks(bricksTextureRegion[1]);
            temp2.setFriction(0.01f);
            temp2.setPosition(i * 16 , 16);
            bricksGroup.addActor(temp);
            bricksGroup.addActor(temp2);
        }



        Bricks temp = new Bricks(bricksTextureRegion[0], 300, 80, BricksType.BRICKS,0,0.05f);
        Bricks temp1 = new Bricks(bricksTextureRegion[0], 340, 120, BricksType.BRICKS,0,0.05f);
        Bricks temp2 = new Bricks(bricksTextureRegion[0], 360, 180, BricksType.BRICKS,0,0.05f);


        Bricks temp3 = new Bricks(giftTextureRegion, 360, 100, BricksType.GIFT,2,0.05f);
        Bricks temp4 = new Bricks(giftTextureRegion, 360+16, 100, BricksType.REWARDED,2,0.05f);
        Bricks temp5 = new Bricks(giftTextureRegion, 360+16*2, 100, BricksType.GIFT,2,0.05f);

        bricksGroup.addActor(temp);
        bricksGroup.addActor(temp1);
        bricksGroup.addActor(temp2);
        bricksGroup.addActor(temp3);
        bricksGroup.addActor(temp4);
        bricksGroup.addActor(temp5);


        bricksStage.addActor(bricksGroup);

        sceneryStage.addActor(new Bricks(mountainTextureRegion[0],100,32));
        sceneryStage.addActor(new Bricks(mountainTextureRegion[1],150,32));
        sceneryStage.addActor(new Bricks(mountainTextureRegion[0],250,32));

        sceneryStage.addActor(new Bricks(cloudBrickTextureRegion[0],250,350));
        sceneryStage.addActor(new Bricks(cloudBrickTextureRegion[1],266,350));
        sceneryStage.addActor(new Bricks(cloudBrickTextureRegion[2],278,350));

        sceneryStage.addActor(new Bricks(cloudBrickTextureRegion[0],300,380));
        sceneryStage.addActor(new Bricks(cloudBrickTextureRegion[1],316,380));
        sceneryStage.addActor(new Bricks(cloudBrickTextureRegion[2],332,380));


        sceneryStage.addActor(new Bricks(cloudTextureRegion,200,350));

        sceneryStage.addActor(new Bricks(cloudTextureRegion,400,250));


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0.467f,0.894f,1f, 1);
        game.batch.begin();

        playerA.handle();

        playerA.crash(bricksGroup);

        sceneryStage.draw();
        playerA.draw(game.batch, 50);
        playerA.drawAnimation(game.batch);
        bricksStage.draw();



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
        manager.dispose();
    }
}
