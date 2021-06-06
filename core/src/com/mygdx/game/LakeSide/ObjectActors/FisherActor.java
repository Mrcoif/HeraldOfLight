package com.mygdx.game.LakeSide.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.LakeSide.ObjectActors.RodActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class FisherActor extends Actor {
    private Texture[] start = new Texture[2];
    private Texture[] rod = new Texture[5];
    private Texture[] finish = new Texture[3];
    private int animationNum = 0;
    private Texture texture;
    private int time;

    public Rectangle boundary = new Rectangle(610, 330, 130, 140);

    public FisherActor() {
        for (int i = 0; i < start.length; i++) start[i] = new Texture(Gdx.files.internal("Lake/Fisher/start/" + i + ".png"));
        for (int i = 0; i < rod.length; i++) rod[i] = new Texture(Gdx.files.internal("Lake/Fisher/rod/" + i + ".png"));
        for (int i = 0; i < finish.length; i++) finish[i] = new Texture(Gdx.files.internal("Lake/Fisher/finish/" + i + ".png"));
        texture = start[0];
    }

    @Override
    public void act(float delta) {
        if(animationNum == 0) {
            boolean useButton = false;
            boolean overlap = false;
            Array<Actor> actors = getStage().getActors();
            Array.ArrayIterator<Actor> iterator1 = actors.iterator();
            while (iterator1.hasNext()) {
                Actor actor1 = iterator1.next();
                if (actor1 instanceof HeroActor) {
                    HeroActor heroActor = (HeroActor) actor1;
                    if (boundary.overlaps(heroActor.getBoundary())) {
                        overlap = true;
                    }
                }
            }
            if(overlap){
                Array.ArrayIterator<Actor> iterator2 = actors.iterator();
                while (iterator2.hasNext()) {
                    Actor actor2 = iterator2.next();
                    if (actor2 instanceof ButtonActor) {
                        ButtonActor buttonActor = (ButtonActor) actor2;
                        if (buttonActor.isTouched && buttonActor.buttonType.equals("use")) {
                            useButton = true;
                            animationNum = 1;
                        }
                    }
                }
            }
        }

        animation();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT - 100);
    }

    private void animation() {
        if (time < 10000) time++;

        if (animationNum == 0) {
            if (time == 120) {
                time = 0;
            }

            if (time % 60 == 0) {
                texture = start[time / 60];
            }
        }
        if (animationNum == 1) {
            if (time == 240) {
                animationNum = 2;
                getStage().addActor(new RodActor());
                Array<Actor> actors = getStage().getActors();
                Array.ArrayIterator<Actor> iterator1 = actors.iterator();
                while (iterator1.hasNext()) {
                    Actor actor1 = iterator1.next();
                    if (actor1 instanceof HeroActor) {
                        actor1.toFront();
                    }
                }
                time = 0;
            }

            if (time % 48 == 0) {
                texture = rod[time / 48];
            }
        }
        if (animationNum == 2) {
            if (time == 120) {
                time = 0;
            }

            if (time % 40 == 0) {
                texture = finish[time / 40];
            }
        }
    }
}
