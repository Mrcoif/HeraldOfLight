package com.mygdx.game.Сrossroad.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class CrossroadHeroActor extends HeroActor {

    private float floorY;
    private float floorHeight;
    private Rectangle leftSquare;
    private Rectangle rightSquare;
    private boolean XGo = true;

    public boolean leftUpScreen = false;
    public boolean rightUpScreen = false;

    public CrossroadHeroActor(float floorY, float floorHeight, Rectangle leftSquare, Rectangle rightSquare) {
        super();
        this.floorY = floorY;
        this.floorHeight = floorHeight;
        this.leftSquare = leftSquare;
        this.rightSquare = rightSquare;
        start.x = SCREEN_WIDTH - getWidth();
        setX(start.x);
    }

    @Override
    public boolean clickCondition() {
        if (
                Gdx.input.isTouched() && touchPos.y > floorY && touchPos.y < leftSquare.y + leftSquare.height &&
                        (
                                touchPos.y < floorY + floorHeight ||
                                (
                                        (getY()<floorY + floorHeight || (getX() < leftSquare.x + leftSquare.width && getY() > leftSquare.y)) &&
                                                        (touchPos.x > leftSquare.x && touchPos.x < leftSquare.x + leftSquare.width)
                                ) ||
                                (
                                        (getY()<floorY + floorHeight || (getX() > rightSquare.x && getY() > rightSquare.y)) &&
                                        touchPos.x > rightSquare.x && touchPos.x < rightSquare.x + rightSquare.width)
                        )
        ) {
            return true;
        }
        return false;
    }

    @Override
    protected void moveCondition() {
        if (getY() > floorY + floorHeight && YMoveVector &&
                (
                        (moveTo.x < SCREEN_WIDTH/2 && !(getX() > leftSquare.x && getX() < leftSquare.x + leftSquare.width)) ||
                                (moveTo.x > SCREEN_WIDTH/2 && !(getX() > rightSquare.x && getX() < rightSquare.x + rightSquare.width))
                )
        ) {
            setY(getY() - step.y);
        }


        if(!XGo){
            XGo = true;
            heroIsMoveX = true;
        }
        if (getY() > floorY + floorHeight && !YMoveVector &&
                (
                        (getX() < SCREEN_WIDTH/2 && (getX() - step.x < leftSquare.x || getX() + step.x > leftSquare.x + leftSquare.width)) ||
                                (getX() > SCREEN_WIDTH/2 && (getX() - step.x < rightSquare.x || getX() + step.x > rightSquare.x + rightSquare.width))
                )
        ) {
            heroIsMoveX = false;
            XGo = false;
        }
    }

    @Override
    protected void screenTick() {
        super.screenTick();
        if(getY()>leftSquare.y + leftSquare.height-30){
           if(getX()<SCREEN_WIDTH/2) {
            leftUpScreen = true;
           }
           if(getX()>=SCREEN_WIDTH/2){
               rightUpScreen = true;
           }
        }
    }
}
