package com.mygdx.game.LakeSide.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.HeroActor;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;

import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class PillarActor extends Actor {

    private Texture texture;
    private Texture grass;
    private Rectangle boundary;
    private Rectangle tpBoundary;
    private float startX = 0;
    private float startY = 100;

    public int id;
    public boolean islandScreen = false;

    public PillarActor(int id) {
        this.id = id;
        setX(startX);
        setY(startY);
        texture = new Texture(Gdx.files.internal("Lake/pillar_" + id + ".png"));
        setWidth(SCREEN_WIDTH);
        setHeight(SCREEN_HEIGHT - 100);
        tpBoundary = new Rectangle(566, 499, 200, 1);
        if (id == 1) {
            boundary = new Rectangle(566, 309, 19, 190);
        } else if(id == 2){
            boundary = new Rectangle(757, 309, 19, 190);
            grass = new Texture("Lake/grass.png");
        }
    }

    @Override
    public void act(float delta) {
        boolean next = false;
        Array<Actor> actors = getStage().getActors();
        Array.ArrayIterator<Actor> iterator1 = actors.iterator();
        while (iterator1.hasNext()) {
            Actor actor1 = iterator1.next();
            if (actor1 instanceof HeroActor) {
                HeroActor heroActor = (HeroActor) actor1;
                if (tpBoundary.overlaps(heroActor.getBoundary())) {
                    next = true;
                }
            }
        }

        Array.ArrayIterator<Actor> iterator2 = actors.iterator();
        if (next) {
            while (iterator2.hasNext()) {
                Actor actor2 = iterator2.next();
                if (actor2 instanceof ButtonActor) {
                    ButtonActor buttonActor = (ButtonActor) actor2;
                    if (buttonActor.isTouched && buttonActor.buttonType.equals("use")) {
                        if(id == 1){
                            System.out.println("left pillar");
                        }
                        if(id == 2){
                            System.out.println("right pillar");
                        }
                        islandScreen = true;
                    }
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if(id == 2) batch.draw(grass, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.MAGENTA);
        shapes.rect(boundary.x, boundary.y, boundary.width, boundary.height);
        shapes.rect(tpBoundary.x, tpBoundary.y, tpBoundary.width, tpBoundary.height);
        shapes.end();
    }
}
