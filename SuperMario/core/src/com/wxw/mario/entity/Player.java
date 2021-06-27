package com.wxw.mario.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Player extends Actor {

    // 用于展示该演员的纹理区域
    private TextureRegion region;
    private TextureRegion[][] splitAnim;

    private int statusX = 0;

    public int jumpStatus = 0;
    public int jumpHeight = 50;
    public int dropStatus = 1;


    private TextureRegion run;
    private TextureRegion walk;
    private TextureRegion jump;
    private TextureRegion squat;

    private Animation walkAnimation;
    private Animation runAnimation;
    private Animation jumpAnimation;

    float stateTime;
//    private TextureRegion currentFrame;


    public Player(TextureRegion region) {
        super();
        this.region = region;


        splitAnim = region.split(17, 32);

        TextureRegion[] walkRegion = new TextureRegion[3];
        walkRegion[0] = splitAnim[0][1];
        walkRegion[1] = splitAnim[0][2];
        walkRegion[2] = splitAnim[0][3];

        walkAnimation = new Animation(0.05f, walkRegion);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
        
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        setSize(region.getRegionWidth(), region.getRegionHeight());

        setPosition(400, 480);

    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
        // 重新设置纹理区域后, 需要重新设置宽高
        setSize(this.region.getRegionWidth(), this.region.getRegionHeight());
    }

    /**
     * 演员的逻辑处理
     *
     * @param delta 表示从渲染上一帧开始到现在渲染当前帧的时间间隔, 或者称为渲染的 时间步 / 时间差。单位: 秒
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        // 现在这里一般没有什么逻辑要处理
    }

    public void drop() {
        if (dropStatus == 0){
            setRegion(splitAnim[0][0]);
            return;
        }


        float g = ((getY() + 1000) / getY());

        if (g > 15) g = 15;

        float Y_ = getY() - g;

        setRegion(splitAnim[0][5]);
        setY(Y_);
    }

    public void moveRight() {
        stateTime += Gdx.graphics.getDeltaTime();
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
        TextureRegion currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime);
        setRegion(currentFrame);
        setX(getX() + 1);




    }

    public void runRight() {
        stateTime += Gdx.graphics.getDeltaTime();
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
        TextureRegion currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime);
        setRegion(currentFrame);
        setX(getX() + 3);
    }

    public void moveLeft() {
        stateTime += Gdx.graphics.getDeltaTime();
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
        TextureRegion currentFrame = new TextureRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime));
        currentFrame.flip(true, false);
        setRegion(currentFrame);
        setX(getX() - 1);
    }

    public void runLeft() {
        stateTime += Gdx.graphics.getDeltaTime();
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
        TextureRegion currentFrame = new TextureRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime));
        currentFrame.flip(true, false);
        setRegion(currentFrame);
        setX(getX() - 3);
    }

    public void jump() {
        setRegion(splitAnim[0][5]);
        dropStatus = 1;
        jumpHeight -= jumpHeight * 0.1;
        if (jumpHeight <= 0) return;
        setY(getY() + jumpHeight * 0.1f);
    }

    public void runJump() {
        setRegion(splitAnim[0][5]);
        dropStatus = 1;
        jumpHeight -= jumpHeight * 0.1;
        if (jumpHeight <= 0) return;
        setY(getY() + jumpHeight * 0.12f);
    }

    public void crash(float x, float y, float width, float height) {

        if ((getX() + getWidth() < x || x + width < getX() || getY() + getHeight() < y || y + height < getY())) return;




//        if (getX()<x){   // target on left
//            if (getX()+getWidth()>x) setPosition(x-getWidth(),getY());
//        }
//        if (getX()>x){   // target on right
//            if (getX()<x+width) setPosition(x+width,getY());
//        }

        if (getY() < y) {   // target on left
            jumpHeight = 0;
            if (getY() + getHeight() > y) setPosition(getX(), y - getHeight());

        }
        if (getY() > y) {   // target on right
            jumpHeight = 400;
            dropStatus=0;
//            setRegion(splitAnim[0][0]);
            if (getY() < y + height) setPosition(getX(), y + height);

        }
    }

    /**
     * 绘制演员
     *
     * @param batch       纹理画布, 用于绘制演员封装的纹理。SpriteBatch 就是实现了 Batch 接口
     * @param parentAlpha 父节点的透明度, 处理透明度和演员的颜色属性有关, 稍微复杂, 这里暂时先不处理
     */
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
