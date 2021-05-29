package com.mygdx.game.GeneralActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureActor extends Actor {

    public Texture texture;

    public TextureActor(String textureName, float startX, float startY, float width, float height) {
        texture = new Texture(Gdx.files.internal(textureName));
        setX(startX);
        setY(startY);
        setWidth(width);
        setHeight(height);
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}