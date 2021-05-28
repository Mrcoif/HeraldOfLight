package com.mygdx.game.Bridge.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class LeftHeartPartActor extends Actor {

    private Texture texture;
    private Rectangle boundary;

    public LeftHeartPartActor() {
        texture = new Texture(Gdx.files.internal("Bridge/leftHeartPart.png"));
        setX(425-texture.getWidth()/2);
        setY(210);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        boundary = new Rectangle(400, 475, 100, 10);
    }

    @Override
    public void act(float delta) {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();

        boolean overlap = false;
        boolean buttonUse = false;
        boolean rodIsUsed = false;

        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor = iterator1.next();
            if (actor instanceof HeroActor) {
                HeroActor heroActor = (HeroActor) actor;
                if (boundary.overlaps(heroActor.getBoundary())) {
                    overlap = true;
                }
            }
        }

        if (overlap) {
            Array.ArrayIterator<Actor> iterator2 = actors.iterator();
            while (iterator2.hasNext()) {
                Actor actor = iterator2.next();
                if (actor instanceof ButtonActor) {
                    if (((ButtonActor) actor).buttonType.equals("use") && ((ButtonActor) actor).isTouched) {
                        buttonUse = true;
                    }
                }
            }
        }

        if (buttonUse) {
            Array.ArrayIterator<Actor> iterator2 = actors.iterator();
            while (iterator2.hasNext()) {
                Actor actor = iterator2.next();
                if (actor instanceof ItemActor) {
                    if (((ItemActor) actor).itemName.equals("rod") && ((ItemActor) actor).used) {
                        rodIsUsed = true;
                    }
                }
            }
        }

        if (rodIsUsed) {
            int i = 0;
            while (!stuff[i].itemName.equals("rod")) {
                i++;
            }
            stuff[i] = null;
            ItemActor heart = new ItemActor(this.texture, i, "leftHeartPart");
            stuff[i] = heart;
            stage.addActor(stuff[i]);
            remove();

            Array.ArrayIterator<Actor> iterator2 = actors.iterator();
            while (iterator2.hasNext()) {
                Actor actor = iterator2.next();
                if (actor instanceof ItemActor) {
                    if (((ItemActor) actor).itemName.equals("rod") && ((ItemActor) actor).used) {
                        ((ItemActor) actor).used = false;
                        ((ItemActor) actor).toBack();
                    }
                }
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
