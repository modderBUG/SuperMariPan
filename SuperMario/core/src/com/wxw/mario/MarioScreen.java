package com.wxw.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wxw.mario.brick.Bricks;
import com.wxw.mario.brick.BricksType;
import com.wxw.mario.actor.Player;
import com.wxw.mario.brick.CoinBlock;
import com.wxw.mario.brick.GiftBricks;
import com.wxw.mario.scenery.Blocks;
import com.wxw.mario.texture.ResourceName;
import com.wxw.mario.texture.ResourcePosition;

public class MarioScreen implements Screen {

    final Mario game;
    private Viewport viewport;
    private Camera camera;
    final AssetManager manager;

    Group playerGroup   = new Group();
    Group enemyGroup    =  new Group();
    Stage playerStage   ;

    Group bricksGroup   = new Group();
    Stage bricksStage  ;

    Stage sceneryStage ;

    Music BGM ;

    public MarioScreen(final Mario gam, final AssetManager manager) {
        this.manager = manager;
        this.game = gam;
        BGM = manager.get(ResourceName.Stage01_BGM);
        BGM.setLooping(true);
        BGM.play();

        viewport = new FitViewport(800, 480);
        camera = viewport.getCamera();
        playerStage = new Stage(viewport);
        playerGroup.addActor(new Player(manager,ResourcePosition.PLAYER_MARIO));
        playerGroup.addActor(new Player(manager,ResourcePosition.PLAYER_MARIO,200f,100f));
        playerGroup.addActor(new Player(manager,ResourcePosition.PLAYER_MARIO,250f,100f));
        playerGroup.addActor(new Player(manager,ResourcePosition.PLAYER_MARIO,280f,100f));
        playerStage.addActor(playerGroup);

        sceneryStage = new Stage(viewport);
        bricksStage  = new Stage(viewport);

        for (int i = 0; i < 20; i++) {
            Bricks temp = new Bricks(manager,ResourcePosition.GroundBricks);
            temp.setPosition(i * 16 , 0);
            Bricks temp2 = new Bricks(manager,ResourcePosition.GroundBricks);
            temp2.setPosition(i * 16 , 16);
            bricksGroup.addActor(temp);
            bricksGroup.addActor(temp2);

        }
        for (int i = 20; i < 40; i++) {
            Bricks temp = new Bricks(manager,ResourcePosition.IceGroundBricks);
            temp.setFriction(0.01f);
            temp.setPosition(i * 16  , 0);
            Bricks temp2 = new Bricks(manager,ResourcePosition.IceGroundBricks);
            temp2.setFriction(0.01f);
            temp2.setPosition(i * 16 , 16);
            bricksGroup.addActor(temp);
            bricksGroup.addActor(temp2);
        }

        Bricks temp = new Bricks(manager, 300, 80, BricksType.BRICKS,ResourcePosition.Bricks,0,0.05f);
        Bricks temp1 = new Bricks(manager, 340, 120, BricksType.BRICKS,ResourcePosition.Bricks,0,0.05f);
        Bricks temp2 = new Bricks(manager, 360, 180, BricksType.BRICKS,ResourcePosition.Bricks,0,0.05f);


        Bricks temp3 = new GiftBricks(manager, 360, 100, BricksType.GIFT,ResourcePosition.Gift,2,0.05f);
        Bricks temp4 = new GiftBricks(manager, 360+16, 100, BricksType.REWARDED,ResourcePosition.Gift,2,0.05f);
        Bricks temp5 = new GiftBricks(manager, 360+16*2, 100, BricksType.GIFT,ResourcePosition.Gift,2,0.05f);

        addBrickGroup(bricksGroup,3,0,true,430f, 100f, BricksType.GIFT,ResourcePosition.Gift,2,0.05f);
        addCoinGroup(bricksGroup,8,0,true,430f, 116f, BricksType.COIN,ResourcePosition.Coin_01,2,0.05f);


        bricksGroup.addActor(temp);
        bricksGroup.addActor(temp1);
        bricksGroup.addActor(temp2);
        bricksGroup.addActor(temp3);
        bricksGroup.addActor(temp4);
        bricksGroup.addActor(temp5);
        bricksStage.addActor(bricksGroup);

        sceneryStage.addActor(new Blocks(manager,ResourcePosition.Mountain_01,100,32));
        sceneryStage.addActor(new Blocks(manager,ResourcePosition.Mountain_02,150,32));
        sceneryStage.addActor(new Blocks(manager,ResourcePosition.Mountain_01,250,32));

        sceneryStage.addActor(new Blocks(manager,ResourcePosition.CloudBricks_L,250,350));
        sceneryStage.addActor(new Blocks(manager,ResourcePosition.CloudBricks_M,266,350));
        sceneryStage.addActor(new Blocks(manager,ResourcePosition.CloudBricks_R,278,350));

        sceneryStage.addActor(new Blocks(manager,ResourcePosition.CloudBricks_L,350,380));
        sceneryStage.addActor(new Blocks(manager,ResourcePosition.CloudBricks_M,366,380));
        sceneryStage.addActor(new Blocks(manager,ResourcePosition.CloudBricks_R,378,380));
        sceneryStage.addActor(new Blocks(manager, ResourcePosition.Cloud,200,350));
        sceneryStage.addActor(new Blocks(manager, ResourcePosition.Cloud,400,250));

    }

    public void addBrickGroup(Group g,
                              int number,
                              int interval,
                              boolean  direction,
                              Float x,Float y,BricksType type, ResourcePosition rect,int hardness,Float friction){
        for (int i = 0; i < number; i++) {
            Bricks temp;
            if (direction)
                temp = new GiftBricks(manager, x+interval+16*i, y, type,rect,hardness,friction);
            else
                temp = new GiftBricks(manager, y+interval+16*i, y, type,rect,hardness,friction);
            g.addActor(temp);
        }
    }

    public void addCoinGroup(Group g,
                              int number,
                              int interval,
                              boolean  direction,
                              Float x,Float y,BricksType type, ResourcePosition rect,int hardness,Float friction){
        for (int i = 0; i < number; i++) {
            Bricks temp;
            if (direction)
                temp = new CoinBlock(manager, x+interval+16*i, y, type,rect,hardness,friction);
            else
                temp = new CoinBlock(manager, y+interval+16*i, y, type,rect,hardness,friction);
            g.addActor(temp);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.467f,0.894f,1f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();


        for (Actor actor:playerGroup.getChildren()) {
            Player player = (Player) actor;
            player.handle();
            player.crash(bricksGroup);
            game.font.draw(game.batch, "Score : "+player.getScore(), player.getX(), viewport.getWorldHeight()-50);
        }
        for (Actor player:playerGroup.getChildren()) {
            if (player.getX()>400 ){
                viewport.getCamera().position.set(player.getX(),camera.position.y,0);
                viewport.getCamera().update();
                break;
            }
        }


        sceneryStage.act();
        bricksStage.act();
        playerStage.act();


        sceneryStage.draw();
        playerStage .draw();
        bricksStage .draw();
        game.batch.end();

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
