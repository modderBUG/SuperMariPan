package com.wxw.mario.brick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxw.mario.texture.ResourceName;
import com.wxw.mario.texture.ResourcePosition;

public class GiftBricks extends Bricks {

    Animation giftAnimation;
    float stateTime;

    // 限时奖励砖块。
    private long timer = Long.MAX_VALUE;
    private int bricksStatus = 0;

    @Override
    public void createAnimation(){
        TextureRegion[] giftRegion = new TextureRegion[2];
        giftRegion[0] = getResource(ResourcePosition.Gift);
        giftRegion[1] = getResource(ResourcePosition.Rewarded);

        giftAnimation = new Animation(1f, giftRegion);
        giftAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }



    public GiftBricks(AssetManager manager, float x, float y, BricksType bricksType, ResourcePosition rect, int hardness, Float friction) {
        super(manager, x, y, bricksType, rect, hardness, friction);
    }

    public GiftBricks(AssetManager manager) {
        super(manager);
    }

    public GiftBricks(AssetManager manager, ResourcePosition rect) {
        super(manager, rect);
    }

    public GiftBricks(AssetManager manager, float x, float y, BricksType bricksType, int hardness, Float friction) {
        super(manager, x, y, bricksType, hardness, friction);
    }


    @Override
    public void crashedByPlayer() {
        super.crashedByPlayer();
        if (bricksStatus < 1 && getBricksType() == BricksType.REWARDED) {
            bricksStatus = 1;
            changeTexture(ResourcePosition.Rewarded);
            timer = System.currentTimeMillis();
        }

        if (getBricksType()==BricksType.GIFT||getBricksType()==BricksType.REWARDED){
            sound = manager.get(ResourceName.Coin);
            sound.play();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getBricksType()==BricksType.GIFT||getBricksType()==BricksType.REWARDED){
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = new TextureRegion((TextureRegion) giftAnimation.getKeyFrame(stateTime));
            setRegion(currentFrame);
        }

        if (bricksStatus == 1) {
            long end = System.currentTimeMillis();
            long keepTime = 5000;
            if (end - timer > keepTime) {
                setBricksType(BricksType.GIFT);
                bricksStatus = 3;
            }
        }

        if (symmetry) {
            TextureRegion a = new TextureRegion(getRegion());
            a.flip(true, false);
            batch.draw(
                    a,
                    getX() + 8, getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(),
                    getRotation()
            );
        }

        super.draw(batch, parentAlpha);
    }
}
