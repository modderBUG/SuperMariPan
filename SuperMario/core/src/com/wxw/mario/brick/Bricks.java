package com.wxw.mario.brick;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    public boolean symmetry = false;

    AssetManager manager;
    Texture sceneryTexture;




    public Bricks(final AssetManager manager, float x, float y, BricksType bricksType, ResourcePosition rect, int hardness, Float friction) {
        super();
        this.manager = manager;
        sceneryTexture = manager.get(ResourceName.SCAN);
        this.bricksType = bricksType;
        this.hardness = hardness;
        this.friction = friction;
        changeTexture(rect);
        setPosition(x, y);
        createAnimation();

    }
    public void createAnimation(){

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

    public void crashedByPlayer(){
        if (bricksType==BricksType.GIFT){
            bricksType = BricksType.GROUND;
            changeTexture(ResourcePosition.GiftAcquired,true);
        }

    }

    public Float getFriction() {
        return friction;
    }

    public void setFriction(Float friction) {
        this.friction = friction;
    }


    public void setBricksType(BricksType bricksType) {
        this.bricksType = bricksType;
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

    public TextureRegion getRegion() {
        return region;
    }

    public void changeTexture(ResourcePosition rectangle) {
        TextureRegion textureRegion = new TextureRegion(sceneryTexture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        setRegion(textureRegion);
    }

    public void changeTexture(ResourcePosition rectangle, boolean symmetry) {
        changeTexture(rectangle);
        this.symmetry = symmetry;
    }


    public TextureRegion getResource(ResourcePosition rect){
        return new TextureRegion(sceneryTexture,rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 如果 region 为 null 或者 演员不可见, 则直接不绘制
        if (region == null || !isVisible()) {
            return;
        }

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
