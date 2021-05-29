package com.mygdx.game.LakeSide.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class PaddleActor extends Actor {



    private Texture texture;
    private Rectangle boundary;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("Lake/cane.mp3"));

    public PaddleActor() {
        setX(930);
        setY(190);
        texture = new Texture(Gdx.files.internal("Lake/paddleCane.png"));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        boundary = new Rectangle(getX(), getY(), texture.getWidth(), +texture.getHeight());
    }

    @Override
    public void act(float delta) {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();

        boolean overlap = false;
        boolean buttonUse = false;

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
                    if (((ButtonActor) actor).buttonType.equals("take") && ((ButtonActor) actor).isTouched) {
                        buttonUse = true;
                    }
                }
            }
        }

        if (buttonUse) {
            int i = 0;
            while (stuff[i] != null) {
                i++;
            }
            ItemActor heart = new ItemActor(this.texture, i, "paddle",new Vector2(25,35), 110, 75);
            stuff[i] = heart;
            stage.addActor(stuff[i]);

            sound.setVolume(sound.play(), 0.5f);
            remove();
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
