package com.mygdx.game.GeneralActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GeneralActors.UserInterface.InventoryActor;

import org.jetbrains.annotations.NotNull;

import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;
import static java.lang.Math.sqrt;

public abstract class HeroActor extends Actor {

    public boolean heroIsMoveX = false;
    public boolean heroIsMoveY = false;

    public boolean rightScreen = false;
    public boolean leftScreen = false;

    protected boolean XMoveVector = false;
    protected boolean YMoveVector = false;

    protected Texture texture;
    protected Rectangle boundary;

    protected float startStep = 5;
    protected Vector2 step = new Vector2(5, 5);
    public Vector2 moveTo = new Vector2();
    public Vector2 start = new Vector2(40, 100);

    protected int time = 0;
    protected int animationStepTime = 15;

    private boolean XAnimation = false;

    public HeroActor() {

        setX(start.x);
        setY(start.y);

        this.texture = new Texture(Gdx.files.internal("General/Hero/hero_stand.png"));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());

        this.boundary = new Rectangle(getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }


    @Override
    public void act(float delta) {

        screenTick();

        Array<Actor> actors = getStage().getActors();
        for (Actor actor : actors) {
            if (actor instanceof InventoryActor) {
                if (!((InventoryActor) actor).getStatus()) {
                    if (clickCondition()) {
                        {

                            moveTo.x = touchPos.x;
                            moveTo.y = touchPos.y;

                            if (getX() + step.x < moveTo.x) {
                                heroIsMoveX = true;
                                XMoveVector = true;
                            } else if (getX() - step.x > moveTo.x) {
                                heroIsMoveX = true;
                                XMoveVector = false;
                            }

                            if (getY() + step.y > moveTo.y) {
                                heroIsMoveY = true;
                                YMoveVector = false;
                            } else if (getY() - step.y < moveTo.y) {
                                heroIsMoveY = true;
                                YMoveVector = true;
                            }

                            float a;
                            float b;
                            if (XMoveVector) a = moveTo.x - getX();
                            else a = getX() - moveTo.x;
                            if (YMoveVector) b = moveTo.y - getY();
                            else b = getY() - moveTo.y;

                            if (a > b) {
                                XAnimation = true;
                                step.x = startStep;
                                step.y = (step.x * (b)) / (a);
                            } else {
                                XAnimation = false;
                                step.y = startStep;
                                step.x = (step.y * (a)) / (b);
                            }
                        }
                    }
                }
            }
        }
        if (heroIsMoveX) {
            if (XMoveVector && getX() < moveTo.x) {
                setX(getX() + step.x);
            } else if (!XMoveVector && getX() > moveTo.x) {
                setX(getX() - step.x);
            } else {
                heroIsMoveX = false;
            }
        }
        if (heroIsMoveY) {
            if (YMoveVector && getY() < moveTo.y) {
                setY(getY() + step.y);
            } else if (!YMoveVector && getY() > moveTo.y) {
                setY(getY() - step.y);
            } else {
                heroIsMoveY = false;
            }
        }

        if (stopCondition()) {
            stopHero();
        }
        moveCondition();

        goAnimation();

        boundary.x = getX() - getWidth() / 2;
        boundary.y = getY();
    }

    public Rectangle getBoundary() {
        return boundary;
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    protected void screenTick() {
        if (getX() > SCREEN_WIDTH - getWidth()/4*3) {
            rightScreen = true;
        }
        if (getX() < 0 + getWidth()/4*3) {
            leftScreen = true;
        }
    }

    protected boolean clickCondition() {
        return false;
    }

    protected boolean stopCondition() {
        return false;
    }

    protected void moveCondition() {
    }

    public void stopHero() {
        heroIsMoveX = false;
        heroIsMoveY = false;
        texture = new Texture(Gdx.files.internal("General/Hero/hero_stand.png"));
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.MAGENTA);
        shapes.rect(boundary.x, boundary.y, boundary.width, boundary.height);
        shapes.end();
    }

    private void goAnimation() {
        if (time < 30) time++;
        if(time == 30) time = 0;

        if (heroIsMoveX || heroIsMoveY) {
            if(XAnimation && XMoveVector){
                if(time%8 == 0)texture = new Texture(Gdx.files.internal("General/Hero/goRight/" + time/8 +".png"));
            }
            if(XAnimation && !XMoveVector){
                if(time%8 == 0)texture = new Texture(Gdx.files.internal("General/Hero/goLeft/" + time/8 +".png"));
            }
            if(!XAnimation && YMoveVector){
                if(time%5 == 0)texture = new Texture(Gdx.files.internal("General/Hero/goUp/" + time/5 +".png"));
            }
            if(!XAnimation && !YMoveVector){
                if(time%6 == 0)texture = new Texture(Gdx.files.internal("General/Hero/goDown/" + time/6 +".png"));
            }
        } else {
            texture = new Texture(Gdx.files.internal("General/Hero/hero_stand.png"));
        }
    }
}