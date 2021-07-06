package com.wxw.mario.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.wxw.mario.brick.Bricks;
import com.wxw.mario.brick.BricksType;
import com.wxw.mario.texture.ResourcePosition;

public class CoinAnim extends Bricks {
    public CoinAnim(AssetManager manager, float x, float y, BricksType bricksType, ResourcePosition rect, int hardness, Float friction) {
        super(manager, x, y, bricksType, rect, hardness, friction);
    }

    public CoinAnim(AssetManager manager,Float x,Float y) {
        super(manager);
        setPosition(x,y);
        changeTexture(ResourcePosition.Coin_01);
        setBricksType(BricksType.COIN);
        MoveByAction action = Actions.moveBy(0, 50, 0.5F);
        RunnableAction runnable = Actions.run(new Runnable() {
            @Override
            public void run() {
                clear();
                remove();
            }
        });
        AfterAction afterAction = Actions.after(runnable);

        addAction(action);
        addAction(afterAction);

    }

    public CoinAnim(AssetManager manager, ResourcePosition rect) {
        super(manager, rect);
    }

    public CoinAnim(AssetManager manager, float x, float y, BricksType bricksType, int hardness, Float friction) {
        super(manager, x, y, bricksType, hardness, friction);
    }
}
