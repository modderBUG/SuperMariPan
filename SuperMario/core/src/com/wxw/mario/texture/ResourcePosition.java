package com.wxw.mario.texture;

public enum ResourcePosition {
    Bricks(96, 24, 16, 16),
    IceGroundBricks(64, 152, 16, 16),
    GroundBricks(0, 56, 16, 16),


    GlassBricks_01(112, 192, 24, 16),
    GlassBricks_02(112, 80, 24, 16 ),

    Rewarded25(64, 24, 16, 16),
    Rewarded(80, 40, 16, 16),
    Gift(80, 24, 16, 16),
    GiftAcquired(64, 0, 8, 16),  // half

    Coin_01(48, 16, 16, 16 ),
    Coin_02(48, 64, 16, 16 ),
    Coin_03(48, 128, 16, 16),
    Coin_04(48, 176, 16, 16),


    // 背景

    Mountain_01(128, 118, 48, 18),
    Mountain_02(128, 6, 48, 18),
    Cloud(212, 118, 32, 24),

    CloudBricks_R(64, 56, 16, 16),
    CloudBricks_M(48, 32, 16, 16),
    CloudBricks_L(48, 48, 16, 16),



    ;

    private int x;
    private int y;
    private int w;
    private int h;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    ResourcePosition(int x, int y, int w, int h) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }
}
