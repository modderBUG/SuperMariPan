package com.wxw.mario.texture;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bounds extends Actor {



    Bounds(Float x, Float y, Float width, Float height){
        setBounds(x,y,width,height);
    }

   public Float[] toBounds(){

       return new Float[]{getX(),getY(),getWidth(),getHeight()};
   }




}
