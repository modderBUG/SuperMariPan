package com.wxw.mario.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
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

    public Float maxDropSpeed = -15f;
    public Float currentDropSpeed = -1f;
    public Float gravity = 0.098f;


    public Float maxSpeed = 3f;
    public Float currentSpeed = 0f;
    public Float accelerate = 0.1f;
    public Float f = 0.05f;
    public char direction = 'R';


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
        setPosition(400, 400);

    }

    private void init() {
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


    // 走路摩擦
    public void computeCurrent() {
        if (currentSpeed > 0.05) {
            currentSpeed = currentSpeed - f;
            setX(getX() + currentSpeed);
            if (currentSpeed > 0.5 && direction == 'L')
                setRegion(splitAnim[0][4]);
            return;
        }
        if (currentSpeed < -0.05) {
            currentSpeed = currentSpeed + f;
            setX(getX() + currentSpeed);
            if (currentSpeed < -0.5 && direction == 'R') {
                setRegion(getFlip(splitAnim[0][4]));
            }
            return;
        }
        if (direction == 'R')
            setRegion(splitAnim[0][0]);
        if (direction == 'L')
            setRegion(getFlip(splitAnim[0][0]));
    }

    public TextureRegion getFlip(TextureRegion textureRegion) {
        TextureRegion flipTextureRegion = new TextureRegion(textureRegion);
        flipTextureRegion.flip(true, false);
        return flipTextureRegion;
    }

    public void setToCurrentSpeed() {
        if (direction == 'R') {
            if (maxSpeed > currentSpeed)
                currentSpeed = currentSpeed + accelerate;
            if (maxSpeed < currentSpeed)
                currentSpeed = currentSpeed - accelerate;
            setX(getX() + currentSpeed);
        }

        if (direction == 'L') {
            if (maxSpeed < currentSpeed)
                currentSpeed = currentSpeed - accelerate;
            if (maxSpeed > currentSpeed)
                currentSpeed = currentSpeed + accelerate;
            setX(getX() + currentSpeed);
        }
    }

    public void moveRight() {
        playMoveAnimation(false);
        direction = 'R';
        maxSpeed = 1f;
        setToCurrentSpeed();

    }

    public void runRight() {
        playMoveAnimation(false);
        direction = 'R';
        maxSpeed = 3f;
        setToCurrentSpeed();

    }

    public void moveLeft() {
        playMoveAnimation(true);
        direction = 'L';
        maxSpeed = -1f;
        setToCurrentSpeed();

    }

    public void runLeft() {
        playMoveAnimation(true);
        direction = 'L';
        maxSpeed = -3f;
        setToCurrentSpeed();
    }

    public void playMoveAnimation(boolean flip) {
        stateTime += Gdx.graphics.getDeltaTime();
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
        TextureRegion currentFrame = new TextureRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime));
        currentFrame.flip(flip, false);
        setRegion(currentFrame);

    }


    public void jump() {
        setRegion(splitAnim[0][5]);
        jumpHeight -= jumpHeight * 0.1;
        if (jumpHeight <= 0) return;
        setY(getY() + jumpHeight * 0.1f);
    }

    public void runJump() {
        setRegion(splitAnim[0][5]);
        jumpHeight -= jumpHeight * 0.1;
        if (jumpHeight <= 0) return;
        setY(getY() + jumpHeight * 0.12f);
    }

    public void handle() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                runRight();
            else
                moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                runLeft();
            else
                moveLeft();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                runJump();
            else
                jump();
        }


        computeCurrent();
        drop();

        if (getX() <= 20)
            init();

        if (getX() >= Gdx.graphics.getWidth())
            setX(0);

        if (getY() < 0)
            init();
    }

    public void drop() {

        if (currentDropSpeed==0)
            return;

        if (currentDropSpeed > maxDropSpeed)
            currentDropSpeed = currentDropSpeed - gravity;
        float Y_ = getY() + currentDropSpeed;

        if (currentDropSpeed<=-2)
            setRegion(splitAnim[0][5]);
        setY(Y_);
    }

    public void jumping() {

    }


    public void crash(Stage stage) {
        Array<Actor> actors = stage.getActors();
        for (int i = 0; i < actors.size; i++) {
            Bricks temp = (Bricks) actors.get(i);
            crash(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight(), temp.getFriction());
        }
    }

    public void crash(float x, float y, float width, float height, float friction) {
        if ((getX() + getWidth() <= x || x + width <= getX() || getY() + getHeight() <= y || y + height < getY())) {

            if (currentDropSpeed==0) currentDropSpeed=-1f;
            return;
        }

//            if (getX() < x) {   // target on left
//                if (getX() + getWidth() > x) setX(x - getWidth());
//            }
//            if (getX() > x) {   // target on right
//                if (getX() < x + width) setX(x + width);
//            }

        if (getY() < y) {   // target on left
            jumpHeight = 0;
            if (getY() + getHeight() > y) setY(y - getHeight());

        }
        if (getY() > y) {   // target on right
            jumpHeight = 400;
            if (getY() < y + height) setY(y + height);
            currentDropSpeed = 0f;
            f = friction;
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
