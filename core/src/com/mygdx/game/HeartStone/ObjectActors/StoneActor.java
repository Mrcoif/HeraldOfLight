package com.mygdx.game.HeartStone.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class StoneActor extends Actor {

    private Texture texture;
    private Rectangle boundary;
    private float startX = 0;
    private float startY = 100;
    private BaseScreen baseScreen;
    private int time = 180;
    private Texture leftHeartPartTexture;
    private Texture rightHeartPartTexture;

    public StoneActor(final BaseScreen baseScreen) {
        setX(startX);
        setY(startY);
        texture = new Texture(Gdx.files.internal("HeartStone/stone.png"));
        setWidth(SCREEN_WIDTH);
        setHeight(SCREEN_HEIGHT - 100);
        boundary = new Rectangle(455, 195, 325, 340);

        this.baseScreen = baseScreen;
    }

    @Override
    public void act(float delta) {
        boolean overlap = false;
        boolean useIsTouched = false;
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



        Array.ArrayIterator<Actor> iterator2 = actors.iterator();
        if (overlap) {
            while (iterator2.hasNext()) {
                Actor actor2 = iterator2.next();
                if (actor2 instanceof ButtonActor) {
                    ButtonActor buttonActor = (ButtonActor) actor2;
                    if (buttonActor.isTouched && buttonActor.buttonType.equals("use")) {
                        useIsTouched = true;
                    }

                    if (buttonActor.isTouched && buttonActor.buttonType.equals("explore")) {
                        time = 0;
                    }
                }
            }
        }
        if(useIsTouched){
            if(useIsTouched) {
                Array.ArrayIterator<Actor> iterator3 = actors.iterator();
                while (iterator3.hasNext()) {
                    Actor actor = iterator3.next();
                    if (actor instanceof ItemActor) {
                        if (((ItemActor) actor).itemName.equals("leftHeartPart")) {
                            if(((ItemActor) actor).used) {
                                for (int i = 0; stuff[i] != null; i++) {
                                    if (stuff[i].itemName.equals("leftHeartPart")) {
                                        leftHeartPartTexture = stuff[i].texture;
                                        stuff[i].toBack();
                                        stuff[i] = null;
                                    }
                                }
                            }
                        }
                        if (((ItemActor) actor).itemName.equals("rightHeartPart")) {
                            if(((ItemActor) actor).used) {
                                for (int i = 0; stuff[i] != null; i++) {
                                    if (stuff[i].itemName.equals("rightHeartPart")) {
                                        rightHeartPartTexture = stuff[i].texture;
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

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(time < 180) {
            time+=1;
            baseScreen.myGame.getFont().draw(batch,"It's stone with hole", 100, 400);
        }
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if(leftHeartPartTexture!=null) batch.draw(leftHeartPartTexture, getX(), getY(), getWidth(), getHeight());
        if(rightHeartPartTexture!=null) batch.draw(rightHeartPartTexture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.MAGENTA);
        shapes.rect(boundary.x, boundary.y, boundary.width, boundary.height);
        shapes.end();
    }
}
