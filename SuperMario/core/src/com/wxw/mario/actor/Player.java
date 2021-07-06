package com.wxw.mario.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.wxw.mario.brick.Bricks;
import com.wxw.mario.brick.BricksType;
import com.wxw.mario.texture.ResourceName;
import com.wxw.mario.texture.ResourcePosition;


public class Player extends Actor {

    final AssetManager manager;

    Rectangle playerRectangle;

    private Texture playerTexture;
    private TextureRegion region;
    private TextureRegion[][] splitAnim;


    public int jumpStatus = 0;
    public int jumpV0 = 3;

    public Float maxDropSpeed = -15f;
    public Float currentDropSpeed = -1f;
    public Float gravity = 0.2f;


    public Float maxSpeed = 3f;
    public Float currentSpeed = 0f;
    public Float accelerate = 0.1f;
    public Float f = 0.05f;
    public char direction = 'R';

    private long score = 0;

    private final TextureRegion jumpTextureRegion;
    private final TextureRegion squatTextureRegion;

    private Animation walkAnimation;
    private Animation runAnimation;
    private Animation jumpAnimation;

    float stateTime;

    Music sound;


    public void setWalkAnimation(TextureRegion[][] splitAnim) {
        TextureRegion[] walkRegion = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            walkRegion[i] = splitAnim[0][i + 1];
        }
        walkAnimation = new Animation(0.05f, walkRegion);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void setRunAnimation(TextureRegion[][] splitAnim) {
        TextureRegion[] runRegion = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            runRegion[i] = splitAnim[0][i + 16];
        }
        runAnimation = new Animation(0.05f, runRegion);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void setJumpAnimation(TextureRegion[][] splitAnim) {
        TextureRegion[] jumpRegion = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            jumpRegion[i] = splitAnim[0][i + 7];
        }
        jumpAnimation = new Animation(0.05f, jumpRegion);
        jumpAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }


    public Player(final AssetManager manager, ResourcePosition rect,Float x,Float y) {
        super();
        this.manager = manager;
        playerTexture = manager.get(ResourceName.PLAYER);
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        splitAnim = playerTextureRegion.split(17, 32);
        setWalkAnimation(splitAnim);
        setRunAnimation(splitAnim);
        setJumpAnimation(splitAnim);
        jumpTextureRegion = splitAnim[0][5];
        squatTextureRegion = splitAnim[0][6];
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        this.region = splitAnim[0][0];
        setSize(region.getRegionWidth(), region.getRegionHeight());
        playerRectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        this.setPosition(x,y);
    }

    public Player(final AssetManager manager, ResourcePosition rect) {
        super();
        this.manager = manager;
        playerTexture = manager.get(ResourceName.PLAYER);
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        splitAnim = playerTextureRegion.split(17, 32);
        setWalkAnimation(splitAnim);
        setRunAnimation(splitAnim);
        setJumpAnimation(splitAnim);
        jumpTextureRegion = splitAnim[0][5];
        squatTextureRegion = splitAnim[0][6];
        // 将演员的宽高设置为纹理区域的宽高（必须设置, 否则宽高默认都为 0, 绘制后看不到）
        this.region = splitAnim[0][0];
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setPosition(400, 400);
        playerRectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
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
                setRegion(getFlip(splitAnim[0][4]));
            return;
        }
        if (currentSpeed < -0.05) {
            currentSpeed = currentSpeed + f;
            setX(getX() + currentSpeed);
            if (currentSpeed < -0.5 && direction == 'R') {
                setRegion(splitAnim[0][4]);
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
        playMoveAnimation(false, "walk");
        direction = 'R';
        maxSpeed = 1f;
        setToCurrentSpeed();

    }

    public void runRight() {
        playMoveAnimation(false, "run");
        direction = 'R';
        maxSpeed = 3f;
        setToCurrentSpeed();

    }

    public void moveLeft() {
        playMoveAnimation(true, "walk");
        direction = 'L';
        maxSpeed = -1f;
        setToCurrentSpeed();

    }

    public void runLeft() {
        playMoveAnimation(true, "run");
        direction = 'L';
        maxSpeed = -3f;
        setToCurrentSpeed();
    }

    public void playMoveAnimation(boolean flip, String action) {
        stateTime += Gdx.graphics.getDeltaTime();
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧

        TextureRegion currentFrame = null;
        if (action.equals("walk"))
            currentFrame = new TextureRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime));
        if (action.equals("run"))
            currentFrame = new TextureRegion((TextureRegion) runAnimation.getKeyFrame(stateTime));
        if (action.equals("jump"))
            currentFrame = new TextureRegion((TextureRegion) jumpAnimation.getKeyFrame(stateTime));
        currentFrame.flip(flip, false);
        setRegion(currentFrame);

    }

    public void squat() {
        setRegion(squatTextureRegion);
    }

    public void jump() {

        if (jumpStatus == 1) return;
        currentDropSpeed = 4f;
        setRegion(jumpTextureRegion);
        jumpStatus = 1;
//        jumpHeight -= jumpHeight * 0.1;
//        if (jumpHeight <= 0) return;
//        setY(getY() + jumpHeight * 0.1f);
    }

    public void runJump() {
        if (jumpStatus == 1) return;
        currentDropSpeed = 6f;
        setRegion(jumpTextureRegion);
        jumpStatus = 1;
//        playMoveAnimation(direction == 'L',"jump");
//        jumpHeight -= jumpHeight * 0.1;
//        if (jumpHeight <= 0) return;
//        setY(getY() + jumpHeight * 0.12f);
    }

    public void handle() {

        // 蹲下
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            squat();
        }

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
            sound = manager.get(ResourceName.Jump);
            sound.play();
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

        if (currentDropSpeed == 0)
            return;

        if (currentDropSpeed > maxDropSpeed)
            currentDropSpeed = currentDropSpeed - gravity;

        float Y_ = getY() + currentDropSpeed;

        if (currentDropSpeed <= -2 || jumpStatus == 1)
            setRegion(splitAnim[0][5]);
        setY(Y_);
    }

    public void crash(Group stage) {
        Array<Actor> actors = stage.getChildren();
        for (int i = 0; i < actors.size; i++) {
            Bricks temp = (Bricks) actors.get(i);


            boolean crashed = crash(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight(), temp);

            if (crashed) {


                if (temp.getBricksType() == BricksType.GIFT) {
                    temp.crashedByPlayer();
                    score += 1;
                }

                if (temp.getBricksType() == BricksType.COIN) {
                    temp.crashedByPlayer();
                    stage.removeActor(temp);
                    score += 1;
                }

                if (temp.getBricksType() == BricksType.REWARDED) {
                    temp.crashedByPlayer();
                    score += 1;
                }

                if (temp.getBricksType() == BricksType.GROUND) {
                    temp.crashedByPlayer();
                }


                if (temp.getBricksType() == BricksType.BRICKS) {
                    temp.crashedByPlayer();
                    if (temp.getHardness() < 2)
                        stage.removeActor(temp);
                }


            }


        }
    }

    public boolean crash(float x, float y, float width, float height, Bricks temp) {

//        if ((getX() + getWidth() <= x || x + width <= getX() || getY() + getHeight() <= y || y + height < getY())) {
//            if (currentDropSpeed == 0) currentDropSpeed = -1f;
//            return false;
//        }

        Float friction = temp.getFriction();

        playerRectangle.set(getX(), getY(), getWidth(), getHeight());
        Rectangle bricks = new Rectangle(x, y, width, height);
        if (!bricks.overlaps(playerRectangle)) {
            if (currentDropSpeed == 0) currentDropSpeed = -1f;
            return false;
        }

        if (temp.getBricksType()==BricksType.COIN)
            return true;

        if (getY() < y) {   // target on left
            currentDropSpeed = 0f;
            if (getY() + getHeight() > y) setY(y - getHeight());
            return true;
        }
        if (getY() > y) {   // target on right
            if (getY() < y + height) setY(y + height);
            currentDropSpeed = 0f;
            jumpStatus = 0;
            f = friction;
        }

        return false;
    }


    public void drawAnimation(Batch batch) {
        super.draw(batch, 1);

    }

    public long getScore() {
        return score;
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
