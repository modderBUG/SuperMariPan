package com.wxw.mario.brick;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxw.mario.texture.ResourceName;
import com.wxw.mario.texture.ResourcePosition;

public class Bricks extends Actor {

    // 用于展示该演员的纹理区域
    private TextureRegion region;

    // 摩擦系数
    private Float friction;

    // 砖块种类
    private BricksType bricksType;

    // 硬度
    private int hardness;

    // 是否需要对称绘制
    private boolean symmetry = false;

    AssetManager manager;
    Texture sceneryTexture;

    // 限时奖励砖块。
    private long timer = Long.MAX_VALUE;
    private int bricksStatus = 0;

    public Bricks(final AssetManager manager, float x, float y, BricksType bricksType, ResourcePosition rect, int hardness, Float friction) {
        super();
        this.manager = manager;
        sceneryTexture = manager.get(ResourceName.SCAN);
        this.bricksType = bricksType;
        this.hardness = hardness;
        this.friction = friction;
        changeTexture(rect);
        setPosition(x, y);
    }

    public Bricks(final AssetManager manager) {
        this(manager, 300f, 400f, BricksType.BRICKS, ResourcePosition.Bricks, 2, 0.05f);
    }

    public Bricks(final AssetManager manager, ResourcePosition rect) {
        this(manager, 300f, 400f, BricksType.BRICKS, rect, 2, 0.05f);
    }

    public Bricks(final AssetManager manager, float x, float y, BricksType bricksType, int hardness, Float friction) {
        this(manager, x, y, bricksType, ResourcePosition.Bricks, hardness, friction);
    }

    public Float getFriction() {
        return friction;
    }

    public void setFriction(Float friction) {
        this.friction = friction;
    }

    public BricksType getBricksType() {
        return bricksType;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public int getHardness() {
        return hardness;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        setSize(region.getRegionWidth(), region.getRegionHeight());
    }

    public void changeTexture(ResourcePosition rectangle) {
        TextureRegion textureRegion = new TextureRegion(sceneryTexture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        setRegion(textureRegion);
    }

    public void changeTexture(ResourcePosition rectangle, boolean symmetry) {
        changeTexture(rectangle);
        this.symmetry = symmetry;
        bricksType = BricksType.GROUND;
    }

    public void activateBrick() {
        if (bricksStatus < 1 && bricksType == BricksType.REWARDED) {
            bricksStatus = 1;
            changeTexture(ResourcePosition.Rewarded);
            timer = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 如果 region 为 null 或者 演员不可见, 则直接不绘制
        if (region == null || !isVisible()) {
            return;
        }

        if (bricksStatus == 1) {
            long end = System.currentTimeMillis();
            long keepTime = 5000;
            if (end - timer > keepTime) {
                bricksType = BricksType.GIFT;
                bricksStatus = 3;
            }
        }



		/* 这里选择一个较为复杂的绘制方法进行绘制
		batch.draw(
				region,
				x, y,
				originX, originY,
				width, height,
				scaleX, scaleY,
				rotation
		);*/

        /*
         * 绘制纹理区域
         * 将演员中的 位置(position, 即 X, Y 坐标), 缩放和旋转支点(origin), 宽高尺寸, 缩放比, 旋转角度 应用到绘制中,
         * 最终 batch 会将综合结果绘制到屏幕上
         */
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );

        if (symmetry) {
            TextureRegion a = new TextureRegion(region);
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
    }
}
