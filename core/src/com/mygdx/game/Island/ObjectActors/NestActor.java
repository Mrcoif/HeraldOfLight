package com.mygdx.game.Island.ObjectActors;

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
import com.mygdx.game.Island.IslandScreen;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class NestActor extends Actor {
    private Texture texture;
    private Texture heartTexture;
    private Rectangle boundary;
    private IslandScreen islandScreen;

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("Island/heart.mp3"));

    private boolean stonesIsUsed = false;

    public NestActor(IslandScreen islandScreen) {
        this.islandScreen = islandScreen;
        texture = new Texture(Gdx.files.internal("Island/nest.png"));
        heartTexture = new Texture(Gdx.files.internal("Island/rightHeartPart.png"));
        setX(0);
        setY(100);
        setWidth(SCREEN_WIDTH);
        setHeight(SCREEN_HEIGHT - 100);
        boundary = new Rectangle(450, 290, 20, 20);
    }

    @Override
    public void act(float delta) {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();

        boolean buttonUse = false;
        boolean stonesIsUsedNow = false;

        Array.ArrayIterator<Actor> iterator2 = actors.iterator();
        while (iterator2.hasNext()) {
            Actor actor = iterator2.next();
            if (actor instanceof ButtonActor) {
                if (((ButtonActor) actor).buttonType.equals("use") && ((ButtonActor) actor).isTouched) {
                    buttonUse = true;
                }
            }
        }

        if (!stonesIsUsed) {
            if (buttonUse) {
                Array.ArrayIterator<Actor> iterator3 = actors.iterator();
                while (iterator3.hasNext()) {
                    Actor actor = iterator3.next();
                    if (actor instanceof ItemActor) {
                        if (((ItemActor) actor).itemName.equals("stones") && ((ItemActor) actor).used) {
                            stonesIsUsed = true;
                            stonesIsUsedNow = true;
                        }
                    }
                }
            }
        }

        if (stonesIsUsedNow) {
            for (int i = 0; i < 3; i++) {
                if (stuff[i] != null) {
                    if (stuff[i].itemName.equals("stones")) {
                        stuff[i] = null;
                    }
                }
            }
            islandScreen.startNestFalling();
            Array.ArrayIterator<Actor> iterator4 = actors.iterator();
            while (iterator4.hasNext()) {
                Actor actor = iterator4.next();
                if (actor instanceof ItemActor) {
                    if (((ItemActor) actor).itemName.equals("stones") && ((ItemActor) actor).used) {
                        ((ItemActor) actor).used = false;
                        ((ItemActor) actor).toBack();
                        texture = new Texture(Gdx.files.internal("Island/nest2.png"));
                        heartTexture = new Texture(Gdx.files.internal("Island/rightHeartPart2.png"));
                    }
                }
            }
        }

        if (stonesIsUsed) {

            boolean buttonTake = false;

            Array.ArrayIterator<Actor> iterator3 = actors.iterator();
            while (iterator3.hasNext()) {
                Actor actor = iterator3.next();
                if (actor instanceof ButtonActor) {
                    if (((ButtonActor) actor).buttonType.equals("take") && ((ButtonActor) actor).isTouched) {
                        buttonTake = true;
                    }
                }
            }
            if (buttonTake) {
                Array.ArrayIterator<Actor> iterator1 = actors.iterator();
                while (iterator1.hasNext()) {
                    Actor actor1 = iterator1.next();
                    if (actor1 instanceof HeroActor) {
                        HeroActor heroActor = (HeroActor) actor1;
                        if (boundary.overlaps(heroActor.getBoundary())) {
                            sound.play();
                            for (int i = 0; i < stuff.length; i++) {
                                if (stuff[i] == null) {
                                    ItemActor heart = new ItemActor(new Texture(Gdx.files.internal("Island/rightHeartPartItem.png")), i, "rightHeartPart",new Vector2(50,40), 55, 50);
                                    stuff[i] = heart;
                                    stage.addActor(stuff[i]);
                                    heartTexture = null;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if (heartTexture != null) batch.draw(heartTexture, getX(), getY(), getWidth(), getHeight());
    }
}
