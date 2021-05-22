package com.mygdx.game.HeartStone.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class HeartStoneHeroActor extends HeroActor {

    private float floorY;

    public HeartStoneHeroActor(float floorY) {
        super();
        this.floorY = floorY;
        start.x = SCREEN_WIDTH /2;
        setX(start.x);
    }

    @Override
    public boolean clickCondition() {

        if (
                Gdx.input.isTouched() && touchPos.y > floorY &&
                        (
                                touchPos.y < (float) (50 * Math.sin(0.0025 * touchPos.x) + 230)
                        )
        ) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean stopCondition() {
        if (getX() + getWidth()/2 + 1 > SCREEN_WIDTH){
            setX(SCREEN_WIDTH- getWidth()/2);
            return true;
        }
        return false;
    }
}