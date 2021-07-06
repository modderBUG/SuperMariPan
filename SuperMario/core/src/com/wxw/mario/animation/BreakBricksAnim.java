package com.wxw.mario.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.wxw.mario.brick.Bricks;
import com.wxw.mario.brick.BricksType;
import com.wxw.mario.texture.ResourcePosition;
import javafx.scene.shape.MoveTo;

public class BreakBricksAnim extends Bricks {
    public BreakBricksAnim(AssetManager manager, float x, float y, BricksType bricksType, ResourcePosition rect, int hardness, Float friction) {
        super(manager, x, y, bricksType, rect, hardness, friction);
    }

    public BreakBricksAnim(AssetManager manager, Float x, Float y) {
        super(manager);
        setPosition(x,y);
        changeTexture(ResourcePosition.Bricks);
        setBricksType(BricksType.COIN);
        setOrigin(getWidth() / 2, getHeight() / 2);

        RunnableAction runnable = Actions.run(new Runnable() {
            @Override
            public void run() {
                clear();
                remove();
            }
        });
        AfterAction afterAction = Actions.after(runnable);

        // 3. 并行动作, 包含 缩放 和 旋转 两个动作
        ParallelAction parallel = Actions.parallel(
                Actions.rotateBy(720.0F, 0.50F),
                Actions.moveBy(80, 50, 0.5F)
        );

        ParallelAction parallel2 = Actions.parallel(
                Actions.rotateBy(720.0F, 0.50F),
                Actions.moveBy(80, 0, 0.5F)
        );

        // 顺序动作, 包含 delay, moveTo, parallel
        SequenceAction sequenceAction = Actions.sequence(parallel,parallel2);


        addAction(sequenceAction);
        addAction(afterAction);

    }

    public BreakBricksAnim(AssetManager manager, ResourcePosition rect) {
        super(manager, rect);
    }

    public BreakBricksAnim(AssetManager manager, float x, float y, BricksType bricksType, int hardness, Float friction) {
        super(manager, x, y, bricksType, hardness, friction);
    }
}
