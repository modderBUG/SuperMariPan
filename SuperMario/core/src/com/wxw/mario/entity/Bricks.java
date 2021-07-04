package com.wxw.mario.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxw.mario.ResourceName;
import com.wxw.mario.texture.ResourcePosition;

public class Bricks extends Actor {

    // 用于展示该演员的纹理区域
    private TextureRegion region;

    // 摩擦系数
    private Float friction = 0.05f;

    // 砖块种类
    private BricksType bricksType = BricksType.BRICKS;

    // 硬度
    private int hardness = 2;

    private boolean symmetry = false;

    AssetManager manager;
    Texture sceneryTexture;

    private long timer=Long.MAX_VALUE;
    private int bricksStatus = 0;
    private long keepTime = 5000;

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

    public Bricks(final AssetManager manager,TextureRegion region) {
        this(region);
        this.manager = manager;
        sceneryTexture = manager.get(ResourceName.SCAN);

    }

    public Bricks(final AssetManager manager) {
        super();
        this.manager = manager;
        sceneryTexture = manager.get(ResourceName.SCAN);
        this.region= new TextureRegion(sceneryTexture, 64, 152, 16, 16);
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(400, 480);
    }

    public Bricks(final AssetManager manager, float x, float y,BricksType bricksType,int hardness,Float friction) {
        this(manager);
        this.bricksType =bricksType;
        this.hardness =hardness;
        this.friction =friction;
    }

    public Bricks(final AssetManager manager,TextureRegion region, float x, float y) {
        super();
        this.manager=manager;
        sceneryTexture = manager.get(ResourceName.SCAN);
        this.region = region;
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(x, y);
    }



    public Bricks(final AssetManager manager,TextureRegion region, float x, float y,BricksType bricksType,int hardness,Float friction) {
        super();
        this.manager=manager;
        sceneryTexture = manager.get(ResourceName.SCAN);
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
        setSize(region.getRegionWidth(), region.getRegionHeight());
    }


    public void changeTexture(ResourcePosition rectangle){
        TextureRegion textureRegion = new TextureRegion(sceneryTexture,rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
        setRegion(textureRegion);
    }

    public void changeTexture(ResourcePosition rectangle,boolean symmetry){
        changeTexture(rectangle);
        this.symmetry = symmetry;
        bricksType =BricksType.GROUND ;
    }


    public void activateBrick(){
        if (bricksStatus<1 && bricksType==BricksType.REWARDED){
            bricksStatus=1;
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

        if (bricksStatus==1){

          long  end = System.currentTimeMillis();
          if (end-timer>5000){
              bricksType = BricksType.GIFT;
              bricksStatus=3;
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

        if (symmetry){
            TextureRegion a =new TextureRegion(region);
            a.flip(true,false);
            batch.draw(
                    a,
                    getX()+8, getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(),
                    getRotation()
            );
        }
    }
}
