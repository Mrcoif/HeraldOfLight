package com.mygdx.game.Room.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;

import static com.mygdx.game.BaseScreen.stuff;

public class DoorActor extends Actor {

    private Texture backgroundTexture;
    private Texture texture;
    private Rectangle boundary;
    final float startX = 700;
    final float startY = 300;
    final float width = 150;
    final float height = 350;
    private float doorWidth = width;
    private boolean doorIsOpening = false;
    private boolean doorIsOpen = false;

    public DoorActor() {
        setX(startX);
        setY(startY);
        backgroundTexture = new Texture(Gdx.files.internal("Room/Door/door_background.png"));
        texture = new Texture(Gdx.files.internal("Room/Door/door.png"));
        setWidth(width);
        setHeight(height);
        boundary = new Rectangle(getX(), getY(), texture.getWidth(), +texture.getHeight());
    }

    @Override
    public void act(float delta) {
        if (!doorIsOpen) {
            boolean overlap = false;
            boolean buttonUse = false;

            Array<Actor> actors = getStage().getActors();

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

            if(overlap){
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

            if(buttonUse) {
                Array.ArrayIterator<Actor> iterator3 = actors.iterator();
                while (iterator3.hasNext()) {
                    Actor actor = iterator3.next();
                    if (actor instanceof ItemActor) {
                        if (((ItemActor) actor).itemName.equals("key")) {
                            if(((ItemActor) actor).used) {
                                doorIsOpening = true;
                                for (int i = 0; stuff[i] != null; i++) {
                                    if (stuff[i].itemName.equals("key")) {
                                        stuff[i].toBack();
                                        stuff[i] = null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (doorIsOpening) {
            doorWidth = doorWidth - 1;
            if (doorWidth <= 70) {
                doorIsOpening = false;
                doorIsOpen = true;
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture, getX(), getY(), getWidth(), getHeight());
        batch.draw(texture, getX(), getY(), doorWidth, getHeight());
    }
}