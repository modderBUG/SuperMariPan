package com.wxw.mario.texture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.wxw.mario.ResourceName;

import java.lang.reflect.Array;
import java.util.Arrays;

@Deprecated
public class TexturePositions extends Actor {

    final AssetManager manager;

    public Rectangle Reward25TextureRegion = new Rectangle(64, 24, 16, 16);
    public Rectangle giftTextureRegion = new Rectangle(80, 24, 16, 16);
    public Rectangle rewardTextureRegion = new Rectangle(80, 40, 16, 16);
    public Rectangle emptyTextureRegion = new Rectangle(64, 0, 8, 16);

    private Texture SceneryTexture ;
    private Texture EnemyTexture ;
    private Texture PlayerTexture ;


    public TexturePositions(final AssetManager manager) {
        this.manager = manager;
        SceneryTexture = manager.get(ResourceName.SCAN);
        EnemyTexture = manager.get(ResourceName.ENEMY);
        PlayerTexture = manager.get(ResourceName.PLAYER);
    }

    public TextureRegion getGiftTextureRegion() {
        Texture texture = manager.get(ResourceName.SCAN);
        TextureRegion text = new TextureRegion(texture, giftTextureRegion.getX(), giftTextureRegion.getY(), giftTextureRegion.getWidth(), giftTextureRegion.getHeight());
        return text;
    }

    public TextureRegion getSCANRegion(Rectangle bounds) {

        TextureRegion a = new TextureRegion();




        return new TextureRegion(SceneryTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public TextureRegion getEnemyRegion(Rectangle bounds) {
        Texture texture = manager.get(ResourceName.ENEMY);
        System.out.println("" + bounds.getX() + bounds.getY() + bounds.getWidth() + bounds.getHeight());
        return new TextureRegion(texture, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public TextureRegion getPlayerRegion(Rectangle bounds) {
        Texture texture = manager.get(ResourceName.PLAYER);
        return new TextureRegion(texture, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }
}
