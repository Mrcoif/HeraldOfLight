package com.mygdx.game.Bridge.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.HeroActor;

import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class BridgeHeroActor extends HeroActor {
    private float floorY;
    private boolean calibration = false;
    private BaseScreen baseScreen;
    private Rectangle bridge = new Rectangle(230, 250, 757, 310);
    private Rectangle right = new Rectangle(987, 180, 350, 110);
    private boolean firstGo = true;
    private int textTime;

    public BridgeHeroActor(float floorY, BaseScreen baseScreen) {
        super();
        this.baseScreen =baseScreen;
        this.floorY = floorY;
        start.x = SCREEN_WIDTH - getWidth();
        start.y = 200;
        setX(start.x);
        setY(start.y);
    }

    @Override
    public boolean clickCondition() {

        if (
                Gdx.input.isTouched() && touchPos.y > floorY &&
                        ((
                                touchPos.x > right.x &&
                                        touchPos.y > right.y &&
                                        touchPos.x < right.x + right.width &&
                                        touchPos.y < right.y + right.height
                        ) ||
                                (
                                        touchPos.x > bridge.x &&
                                                touchPos.y > bridge.y &&
                                                touchPos.x < bridge.x + bridge.width &&
                                                touchPos.y < bridge.y + bridge.height
                                )
                        )
        ) {
            return true;
        }

        return false;
    }

    @Override
    protected boolean stopCondition() {
        if (getX() - getWidth() / 2 - 1 < 0) {
            setX(getWidth() / 2);
            return true;
        }
        return false;
    }

    @Override
    protected void moveCondition() {

        if(firstGo && getX()< 700){
            textTime = 0;
            firstGo = false;
        }

        if (getX() > bridge.x && getX() < bridge.x + bridge.width) {
            heroIsMoveY = false;
            if (getY() > 10 + (-0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440) || getY() < (-0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440) - 10)
                calibration = true;
            if (calibration) {
                if (getY() < -0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440) {
                    if (getY() + startStep > -0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440) {
                        calibration = false;
                    }
                    setY(getY() + startStep);
                }
                if (getY() > -0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440) {
                    if (getY() + startStep < -0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440) {
                        calibration = false;
                    }
                    setY(getY() - startStep);
                }
            } else {
                setY((float) (-0.0008 * (getX() - 550) * (getX() - 550) + 0.0001 * getX() + 440));
            }
        }

        if(getX()>1000 && getY()+step.y>right.y + right.height){
            heroIsMoveY = false;
        }
        if(getX()> 1000 && getX()< 1020 && getY()> moveTo.y){
            heroIsMoveY = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (textTime < 300) {
            textTime += 1;
            baseScreen.myGame.getFont().draw(batch, "Can I fishing this heart under the bridge?", 50, 150);
        }
    }
}
