package com.mygdx.game.Room.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class RoomHeroActor extends HeroActor {

    private float floorY;
    private float floorHeight;

    public RoomHeroActor(float floorY, float floorHeight) {
        super();
        this.floorY = floorY;
        this.floorHeight = floorHeight;
        start.x = SCREEN_WIDTH -200;
        setX(start.x);
    }

    @Override
    public boolean clickCondition() {

        if (
                Gdx.input.isTouched() && touchPos.y > floorY && touchPos.y < floorY + floorHeight
        ) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean stopCondition() {
        if (getX() - getWidth()/2 - 1 < 0){
            setX(0 + getWidth()/2);
            return true;
        }
        return false;
    }
}
