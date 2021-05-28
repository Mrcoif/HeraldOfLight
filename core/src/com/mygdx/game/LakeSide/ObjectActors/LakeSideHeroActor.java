package com.mygdx.game.LakeSide.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class LakeSideHeroActor extends HeroActor {
    private float floorY;
    private float floorHeight;
    private boolean moveXCondition = true;
    private Rectangle bridge;
    
    public LakeSideHeroActor(float floorY, float floorHeight, Rectangle bridge) {
        super();
        this.floorY = floorY;
        this.floorHeight = floorHeight;
        this.bridge = bridge;
        start.x = getWidth() + 1;
        setX(start.x);
    }

    @Override
    public boolean clickCondition() {
        if (
                Gdx.input.isTouched() &&
                        (touchPos.y > floorY && touchPos.y < bridge.y + bridge.height) &&
                        (
                                (
                                        getY() < floorY + floorHeight &&
                                                (
                                                        (
                                                                touchPos.y > floorY + floorHeight &&
                                                                        touchPos.x > bridge.x && touchPos.x < bridge.x + bridge.width &&
                                                                        touchPos.y < 60 * Math.sin(0.01 * touchPos.x - 5) + 280
                                                        ) ||
                                                                (
                                                                        touchPos.y < floorY + floorHeight
                                                                )
                                                )
                                ) ||
                                        getY() > floorY + floorHeight &&
                                                (
                                                        touchPos.y > floorY + floorHeight &&
                                                                touchPos.x > bridge.x && touchPos.x < bridge.x + bridge.width &&
                                                                touchPos.y < 60 * Math.sin(0.01 * touchPos.x - 5) + 280
                                                ) ||
                                        (
                                                touchPos.y < floorY + floorHeight
                                        )
                        )
        ) {
            return true;
        }

        return false;
    }

    @Override
    protected void moveCondition() {
        if (getY() > floorY + floorHeight && (getX() < bridge.x || getX() > bridge.x + bridge.width) && YMoveVector) {
            setY(getY() - step.y);
        }

        if (
                getY() > floorY + floorHeight && XMoveVector && !YMoveVector &&
                        (getX() > bridge.x + bridge.width || getY() > 60 * Math.sin(0.01 * getX() - 5) + 280)
        ) {
            setX(getX() - step.x);

        }
        if (
                getY() > floorY + floorHeight && !XMoveVector && !YMoveVector &&
                        (getX() - step.x < bridge.x || getY() > 60 * Math.sin(0.01 * (getX() - step.x) - 5) + 280)
        ) {
            float a;
            float b;
            if(XMoveVector) a = bridge.x - getX();
            else a =  getX() - bridge.x;
            if(YMoveVector) b = bridge.y - getY();
            else b =  getY() - bridge.y;
            step.x = startStep;
            step.y = (step.x * (b)) / (a);
        }
    }

    @Override
    protected boolean stopCondition() {
        if (getX() + getWidth() + startStep +1 > SCREEN_WIDTH){
            setX(SCREEN_WIDTH- getWidth() - startStep-1);
            return true;
        }
        return false;
    }

}