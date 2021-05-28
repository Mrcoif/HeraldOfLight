package com.mygdx.game.Island.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.touchPos;

public class IslandHeroActor extends HeroActor {

    private float floorY;
    private float floorHeight;

    public IslandHeroActor(float floorY, float floorHeight) {
        super();
        start.x = 570;
        start.y = 200;
        setX(start.x);
        setY(start.y);
        this.floorY = floorY;
        this.floorHeight = floorHeight;
    }

    @Override
    protected boolean clickCondition() {
        boolean out = false;

        if (
                Gdx.input.isTouched() &&
                        touchPos.y < 50 * Math.sin(0.005 * touchPos.x + 400) + 170 &&
                        touchPos.y > -50 * Math.sin(0.005 * touchPos.x + 400) + 190 &&
                        touchPos.x > 465 &&
                        touchPos.x < 1015
        ) {
            out = true;
        }

        return out;
    }
}
