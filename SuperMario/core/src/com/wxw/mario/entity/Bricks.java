package com.wxw.mario.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bricks extends Actor {

    // 用于展示该演员的纹理区域
    private TextureRegion region;

    // 摩擦系数
    private Float friction = 0.05f;

    // 砖块种类
    private BricksType bricksType = BricksType.BRICKS;

    // 硬度
    private int hardness = 2;



    public Float getFriction() {
        return friction;
    }

    public void setFriction(Float friction) {
        this.friction = friction;
    }

    public Bricks(TextureRegion region) {
        super();
        this.region = region;
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(400, 480);
    }

    public Bricks(TextureRegion region, float x, float y) {
        super();
        this.region = region;
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(x, y);
    }

    public Bricks(TextureRegion region, float x, float y,BricksType bricksType,int hardness,Float friction) {
        super();
        this.region = region;
        this.bricksType =bricksType;
        this.hardness =hardness;
        this.friction =friction;
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(x, y);
    }

    public BricksType getBricksType() {
        return bricksType;
    }



    public int getHardness() {
        return hardness;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 如果 region 为 null 或者 演员不可见, 则直接不绘制
        if (region == null || !isVisible()) {
            return;
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
    }
}
