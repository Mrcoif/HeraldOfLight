package com.mygdx.game.Island.ObjectActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GeneralActors.UserInterface.ButtonActor;
import com.mygdx.game.GeneralActors.UserInterface.ItemActor;
import com.mygdx.game.Island.IslandScreen;

import static com.mygdx.game.BaseScreen.stuff;
import static com.mygdx.game.MyGame.SCREEN_HEIGHT;
import static com.mygdx.game.MyGame.SCREEN_WIDTH;

public class NestActor extends Actor {
    private Texture texture;
    private Rectangle boundary;
    private IslandScreen islandScreen;

    public NestActor(IslandScreen islandScreen) {
        this.islandScreen = islandScreen;
        texture = new Texture(Gdx.files.internal("Island/nest.png"));
        setX(0);
        setY(100);
        setWidth(SCREEN_WIDTH);
        setHeight(SCREEN_HEIGHT - 100);
        boundary = new Rectangle(SCREEN_WIDTH / 2 - 50, 500, 100, 10);
    }

    @Override
    public void act(float delta) {
        Array<Actor> actors = getStage().getActors();

        boolean buttonUse = false;
        boolean stonesIsUsed = false;

        Array.ArrayIterator<Actor> iterator2 = actors.iterator();
        while (iterator2.hasNext()) {
            Actor actor = iterator2.next();
            if (actor instanceof ButtonActor) {
                if (((ButtonActor) actor).buttonType.equals("use") && ((ButtonActor) actor).isTouched) {
                    buttonUse = true;
                }
            }
        }

        if (buttonUse) {
            Array.ArrayIterator<Actor> iterator3 = actors.iterator();
            while (iterator3.hasNext()) {
                Actor actor = iterator3.next();
                if (actor instanceof ItemActor) {
                    if (((ItemActor) actor).itemName.equals("stones") && ((ItemActor) actor).used) {
                        stonesIsUsed = true;
                    }
                }
            }
        }

        if (stonesIsUsed) {
            for(int i = 0; i<3; i++){
                if(stuff[i] != null) {
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
                    }
                }
            }

        }
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
