package com.wxw.mario.scenery;
/*
 * @author: wuxiaowei(wuxiaowei_a@aspirecn.com)
 * @create: 2021-07-05 14:16
 * @description: 风景区块，不能交互。
 */

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxw.mario.brick.BricksType;
import com.wxw.mario.texture.ResourceName;
import com.wxw.mario.texture.ResourcePosition;

public class Blocks extends Actor {
    final AssetManager manager;
    final Texture sceneryTexture;
    TextureRegion region;

    public Blocks(final AssetManager manager, ResourcePosition rect, float x, float y) {
        super();
        this.manager = manager;
        sceneryTexture = manager.get(ResourceName.SCAN);
        this.region = new TextureRegion(sceneryTexture,rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(x, y);
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        setSize(region.getRegionWidth(), region.getRegionHeight());
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