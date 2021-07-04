package com.wxw.mario.texture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxw.mario.ResourceName;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TexturePositions extends Actor {

    final AssetManager manager;

    public Bounds Reward25TextureRegion    = new Bounds(64f, 24f, 16f, 16f);
    public Bounds giftTextureRegion        = new Bounds(64f, 24f, 16f, 16f);
    public  Bounds rewardTextureRegion      = new Bounds(64f, 24f, 16f, 16f);


    public TexturePositions(final AssetManager manager){
        this.manager =manager;
    }

    public TextureRegion getGiftTextureRegion() {
       Texture texture =  manager.get(ResourceName.SCAN);
       TextureRegion text = new TextureRegion(texture,giftTextureRegion.getX(),giftTextureRegion.getY(),giftTextureRegion.getWidth(),giftTextureRegion.getHeight());
       return text;
    }

    public TextureRegion getSCANRegion(Bounds bounds) {
        Texture texture =  manager.get(ResourceName.SCAN);
        TextureRegion text = new TextureRegion(texture,bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
        return text;
    }

    public TextureRegion getEnemyRegion(Bounds bounds) {
        Texture texture =  manager.get(ResourceName.ENEMY);
        TextureRegion text = new TextureRegion(texture,bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
        return text;
    }

    public TextureRegion getPlayerRegion(Bounds bounds) {
        Texture texture =  manager.get(ResourceName.PLAYER);
        TextureRegion text = new TextureRegion(texture,bounds.getX(),bounds.getY(),bounds.getWidth(),bounds.getHeight());
        return text;
    }
}
