package com.mygdx.game.LakeSide.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;
import static com.mygdx.game.MyGame.touchPos;

public class BoatActor extends Actor {

    private Texture texture;
    private Rectangle boundary;
    private float startX = 0;
    private float startY = 100;
    private int textTime = 10000;
    public boolean paddleFound = false;
    private int time = 10000;

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("Lake/paddle.mp3"));

    public boolean islandScreen = false;
    private BaseScreen baseScreen;

    public BoatActor(BaseScreen baseScreen) {
        this.baseScreen = baseScreen;
        setX(startX);
        setY(startY);
        texture = new Texture(Gdx.files.internal("Lake/paddleBoat.png"));
        setWidth(SCREEN_WIDTH);
        setHeight(SCREEN_HEIGHT - 100);
        boundary = new Rectangle(140, 230, 565-140, 380-230);
    }

    @Override
    public void act(float delta) {
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

        if(!paddleFound){
            boolean buttonUse = false;
            Array.ArrayIterator<Actor> iterator2 = actors.iterator();
            if (overlap) {
                while (iterator2.hasNext()) {
                    Actor actor2 = iterator2.next();
                    if (actor2 instanceof ButtonActor) {
                        ButtonActor buttonActor = (ButtonActor) actor2;
                        if (buttonActor.isTouched && buttonActor.buttonType.equals("use")) {
                            buttonUse = true;
                        }
                        if (buttonActor.isTouched && buttonActor.buttonType.equals("explore")) {
                            textTime = 0;
                        }
                    }
                }
            }

            Array.ArrayIterator<Actor> iterator3 = actors.iterator();
            if (buttonUse) {
                while (iterator3.hasNext()) {
                    Actor actor2 = iterator3.next();
                    if (actor2 instanceof ItemActor) {
                        ItemActor itemActor = (ItemActor) actor2;
                        if (itemActor.itemName.equals("paddle") && itemActor.used) {
                            paddleFound = true;
                            time = 0;
                            stuff[itemActor.place] = null;
                            itemActor.used = false;
                            itemActor.toBack();
                            sound.play();
                        }
                    }
                }
            }
        }

        if(time<60) time++;
        if(paddleFound && time==60) {
            Array.ArrayIterator<Actor> iterator2 = actors.iterator();
            if (overlap) {
                while (iterator2.hasNext()) {
                    Actor actor2 = iterator2.next();
                    if (actor2 instanceof ButtonActor) {
                        ButtonActor buttonActor = (ButtonActor) actor2;
                        if (buttonActor.isTouched && buttonActor.buttonType.equals("use")) {
                            islandScreen = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (textTime < 180) {
            textTime += 1;
            baseScreen.myGame.getFont().draw(batch, "Boat but no oars", 100, 600);
        }
        if(paddleFound) batch.draw(texture, 0, 100, SCREEN_WIDTH,SCREEN_HEIGHT-100);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.MAGENTA);
        shapes.rect(boundary.x, boundary.y, boundary.width, boundary.height);
        shapes.end();
    }
}
