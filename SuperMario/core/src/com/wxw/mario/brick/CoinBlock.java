package com.wxw.mario.brick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxw.mario.scenery.Blocks;
import com.wxw.mario.texture.ResourceName;
import com.wxw.mario.texture.ResourcePosition;

public class CoinBlock extends Bricks {

    Animation coinAnimation;
    float stateTime;

    public CoinBlock(AssetManager manager, float x, float y, BricksType bricksType, ResourcePosition rect, int hardness, Float friction) {
        super(manager, x, y, bricksType, rect, hardness, friction);

    }

    public CoinBlock(AssetManager manager) {
        super(manager);
    }

    public CoinBlock(AssetManager manager, ResourcePosition rect) {
        super(manager, rect);

    }

    public CoinBlock(AssetManager manager, float x, float y, BricksType bricksType, int hardness, Float friction) {
        super(manager, x, y, bricksType, hardness, friction);

    }

    @Override
    public void createAnimation(){
        TextureRegion[] giftRegion = new TextureRegion[4];
        giftRegion[0] = getResource(ResourcePosition.Coin_01);
        giftRegion[1] = getResource(ResourcePosition.Coin_02);
        giftRegion[2] = getResource(ResourcePosition.Coin_03);
        giftRegion[3] = getResource(ResourcePosition.Coin_04);

        coinAnimation = new Animation(1f, giftRegion);
        coinAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void crashedByPlayer() {
        super.crashedByPlayer();
        sound = manager.get(ResourceName.Coin);
        sound.play();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getBricksType()==BricksType.COIN){
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = new TextureRegion((TextureRegion) coinAnimation.getKeyFrame(stateTime));
            setRegion(currentFrame);
        }
        super.draw(batch, parentAlpha);
    }
}
