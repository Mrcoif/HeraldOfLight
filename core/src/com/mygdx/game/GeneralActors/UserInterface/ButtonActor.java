package com.mygdx.game.GeneralActors.UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.MyGame.touchPos;

public class ButtonActor extends Actor {

    public boolean isTouched = false;
    public String buttonType;

    private boolean canBeTouched = false;
    private Texture texture;
    private int time;


    public ButtonActor(float x, float y, String image_name, String buttonType) {
        setX(x);
        setY(y);
        this.buttonType = buttonType;
        texture = new Texture(Gdx.files.internal(image_name));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
    }

    @Override
    public void act(float delta) {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof InventoryActor) {
                InventoryActor inventoryActor = (InventoryActor) actor;
                if (!inventoryActor.getStatus()) {
                    if (Gdx.input.isTouched() && canBeTouched) {
                        if (touchPos.x > getX() &&
                                touchPos.x < getX() + getWidth() &&
                                touchPos.y > getY() &&
                                touchPos.y < getY() + getHeight()
                        ) {
                            canBeTouched = false;
                            isTouched = true;
                            time = 0;
                        }
                    } else {
                        isTouched = false;
                    }
                }
            }
        }
        time++;
        if(time>30) canBeTouched = true;
    }


    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
